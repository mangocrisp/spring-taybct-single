package io.github.mangocrisp.spring.taybct.module.lf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 待办统计
 *
 * @author XiJieYin <br> 2023/7/19 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "待办统计")
public class TodoCount implements Serializable {

    @Serial
    private static final long serialVersionUID = -2243285972051108221L;

    /**
     * key
     */
    @Schema(description = "key")
    private Object key;
    /**
     * 数量
     */
    @Schema(description = "数量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long count;

}
