package com.liblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 前台，转发到各种进入系统后的UI页面
 * Created by linzhi on 2016/12/12.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    /**
     * 首页
     */
    @RequestMapping("/indexUI")
    public String indexUI() {
        return "home/index";
    }
    /**
     * 投票榜
     * @return
     */
    @RequestMapping("/rankingUI")
    public String rankingUI(){
        return "home/ranking";
    }
    /**
     * 荐书历史
     */
    @RequestMapping("/newestUI")
    public String newestUI() {
        return "home/newest";
    }
    /**
     * 我要荐书
     */
    @RequestMapping("/addUI")
    public String addUI() {
        return "home/add";
    }
    /**
     * 搜索
     */
    @RequestMapping("/searchUI")
    public String searchUI(String searchStr, HttpServletRequest request) {
        //防止js中'${searchStr}'当为'content'conent'时出错
        request.setAttribute("searchStr", searchStr.replace("'", "\\'"));
        return "home/search";
    }
    /**
     * 我的荐书
     */
    @RequestMapping("/myCollectUI")
    public String myCollectUI() {
        return "home/my_collect";
    }
    /**
     * 我的投票
     */
    @RequestMapping("/myVoteUI")
    public String myVoteUI() {
        return "home/my_vote";
    }
    /**
     * 预购图书
     */
    @RequestMapping("/toBuyUI")
    public String toBuyUI() {
        return "home/to_buy";
    }
    /**
     * 已购图书
     */
    @RequestMapping("/purchasedUI")
    public String purchasedUI() {
        return "home/purchased";
    }
    /**
     * 用户的个人主页
     */
    @RequestMapping("/userHomeUI")
    public String userHomeUI(){
        return "home/user_home";
    }
    /**
     * 粉丝列表页面
     * @return
     */
    @RequestMapping("/fansUI")
    public String fansUI(){
        return "home/fans";
    }

    /**
     * 关注列表页面
     * @return
     */
    @RequestMapping("/followingUI")
    public String followingUI(){
        return "home/following";
    }

}
