<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/12/12
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我要荐书</title>
    <%@include file="/WEB-INF/view/common/common.jsp" %>
    <%--日期控件--%>
    <link href="${path}/assets/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script type="text/javascript" src="${path}/assets/datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="${path}/assets/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body>
<%--导航栏--%>
<%@include file="/WEB-INF/view/common/navbar.jsp" %>

<div class="container">
    <%--标题--%>
    <div class="page_title">
        我要荐书
    </div>

    <%--中心内容--%>
    <div class="row">
        <%--左侧--%>
        <div class="col-sm-8 blog-main">

            <form class="form-horizontal" action="${path}/book/save.do" method="post">
                <div class="form-group">
                    <label for="title" class="col-sm-2 control-label"></label>
                    <div class="col-sm-7">
                        <span class="book_error_msg">${msg}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="title" class="col-sm-2 control-label">* 书名：</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <button class="btn btn-default" type="button" id="btn-search-title">搜索网络资源</button>
                </div>

                <div class="form-group">
                    <label for="author" class="col-sm-2 control-label">* 作者：</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="author" name="author" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="publisher" class="col-sm-2 control-label">出版社：</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="publisher" name="publisher">
                    </div>
                </div>

                <div class="form-group">
                    <label for="pubdate" class="col-sm-2 control-label">出版年：</label>
                    <div class="col-sm-7">
                        <div class="input-group date form_date">
                            <input class="form-control" type="text" value="" name="pubdate" id="pubdate" readonly>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">语种：</label>
                    <div class="col-sm-7">
                        <label class="radio-inline">
                            <input type="radio" name="language" id="language1" value="0" checked> 中文
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="language" id="language2" value="1"> 外文
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="isbn" class="col-sm-2 control-label">* ISBN：</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="isbn" name="isbn" required>
                    </div>
                    <button class="btn btn-default" type="button" id="btn-search-isbn">搜索网络资源</button>
                </div>

                <div class="form-group">
                    <label for="reason" class="col-sm-2 control-label">推荐理由：</label>
                    <div class="col-sm-7">
                        <textarea class="form-control" rows="4" id="reason" name="reason"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">荐书</button>
                    </div>
                </div>
                <input type="hidden" name="image" value="${path}/image/book-icon.png">
                <input type="hidden" name="url">
            </form>

            <%--书籍列表--%>
            <div id="book-list"></div>

        </div><!-- /.blog-main -->
        <%--右侧栏--%>
        <div class="col-sm-3 blog-sidebar">
            <div class="sidebar-module sidebar-module-inset">
                <label>书本封面：</label>
                <div class="book-image">
                    <img src="${path}/image/book-icon.png" class="img-thumbnail" id="book-image">
                </div>
                <br/>
                <label>网络资源链接：</label><br/>
                <div id="favicon"></div>
            </div>
        </div><!-- /.blog-sidebar -->

    </div><!-- /.row -->

</div><!-- /.container -->

<%@include file="/WEB-INF/view/common/footer.jsp" %>

<script type="text/javascript">
    $(function () {
        //日期选择器
        $('.form_date').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            autoclose: 1,
            startView: 4,
            minView: 2,
            forceParse: 0
        });

        //搜索网络资源-书名
        $("#btn-search-title").click(function () {
            var title = $("#title").val();
            if (title != null && title != '') {
                $.getJSON("https://api.douban.com/v2/book/search?q=" + title + "&count=10&callback=?",
                        function (data) {
                            var html = '';
                            $.each(data.books, function (index, book) {
                                html += '<hr/>' +
                                        '<div class="row book-item">' +
                                        '    <div class="col-sm-3 book-image">' +
                                        '        <img src="' + book.image + '" class="img-thumbnail">' +
                                        '    </div>' +
                                        '    <div class="col-sm-8 book-info">' +
                                        '        <div class="row">' +
                                        '            <h4><a href="' + book.alt + '">' + book.title + '</a></h4>' +
                                        '        </div>' +
                                        '        <div class="row">' +
                                        '            作者：' + book.author +
                                        '        </div>' +
                                        '        <div class="row">' +
                                        '            出版社：' + book.publisher +
                                        '        </div>' +
                                        '        <div class="row">' +
                                        '            出版日期：' + book.pubdate +
                                        '        </div>' +
                                        '        <div class="row">' +
                                        '            ISBN：' + book.isbn13 +
                                        '        </div>' +
                                        '        <div class="row">' +
                                        '            <button class="btn btn-default" type="button" id="btn-choose-'+book.id+'">选择</button>' +
                                        '        </div>' +
                                        '    </div>' +
                                        '</div>';
                            });
                            html += '<hr/>';
                            $("#book-list").html(html);

                            //注册“选择”按钮事件
                            $.each(data.books, function (index, book) {
                                $("#btn-choose-" + book.id).click(function () {
                                    $("#title").val(book.title);
                                    $("#author").val(book.author);
                                    $("#publisher").val(book.publisher);
                                    $("#pubdate").val(book.pubdate);
                                    $("#isbn").val(book.isbn13);
                                    $("#book-image").attr("src", book.image);
                                    $("input[name='image']").val(book.image);
                                    $("input[name='url']").val(book.alt);
                                    $("#favicon").html('<a href="'+book.alt+'" style="cursor: pointer;text-decoration: none">&nbsp;<img src="${path}/image/favicon_douban.png"></a>');
                                });
                            });
                        }
                );
            }

        });

        //搜索网络资源-isbn
        $("#btn-search-isbn").click(function () {
            var isbn = $("#isbn").val();
            if (isbn != null && isbn != '') {
                $.getJSON("https://api.douban.com/v2/book/isbn/" + isbn + "?callback=?",
                        function (book) {
                            var html = '';
                            html += '<hr/>' +
                                    '<div class="row book-item">' +
                                    '    <div class="col-sm-3 book-image">' +
                                    '        <img src="' + book.image + '" class="img-thumbnail">' +
                                    '    </div>' +
                                    '    <div class="col-sm-8 book-info">' +
                                    '        <div class="row">' +
                                    '            <h4><a href="' + book.alt + '">' + book.title + '</a></h4>' +
                                    '        </div>' +
                                    '        <div class="row">' +
                                    '            作者：' + book.author +
                                    '        </div>' +
                                    '        <div class="row">' +
                                    '            出版社：' + book.publisher +
                                    '        </div>' +
                                    '        <div class="row">' +
                                    '            出版日期：' + book.pubdate +
                                    '        </div>' +
                                    '        <div class="row">' +
                                    '            ISBN：' + book.isbn13 +
                                    '        </div>' +
                                    '        <div class="row">' +
                                    '            <button class="btn btn-default" type="button" id="btn-choose-'+book.id+'">选择</button>' +
                                    '        </div>' +
                                    '    </div>' +
                                    '</div>' +
                                    '<hr/>';
                            $("#book-list").html(html);

                            //注册“选择”按钮事件
                            $("#btn-choose-" + book.id).click(function () {
                                $("#title").val(book.title);
                                $("#author").val(book.author);
                                $("#publisher").val(book.publisher);
                                $("#pubdate").val(book.pubdate);
                                $("#isbn").val(book.isbn13);
                                $("#book-image").attr("src", book.image);
                                $("input[name='image']").val(book.image);
                                $("input[name='url']").val(book.alt);
                                $("#favicon").html('<a href="'+book.alt+'" style="cursor: pointer;text-decoration: none">&nbsp;<img src="${path}/image/favicon_douban.png"></a>');
                            });
                        }
                );
            }

        });
    });
</script>
</body>
</html>
