package io.github.mangocrisp.spring.taybct.admin.file.controller.impl;

import io.github.mangocrisp.spring.taybct.admin.file.controller.IFileController;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author XiJieYin <br> 2023/7/25 16:24
 */
public class FileControllerRegister implements IFileController {

    @Autowired(required = false)
    ISysFileService sysFileService;

    @Override
    public ISysFileService getSysFileService() {
        return sysFileService;
    }
}
