package io.github.mangocrisp.spring.taybct.auth.controller;

import cn.hutool.captcha.*;
import cn.hutool.core.util.IdUtil;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.CaptchaConstants;
import io.github.mangocrisp.spring.taybct.common.constants.CaptchaType;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码请求类
 *
 * @author xijieyin <br> 2022/8/4 20:15
 * @since 1.0.0
 */
@RestController
@AutoConfiguration
@RequiredArgsConstructor
@Slf4j
public class CaptchaController {

    final StringRedisTemplate redisTemplate;

    final ISysParamsObtainService sysParamsObtainService;

    /**
     * 检查是否需要验证码
     *
     * @return R
     * @author xijieyin <br> 2022/8/4 20:16
     * @since 1.0.0
     */
    @GetMapping("enableCaptcha")
    public R<?> enableCaptcha() {
        return R.data(Boolean.parseBoolean(sysParamsObtainService.get(CacheConstants.Params.ENABLE_CAPTCHA)));
    }

    /**
     * 获取验证码<br>
     * 会返回一个 uuid 和图片 base64，登录的时候输入看到 base64 上的文件和传入这个返回的 uuid 来确定验证码是否正确
     *
     * @return R
     * @author xijieyin <br> 2022/8/4 20:16
     * @since 1.0.0
     */
    @GetMapping("captcha")
    public R<?> captcha() {
        // 验证码类型
        String captchaType = sysParamsObtainService.get(CacheConstants.Params.CAPTCHA_TYPE);
        AbstractCaptcha captcha;
        if (captchaType != null) {
            if (captchaType.equalsIgnoreCase(CaptchaType.CIRCLE)) {
                captcha = new CircleCaptcha(130, 48, 5);
            } else if (captchaType.equalsIgnoreCase(CaptchaType.LINE)) {
                captcha = new LineCaptcha(130, 48, 5, 5);
            } else if (captchaType.equalsIgnoreCase(CaptchaType.SHEAR)) {
                captcha = new ShearCaptcha(130, 48, 5);
            } else {
                captcha = new GifCaptcha(130, 48, 5);
            }
        } else {
            captcha = new GifCaptcha(130, 48, 5);
        }
        String code = captcha.getCode();
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(CacheConstants.Captcha.KEY + uuid, code, 60 * 5, TimeUnit.SECONDS);
        Map<String, String> result = new HashMap<>();
        result.put(CaptchaConstants.CAPTCHA_UUID_KEY, uuid);
        result.put(CaptchaConstants.CAPTCHA_IMG_KEY, captcha.getImageBase64());
        log.debug("生成了验证码：" + code);
        log.debug("uuid：" + uuid);
        return R.data(result);
    }
}
