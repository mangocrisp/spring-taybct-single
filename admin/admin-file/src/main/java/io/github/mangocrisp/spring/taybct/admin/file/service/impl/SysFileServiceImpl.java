package io.github.mangocrisp.spring.taybct.admin.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileTypeUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.admin.file.mapper.SysFileMapper;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.file.enums.FileManageType;
import io.github.mangocrisp.spring.taybct.tool.file.util.FileServiceBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 针对表【sys_file(文件管理)】的数据库操作Service实现
 * </pre>
 *
 * @author 24154
 * @see SysFile
 * @since 2024-09-01 21:20:40
 */
@AutoConfiguration
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile>
        implements ISysFileService {

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean upload(Set<String> pathSet) {
        if (CollectionUtil.isEmpty(pathSet)) {
            return false;
        }
        List<SysFile> list = pathSet.stream().map(path -> {
            SysFile sysFile = new SysFile();
            sysFile.setPath(path);
            try {
                String fileTpe = FileTypeUtil.getType(FileServiceBuilder.get(path));
                sysFile.setFileType(fileTpe);
            } catch (Exception e) {
                log.trace(e.getMessage());
            }
            return sysFile;
        }).toList();
        return saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean cleanNotLinkedFile(Map<String, Object> params) {
        // 同步分页
        Long limit = Convert.convert(Long.class, params.get("limit"), 999L);
        Page<SysFile> page = new Page<>();
        page.setSize(limit);
        for (long i = 1L; ; i++) {
            page.setCurrent(i);
            List<SysFile> list = page(page, Wrappers.<SysFile>lambdaQuery()
                    .select(SysFile::getId, SysFile::getPath, SysFile::getManageType)
                    .eq(SysFile::getLinked, 0)
                    .eq(SysFile::getIsDeleted, 0)).getRecords();
            if (CollectionUtil.isNotEmpty(list)) {
                break;
            } else {
                removeBatchByIds(list.stream()
                        .peek(f -> {
                            try {
                                // 一个一个的删除文件
                                FileServiceBuilder.build(new FileManageType(f.getFileType())).delete(f.getPath());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        // 最后一次性全删除
                        .map(SysFile::getId).toList());
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean link(List<SysFile> list) {
        list.forEach(file -> update(file, Wrappers.<SysFile>lambdaUpdate()
                .eq(SysFile::getPath, file.getPath())));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <UM extends ModelConvertible<? extends SysFile>, QM extends ModelConvertible<? extends SysFile>> boolean update(UpdateModel<SysFile, UM, QM> model) {
        model.getUpdateList().forEach(condition -> getBaseMapper().updateBatchByCondition(condition.getBean(), condition.getParams()));
        return true;
    }

    @Override
    public <E extends SysFile> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams) {
        IPage<E> page = sqlPageParams.genPage();
        long total = getBaseMapper().total(params);
        page.setTotal(total);
        if (total > 0) {
            List<? extends SysFile> list = getBaseMapper().page(params, sqlPageParams);
            page.setRecords(list.stream().map(e -> (E) e).toList());
        }
        return page;
    }

    @Override
    public <E extends SysFile> List<E> list(JSONObject params, SqlPageParams sqlPageParams) {
        return getBaseMapper().page(params, sqlPageParams).stream().map(e -> (E) e).toList();
    }

    @Override
    public <E extends SysFile> E detail(JSONObject params) {
        return getBaseMapper().detail(params);
    }

}
