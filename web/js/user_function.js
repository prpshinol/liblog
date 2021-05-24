//用户相关的一些函数
/**
 * 获取个人简介
 * @param userId    用户id
 * @param containerId    存放用户信息的div容器的id
 * @returns
 */
function getProfile(userId, containerId) {
    $.ajax({
        url: path + "/user/getProfile.do",
        type: "post",
        dataType: "json",
        data: {
            userId: userId
        },
        success: function (data) {
            if (!data.status) {
                alert(data.msg);
                return;
            }

            var user = data.data;
            var $container = $("#" + containerId);
            $container.addClass('my_profile_container').addClass('profile_card');
            var html = '<div class="profile_avatar_container">';
            html += '<a href="' + path + '/home/userHomeUI.do?userId=' + user.id + '">';
            html += '<img src="' + path + '/' + user.imgPath + '" class="profile_avatar"></a></div>';
            html += '<div class="profile_info_container">';
            html += '<div class="profile_username">',
                html += '<a href="' + path + '/home/userHomeUI.do?userId=' + user.id + '">' + user.username + '</a>';
            if (user.status == 1) {
                html += '<span class="pinbi">(屏蔽中)</span>';
            }
            html += '</div>';
            html += '<ul class="profile_info">';
            html += '<li> <a href="' + path + '/home/followingUI.do?userId=' + userId + '"><strong id="followCount">' + user.followCount + '</strong><span>关注</span></a> </li>';
            html += '<li> <a href="' + path + '/home/fansUI.do?userId=' + userId + '"><strong id="fansCount">' + user.fansCount + '</strong><span>粉丝</span></a> </li>';
            html += '<li> <a href="' + path + '/home/userHomeUI.do?userId=' + user.id + '"><strong>' + user.bookCount + '</strong><span>分享</span></a> </li>';
            html += '</ul>';
            html += '</div>';
            $container.html(html);

            //判断是否显示关注/取消关注按钮
            if (!user.isMe) {
                createButton(user.isFollow, userId, containerId);
            }

            $('#titie_username').html(user.username);

        }
    });

    /**
     * 创建关注/取消关注按钮,同时为按钮绑定点击事件
     */
    function createButton(isFollow, userId, containerId) {
        var $btn = $('<button class="btn btn-default f-btn" userId="' + userId + '"></button>');
        if (!isFollow) {
            $btn.html("关注");
            $btn.click(function () {
                $.ajax({
                    url: path + "/follow/addFollow.do",
                    type: 'post',
                    dataType: 'json',
                    data: {
                        userId: userId
                    },
                    success: function (data) {
                        if (!data.status) {
                            alert(data.msg);
                            return;
                        }
                        $("button[userId='" + userId + "']").remove();
                        $("#" + containerId).append(createButton(true, userId, containerId));

                        //该用户显示的粉丝数+1
                        $("#fansCount").html(parseInt($("#fansCount").html()) + 1);

                        toastr.success("关注成功！");
                    }
                });
            });
        } else {
            $btn.html("取消关注");
            $btn.click(function () {
                $.ajax({
                    url: path + "/follow/deleteFollow.do",
                    type: 'post',
                    dataType: 'json',
                    data: {
                        userId: userId
                    },
                    success: function (data) {
                        if (!data.status) {
                            alert(data.msg);
                            return;
                        }
                        $("button[userId='" + userId + "']").remove();
                        $("#" + containerId).append(createButton(false, userId, containerId));

                        //该用户显示的粉丝数-1
                        $("#fansCount").html(parseInt($("#fansCount").html()) - 1);

                        toastr.success("取消关注成功！");
                    }
                });
            });
        }
        $("#" + containerId).append($btn);
    }
}


/**
 * 获取用户的粉丝列表
 * @param userId    用户id
 * @param containerId    容器id
 * @param type    1：个人主页右侧的样式 2:粉丝列表样式页面
 * @param limit 返回的数量限制,不限制请设为null
 * @param page 页号
 * @param paginationId 分页条容器的id
 */
