<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/11/10
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>主页</title>
    <%@include file="/WEB-INF/view/common/common.jsp"%><!-- 包含公共引用的 js, css 等 -->

    <script>
        $(document).ready(function () {
            judgeUrl();

            //获取粉丝列表
            getFollowingList(${param.userId}, 'myList', 2, 12, 1, 'pageBar');
        });
    </script>
</head>
<body>
<%@include file="/WEB-INF/view/common/navbar.jsp"%><!-- 导航栏 -->

<div class="container" id="root-container">

    <div class="row follow_page">

        <div class="follow_container">

            <%@include file="/WEB-INF/view/common/follow_head.jsp" %>

            <!-- 关注列表 -->
            <div id="myList">

            </div>
            <!-- /followList -->
        </div>

        <hr/>
        <div id="pageBar"></div>
    </div>
</div>

<!-- 页脚 -->
<%@include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>