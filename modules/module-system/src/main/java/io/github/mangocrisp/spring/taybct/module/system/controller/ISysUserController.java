package io.github.mangocrisp.spring.taybct.module.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserOnline;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysUserSafeIn;
import io.github.mangocrisp.spring.taybct.api.system.dto.UserPassCheckDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysUserSafeOut;
import io.github.mangocrisp.spring.taybct.api.system.vo.UserInfoVO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.*;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.enums.EntityType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 系统用户相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:32
 * @see SysUser
 * @see ISysUserService
 * @since 1.0.0
 */
@Tag(name = "系统用户相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/user")
@ApiVersion
public interface ISysUserController extends BaseController<SysUser, ISysUserService> {

    @Operation(summary = "用户有效性校验")
    @PostMapping("passableCheck")
    default R<?> userPassableCheck(@RequestBody UserPassCheckDTO dto) {
        return R.data(getBaseService().userPassableCheck(dto));
    }

    /**
     * 分页查询用户
     *
     * @param params 所有参数
     * @return {@code R<IPage<SysUser>>}
     * @author xijieyin <br> 2022/8/5 21:32
     * @since 1.0.0
     */
    @SafeConvert(safeOut = SysUserSafeOut.class, resultType = EntityType.Page, ignoreOut = {"password"})
    @Override
    @WebLog
    default R<IPage<? extends SysUser>> page(@RequestParam(required = false) Map<String, Object> params) {
        return BaseController.super.page(params);
    }

    @SafeConvert(safeOut = SysUserSafeOut.class, resultType = EntityType.Collection, ignoreOut = {"password"})
    @Override
    @WebLog
    default R<List<? extends SysUser>> list(@RequestParam(required = false) Map<String, Object> params) {
        return BaseController.super.list(params);
    }

    /**
     * 新增用户
     *
     * @param domain 新增的用户对象
     * @return {@literal R<SysUser>}
     * @author xijieyin <br> q 15:41
     * @since 1.0.4
     */
    @Override
    @WebLog
    default R<? extends SysUser> add(@Valid @NotNull @RequestBody SysUser domain) {
        return BaseController.super.add(domain);
    }

    @Override
    default R<Collection<? extends SysUser>> saveBatch(@Valid @NotNull Collection<SysUser> domains) {
        throw new BaseException("用户不允许批量添加操作！");
    }

    /**
     * 更新用户
     *
     * @param domain 请求参数对象
     * @return {@code R<SysUser>}
     * @author xijieyin <br> 2022/8/5 21:33
     * @since 1.0.0
     */
    @Override
    @WebLog
    @SafeConvert(key = "domain", safeIn = SysUserSafeIn.class, ignoreIn = {"username", "password"})
    default R<? extends SysUser> updateAllField(@Valid @NotNull @RequestBody SysUser domain) {
        return BaseController.super.updateAllField(domain);
    }

    @Override
    default R<Collection<? extends SysUser>> updateAllFieldBatch(@Valid @NotNull Collection<SysUser> domains) {
        throw new BaseException("用户不允许批量更新操作！");
    }

    /**
     * 更新用户
     *
     * @param domain {@literal 请求实体，{key:value}，实体需要有主键 id}
     * @return 处理结果
     */
    @Override
    @WebLog
    @SafeConvert(key = "domain", safeIn = SysUserSafeIn.class, ignoreIn = {"username", "password"})
    default R<? extends SysUser> updateAssignField(@Valid @NotNull @RequestBody SysUser domain) {
        return BaseController.super.updateAssignField(domain);
    }

    @Override
    default R<Collection<? extends SysUser>> updateAssignFieldBatch(@Valid @NotNull Collection<SysUser> domains) {
        throw new BaseException("用户不允许批量更新操作！");
    }

    /**
     * 我的信息<br>
     * 从请求头里面获取到用户 token 然后解析成用户，然后去数据查询用户信息返回
     *
     * @return {@code R<UserInfoVO> }
     * @author xijieyin <br> 2022/8/5 21:33
     * @since 1.0.0
     */
    @Operation(summary = "我的信息")
    @GetMapping(value = "myInfo")
    R<UserInfoVO> myInfo();

