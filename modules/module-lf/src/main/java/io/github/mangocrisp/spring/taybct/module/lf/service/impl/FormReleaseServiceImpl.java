package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Form;
import io.github.mangocrisp.spring.taybct.module.lf.domain.FormRelease;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.FormMapper;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.FormReleaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IFormReleaseService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author admin
 * <br>description 针对表【lf_form_release(流程表单发布表)】的数据库操作Service实现
 * @since 2023-07-21 15:18:29
 */
public class FormReleaseServiceImpl extends BaseServiceImpl<FormReleaseMapper, FormRelease>
        implements IFormReleaseService {

    @Autowired(required = false)
    protected FormMapper formMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean publish(FormReleasePublishDTO dto) {
        if (formMapper.exists(Wrappers.<Form>lambdaQuery()
                .isNull(Form::getData)
                .eq(Form::getId, dto.getFormId()))) {
            throw new BaseException("表单设计数据为空！无法发布！");
        }
        boolean b = getBaseMapper().publish(Collections.singleton(dto)) > 0;
        if (b) {
            formMapper.update(Wrappers.<Form>lambdaUpdate()
                    .set(Form::getLastVersion, dto.getVersion())
                    .eq(Form::getId, dto.getFormId()));
        }
        return b;
    }

    @Override
    public IPage<? extends FormRelease> publishList(FormReleaseQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return getBaseMapper().page(MyBatisUtil.genPage(sqlQueryParams), dto);
    }
}




