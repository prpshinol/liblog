/**
 * Created by linzhi on 2017/3/12.
 */
/**
 * 搜索描红
 */
function replace_red(content, searchStr) {
    if (searchStr != undefined && searchStr != null && searchStr != '') {
        return content.replace(new RegExp(searchStr, "gi"), "<font color='red'>" + searchStr + "</font>");
    }
    return content;
}

function replace_em(str) {

    str = str.replace(/\</g, '&lt;');
    str = str.replace(/\>/g, '&gt;');
    str = str.replace(/\n/g, '<br/>');
    str = str.replace(/\[em_([0-9]*)\]/g, '<img src="'+path+'/assets/qqFace/arclist/$1.gif" border="0" />');
    return str;
}

/**
 * 获得荐书记录列表
 * @param options {pageNo,pageSize,listType,searchStr,userId,status,lastNDays}
 */
function getBookList(options) {
    if(options==undefined) {
        options = {};
    }
    $.ajax({
        url:path+"/book/getBookList.do",
        type:"post",
        data:{
            "pageNo":options.pageNo,
            "pageSize":options.pageSize,
            "listType":options.listType,
            "searchStr":options.searchStr,
            "userId":options.userId,
            "status":options.status,
            "lastNDays": options.lastNDays
        },
        dataType:"json",
        success:function (json) {
            if(!json.status) {
                return;
            }
            var html = '';
            if (json.data.books.length > 0) {
                $.each(json.data.books, function (index, book) {
                    //点赞榜，排名1~3的分别加上金银铜图标
                    var rankHtml = '';
                    if (options.listType == 1) {
                        if (index < 3 && (options.pageNo==null||options.pageNo==1)) {
                            if (index == 0) {
                                rankHtml =
                                    '    <div class="rank_gold">' +
                                    '        <i class="iconfont icon-rank-2"></i>' +
                                    '    </div>';
                            } else if (index == 1) {
                                rankHtml =
                                    '    <div class="rank_silver">' +
                                    '        <i class="iconfont icon-rank-2"></i>' +
                                    '    </div>';
                            } else if (index == 2) {
                                rankHtml =
                                    '    <div class="rank_coppery">' +
                                    '        <i class="iconfont icon-rank-2"></i>' +
                                    '    </div>';
                            }
                        }
                    }

                    //状态显示
                    var statusLabel;
                    if(book.status==1) {
                        statusLabel = '<label class="label-default">投票中</label>';
                    } else if(book.status==2) {
                        statusLabel = '<label class="label-info">预购中</label>';
                    } else {
                        statusLabel = '<label class="label-success">已馆藏</label>';
                    }

                    html +=
                        '<div class="share_container">' +
                        rankHtml +
                        '    <!-- 分享的上部分, 头像, 内容等 -->' +
                        '    <div class="share_main_container">';

                    html +=
                        '        <div class="share_layout">' +
                        '            <div class="share_avatar"> <!-- 头像 -->' +
                        '                <a href="'+path+'/home/userHomeUI.do?userId=' + book.userId + '"><img src="'+path+'/' + book.userImg + '" class="share_avatar"> </a>' +
                        '            </div>' +
                        '            <div class="share_main">' +
                        '                <div class="share_username">' +
                        '                    <a href="'+path+'/home/userHomeUI.do?userId=' + book.userId + '" class="share_username_link">' +
                        '                        ' + book.userName + '' +
                        '                    </a>' +
                        '                </div>' +
                        '                <div class="share_date">' +
                        '                    <div class="share_date_link">' +
                        '                        ' + book.createTime +
                        '                    </div>' +
                        '                </div>' +
                        '                <!-- 书籍信息 -->' +
                        '                <div class="row book-item">' +
                        '                    <div class="col-sm-3 book-image">' +
                        '                        <img src="' + book.image + '" class="img-thumbnail">' +
                        '                    </div>' +
                        '                    <div class="col-sm-9 book-info">' +
                        '                        <div class="row">' +
                        '                            <h4><a href="' + book.url + '">' + replace_red(book.title, options.searchStr) + '</a></h4>' +
                        '                        </div>' +
                        '                        <!--第一列-->' +
                        '                        <div class="col-sm-6">' +
                        '                           <div class="row">' +
                        '                               作者：' + replace_red(book.author, options.searchStr) +
                        '                           </div>' +
                        '                           <div class="row">' +
                        '                               出版社：' + book.publisher +
                        '                           </div>' +
                        '                           <div class="row">' +
                        '                               出版日期：' + book.pubdate +
                        '                           </div>' +
                        '                           <div class="row">' +
                        '                               ISBN：' + replace_red(book.isbn, options.searchStr) +
                        '                           </div>' +
                        '                           <div class="row">' +
                        '                               语言：' + (book.language?'外文':'中文') +
                        '                           </div>' +
                        '                        </div>';
                  html+='                        <!--第二列-->' +
                        '                        <div class="col-sm-6">' +
                        '                           <div class="row">' +
                        statusLabel+
                        '                           </div>' +
                        '                        </div>' +
                        '                    </div>' +
                        '                </div>'+
                        '                <div class="share_content">' +
                        '                    ' + book.reason +
                        '                </div>' +
                        '' +
                        '            </div>' +
                        '        </div>' +
                        '    </div>' +
                        '    <!-- 分享的下部分, 收藏/评论/点赞等 -->' +
                        '    <div class="share_opt_container">' +
                        '        <ul class="opt_line">' +
                        '            <li><a href="javascript:void(0);" id="collectBtn-' + book.id + '"><span class="opt_pos"><i class="icon iconfont icon-shoucang ' + (book.isCollect ? 'orange' : '') + '"></i> <span id="collectText-' + book.id + '">' + (book.isCollect ? '取消收藏' : '收藏') + '</span> <span id="collectCount-' + book.id + '">' + book.collectCount + '</span></span></a>' +
                        '            </li>' +
                        '            <li><a href="javascript:void(0);" id="commentBtn-' + book.id + '" class="show_comment"><span class="opt_pos"><i' +
                        '                    class="icon iconfont icon-groupcopy5"></i> 评论 <span id="commentCount-' + book.id + '">' + book.commentCount + '</span></span></a>' +
                        '                <span id="arrow-' + book.id + '" class="arrow hidden"></span>　<!-- 三角箭头, 点击评论时出现, 收起时隐藏. -->' +
                        '            </li>' +
                        '            <li><a href="javascript:void(0);" id="voteBtn-' + book.id + '"><span class="opt_pos"><span class="glyphicon glyphicon-thumbs-up ' + (book.isVote ? 'red' : '') + '"></span> <span id="likeText-' + book.id + '">' + (book.isVote ? '取消投票' : '投票') + '</span> <span id="voteCount-' + book.id + '">' + book.voteCount + '</span></span></a></li>' +
                        '        </ul>' +
                        '    </div>' +
                        '' +
                        '    <!-- 评论展开加载状态 -->' +
                        '    <div id="share_loader-' + book.id + '" class="share_loader hidden"> <!-- 添加 hidden 到 class 中时隐藏加载状态, 即在评论加载完成时添加 hidden -->' +
                        '        <div class="loader_container">' +
                        '            <i class="loading_img"></i><span class="loading_words"> 正在加载，请稍候...</span>' +
                        '        </div>' +
                        '    </div>' +
                        '' +
                        '    <!-- 评论展示 -->' +
                        '    <div id="commentArea-' + book.id + '"></div>' +
                        '</div>';

                });

                html += '<hr/>' +
                    '<div id="pageNavigator"></div>';
            } else {    //无数据
                html += '<div class="share_container"><div class="no_data">暂无相关记录</div></div>';
            }

            $("#book-list").html(html);

            //分页导航条
            var p = new Pagination({
                containerId: 'pageNavigator',
                page: options.pageNo?options.pageNo:1,
                rows: options.pageSize?options.pageSize:5,
                count: json.data.totalCount,
                toPage: function (page) {
                    getBookList({
                        pageNo:page,
                        pageSize:options.pageSize,
                        listType:options.listType,
                        searchStr:options.searchStr,
                        userId:options.userId,
                        status:options.status,
                        lastNDays: options.lastNDays
                    });
                }
            });

            //注册按钮事件
            $.each(json.data.books, function (index, book) {
                //投票
                $("#voteBtn-" + book.id).click(function () {
                    var url;
                    if(book.isVote) {
                        url = path+"/vote/disVoteBook.do";
                    } else {
                        url = path+"/vote/voteBook.do";
                    }
                    $.ajax({
                        url:url,
                        type:"post",
                        data:{"bookId":book.id},
                        dataType:"json",
                        success:function (json2) {
                            if(json2.status) {
                                //更新显示
                                getBookList({
                                    pageNo:options.pageNo,
                                    pageSize:options.pageSize,
                                    listType:options.listType,
                                    searchStr:options.searchStr,
                                    userId:options.userId,
                                    status:options.status,
                                    lastNDays: options.lastNDays
                                });
                            }
                        }
                    });
                });
                //收藏
                $("#collectBtn-" + book.id).click(function () {
                    var url;
                    if(book.isCollect) {
                        url = path+"/collect/disCollectBook.do";
                    } else {
                        url = path+"/collect/collectBook.do";
                    }
                    $.ajax({
                        url:url,
                        type:"post",
                        data:{"bookId":book.id},
                        dataType:"json",
                        success:function (json2) {
                            if(json2.status) {
                                //更新显示
                                getBookList({
                                    pageNo:options.pageNo,
                                    pageSize:options.pageSize,
                                    listType:options.listType,
                                    searchStr:options.searchStr,
                                    userId:options.userId,
                                    status:options.status,
                                    lastNDays: options.lastNDays
                                });
                            }
                        }
                    });
                });
                //评论
                $("#commentBtn-" + book.id).click(function () {
                    if ($("#arrow-" + book.id).hasClass("hidden")) {
                        //显示三角形
                        $("#arrow-" + book.id).removeClass("hidden");
                        //显示加载
                        $("#share_loader-" + book.id).removeClass("hidden");
                        //模拟加载
                        setTimeout(function () {
                            //展开
                            getCommentList(book.id);
                        }, 400);
                    } else {
                        //闭合
                        $("#arrow-" + book.id).addClass("hidden");
                        $("#commentArea-" + book.id).empty();
                    }
                });
            });

            //默认为登录用户
            var optionsUserId = options.userId?options.userId:loginUserId;

            //加载个人简介
            getProfile(optionsUserId, "myProfile");

            //加载个人粉丝
            getFansList(optionsUserId, 'myFansList', 1, 6);

            //加载关注列表
            getFollowingList(optionsUserId, 'myFollowing', 1, 6);
        }
    });

}

