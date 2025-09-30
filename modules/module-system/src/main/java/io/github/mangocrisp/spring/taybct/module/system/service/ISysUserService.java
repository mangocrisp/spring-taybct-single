package io.github.mangocrisp.spring.taybct.module.system.service;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.dto.UserPassCheckDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.UserInfoVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

/**
 * @author xijieyin
 */
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 检查用户是否已经存在
     *
     * @param dto 需要校验的用户信息
     */
    boolean userPassableCheck(UserPassCheckDTO dto);

    /**
     * 个人中心修改信息
     *
     * @param domain 用户信息
     * @return 是否修改成功
     */
    boolean updateMyInfo(SysUser domain);

    /**
     * 根据字段获取用户信息
     *
     * @param field 字段名
     * @param value 值
     * @return OAuth2UserDTO
     */
    OAuth2UserDTO getUserByFiled(String field, String value);

    /**
     * 根据用户 id 获取用户信息，可以获取所有关于这个用户的信息
     *
     * @param userId 用户 id
     * @return UserInfoVO
     */
    UserInfoVO getUserInfoByUserId(Long userId);

    /**
     * 修改用户密码
     *
     * @param old 旧密码
     * @param now 新密码
     * @return boolean
     */
    boolean modifyPasswd(String old, String now);

    /**
     * 重置用户密码
     *
     * @param userId 用户 id
     * @return boolean
     */
    boolean resetPasswd(Long... userId);

    /**
     * 重置用户密码
     *
     * @param password 密码
     * @param userId   用户 id
     * @return boolean
     * @author xijieyin <br> 2022/9/16 17:40
     * @since 1.0.3
     */
    boolean resetPasswd(String password, Long... userId);

    /**
     * 创建微信用户并返回登录用户信息
     *
     * @param dto 微信用户信息
     * @return OAuth2UserDTO
     */
    OAuth2UserDTO addWechatUser(JSONObject dto);

}
