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
        <!-- 导航栏标志 -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${path}/home/indexUI.do" id="nav-brand">
                <img src="${path}/image/icon.png" id="img-brand"> LiBlog</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${path}/home/indexUI.do"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
                <li><a href="${path}/home/rankingUI.do"><span class="glyphicon glyphicon-thumbs-up"></span> 投票榜</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-book"></span> 荐书列表
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${path}/home/newestUI.do">最新荐书</a></li>
                        <li><a href="${path}/home/toBuyUI.do">预购图书</a></li>
                        <li><a href="${path}/home/purchasedUI.do">已购图书</a></li>
                    </ul>
                </li>
                <li><a href="${path}/home/addUI.do"><span class="glyphicon glyphicon-edit"></span> 我要荐书</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search" action="${path}/home/searchUI.do">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="书名、作者、isbn、用户" name="searchStr" value="${param.searchStr}" required>
                </div>
                <button type="submit" class="btn btn-default" id="btn-search">搜索</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-user"></span> ${sessionScope.loginUser.username}
                        <img src="${path}/${sessionScope.loginUser.imgPath}" class="img-thumbnail">
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${path}/home/myCollectUI.do">我的收藏</a></li>
                        <li><a href="${path}/home/myVoteUI.do">我的投票</a></li>
                    </ul>
                </li>
                <li><a href="${path}/login/logout.do"><span class="glyphicon glyphicon-log-out"></span> 注销</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container -->
</nav>