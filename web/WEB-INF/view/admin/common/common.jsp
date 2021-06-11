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

<!--js函数-->
<script>
    //js函数要用到的常量
    var path = "${path}";
</script>
<script src="${path}/js/admin_book_function.js"></script>