    /**
     * 根据 id 修改个人信息 <br>
     * 这里只允许修改自己的信息，不能前端传入别人的 id 来修改个人信息
     *
     * @param domain 请求实体，{key:value}，实体需要有主键 id
     * @return {@code R<? extends T>}
     * @author xijieyin <br> 2022/8/4 18:39
     * @since 2.1.2
     */
    @Operation(summary = "根据 id 修改个人信息")
    @PatchMapping("myInfo")
    @WebLog
    @ApiLog(title = "根据 id 修改个人信息", description = "根据 id 修改个人信息", type = OperateType.UPDATE)
    @SafeConvert(key = "domain", safeIn = SysUserSafeIn.class, ignoreIn = {"id", "username", "password", "openid", "phone", "email"})
    R<SysUser> updateMyInfo(@Valid @NotNull @RequestBody SysUser domain);

    @Operation(summary = "变更个人修改密码的时间")
    @PatchMapping("passwdTime")
    @WebLog
    @ApiLog(title = "变更个人修改密码的时间", description = "变更个人修改密码的时间", type = OperateType.UPDATE)
    R<?> updatePasswdTime();

    /**
     * 根据用户 id 获取用户详细信息
     *
     * @param id 用户 id
     * @return {@code R<SysUser>}
     * @author xijieyin <br> 2022/8/5 21:35
     * @since 1.0.0
     */
    @SafeConvert(resultType = EntityType.Entity, safeOut = SysUser.class, ignoreOut = {"password"})
    @Override
    default R<? extends SysUser> detail(@NotNull @PathVariable Long id) {
        return BaseController.super.detail(id);
    }

    /**
     * 既可以用用户名，也可以用邮箱登录
     *
     * @param username 用户名和邮箱共用
     * @return {@code R<OAuth2UserDTO>}
     * @author xijieyin <br> 2022/8/5 21:35
     * @since 1.0.0
     */
    @Operation(summary = "根据用户名获取用户")
    @GetMapping("/username/{username}")
    default R<OAuth2UserDTO> getUserByUsername(@PathVariable(value = "username") String username) {
        return Optional.ofNullable(getBaseService().getUserByFiled("username", username))
                .map(R::data).orElseGet(() ->
                        Optional.ofNullable(getBaseService().getUserByFiled("email", username))
                                .map(R::data).orElse(R.fail("根据用户名获取用户，未找到用户！")));
    }

    /**
     * 根据手机号码获取用户
     *
     * @param phone 手机号码
     * @return {@code R<OAuth2UserDTO>}
     * @author xijieyin <br> 2022/8/5 21:36
     * @since 1.0.0
     */
    @Operation(summary = "根据手机号码获取用户")
    @GetMapping("/phone/{phone}")
    default R<OAuth2UserDTO> getUserByPhone(@PathVariable(value = "phone") String phone) {
        return R.data(getBaseService().getUserByFiled("phone", phone));
    }

    /**
     * 根据用户id获取用户
     *
     * @param userid 用户 id
     * @return {@code R<OAuth2UserDTO>}
     * @author xijieyin <br> 2022/8/5 21:37
     * @since 1.0.0
     */
    @Operation(summary = "根据用户id获取用户")
    @GetMapping("/userid/{userid}")
    default R<OAuth2UserDTO> getUserByUserid(@PathVariable(value = "userid") String userid) {
        return R.data(getBaseService().getUserByFiled("userid", userid));
    }

    /**
     * 根据 open id 获取用户
     *
     * @param openid 微信 open id
     * @return {@code R<OAuth2UserDTO>}
     * @author xijieyin <br> 2022/8/5 21:37
     * @since 1.0.0
     */
    @Operation(summary = "根据 open id 获取用户")
    @GetMapping("/openid/{openid}")
    default R<OAuth2UserDTO> getUserByOpenid(@PathVariable(value = "openid") String openid) {
        return R.data(getBaseService().getUserByFiled("openid", openid));
    }

