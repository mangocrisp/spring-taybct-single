package io.github.mangocrisp.spring.taybct.module.lf.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.extra.expression.engine.spel.SpELEngine;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.module.lf.api.ProcessAutoDealHandler;
import io.github.mangocrisp.spring.taybct.module.lf.constants.ProcessConstant;
import io.github.mangocrisp.spring.taybct.module.lf.dict.ProcessCondition;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.enums.ProcessItemType;
import io.github.mangocrisp.spring.taybct.tool.core.util.SpringUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PGobject;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 流程工具类
 *
 * @author XiJieYin <br> 2023/7/14 14:43
 */
@Slf4j
public class ProcessUtil {

    /**
     * 获取流程的表单属性
     *
     * @param process 流程信息
     * @return 表单键值对
     */
    public static JSONObject getProcessFormData(Process process) {
        return Optional.ofNullable(ProcessUtil.getJSONObject(process.getFormData())).orElseGet(JSONObject::new);
    }

    /**
     * 自动处理
     *
     * @param history         流程信息
     * @param process         流程信息
     * @param edges           连线信息（可以是连线本身，也可以是连接到节点的线）
     * @param nodes           连线或者节点的属性
     * @param properties      连线或者节点的属性
     * @param contextSupplier 提供可操作的表单属性
     * @return 处理结果
     */
    public static Boolean autoDeal(History history
            , Process process
            , Edges edges
            , Nodes nodes
            , JSONObject properties
            , Supplier<Map<String, Object>> contextSupplier) {
        String beanName = null;
        String SpELExpression = null;
        Map<String, Object> stringObjectMap = null;
        try {
            if (properties != null) {
                // 是否自动处理
                Boolean autoExecute = properties.getBooleanValue(ProcessConstant.AUTO_EXECUTE);
                // 连线如果有判断条件是否经过这条线
                String condition = properties.getString(ProcessConstant.CONDITION);
                if (condition != null) {
                    if (condition.equals(ProcessCondition.topic.getKey())) {
                        // 如果是根据 spring bean 来判断
                        String topic = properties.getString(ProcessConstant.TOPIC);
                        if (StringUtil.isBlank(topic)) {
                            return false;
                        }
                        String[] topicList = topic.split(",");
                        for (String t : topicList) {
                            beanName = t;
                            ProcessAutoDealHandler processAutoDealHandler = SpringUtil.getBean(t, ProcessAutoDealHandler.class);
                            if (!processAutoDealHandler.apply(history, process, edges, nodes)) {
                                return false;
                            }
                        }
                        return true;
                    } else if (condition.equals(ProcessCondition.SpEL.getKey())) {
                        // 如果是根据 SpEL 表达式来判断
                        String expressionTemplate = properties.getString(ProcessConstant.EXPRESSION);
                        SpELExpression = StringUtils.replaceAll(expressionTemplate, ProcessConstant.HISTORY_ID_PLACEHOLDER, Convert.toStr(history.getId()));
                        if (StringUtil.isBlank(SpELExpression)) {
                            return false;
                        }
                        stringObjectMap = contextSupplier.get();
                        if (CollectionUtil.isEmpty(stringObjectMap)) {
                            return false;
                        }
                        Object eval = new SpELEngine().eval(SpELExpression, stringObjectMap, new LinkedHashSet<>());
                        return (eval instanceof Boolean) && (Boolean) eval;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error("beanName: {}", beanName);
            log.error("SpELExpression: {}", SpELExpression);
            if (stringObjectMap != null) {
                log.error("stringObjectMap: \r\n {}", stringObjectMap);
            }
            log.error("自动处理失败", e);
            return false;
        }
    }

    /**
     * 获取连线的属性
     *
     * @param data json 数据
     * @return 属性
     */
    @Nullable
    public static JSONObject getJSONObject(Object data) {
        if (data == null) {
            return null;
        }
        JSONObject edgeProperties;
        if (data instanceof PGobject) {
            edgeProperties = JSONObject.parseObject(((PGobject) data).getValue());
        } else {
            edgeProperties = JSONObject.parseObject((String) data);
        }
        return edgeProperties;
    }

    /**
     * 生成表单属性名
     *
     * @param historyId 历史 id
     * @param type      类型 node 或者 edge
     * @param id        节点或者连线的 id
     * @param name      字段名
     * @param value     自动处理的节点
     * @return JSONObject
     */
    public static JSONObject generatorFormData(long historyId, ProcessItemType type, String id, String name, Object value) {
        return generatorFormData(new JSONObject(), historyId, type, id, name, value);
    }

    /**
     * 生成表单属性名
     *
     * @param historyId 历史 id
     * @param data      指定数据表单属性
     * @param type      类型 node 或者 edge
     * @param id        节点或者连线的 id
     * @param name      字段名
     * @param value     自动处理的节点
     * @return JSONObject
     */
    public static JSONObject generatorFormData(JSONObject data, long historyId, ProcessItemType type, String id, String name, Object value) {
        data.put(generatorFormDataKey(historyId
                , type
                , id
                , name), value);
        return data;
    }

    /**
     * 生成表单属性名 Key
     *
     * @param historyId 历史 id
     * @param type      类型 node 或者 edge
     * @param id        节点或者连线的 id
     * @param name      字段名
     * @return 表单属性名 key
     */
    public static String generatorFormDataKey(long historyId, ProcessItemType type, String id, String name) {
        return String.format("%s_%s_%s_%s"
                , type.getAlias()
                , id
                , name
                , historyId);
    }

}
