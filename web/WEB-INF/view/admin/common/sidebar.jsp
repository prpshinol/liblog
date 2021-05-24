<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/12/15
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--右侧栏--%>
<div class="col-sm-3 col-sm-offset-1 blog-sidebar">
    <h4><button class="btn btn-info" id="btn-hot">最热荐书</button></h4>
    <h4><button class="btn" id="btn-new">最新荐书</button></h4>
    <hr/>
    <h4><button class="btn" id="btn-all">全部图书</button></h4>
    <h4><button class="btn" id="btn-tobuy">已预购图书</button></h4>
    <h4><button class="btn" id="btn-buyed">已购入图书</button></h4>
    <hr/>
    <h4><button class="btn" id="btn-chinese">中文</button></h4>
    <h4><button class="btn" id="btn-foreigh">外文</button></h4>

</div><!-- /.blog-sidebar -->
<script>
    $(function () {
        $("#btn-hot").click(function () {
            getAdminBookList({
                listType:1,
                status:1
            });
            $("button").removeClass("btn-info");
            $("#btn-hot").addClass('btn-info');
        });
        $("#btn-new").click(function () {
            getAdminBookList({
                status:1
            });
            $("button").removeClass("btn-info");
            $("#btn-new").addClass('btn-info');
        });
        $("#btn-all").click(function () {
            getAdminBookList();
            $("button").removeClass("btn-info");
            $("#btn-all").addClass('btn-info');
        });
        $("#btn-tobuy").click(function () {
            getAdminBookList({
                status:2
            });
            $("button").removeClass("btn-info");
            $("#btn-tobuy").addClass('btn-info');
        });
        $("#btn-buyed").click(function () {
            getAdminBookList({
                status:3
            });
            $("button").removeClass("btn-info");
            $("#btn-buyed").addClass('btn-info');
        });
        $("#btn-chinese").click(function () {
            getAdminBookList({
                language:0
            });
            $("button").removeClass("btn-info");
            $("#btn-chinese").addClass('btn-info');
        });
        $("#btn-foreigh").click(function () {
            getAdminBookList({
                language:1
            });
            $("button").removeClass("btn-info");
            $("#btn-foreigh").addClass('btn-info');
        });
    });
</script>