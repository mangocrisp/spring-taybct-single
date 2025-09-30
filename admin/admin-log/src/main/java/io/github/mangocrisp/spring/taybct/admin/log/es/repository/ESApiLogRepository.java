package io.github.mangocrisp.spring.taybct.admin.log.es.repository;

import io.github.mangocrisp.spring.taybct.admin.log.es.domain.ESApiLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * es 仓库索引构建，如果需要 es 框架自动创建索引，需要把这个类配置在和 Application 启动类同包或者同包下里面
 *
 * @author xijieyin <br> 2022/8/4 18:55
 * @since 1.0.0
 */
public interface ESApiLogRepository extends ElasticsearchRepository<ESApiLog, Long> {
}
