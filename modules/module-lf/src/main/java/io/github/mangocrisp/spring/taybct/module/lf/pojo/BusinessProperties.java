package io.github.mangocrisp.spring.taybct.module.lf.pojo;

import com.alibaba.fastjson2.JSONObject;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * 业务属性
 *
 * @author XiJieYin <br> 2023/7/11 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BusinessProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 3282580153238788918L;

    /**
     * 如果节点是 custom-node-end(结束节点) 这里就会判断是否是正常结束的节点,例如,请假成功就是正常,请假被拒绝,就是不正常节点
     */
    private Boolean success;
    /**
     * 是否激活,也就是是否流程从当前的节点或者边通过
     */
    private Boolean isActive;
    /**
     * 次序,一般是在点上的次序,而且一个点可能有多次进入
     */
    private Collection<Integer> sequence;
    /**
     * 所有的业务字段表单
     */
    private Collection<BusinessField> fields;
    /**
     * 流程中传递的一些属性,虽然可以直接是 properties.xxx 这样去直接往 properties 里面 put 属性,但是,不推荐这样做,这样,就不知道你是一个什么属性
     */
    private JSONObject props;
    /**
     * 如果是用户节点是否会签
     */
    private Boolean isCountersign;
    /**
     * 如果是用户节点是否抄送
     */
    private Boolean isCC;
    /**
     * 角色列表,一般用于用户节点,用来分配当前节点的权限,有哪些角色可以处理
     */
    private Collection<String> roles;
    /**
     * 用户 id 列表,一般用于用户节点,用来分配当前节点被哪些用户可以处理
     */
    private Collection<?> userIdList;
    /**
     * 部门 id 列表,一般用于用户节点,用来分配当前节点被哪些部门可以处理
     */
    private Collection<?> deptIdList;
    /**
     * 绑定表单
     */
    private Object formBind;
    /**
     * 是否自动处理,当节点是系统节点时,默认为 true,如果节点是用户节点,还希望自动处理逻辑,可以开启
     */
    private Boolean autoExecute;
    /**
     * 自动处理/判断的方式,这里提供 SpES 表达式和提供一个 topic 来让java程序处理
     */
    private String condition;
    /**
     * 表达式
     */
    private String expression;
    /**
     * 自动处理的主题,当流程进行到当前节点时,所有订阅了这个主题的 spring boot bean,就会去处理
     */
    private String topic;
    /**
     * 是否审核通过,这个一般和 isActived 一起使用的
     */
    private Boolean approved;
    /**
     * 说明文档,可以加备注
     */
    private String documentation;

}
