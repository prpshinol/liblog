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
<!-- 图标 -->
<link href="${path}/assets/bootstrap/css/iconfont.css" rel="stylesheet">

<!-- Common styles -->
<link href="${path}/css/common.css" rel="stylesheet">
<!-- 导航栏样式 -->
<link href="${path}/css/navbar.css" rel="stylesheet">
<!-- 单条分享的样式 -->
<link href="${path}/css/share.css" rel="stylesheet">
<!-- index页面自定义样式 -->
<link href="${path}/css/index.css" rel="stylesheet">
<!-- 右边栏 -->
<link href="${path}/css/sidebar.css" rel="stylesheet">
<!-- 关注/粉丝列表样式 -->
<link href="${path}/css/follow.css" rel="stylesheet">
<!-- 点赞榜样式 -->
<link href="${path}/css/ranking.css" rel="stylesheet">
<!-- 搜索页样式 -->
<link href="${path}/css/searchPage.css" rel="stylesheet">

<!-- jQuery -->
<script src="${path}/assets/jquery/jquery-3.1.1.min.js"></script>

<!-- Bootstrap js -->
<script src="${path}/assets/bootstrap/js/bootstrap.min.js"></script>

<!-- 分页工具类 -->
<script src="${path }/assets/pagination/js/Pagination.js"></script>
<link rel="stylesheet" href="${path }/assets/pagination/css/Pagination.css"/>

<!-- 表情 -->
<script src="${path}/assets/qqFace/js/jquery.qqFace.js"></script>

<!-- toastr消息提示 -->
<link href="${path }/assets/toastr/toastr.css" rel="stylesheet"/>
<script src="${path }/assets/toastr/toastr.js"></script>

<!--js函数-->
<script>
    //js函数要用到的常量
    var path = "${path}";
    var loginUserId = "${sessionScope.loginUser.userId}";
    var loginUserName = "${sessionScope.loginUser.username}";
    var loginUserImgPath = "${sessionScope.loginUser.imgPath}";
</script>
<script src="${path}/js/book_function.js"></script>
<script src="${path}/js/user_function.js"></script>
