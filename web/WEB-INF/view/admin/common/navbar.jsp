<%--
  Created by IntelliJ IDEA.
  User: linzhi
  Date: 2016/12/12
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${path}/admin/indexUI.do">图书馆荐书系统后台</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left" role="search" action="${path}/admin/searchUI.do">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="书名、作者、ISBN" name="searchStr" value="${param.searchStr}" required>
                </div>
                <button type="submit" class="btn btn-default" id="btn-search">搜索</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="overdown"><span class="glyphicon glyphicon-user"></span> ${sessionScope.loginAdmin.username} </a>
                </li>
                <li><a href="${path}/login/adminLogout.do"><span class="glyphicon glyphicon-log-out"></span> 注销</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container -->
</nav>