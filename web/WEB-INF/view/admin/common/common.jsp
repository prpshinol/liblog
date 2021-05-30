<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/12/12
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    pageContext.setAttribute("path", path);
%>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<link rel="icon" href="${path}/image/icon.png">

<!-- Bootstrap styles -->
<link href="${path}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Common styles -->
<link href="${path}/css/common.css" rel="stylesheet">
<link href="${path}/css/signin.css" rel="stylesheet">

<!-- jQuery -->
<script src="${path}/assets/jquery/jquery-3.1.1.min.js"></script>

<!-- Bootstrap js -->
<script src="${path}/assets/bootstrap/js/bootstrap.min.js"></script>

<!-- 分页工具类 -->
<script src="${path }/assets/pagination/js/Pagination.js"></script>
<link rel="stylesheet" href="${path }/assets/pagination/css/Pagination.css"/>

<%--通用js代码--%>
<script>
    /**
     * 搜索描红
     */
    function replace_red(content, searchStr) {
        if (searchStr != undefined && searchStr != null && searchStr != '') {
            return content.replace(new RegExp(searchStr, "gi"), "<font color='red'>" + searchStr + "</font>");
        }
        return content;
    }

    /**
     * 获得荐书记录列表
     * @param options {pageNo,pageSize,listType,searchStr,status,language}
     */
    function getAdminBookList(options) {
        if(options==undefined) {
            options = {};
        }
        $.ajax({
            url:"${path}/admin/getBookList.do",
            type:"post",
            data:{
                "pageNo":options.pageNo,
                "pageSize":options.pageSize,
                "listType":options.listType,
                "searchStr":options.searchStr,
                "status":options.status,
                "language":options.language
            },
            dataType:"json",
            success:function (json) {
                if(!json.status) {
                    return;
                }
                var html = '';
                if (json.data.books.length > 0) {
                    $.each(json.data.books, function (index, book) {
                        var btn;//按钮
                        var status_label;//状态显示
                        if(book.status==1) {
                            btn = '<button class="btn btn-default" type="button" id="btn-choose-'+book.id+'">同意荐书</button>';
                            status_label = '<label class="label-default">投票中</label>';
                        } else if(book.status==2) {
                            btn = '<button class="btn btn-default" type="button" id="btn-choose-'+book.id+'">新书入馆</button>&nbsp;&nbsp;' +
                                    '<button class="btn btn-default" type="button" id="btn-cancel-'+book.id+'">取消预购</button>';
                            status_label = '<label class="label-info">预购中</label>';
                        } else {
                            btn = '<button class="btn btn-default" type="button" id="btn-cancel-'+book.id+'">取消入馆</button>';
                            status_label = '<label class="label-success">已馆藏</label>';
                        }

                        html += '<hr/>' +
                                '<div class="row book-item">' +
                                '    <div class="col-sm-3 book-image">' +
                                '        <img src="' + book.image + '" class="img-thumbnail">' +
                                '    </div>' +
                                '    <div class="col-sm-9 book-info">' +
                                '        <div class="row">' +
                                '            <h4><a href="' + book.url + '">' + replace_red(book.title, options.searchStr) + '</a></h4>' +
                                '        </div>' +
                                '        <div class="row">' +
                                '           <!--第一列-->' +
                                '           <div class="col-sm-6">' +
                                '              <div class="row">' +
                                '                  作者：' + replace_red(book.author, options.searchStr) +
                                '              </div>' +
                                '              <div class="row">' +
                                '                  出版社：' + book.publisher +
                                '              </div>' +
                                '              <div class="row">' +
                                '                  出版日期：' + book.pubdate +
                                '              </div>' +
                                '              <div class="row">' +
                                '                  ISBN：' + replace_red(book.isbn, options.searchStr) +
                                '              </div>' +
                                '              <div class="row">' +
                                '                  语种：' + (book.language?'外文':'中文') +
                                '              </div>' +
                                '           </div>' +
                                '           <!--第二列-->' +
                                '           <div class="col-sm-6">' +
                                '              <div class="row">' +
                                '                  荐书时间：' + book.createTime +
                                '              </div>' +
                                '              <div class="row">' +
                                '                  投票数：' + book.voteCount +
                                '              </div>' +
                                '              <div class="row">' +
                                                    status_label +
                                '              </div>' +
                                '           </div>' +
                                '        </div>' +
                                '        <div class="row">' +
                                '           荐书理由：' +
                                '        </div>' +
                                '        <div class="row admin-book-reason">' +
                                            book.reason +
                                '        </div>' +
                                '        <div class="row">' +
                                '           <div class="">' +
                                                btn +
                                '           </div>' +
                                '        </div>' +
                                '    </div>' +
                                '</div>';
                    });
                    html += '<hr/>' +
                            '<div id="pageNavigator"></div>';
                } else {    //无数据
                    html += '暂无数据';
                }

                $("#book-list").html(html);

                //分页导航条
                var p = new Pagination({
                    containerId: 'pageNavigator',
                    page: options.pageNo?options.pageNo:1,
                    rows: options.pageSize?options.pageSize:5,
                    count: json.data.totalCount,
                    toPage: function (page) {
                        getAdminBookList({
                            pageNo:page,
                            pageSize:options.pageSize,
                            listType:options.listType,
                            searchStr:options.searchStr,
                            status:options.status
                        });
                    }
                });

                //注册按钮事件
                $.each(json.data.books, function (index, book) {
                    $("#btn-choose-" + book.id).click(function () {
                        var url = "${path}/admin/addBookStatus.do";
                        $.ajax({
                            url:url,
                            type:"post",
                            data:{"bookId":book.id},
                            dataType:"json",
                            success:function (json2) {
                                if(json2.status) {
                                    //更新显示
                                    getAdminBookList({
                                        pageNo:options.pageNo,
                                        pageSize:options.pageSize,
                                        listType:options.listType,
                                        searchStr:options.searchStr,
                                        status:options.status
                                    });
                                }
                            }
                        });
                    });
                    $("#btn-cancel-" + book.id).click(function () {
                        var url = "${path}/admin/reduceBookStatus.do";
                        $.ajax({
                            url:url,
                            type:"post",
                            data:{"bookId":book.id},
                            dataType:"json",
                            success:function (json2) {
                                if(json2.status) {
                                    //更新显示
                                    getAdminBookList({
                                        pageNo:options.pageNo,
                                        pageSize:options.pageSize,
                                        listType:options.listType,
                                        searchStr:options.searchStr,
                                        status:options.status
                                    });
                                }
                            }
                        });
                    });
                });
            }
        });
    }

</script>