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
    <title>注册</title>

    <%@include file="/WEB-INF/view/common/common.jsp"%>

    <script src="${path}/assets/photoclip/iscroll-zoom.js"></script>
    <script src="${path}/assets/photoclip/hammer.js"></script>
    <script src="${path}/assets/photoclip/lrz.all.bundle.js"></script>
    <script src="${path}/assets/photoclip/PhotoClip.min.js"></script>

    <link href="${path}/assets/bootstrap/css/awesome-bootstrap-checkbox.css" rel="stylesheet">

    <!-- 自定义样式 -->
    <link href="${path}/css/signup.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <form class="form-signup" role="form" method="post" action="${path }/login/register.do">
        <h1 class="form-signup-logo"></h1>
        <div class="form-signup-title">
            <h1 class="form-signup-heading">LiBlog</h1>
            <h3 class="form-signup-subtitle">图书馆荐书平台</h3>
        </div>

        <div style="clear:both;"></div>

        <div class="error_msg">${msg }</div>

        <input sign="username" name="username" type="text"  class="form-control" placeholder="用户名" required="" autofocus="">
        <input name="password" type="password" class="form-control" placeholder="密码" required="">
        <input name="password2" type="password" class="form-control" placeholder="确认密码" required="">
        <div class="input-group">
            <input sign="captcha" name="code" type="text" class="form-control" placeholder="验证码" required="">
            <span type="captchaholder" class="input-group-addon">
                <img height="38px" src="${path }/login/getCode.do" style="cursor:pointer;" id="codeImg"
                     title="点击切换验证码">
            </span>
        </div>

        <!-- Button trigger modal -->

        <div class="upload-avatar">
            <div class="col-sm-3">
                <div class="radio radio-info">
                    <input type="radio" name="gender" id="radio1" value="男" checked="">
                    <label for="radio1">
                        男
                    </label>
                </div>
                <div class="radio radio-danger">
                    <input type="radio" name="gender" id="radio2" value="女">
                    <label for="radio2">
                        女
                    </label>
                </div>
            </div>
            <button type="button" class="btn btn-primary btn-lg btn-avatar" data-toggle="modal" data-target="#myModal">
                上传头像
            </button>
            <img src="${path}/image/head.jpg" class="img-thumbnail" id="finish-view">

            <!-- 图片隐藏域 -->
            <input type="hidden" value="" name="imgStr" id="imgStr"/>
        </div>

        <!-- Modal -->
        <div class="avatar">
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span class="sr-only">关闭</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel">上传头像</h4>
                        </div>
                        <div class="modal-body">

                            <div id="clipArea"></div>

                            <div class="upfilebox">
                                <a href="#" class="file">
                                    选择文件
                                    <input type="file" name="" id="file">
                                </a>
                                <button class="btn" type="button" id="clipBtn">截取</button>
                            </div>


                            <div id="photo-view"></div>
                            <script>
                                var clipFlag=false;
                                var imgUrl="";
                                var clipArea = new PhotoClip("#clipArea", {
                                    size: [140, 140],
                                    outputSize: [200, 200],
                                    file: "#file",
                                    view: "#photo-view",
                                    ok: "#clipBtn",
                                    loadStart: function () {
                                        console.log("照片读取中");
                                    },
                                    loadComplete: function () {
                                        console.log("照片读取完成");
                                    },
                                    clipFinish: function (dataURL) {
                                        clipFlag=true;
                                        imgUrl=dataURL;
                                        console.log(dataURL);
                                    }
                                });
                            </script>


                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" id="finish-btn">完成</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="div-signin">
            <a class="a-signin" href="${path }/login/loginUI.do">登录</a>
            <div class="clear">&nbsp;</div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
    </form>

</div> <!-- /container -->

<script>
    $(function(){
        //点击完成，关闭模态框，设置图片url
        getImageAndCloseDialog();

        //点击切换验证码
        changeCode("codeImg");
    });

    function getImageAndCloseDialog(){
        $("#finish-btn").click(function(){
            if(clipFlag){
                $("#finish-view").attr("src",imgUrl);
                $("#imgStr").val(imgUrl);
            }
            $("#myModal").modal('hide');
        });
    }

    function changeCode(id){
        $("#"+id).click(function () {
            var baseSrc = "${path}/login/getCode.do";
            $(this).attr("src", baseSrc + "?r=" + Math.random());
        });
    }
</script>
</body>
</html>