function getFansList(userId, containerId, type, limit, page, paginationId) {
    if (limit == undefined) {
        limit = null;
    }
    if (page == undefined) {
        page = null;
    }
    $.ajax({
        url: path + '/follow/getFansList.do',
        type: "post",
        dataType: "json",
        data: {
            userId: userId,
            limit: limit,
            page: page
        },
        success: function (data) {
            if (!data.status) {
                alert(data.msg);
                return;
            }
            var user = data.data;
            var fansList = user.fansList;
            var $container = $("#" + containerId);
            if (type == 1) {
                $container.addClass('follower_container').addClass('profile_card');
            } else {
                $container.addClass('follow_list');
            }
            var html = '';
            if (type == 1) {
                html += '<div class="follower_tip">— ' + user.username + '的粉丝 —</div>';
                if (fansList.length == 0) {
                    html += '<p>他(她)还没有粉丝哦...</p>';
                    $container.html(html);
                    return;
                }
                html += '<div class="follower_avatar">';
                html += '<ul>';
                for (var i = 0; i < fansList.length; i++) {
                    html += '<li>';
                    html += '<a href="' + path + '/home/userHomeUI.do?userId=' + fansList[i].id + '" style="display:block"><img src="' + path + '/' + fansList[i].userImg + '" class="following_avatar"></a>';
                    html += '<a href="' + path + '/home/userHomeUI.do?userId=' + fansList[i].id + '"><span>' + fansList[i].username + '</span></a>';
                    html += '</li>';
                }
                html += '</ul>';
                html += '</div>';
                html += '<div class="follower_more">— <a href="' + path + '/home/fansUI.do?userId=' + userId + '">查看更多</a> —</div>';
                $container.html(html);
            } else if (type == 2) {

                //初始化头部的显示
                $("#username").html(user.username);
                $("#userImg").attr("src", path + '/' + user.imgPath);
                $("#userGender").addClass(user.gender == "男" ? 'icon-iconfontiocnnan' : 'icon-nv');
                //粉丝列表的样式
                if (fansList.length == 0) {
                    html += '<div class="no_data">他(她)还没有粉丝哦...</div>';
                    $container.html(html);
                    return;
                }
                for (var i = 0; i < fansList.length; i++) {

                    html += '<div class="follow_container follow_card">' +
                        '   <div class="follow_avatar">' +
                        '        <a href="' + path + '/home/userHomeUI.do?userId=' + fansList[i].id + '"><img' +
                        '               src="' + path + '/' + fansList[i].userImg + '" class="thumbnail"></a>' +
                        '   </div>' +
                        '    <div class="follow_info_container">' +
                        '        <div class="follow_user">' +
                        '            <a href="' + path + '/home/userHomeUI.do?userId=' + fansList[i].id + '"' +
                        '              title="' + fansList[i].username + '">' + fansList[i].username + '</a>' +
                        '            <i class="icon iconfont ' + (fansList[i].gender == '男' ? 'icon-iconfontiocnnan' : 'icon-nv') + '"></i>' +
                        '        </div>' +
                        '        <div class="follow_info">' +
                        '            <ul>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/followingUI.do?userId=' + fansList[i].id + '"><span>关注</span><span>' + fansList[i].followCount + '</span></a>' +
                        '                </li>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/fansUI.do?userId=' + fansList[i].id + '"><span>粉丝</span><span>' + fansList[i].fansCount + '</span></a>' +
                        '                </li>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/userHomeUI.do?userId=' + fansList[i].id + '">' +
                        '                        <span>分享</span><span>' + fansList[i].bookCount + '</span>' +
                        '                    </a>' +
                        '                </li>' +
                        '            </ul>' +
                        '        </div>' +
                        '    </div>' +
                        '' +
                        '</div>';
                }
                $container.html(html);
            }

            if (page != undefined && page != null && paginationId != undefined && paginationId != null) {
                var p = new Pagination({
                    containerId: paginationId,
                    page: page,
                    rows: limit,
                    count: user.count,
                    toPage: function (page) {
                        getFansList(userId, containerId, 2, limit, page, paginationId);
                    }
                });
            }

        }
    });
}


/**
 * 获取用户的关注列表
 * @param userId    用户id
 * @param containerId    容器id
 * @param limit 返回的数量限制,不限制请设为null
 * @param page 页号
 * @param paginationId 分页条容器的id
 */
