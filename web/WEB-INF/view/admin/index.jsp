<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/12/14
  Time: 2:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <%@include file="/WEB-INF/view/admin/common/common.jsp" %>
</head>
<body>
<%--导航栏--%>
<%@include file="/WEB-INF/view/admin/common/navbar.jsp" %>

<div class="container">
    <%--标题--%>
    <div>
        <h1>荐书列表</h1>
    </div>

    <%--中心内容--%>
    <div class="row">
        <%--左侧--%>
        <div class="col-sm-8">
            <%--书籍列表--%>
            <div id="book-list"></div>
        </div><!-- /.blog-main -->
        <%--右侧栏--%>
        <%@include file="/WEB-INF/view/admin/common/sidebar.jsp"%>

    </div><!-- /.row -->

</div><!-- /.container -->

<%@include file="/WEB-INF/view/admin/common/footer.jsp" %>
<script>
    $(function () {
        getAdminBookList({
            listType:1,
            status:1
        });
    });
</script>
</body>
</html>
