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
    <title>登录</title>

    <%@include file="/WEB-INF/view/common/common.jsp"%>

    <!-- 自定义样式 -->
    <link href="${path}/css/signin.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <form class="form-signin" role="form" method="post" action="${path }/login/login.do">
        <h1 class="form-signin-logo"></h1>
        <div class="form-signin-title">
            <h1 class="form-signin-heading">LiBlog</h1>
            <h3 class="form-signin-subtitle">图书馆荐书平台</h3>
        </div>

        <div style="clear:both;"></div>
        <div class="error_msg">${msg }</div>

        <input sign="username" name="username" type="text" class="form-control" placeholder="用户名" required=""
               autofocus="">
        <input name="password" type="password" class="form-control" placeholder="密码" required="">
        <div class="input-group">
            <input sign="captcha" name="code" type="text" class="form-control" placeholder="验证码" required="">
            <span type="captchaholder" class="input-group-addon">
            	<img height="38px" src="${path }/login/getCode.do" style="cursor:pointer;" id="codeImg"
                     title="点击切换验证码">
            </span>
        </div>
        <div class="checkbox">
            <label class="checkbox-remember">
                <input type="checkbox" name="remember" value="1"> 记住一周
            </label>
            <label class="checkbox-signup">
                <a class="a-signup" href="${path }/login/registerUI.do">注册</a>
            </label>
        </div>
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
