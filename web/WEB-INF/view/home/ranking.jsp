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
    <title>投票榜</title>
    <%@include file="/WEB-INF/view/common/common.jsp"%><!-- 包含公共引用的 js, css 等 -->

    <!-- toastr消息提示 -->
    <link href="${path }/assets/toastr/toastr.css" rel="stylesheet"/>
    <script src="${path }/assets/toastr/toastr.js"></script>
    <!-- 点赞榜 -->
    <script src="${path}/js/ranking.js"></script>

    <script>
        $(document).ready(function () {
            //初始化分享列表
            getBookList({
                listType:1,
                status:1,
                lastNDays:1
            });
        });
    </script>
</head>
<body>

<%@include file="/WEB-INF/view/common/navbar.jsp"%><!-- 导航栏 -->

<div class="container">
    <div class="page_title">
        投票榜
    </div>
    <div class="col-md-12 rank_main">
        <div class="rank_container">

            <div class="rank_list_container">
                <span class="arrow_today"></span>
                <span class="arrow_week hidden"></span>
                <span class="arrow_total hidden"></span>
                <div class="rank_list_layout">
                    <div class="rank_list_title">
                        今天
                    </div>

                    <div id="book-list"></div>
                </div>
                <div style="clear: both"></div>
            </div>


            <div class="rank_side_container">
                <ul>
                    <li>
                        <a href="javascript:getBookList({
                            listType:1,
                            status:1,
                            lastNDays:1
                        });">
                            <div class="rank_today click_on">
                                今天
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:getBookList({
                            listType:1,
                            status:1,
                            lastNDays:7
                        });">
                            <div class="rank_week">
                                最近一周
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:getBookList({
                            listType:1,
                            status:1
                        });">
                            <div class="rank_total">
                                总榜单
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
            <div style="clear: both"></div>
        </div>
        <div style="clear: both"></div>
        <hr/>
    </div>
</div>
<%@include file="/WEB-INF/view/common/footer.jsp"%><!-- 页脚 -->
</body>
</html>