    /**
     * 修改密码
     *
     * @param old 旧密码
     * @param now 新密码
     * @return R
     * @author xijieyin <br> 2022/8/5 21:38
     * @since 1.0.0
     */
    @Operation(summary = "修改密码")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.PATH),
            @Parameter(name = "old", description = "旧密码", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "now", description = "新密码", required = true, in = ParameterIn.QUERY)
    })
    @PatchMapping("/password")
    default R<?> modifyPasswd(@RequestParam String old, @RequestParam String now) {
        return getBaseService().modifyPasswd(old, now) ? R.ok() : R.fail();
    }

    /**
     * 重置密码，密码将会被重置为默认密码
     *
     * @param userId 用户 id
     * @return R
     * @author xijieyin <br> 2022/8/5 21:38
     * @since 1.0.0
     */
    @Operation(summary = "重置密码")
    @Parameters({
            @Parameter(name = "userId", description = "用户id [1,2,3...n]", required = true, in = ParameterIn.DEFAULT, example = "[1,2,3]")
    })
    @PatchMapping("/passwd")
    default R<?> resetPasswd(@Validated @NotNull @RequestBody Long[] userId) {
        return getBaseService().resetPasswd(userId) ? R.ok() : R.fail();
    }

    /**
     * 登录成功记录用户登录
     *
     * @param dto 用户登录成功后的信息 token 等
     */
    @PostMapping(value = "/login")
    R<?> login(@RequestBody JSONObject dto);

    /**
     * 登出操作，需要清空登录状态记录
     *
     * @param dto 用户登录成功后的信息 token 等
     */
    @PutMapping(value = "/logoff")
    R<?> logoff(@RequestBody JSONObject dto);

    /**
     * 获取在线列表
     *
     * @return {@code R<List<SysUserOnline>>}
     * @author xijieyin <br> 2022/8/5 21:40
     * @since 1.0.0
     */
    @Operation(summary = "获取在线列表")
    @GetMapping("/online/list")
    R<List<SysUserOnline>> onlineLinst();

    /**
     * 获取在线分页
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code R<IPage<SysUserOnline>>}
     * @author xijieyin <br> 2022/8/5 21:40
     * @since 1.0.0
     */
    @Operation(summary = "获取在线分页")
    @GetMapping("/online/page")
    R<IPage<SysUserOnline>> onlinePage(@RequestParam(required = false) Map<String, Object> sqlQueryParams);

    /**
     * 强制登出
     *
     * @param clientId 客户端 id
     * @param username 用户名
     * @return R
     * @author xijieyin <br> 2022/8/5 21:41
     * @since 1.0.0
     */
    @Operation(summary = "强制登出")
    @Parameters({
            @Parameter(name = "clientId", description = "客户端标识", required = true, in = ParameterIn.PATH),
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.PATH)
    })
    @PutMapping("/force/{clientId}/{username}")
    R<?> force(@PathVariable String clientId, @PathVariable String username);

    /**
     * 强制登出（批量）
     *
     * @param jti token jti 数组
     * @return R
     * @author xijieyin <br> 2022/8/5 21:41
     * @since 1.0.0
     */
    @Operation(summary = "强制登出（批量）")
    @Parameters({
            @Parameter(name = "jti", description = "jwt token id", required = true, in = ParameterIn.DEFAULT)
            , @Parameter(name = "message", description = "强退提示", required = true, in = ParameterIn.QUERY)
    })
    @PutMapping("/forceAll")
    R<?> forceAll(@RequestBody String[] jti, @RequestParam(required = false, defaultValue = "被强制登出！") String message);

    /**
     * 创建微信用户用户，游客
     *
     * @param dto 微信用户信息
     */
    @PutMapping(value = "/addWechatUser")
    default R<OAuth2UserDTO> addWechatUser(@RequestBody JSONObject dto) {
        return R.data(getBaseService().addWechatUser(dto));
    }

}
