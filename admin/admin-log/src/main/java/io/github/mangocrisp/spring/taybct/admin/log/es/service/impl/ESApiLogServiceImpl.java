package io.github.mangocrisp.spring.taybct.admin.log.es.service.impl;

import io.github.mangocrisp.spring.taybct.admin.log.es.domain.ESApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.es.service.IESApiLogService;
import io.github.mangocrisp.spring.taybct.tool.core.es.service.impl.ESServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * 日志管理 es 查询实现
 *
 * @author xijieyin <br> 2022/8/4 19:08
 * @since 1.0.0
 */
@AutoConfiguration
@Service
@ConditionalOnClass(ElasticsearchTemplate.class)
public class ESApiLogServiceImpl extends ESServiceImpl<ESApiLog> implements IESApiLogService {
}
