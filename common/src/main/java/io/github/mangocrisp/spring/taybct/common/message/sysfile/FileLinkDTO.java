package io.github.mangocrisp.spring.taybct.common.message.sysfile;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件关联
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FileLinkDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名（路径）
     */
    private String path;
    /**
     * 是否在使用中
     */
    private Integer linked;
    /**
     * 关联的表
     */
    private String linkedTable;
    /**
     * 关联的表的 id
     */
    private Long linkedTableId;
    /**
     * 是否已删除
     */
    private Integer isDeleted;
    /**
     * 文件管理服务器类型（local,oss,fdfs,minio）
     */
    private String manageType;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 更新人
     */
    private Long updateUser;

}