function getFollowingList(userId, containerId, type, limit, page, paginationId) {
    if (limit == undefined) {
        limit = null;
    }
    if (page == undefined) {
        page = null
    }
    $.ajax({
        url: path + '/follow/getFollowingList.do',
        type: "post",
        dataType: "json",
        data: {
            userId: userId,
            limit: limit,
            page: page
        },
        success: function (data) {
            if (!data.status) {
                alert(data.msg);
                return;
            }
            var user = data.data;
            var followingList = user.followingList;
            var $container = $("#" + containerId);
            if (type == 1) {
                $container.addClass('following_container').addClass('profile_card');
            } else {
                $container.addClass('follow_list');
            }

            var html = '';
            if (type == 1) {
                var html = '<div class="following_tip">— ' + user.username + '关注的人 —</div>';
                if (followingList.length == 0) {
                    html += '<p>他(她)还没有关注任何人哦...</p>';
                    $container.html(html);
                    return;
                }
                html += '<div class="following_avatar">';
                html += '<ul>';
                for (var i = 0; i < followingList.length; i++) {
                    html += '<li>';
                    html += '<a href="' + path + '/home/userHomeUI.do?userId=' + followingList[i].id + '" style="display:block"><img src="' + path + '/' + followingList[i].userImg + '" class="following_avatar"></a>';
                    html += '<a href="' + path + '/home/userHomeUI.do?userId=' + followingList[i].id + '"><span>' + followingList[i].username + '</span></a>';
                    html += '</li>';
                }
                html += '</ul>';
                html += '</div>';
                html += '<div class="following_more">— <a href="' + path + '/home/followingUI.do?userId=' + userId + '">查看更多</a> —</div>';
                $container.html(html);
            } else if (type == 2) {
                //初始化头部的显示
                $("#username").html(user.username);
                $("#userImg").attr("src", path + '/' + user.imgPath);
                $("#userGender").addClass(user.gender == "男" ? 'icon-iconfontiocnnan' : 'icon-nv');
                //粉丝列表的样式
                if (followingList.length == 0) {
                    html += '<div class="no_data">他(她)还没有粉丝哦...</div>';
                    $container.html(html);
                    return;
                }
                for (var i = 0; i < followingList.length; i++) {

                    html += '<div class="follow_container follow_card">' +
                        '   <div class="follow_avatar">' +
                        '        <a href="' + path + '/home/userHomeUI.do?userId=' + followingList[i].id + '"><img' +
                        '               src="' + path + '/' + followingList[i].userImg + '" class="thumbnail"></a>' +
                        '   </div>' +
                        '    <div class="follow_info_container">' +
                        '        <div class="follow_user">' +
                        '            <a href="' + path + '/home/userHomeUI.do?userId=' + followingList[i].id + '"' +
                        '              title="' + followingList[i].username + '">' + followingList[i].username + '</a>' +
                        '            <i class="icon iconfont ' + (followingList[i].gender == '男' ? 'icon-iconfontiocnnan' : 'icon-nv') + '"></i>' +
                        '        </div>' +
                        '        <div class="follow_info">' +
                        '            <ul>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/followingUI.do?userId=' + followingList[i].id + '"><span>关注</span><span>' + followingList[i].followCount + '</span></a>' +
                        '                </li>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/fansUI.do?userId=' + followingList[i].id + '"><span>粉丝</span><span>' + followingList[i].fansCount + '</span></a>' +
                        '                </li>' +
                        '                <li>' +
                        '                    <a href="' + path + '/home/userHomeUI.do?userId=' + followingList[i].id + '">' +
                        '                        <span>分享</span><span>' + followingList[i].bookCount + '</span>' +
                        '                    </a>' +
                        '                </li>' +
                        '            </ul>' +
                        '        </div>' +
                        '    </div>' +
                        '' +
                        '</div>';
                }
                $container.html(html);
            }

            if (page != undefined && page != null && paginationId != undefined && paginationId != null) {
                var p = new Pagination({
                    containerId: paginationId,
                    page: page,
                    rows: limit,
                    count: user.count,
                    toPage: function (page) {
                        getFollowingList(userId, containerId, 2, limit, page, paginationId);
                    }
                });
            }
        }
    });
}

/**
 * 判断当前url是粉丝列表还是关注列表
 */
function judgeUrl() {
    if (window.location.href.indexOf("fansUI") > 0) {
        $("#hisFans").css({
            'border-bottom-color': '#0f88eb',
            'border-bottom-width': '3px',
            'border-bottom-style': 'solid'
        });
    } else if (window.location.href.indexOf("followingUI") > 0) {
        $("#hisFollow").css({
            'border-bottom-color': '#0f88eb',
            'border-bottom-width': '3px',
            'border-bottom-style': 'solid'
        });
    }
}

/**
 * 获取搜索的用户列表
 */
function getSearchUsers(condition, containerId) {
    $.ajax({
        url: path + "/user/getSearchUsers.do",
        type: 'post',
        dataType: 'json',
        data: {
            condition: condition
        },
        success: function (data) {
            if (!data.status) {
                alert(data.msg);
                return;
            }

            var $container = $("#" + containerId);
            var userList = data.data;
            if (userList.length == 0) {
                $container.html("搜索不到相关用户");
                return;
            }
            var html = '';
            $.each(userList, function (index, user) {
                //将包含condition的部分用户名描红
                var redUsername = user.username.replace(new RegExp(condition, "g"), '<font color="red">' + condition + '</font>');
                console.log(redUsername);
                html += '	<div class="result_user">' +
                    '        <div class="result_avatar">' +
                    '            <a href="' + path + '/home/userHomeUI.do?userId=' + user.id + '">' +
                    '				<img src="' + path + '/' + user.imgPath + '" class="thumbnail">' +
                    '			 </a>' +
                    '        </div>' +
                    '        <div class="result_username">' +
                    '            <a href="' + path + '/home/userHomeUI.do?userId=' + user.id + '"' +
                    '               title="' + user.username + '">' + redUsername + '</a>' +
                    '            <i class="icon iconfont ' + (user.gender == '男' ? 'icon-iconfontiocnnan' : 'icon-nv') + '"></i>' +
                    '        </div>' +
                    '    </div><!-- /用户信息 -->';
            });
            $container.html(html);

        }
    });
}