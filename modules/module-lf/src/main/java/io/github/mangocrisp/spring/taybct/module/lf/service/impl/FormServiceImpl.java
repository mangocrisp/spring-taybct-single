package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Form;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.FormMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IFormService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_form(流程表单)】的数据库操作Service实现
 * @since 2023-07-21 15:18:29
 */
public class FormServiceImpl extends BaseServiceImpl<FormMapper, Form>
        implements IFormService {

    @Setter
    @Getter
    protected String field = "id,create_user,create_time,update_user,update_time,is_deleted,name,status,description,type,path,last_version";

    @Override
    public <E extends IPage<Form>> E page(E page, Wrapper<Form> queryWrapper) {
        ((QueryWrapper<Form>) queryWrapper).select(field.split(","));
        return super.page(page, queryWrapper);
    }

    @Override
    public List<Form> list(Wrapper<Form> queryWrapper) {
        ((QueryWrapper<Form>) queryWrapper).select(field.split(","));
        return super.list(queryWrapper);
    }

}




