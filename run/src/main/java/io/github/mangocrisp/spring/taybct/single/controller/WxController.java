//package io.github.mangocrisp.spring.taybct.run.single.controller;
//
//import io.github.mangocrisp.spring.taybct.tool.wechat.controller.WechatController;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author xijieyin
// * @createTime 2022/6/24 8:55
// * @description
// */
//@Controller
//@AutoConfiguration
//@RequestMapping("wx")
//@Slf4j
//public class WxController extends WechatController {
//
//    @GetMapping("loginSuccess")
//    public String wxToken(Model model) {
//        model.addAttribute("loginSuccessPage", wechatConfig.getLoginSuccessPage());
//        return "wx-login-success";
//    }
//
//}