//获取评论列表
function getCommentList(bookId) {
    $.ajax({
        url: path+"/comment/getCommentList.do",
        type: "post",
        data: {
            "bookId": bookId
        },
        dataType: "json",
        async: false,    //展开与小三角同步
        complete: function () {
            $("#share_loader-" + bookId).addClass("hidden");
        },
        success: function (json) {
            var html = '';
            html += '' +
                '    <div class="comment_container">' +
                '        <!-- 评论展示区的输入 -->' +
                '        <div class="comment_layout">' +
                '            <!-- 头像 -->' +
                '            <div class="comment_my_avatar">' +
                '                <img src="'+path+'/'+loginUserImgPath+'" class="comment_avatar">'+
                '            </div>' +
                '' +
                '            <!-- 输入框操作区 -->' +
                '            <div class="comment_opt_container">' +
                '                <div><textarea rows="3" id="comment_content-' + bookId + '"></textarea></div>' +
                '                <div class="comment_footer">' +
                '                    <div class="btn_comment_face">' +
                '                        <a href="javascript:void(0)" class="sendarea_a emotion light_gray" id="btn_face">' +
                '                            <i class="icon iconfont icon-xiaolian" style="color: #ffbe16"></i></a>' +
                '                    </div>' +
                '                    <!-- 分享按钮 -->' +
                '                    <input id="uploadCommentBtn-' + bookId + '" type="button" value="评论" class="btn btn-send btn-sm"' +
                '                           style="display: block; float: right">' +
                '                </div>' +
                '            </div>' +
                '' +
                '            <!-- 评论列表 -->' +
                '            <div class="comment_list_container">';
            $.each(json.data, function (index, comment) {
                html +=
                    '                <div class="comment">' +
                    '                    <!-- 一级评论者头像 -->' +
                    '                    <div class="comment_user_avatar"><a href="'+path+'/home/userHomeUI.do?userId=' + comment.userId + '">' +
                    '                        <img src="' + path + '/' + comment.userImg + '"></a>' +
                    '                    </div>' +
                    '                    <div class="main_comment">' +
                    '                        <!-- 一级评论内容 -->' +
                    '                        <a href="'+path+'/home/userHomeUI.do?userId=' + comment.userId + '">' + comment.username + '</a> : ' + replace_em(comment.content) +
                    '                        <div class="comment_time">' +
                    '                            ' + comment.createTime + ' <a href="javascript:void(0);" id="replyBtn-' + comment.id + '">回复</a>' +
                    '                        </div>' +
                    '                        <div id="replyInput-' + comment.id + '"></div>' +
                    '                        <!-- 一级评论结束 -->' +
                    '                        <!-- 二级评论 -->' +
                    '                        <div class="sub_comment"> <!-- 每一条评论的二级评论列表为一个 ul, 二级评论的每一条回复为一个 li -->' +
                    '                            <ul>';
                $.each(comment.childs, function (ind, child) {
                    html +=
                        '                                <li> <!-- 一条二级评论 -->' +
                        '                                    <div class="sub_comment_item">' +
                        '                                        <div class="sub_item_avatar"> <!-- 子评论回复者的头像, 不显示被回复者的头像 -->' +
                        '                                            <a href="'+path+'/home/userHomeUI.do?userId=' + child.userId + '"><img' +
                        '                                                    src="' + path + '/' + child.userImg + '"></a>' +
                        '                                        </div>' +
                        '' +
                        '                                        <div class="sub_comment_content">' +
                        '                                            <a href="'+path+'/home/userHomeUI.do?userId=' + child.userId + '">' + child.username + '</a> <!-- 回复者的用户名 -->' +
                        '                                            回复 <a href="'+path+'/home/userHomeUI.do?userId=' + child.pUserId + '">' + child.pUsername + '</a> : ' + replace_em(child.content) +
                        '                                            <div class="sub_content_time"> <!-- 每一条回复都有时间以及后面的[回复]操作链接 -->' +
                        '                                                ' + child.createTime + ' <a href="javascript:void(0);" id="replyBtn-' + child.id + '">回复</a>' +
                        '                                            </div>' +
                        '                                            <div id="replyInput-' + child.id + '"></div>' +
                        '                                        </div>' +
                        '                                    </div>' +
                        '                                </li><!-- 一条二级回复结束 -->';
                });
                html +=
                    '                            </ul>' +
                    '                        </div>' +
                    '                        <!---->' +
                    '                    </div>' +
                    '                </div>';
            });
            html +=
                '            </div>' +
                '        </div>' +
                '    </div>' +
                '</div>';

            $("#commentArea-" + bookId).html(html);

            //表情
            $('.emotion').qqFace({
                assign: 'comment_content-' + bookId, //给输入框赋值
                path: path+'/assets/qqFace/arclist/'    //表情图片存放的路径
            });

            //注册发表评论按钮事件
            $("#uploadCommentBtn-" + bookId).click(function () {
                var $comment_content = $("#comment_content-" + bookId);
                if ($comment_content.val() == "" || $comment_content.val().length > 100) {
                    showMsg("错误提示", "内容不能为空，且长度不能超过100.");
                    return;
                }
                var url = path+"/comment/save.do";
                $.ajax({
                    url: url,
                    type: "post",
                    data: {
                        "bookId": bookId,
                        "content": $comment_content.val()
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.status == true) {
                            //清空textfield
                            $("#comment_content-" + bookId).val('');
                            //评论数+1
                            $commentCount = $("#commentCount-" + bookId);
                            $commentCount.text(parseInt($commentCount.text()) + 1);
                            //刷新评论列表
                            getCommentList(bookId);
                        }
                    },
                });
            });

            //准备回复组件
            $.each(json.data, function (index, comment) {
                //第一层
                prepareReply(bookId, comment);
                $.each(comment.childs, function (ind, child) {
                    //第二层
                    prepareReply(bookId, child);
                });
            });
        }
    });
}

