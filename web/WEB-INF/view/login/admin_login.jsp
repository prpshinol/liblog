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
    <title>后台登录</title>

    <%@include file="/WEB-INF/view/admin/common/common.jsp"%>
    <link href="${path}/css/signin.css" rel="stylesheet">

</head>
<body>
<div class="container">

    <form class="form-signin" role="form" action="${path}/login/adminLogin.do" method="post">
        <h2 class="form-signin-subtitle-admin">图书馆荐书系统后台</h2>

        <!--错误提示-->
        <div class="error_msg">${msg==null?"<br/>":msg}</div>

        <input sign="username" name="username" type="text" class="form-control" placeholder="用户名" required autofocus>
        <input type="password" name="password" class="form-control" placeholder="密码" required>
        <div class="input-group">
            <input sign="captcha" name="code" type="text" class="form-control" placeholder="验证码" required="">
            <span type="captchaholder" class="input-group-addon">
            	<img height="38px" src="${path }/login/getCode.do" style="cursor:pointer;" id="codeImg"
                     title="点击切换验证码">
            </span>
        </div>
        <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>

</div> <!-- /container -->

<script>
    $(function () {
        //点击切换验证码
        $("#codeImg").click(function () {
            var baseSrc = "${path}/login/getCode.do";
            $(this).attr("src", baseSrc + "?r=" + Math.random());
        });
    });
</script>
</body>
</html>
