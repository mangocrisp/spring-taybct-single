package io.github.mangocrisp.spring.taybct.single.controller;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xijieyin
 * @createTime 2021/12/7 23:14
 * @description
 */
@Tag(name = "测试 hello")
@RestController
@AutoConfiguration
@Slf4j
@RequestMapping("demo")
@RequiredArgsConstructor
public class HelloController {

    final ISecurityUtil securityUtil;

    @Operation(summary = "hello")
    @GetMapping("hello")
    public String hello() {
        JSONObject payload = securityUtil.getLoginUser().getPayload();
        log.info(payload.toJSONString());
        return String.format("Hello World.%s", JSONObject.toJSONString(securityUtil.getLoginUser()));
    }

    @GetMapping("test")
    R<?> test() {
        return R.ok();
    }

}