//准备回复的组件
function prepareReply(bookId, comment) {
    //点击回复链接（出现回复输入框）
    $("#replyBtn-" + comment.id).click(function () {
        //再次点击清除输入框
        if ($("#replyInput-" + comment.id).html()) {
            //不为空（已有输入框）
            $("#replyInput-" + comment.id).empty();
            return;
        }
        var html =
            '            <!-- 输入框操作区 -->' +
            '            <div class="comment_opt_container">' +
            '                <div><textarea rows="2" id="reply_content-' + comment.id + '"></textarea></div>' +
            '                <div class="comment_footer">' +
            '                    <div class="btn_comment_face">' +
            '                        <a href="javascript:void(0)" class="sendarea_a emotion light_gray" id="btn_face">' +
            '                            <i class="icon iconfont icon-xiaolian" style="color: #ffbe16"></i></a>' +
            '                    </div>' +
            '                    <!-- 分享按钮 -->' +
            '                    <input id="uploadReplyBtn-' + comment.id + '" type="button" value="回复" class="btn btn-send btn-sm"' +
            '                           style="display: block; float: right">' +
            '                </div>' +
            '            </div>' +
            '';
        $("#replyInput-" + comment.id).html(html);
        //表情
        $('.emotion').qqFace({
            assign: 'reply_content-' + comment.id, //给输入框赋值
            path: path+'/assets/qqFace/arclist/'    //表情图片存放的路径
        });
        //点击回复按钮
        $("#uploadReplyBtn-" + comment.id).click(function () {
            var $reply_content = $("#reply_content-" + comment.id);
            if ($reply_content.val() == "" || $reply_content.val().length > 100) {
                showMsg("错误提示", "内容不能为空，且长度不能超过100.");
                return;
            }
            var url = path+"/comment/save.do";
            $.ajax({
                url: url,
                type: "post",
                data: {
                    "bookId": bookId,
                    "content": $reply_content.val(),
                    "parentId": comment.id
                },
                dataType: "json",
                success: function (json) {
                    if (json.status == true) {
                        //清除textfield
                        $("#replyInput-" + comment.id).empty();
                        //评论数+1
                        $commentCount = $("#commentCount-" + bookId);
                        $commentCount.text(parseInt($commentCount.text()) + 1);
                        //刷新评论列表
                        getCommentList(bookId);
                    }
                },
            });
        });
    });
}
