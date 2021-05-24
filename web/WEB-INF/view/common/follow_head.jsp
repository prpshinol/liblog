<!-- 关注/粉丝列表头部 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="follow_head">
    <div class="head_card">
        <div class="head_avatar">
            <img src="${path}/${sessionScope.loginUser.imgPath}" id="userImg">
        </div>
        <div class="head_username">
            <%-- 如果是女性, 把 icon-iconfontiocnnan 替换为 icon-nv --%>
            <span id="username">${sessionScope.loginUser.username}</span> <i class="icon iconfont" id="userGender"></i>
        </div>
    </div>
    <div class="head_nav">
        <div class="nav_pos">
            <div class="nav_btn" >
                <a href="${path }/home/followingUI.do?userId=${param.userId}">
                    <span id="hisFollow">他的关注</span>
                </a>
            </div>
            <div class="nav_btn">
                <a href="${path }/home/fansUI.do?userId=${param.userId}">
                    <span id="hisFans">他的粉丝</span>
                </a>
            </div>
            <div class="nav_btn">
                <a href="${path }/home/userHomeUI.do?userId=${param.userId}">
                    <span>他的分享</span>
                </a>
            </div>
        </div>
    </div>
</div>
