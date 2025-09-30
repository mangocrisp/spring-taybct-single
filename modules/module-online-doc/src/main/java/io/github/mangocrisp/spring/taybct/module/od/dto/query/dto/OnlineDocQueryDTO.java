package io.github.mangocrisp.spring.taybct.module.od.dto.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <pre>
 * 在线文档 列表查询对象
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 02:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【在线文档】列表查询对象")
public class OnlineDocQueryDTO implements Serializable, ModelConvertible<OnlineDoc> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 主键选择
     */
    @Schema(description = "主键")
    private Collection<Long> idSelection;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     * 创建时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "创建时间（时间范围开始）")
    private LocalDateTime createTime_ge;
    /**
     * 创建时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "创建时间（时间范围结束）")
    private LocalDateTime createTime_le;
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     * 修改时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "修改时间（时间范围开始）")
    private LocalDateTime updateTime_ge;
    /**
     * 修改时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "修改时间（时间范围结束）")
    private LocalDateTime updateTime_le;
    /**
     * 是否已删除
     */
    @Schema(description = "是否已删除")
    private Integer isDeleted = 0;
    /**
     * 文档名称
     */
    @Schema(description = "文档名称")
    private String name;
    /**
     * 文档是否共享
     */
    @Schema(description = "文档是否共享")
    private Integer share;
    /**
     * 唯一字段
     */
    @Schema(description = "唯一字段")
    private String uniqueField;
    /**
     * 文档属性设置（字段等）
     */
    @Schema(description = "文档属性设置（字段等）")
    private Object properties;
    /**
     * 所属部门id
     */
    @Schema(description = "所属部门id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /**
     * 所属部门名称
     */
    @Schema(description = "所属部门名称")
    private String deptName;
    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String createUserName;
    /**
     * 关键字搜索
     */
    @Schema(description = "关键字搜索")
    private String keyWords;

    @Hidden
    OnlineDoc convertedBean;

    @Override
    @Hidden
    public void setConvertedBean(OnlineDoc convertedBean) {
        throw new BaseException("not support!");
    }

    @Override
    public OnlineDoc bean(String... ignoreProperties) {
        OnlineDoc bean;
        if ((bean = getConvertedBean()) != null) {
            return bean;
        }
        this.convertedBean = (bean = BeanUtil.copyProperties(this, beanClass(), ignoreProperties));
        return bean;
    }

}