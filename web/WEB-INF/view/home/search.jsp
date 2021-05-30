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
    <title>搜索结果</title>
    <%@include file="/WEB-INF/view/common/common.jsp"%><!-- 包含公共引用的 js, css 等 -->

    <!-- 用户相关函数 -->
    <script src="${path }/js/user_function.js"></script>

    <script>
        $(document).ready(function () {
            //初始化分享列表
            getBookList({
                searchStr:'${searchStr}'
            });

            //获取搜索到的用户
            getSearchUsers('${searchStr}', 'mySearchList');
        });
    </script>
</head>
<body>

<%@include file="/WEB-INF/view/common/navbar.jsp"%><!-- 导航栏 -->

<div class="container">
    <div class="row">
        <div class="page_title">
            搜索结果
        </div>
        <div id="book-list" class="col-md-8 blog-main">
        </div>

        <%-- 搜索结果: 相关的人 --%>
        <div class="blog-sidebar col-md-3">
            <div class="search_pos">
                <%-- 搜索栏标题 --%>
                <div class="search_result_header">
                    <div class="header_tip">
                        相关用户
                    </div>
                </div>

                <%-- 搜索结果 --%>
                <div class="search_result_list" id="mySearchList">

                </div>
            </div>
        </div>

    </div>
    <hr/>
    <div id="pageNavigator"></div>
</div>
<%@include file="/WEB-INF/view/common/footer.jsp"%><!-- 页脚 -->

</body>
</html>
