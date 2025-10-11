/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 150000 (150000)
 Source Host           : localhost:5432
 Source Catalog        : taybct
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150000 (150000)
 File Encoding         : 65001

 Date: 11/10/2025 17:25:26
*/


-- ----------------------------
-- Table structure for api_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."api_log";
CREATE TABLE "public"."api_log" (
  "id" int8 NOT NULL,
  "title" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(200) COLLATE "pg_catalog"."default",
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "client" varchar(30) COLLATE "pg_catalog"."default",
  "module" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "ip" varchar(128) COLLATE "pg_catalog"."default",
  "type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "method" varchar(10) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "params" varchar(2000) COLLATE "pg_catalog"."default",
  "result" varchar(2000) COLLATE "pg_catalog"."default",
  "code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."api_log"."id" IS '主键';
COMMENT ON COLUMN "public"."api_log"."title" IS '模块标题';
COMMENT ON COLUMN "public"."api_log"."description" IS '接口描述';
COMMENT ON COLUMN "public"."api_log"."username" IS '操作人员';
COMMENT ON COLUMN "public"."api_log"."client" IS '客户端类型';
COMMENT ON COLUMN "public"."api_log"."module" IS '模块名';
COMMENT ON COLUMN "public"."api_log"."ip" IS '主机地址';
COMMENT ON COLUMN "public"."api_log"."type" IS '业务类型';
COMMENT ON COLUMN "public"."api_log"."method" IS '请求方式';
COMMENT ON COLUMN "public"."api_log"."url" IS '请求URL';
COMMENT ON COLUMN "public"."api_log"."params" IS '请求参数';
COMMENT ON COLUMN "public"."api_log"."result" IS '返回参数';
COMMENT ON COLUMN "public"."api_log"."code" IS '状态码';
COMMENT ON COLUMN "public"."api_log"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."api_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."api_log"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."api_log"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."api_log"."tenant_id" IS '租户id';
COMMENT ON TABLE "public"."api_log" IS '系统日志';

-- ----------------------------
-- Records of api_log
-- ----------------------------

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS "public"."authorities";
CREATE TABLE "public"."authorities" (
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "authority" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON TABLE "public"."authorities" IS 'spring security 用户角色关联表';

-- ----------------------------
-- Records of authorities
-- ----------------------------

-- ----------------------------
-- Table structure for lf_design
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_design";
CREATE TABLE "public"."lf_design" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 DEFAULT 0,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "data" jsonb,
  "type" varchar(100) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "last_version" int8
)
;
COMMENT ON COLUMN "public"."lf_design"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_design"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."lf_design"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_design"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."lf_design"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."lf_design"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."lf_design"."name" IS '名称';
COMMENT ON COLUMN "public"."lf_design"."status" IS '状态(0未发布，1已经发布)';
COMMENT ON COLUMN "public"."lf_design"."description" IS '备注说明';
COMMENT ON COLUMN "public"."lf_design"."data" IS '数据（实时设计最新的流程数据）';
COMMENT ON COLUMN "public"."lf_design"."type" IS '流程类型（字典项 lf_process_type）';
COMMENT ON COLUMN "public"."lf_design"."icon" IS '图标';
COMMENT ON COLUMN "public"."lf_design"."last_version" IS '最后发布版本号';
COMMENT ON TABLE "public"."lf_design" IS '流程图设计';

-- ----------------------------
-- Records of lf_design
-- ----------------------------
INSERT INTO "public"."lf_design" VALUES (1968238470464376834, 1, '2025-09-17 16:59:54.11852', 1, '2025-10-11 17:24:00.757762', 0, '请假流程', 1, NULL, '{"edges": [{"id": "e7b489a08442403f9b152354a066c666", "type": "custom-edge-line", "endPoint": {"x": -290, "y": -330}, "pointsList": [{"x": -370, "y": -330}, {"x": -290, "y": -330}], "properties": {}, "startPoint": {"x": -370, "y": -330}, "sourceNodeId": "4b4ed343e6bb46c4a5d93b1f80c3903d", "targetNodeId": "c5fc27d82cc148b9b1f578e03b91dceb"}, {"id": "fb78119e22594a66ae18ce43d869e141", "type": "custom-edge-line", "endPoint": {"x": -110, "y": -330}, "pointsList": [{"x": -190, "y": -330}, {"x": -110, "y": -330}], "properties": {}, "startPoint": {"x": -190, "y": -330}, "sourceNodeId": "c5fc27d82cc148b9b1f578e03b91dceb", "targetNodeId": "64f28d65b09849999052cb9075e693be"}, {"id": "d5fe54d720f1422b8525b0891450e865", "text": {"x": 0, "y": -330, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 50, "y": -330, "id": "-100--370"}, "pointsList": [{"x": -50, "y": -330}, {"x": 50, "y": -330}], "properties": {"condition": "SpEL", "expression": "!#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$"}, "startPoint": {"x": -50, "y": -330, "id": "-200--370"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "f9156ceea6b64afba948251df990c8fa"}, {"id": "14715d7893a14edcbc61aaf8c00e31bf", "text": {"x": -80, "y": -230, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": -80, "y": -180, "id": "-230--220"}, "pointsList": [{"x": -80, "y": -280}, {"x": -80, "y": -180}], "properties": {"condition": "SpEL", "expression": "#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": -80, "y": -280, "id": "-230--320"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "a1960c98cef541ec9736d2d0830a54ab"}, {"id": "1990705cc0694e42b94044cad6861125", "type": "custom-edge-line", "endPoint": {"x": 70, "y": -140, "id": "-80--180"}, "pointsList": [{"x": -30, "y": -140}, {"x": 70, "y": -140}], "properties": {}, "startPoint": {"x": -30, "y": -140, "id": "-180--180"}, "sourceNodeId": "a1960c98cef541ec9736d2d0830a54ab", "targetNodeId": "d256a21fdbf64171a50e84b9bfae6f1e"}, {"id": "b1169e25d3454e4bab53e81677a15c3e", "text": {"x": 175, "y": -140, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 220, "y": -140, "id": "70--180"}, "pointsList": [{"x": 130, "y": -140}, {"x": 220, "y": -140}], "properties": {"condition": "SpEL", "expression": "!#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$"}, "startPoint": {"x": 130, "y": -140, "id": "-20--180"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "00a4d5f8588c450aa0df7b3132677a6a"}, {"id": "2b21609bc3e14e6bb304e7668cbc0e66", "text": {"x": 100, "y": -40, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": 100, "y": 10, "id": "-50--30"}, "pointsList": [{"x": 100, "y": -90}, {"x": 100, "y": 10}], "properties": {"condition": "SpEL", "expression": "#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": 100, "y": -90, "id": "-50--130"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "bd986a38799f4c82b265170f359764d0"}], "nodes": [{"x": -420, "y": -330, "id": "4b4ed343e6bb46c4a5d93b1f80c3903d", "text": {"x": -420, "y": -331, "value": "提交请假申请"}, "type": "custom-node-start", "properties": {"fields": [{"key": "fe7329fc03a30431797da48b3cfbc4eb7", "name": "name", "sort": 0, "type": "STRING", "title": "姓名", "value": "", "disabled": false, "readonly": false}, {"key": "f884e56cbf5584885b77ed08aa66b165c", "name": "kind", "sort": "0", "type": "STRING", "title": "请假类型", "value": "", "disabled": false, "readonly": false}, {"key": "fb7f6bdd80a9040e79d0f64742b282c53", "name": "reason", "sort": 1, "type": "STRING", "title": "请假理由", "value": "", "disabled": false, "readonly": false}, {"key": "f3a5feb6e4e864e798b47b1e204f593f9", "name": "howLong", "sort": 2, "type": "NUMBER", "title": "时长", "disabled": false, "readonly": false}, {"key": "fe62e78c6f79e462db435526424aab7c7", "name": "timeUnit", "sort": 3, "type": "STRING", "title": "时间单位", "value": "", "disabled": false, "readonly": false}], "formBind": {"id": "1968263061492568065", "name": "请假流程开始表单.release.20250917183731"}, "documentation": "请假流程"}}, {"x": -240, "y": -330, "id": "c5fc27d82cc148b9b1f578e03b91dceb", "text": {"x": -240, "y": -330, "value": "小组长审批"}, "type": "custom-node-user", "properties": {"fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "userIdList": [{"id": "1963080001398505474", "name": "小组长"}], "autoExecute": false}}, {"x": -80, "y": -330, "id": "64f28d65b09849999052cb9075e693be", "text": {"x": -80, "y": -330, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 100, "y": -330, "id": "f9156ceea6b64afba948251df990c8fa", "text": {"x": 100, "y": -330, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": -80, "y": -140, "id": "a1960c98cef541ec9736d2d0830a54ab", "text": {"x": -80, "y": -140, "value": "领导审批"}, "type": "custom-node-user", "properties": {"roles": [], "fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "deptIdList": [], "userIdList": [{"id": "1963084850613714945", "name": "部门领导"}, {"id": "1963085401439076353", "name": "总经理"}], "autoExecute": false, "isCountersign": true}}, {"x": 100, "y": -140, "id": "d256a21fdbf64171a50e84b9bfae6f1e", "text": {"x": 100, "y": -140, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 270, "y": -140, "id": "00a4d5f8588c450aa0df7b3132677a6a", "text": {"x": 270, "y": -140, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": 100, "y": 60, "id": "bd986a38799f4c82b265170f359764d0", "text": {"x": 100, "y": 60, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowAlarmArchived", "success": true, "condition": "topic", "autoExecute": true}}]}', 'normal', 'ep:avatar', 20251011172400);

-- ----------------------------
-- Table structure for lf_design_permissions
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_design_permissions";
CREATE TABLE "public"."lf_design_permissions" (
  "id" int8 NOT NULL,
  "design_id" int8 NOT NULL,
  "user_id" int8,
  "dept_id" int8,
  "perm_edit" int2,
  "perm_delete" int2,
  "perm_publish" int2,
  "perm_share" int2
)
;
COMMENT ON COLUMN "public"."lf_design_permissions"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_design_permissions"."design_id" IS '设计图 id';
COMMENT ON COLUMN "public"."lf_design_permissions"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."lf_design_permissions"."dept_id" IS '部门id';
COMMENT ON COLUMN "public"."lf_design_permissions"."perm_edit" IS '编辑权限';
COMMENT ON COLUMN "public"."lf_design_permissions"."perm_delete" IS '删除权限';
COMMENT ON COLUMN "public"."lf_design_permissions"."perm_publish" IS '发布权限';
COMMENT ON COLUMN "public"."lf_design_permissions"."perm_share" IS '分享权限';
COMMENT ON TABLE "public"."lf_design_permissions" IS '流程图权限表';

-- ----------------------------
-- Records of lf_design_permissions
-- ----------------------------
INSERT INTO "public"."lf_design_permissions" VALUES (1968238470464376835, 1968238470464376834, 1, NULL, 1, 1, 1, 1);

-- ----------------------------
-- Table structure for lf_edges
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_edges";
CREATE TABLE "public"."lf_edges" (
  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "source_node_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "target_node_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "properties" jsonb,
  "text" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "process_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."lf_edges"."id" IS '主键（节点的id，这里是使用前端生成的 uuid）';
COMMENT ON COLUMN "public"."lf_edges"."source_node_id" IS '起始节点 id';
COMMENT ON COLUMN "public"."lf_edges"."target_node_id" IS '指向节点 id';
COMMENT ON COLUMN "public"."lf_edges"."properties" IS '线的属性数据';
COMMENT ON COLUMN "public"."lf_edges"."text" IS '线上的文字';
COMMENT ON COLUMN "public"."lf_edges"."type" IS '线类型（字典项 lf_node_type）';
COMMENT ON COLUMN "public"."lf_edges"."process_id" IS '流程 id';
COMMENT ON TABLE "public"."lf_edges" IS '流程连线表';

-- ----------------------------
-- Records of lf_edges
-- ----------------------------

-- ----------------------------
-- Table structure for lf_form
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_form";
CREATE TABLE "public"."lf_form" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 DEFAULT 0,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "data" jsonb,
  "type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "path" varchar(500) COLLATE "pg_catalog"."default",
  "last_version" int8
)
;
COMMENT ON COLUMN "public"."lf_form"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_form"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."lf_form"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_form"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."lf_form"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."lf_form"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."lf_form"."name" IS '名称';
COMMENT ON COLUMN "public"."lf_form"."status" IS '状态(0未发布，1已经发布)';
COMMENT ON COLUMN "public"."lf_form"."description" IS '备注说明';
COMMENT ON COLUMN "public"."lf_form"."data" IS '数据（实时设计最新的表单数据）';
COMMENT ON COLUMN "public"."lf_form"."type" IS '表单类型，是表单还是单组件（字典项 lf_form_type）';
COMMENT ON COLUMN "public"."lf_form"."path" IS '表单组件路径';
COMMENT ON COLUMN "public"."lf_form"."last_version" IS '最后发布版本号';
COMMENT ON TABLE "public"."lf_form" IS '流程表单';

-- ----------------------------
-- Records of lf_form
-- ----------------------------
INSERT INTO "public"."lf_form" VALUES (1968239272096534529, 1, '2025-09-17 17:03:05.250565', 1, '2025-09-17 18:37:37.102214', 0, '请假流程开始表单', 1, NULL, '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_F6oxmfnrnywlazc\",\"name\":\"ref_Fr63mfnrnywlb0c\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"select\",\"field\":\"kind\",\"title\":\"请假类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"自己请假\",\"value\":\"1\"},{\"label\":\"帮助同事\",\"value\":\"2\"}],\"_fc_id\":\"id_F39tmfnr9f9zafc\",\"name\":\"ref_Fvftmfnr9f9zagc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"select\",\"field\":\"type\",\"title\":\"假期类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"事假\",\"value\":\"1\"},{\"label\":\"病假\",\"value\":\"2\"},{\"label\":\"年假\",\"value\":\"3\"},{\"label\":\"婚假\",\"value\":\"4\"},{\"label\":\"丧葬\",\"value\":\"5\"}],\"_fc_id\":\"id_F0b5mfnuhx8oacc\",\"name\":\"ref_Fmcomfnuhx8oadc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"input\",\"field\":\"name\",\"title\":\"姓名\",\"info\":\"也可以代为他人请假，多个人按逗号隔开\",\"$required\":true,\"_fc_id\":\"id_F65smfnr80h5acc\",\"name\":\"ref_Frjqmfnr80h5adc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\"},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假理由\",\"info\":\"\",\"$required\":\"必须填写请假理由\",\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fgs1mfnrb2k5aic\",\"name\":\"ref_Fx65mfnrb2k5ajc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"},{\"type\":\"fcRow\",\"children\":[{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"input\",\"field\":\"howLong\",\"title\":\"时长\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"number\",\"min\":0.5},\"_fc_id\":\"id_Fzkpmfnrd1ujalc\",\"name\":\"ref_Flo5mfnrd1ujamc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\",\"_fc_store\":{\"props_keys\":[\"min\"]}}],\"_fc_id\":\"id_F8k6mfnrdmgxapc\",\"name\":\"ref_Fin2mfnrdmgxaqc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"},{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"radio\",\"field\":\"timeUnit\",\"title\":\"\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":false,\"options\":[{\"label\":\"小时\",\"value\":\"h\"},{\"label\":\"天\",\"value\":\"d\"}],\"_fc_id\":\"id_F2tymfnrh87iaxc\",\"name\":\"ref_Fkmqmfnrh87iayc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"radio\",\"style\":{\"marginLeft\":\"20px\"}}],\"_fc_id\":\"id_Fbe7mfnrdmgxarc\",\"name\":\"ref_Ffaamfnrdmgxasc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"}],\"_fc_id\":\"id_Fxvhmfnrdmgxanc\",\"name\":\"ref_Ffp1mfnrdmgxaoc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"fcRow\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"kind\":\"1\",\"howLong\":\"4\",\"timeUnit\":\"h\"}}"}', 'form', NULL, 20250917183737);
INSERT INTO "public"."lf_form" VALUES (1968257092406579202, 1, '2025-09-17 18:13:53.945459', 1, '2025-09-17 18:25:38.732638', 0, '是否审批通过表单', 1, NULL, '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_Fh3mmfntr3xlabc\",\"name\":\"ref_Fht4mfntr3xlacc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"switch\",\"field\":\"approve\",\"title\":\"批准\",\"info\":\"\",\"$required\":true,\"props\":{\"activeValue\":true,\"inactiveValue\":false},\"_fc_id\":\"id_Fwdymfntr8apaec\",\"name\":\"ref_Fmatmfntr8apafc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"switch\",\"value\":true,\"control\":[{\"method\":\"if\",\"condition\":\"==\",\"value\":false,\"rule\":[\"reason\"]}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"理由\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fq7nmfntrx56ahc\",\"name\":\"ref_Fipemfntrx56aic\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"aprove\":true}}"}', 'form', NULL, 20250917182538);

-- ----------------------------
-- Table structure for lf_form_release
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_form_release";
CREATE TABLE "public"."lf_form_release" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 DEFAULT 0,
  "form_id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "data" jsonb NOT NULL,
  "version" int8 NOT NULL,
  "type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "path" varchar(500) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."lf_form_release"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_form_release"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."lf_form_release"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_form_release"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."lf_form_release"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."lf_form_release"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."lf_form_release"."form_id" IS '表单 id';
COMMENT ON COLUMN "public"."lf_form_release"."name" IS '发布名称';
COMMENT ON COLUMN "public"."lf_form_release"."status" IS '状态(0 关闭 1 打开)';
COMMENT ON COLUMN "public"."lf_form_release"."description" IS '备注说明';
COMMENT ON COLUMN "public"."lf_form_release"."data" IS '数据（每个版本的数据）';
COMMENT ON COLUMN "public"."lf_form_release"."version" IS '版本号（yyyyMMddHHmmss）';
COMMENT ON COLUMN "public"."lf_form_release"."type" IS '表单类型，是表单还是单组件（字典项 lf_form_type）';
COMMENT ON COLUMN "public"."lf_form_release"."path" IS '表单组件路径';
COMMENT ON TABLE "public"."lf_form_release" IS '流程表单发布表';

-- ----------------------------
-- Records of lf_form_release
-- ----------------------------
INSERT INTO "public"."lf_form_release" VALUES (1968242302338568193, 1, '2025-09-17 17:15:07.723058', 1, '2025-09-17 17:15:07.723058', 0, 1968239272096534529, '请假流程开始表单.release.20250917171501', 1, '请假流程开始', '{"rule": "[{\"type\":\"select\",\"field\":\"kind\",\"title\":\"请假类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"自己请假\",\"value\":\"1\"},{\"label\":\"帮助同事\",\"value\":\"2\"}],\"_fc_id\":\"id_F39tmfnr9f9zafc\",\"name\":\"ref_Fvftmfnr9f9zagc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"input\",\"field\":\"name\",\"title\":\"姓名\",\"info\":\"也可以代为他人请假，多个人按逗号隔开\",\"$required\":true,\"_fc_id\":\"id_F65smfnr80h5acc\",\"name\":\"ref_Frjqmfnr80h5adc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\"},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假理由\",\"info\":\"\",\"$required\":\"必须填写请假理由\",\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fgs1mfnrb2k5aic\",\"name\":\"ref_Fx65mfnrb2k5ajc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"},{\"type\":\"fcRow\",\"children\":[{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"input\",\"field\":\"howLong\",\"title\":\"时长\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"number\",\"min\":0.5},\"_fc_id\":\"id_Fzkpmfnrd1ujalc\",\"name\":\"ref_Flo5mfnrd1ujamc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\",\"_fc_store\":{\"props_keys\":[\"min\"]}}],\"_fc_id\":\"id_F8k6mfnrdmgxapc\",\"name\":\"ref_Fin2mfnrdmgxaqc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"},{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"radio\",\"field\":\"timeUnit\",\"title\":\"\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":false,\"options\":[{\"label\":\"小时\",\"value\":\"h\"},{\"label\":\"天\",\"value\":\"d\"}],\"_fc_id\":\"id_F2tymfnrh87iaxc\",\"name\":\"ref_Fkmqmfnrh87iayc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"radio\",\"style\":{\"marginLeft\":\"20px\"}}],\"_fc_id\":\"id_Fbe7mfnrdmgxarc\",\"name\":\"ref_Ffaamfnrdmgxasc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"}],\"_fc_id\":\"id_Fxvhmfnrdmgxanc\",\"name\":\"ref_Ffp1mfnrdmgxaoc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"fcRow\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"kind\":\"1\",\"howLong\":\"4\",\"timeUnit\":\"h\"}}"}', 20250917171507, 'form', NULL);
INSERT INTO "public"."lf_form_release" VALUES (1968242557142536193, 1, '2025-09-17 17:16:08.458339', 1, '2025-09-17 17:16:08.458339', 0, 1968239272096534529, '请假流程开始表单.release.20250917171556', 1, '可以查看流程信息', '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_F6oxmfnrnywlazc\",\"name\":\"ref_Fr63mfnrnywlb0c\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"select\",\"field\":\"kind\",\"title\":\"请假类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"自己请假\",\"value\":\"1\"},{\"label\":\"帮助同事\",\"value\":\"2\"}],\"_fc_id\":\"id_F39tmfnr9f9zafc\",\"name\":\"ref_Fvftmfnr9f9zagc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"input\",\"field\":\"name\",\"title\":\"姓名\",\"info\":\"也可以代为他人请假，多个人按逗号隔开\",\"$required\":true,\"_fc_id\":\"id_F65smfnr80h5acc\",\"name\":\"ref_Frjqmfnr80h5adc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\"},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假理由\",\"info\":\"\",\"$required\":\"必须填写请假理由\",\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fgs1mfnrb2k5aic\",\"name\":\"ref_Fx65mfnrb2k5ajc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"},{\"type\":\"fcRow\",\"children\":[{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"input\",\"field\":\"howLong\",\"title\":\"时长\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"number\",\"min\":0.5},\"_fc_id\":\"id_Fzkpmfnrd1ujalc\",\"name\":\"ref_Flo5mfnrd1ujamc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\",\"_fc_store\":{\"props_keys\":[\"min\"]}}],\"_fc_id\":\"id_F8k6mfnrdmgxapc\",\"name\":\"ref_Fin2mfnrdmgxaqc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"},{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"radio\",\"field\":\"timeUnit\",\"title\":\"\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":false,\"options\":[{\"label\":\"小时\",\"value\":\"h\"},{\"label\":\"天\",\"value\":\"d\"}],\"_fc_id\":\"id_F2tymfnrh87iaxc\",\"name\":\"ref_Fkmqmfnrh87iayc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"radio\",\"style\":{\"marginLeft\":\"20px\"}}],\"_fc_id\":\"id_Fbe7mfnrdmgxarc\",\"name\":\"ref_Ffaamfnrdmgxasc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"}],\"_fc_id\":\"id_Fxvhmfnrdmgxanc\",\"name\":\"ref_Ffp1mfnrdmgxaoc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"fcRow\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"kind\":\"1\",\"howLong\":\"4\",\"timeUnit\":\"h\"}}"}', 20250917171608, 'form', NULL);
INSERT INTO "public"."lf_form_release" VALUES (1968258988353622017, 1, '2025-09-17 18:21:25.971315', 1, '2025-09-17 18:21:25.971315', 0, 1968257092406579202, '是否审批通过表单.release.20250917182119', 1, '批准表单', '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_Fh3mmfntr3xlabc\",\"name\":\"ref_Fht4mfntr3xlacc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"switch\",\"field\":\"aprove\",\"title\":\"批准\",\"info\":\"\",\"$required\":true,\"props\":{\"activeValue\":true,\"inactiveValue\":false},\"_fc_id\":\"id_Fwdymfntr8apaec\",\"name\":\"ref_Fmatmfntr8apafc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"switch\",\"control\":[{\"method\":\"if\",\"condition\":\"==\",\"value\":false,\"rule\":[\"reason\"]}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"理由\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fq7nmfntrx56ahc\",\"name\":\"ref_Fipemfntrx56aic\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"aprove\":true}}"}', 20250917182125, 'form', NULL);
INSERT INTO "public"."lf_form_release" VALUES (1968260048388460545, 1, '2025-09-17 18:25:38.708638', 1, '2025-09-17 18:25:38.708638', 0, 1968257092406579202, '是否审批通过表单.release.20250917182529', 1, '单词拼错了', '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_Fh3mmfntr3xlabc\",\"name\":\"ref_Fht4mfntr3xlacc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"switch\",\"field\":\"approve\",\"title\":\"批准\",\"info\":\"\",\"$required\":true,\"props\":{\"activeValue\":true,\"inactiveValue\":false},\"_fc_id\":\"id_Fwdymfntr8apaec\",\"name\":\"ref_Fmatmfntr8apafc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"switch\",\"value\":true,\"control\":[{\"method\":\"if\",\"condition\":\"==\",\"value\":false,\"rule\":[\"reason\"]}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"理由\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fq7nmfntrx56ahc\",\"name\":\"ref_Fipemfntrx56aic\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"aprove\":true}}"}', 20250917182538, 'form', NULL);
INSERT INTO "public"."lf_form_release" VALUES (1968263061492568065, 1, '2025-09-17 18:37:37.081432', 1, '2025-09-17 18:37:37.081432', 0, 1968239272096534529, '请假流程开始表单.release.20250917183731', 1, '添加了请假类型', '{"rule": "[{\"type\":\"LfFormTodoInfo\",\"field\":\"flowInfo\",\"title\":\"\",\"info\":\"请不要修改字段 ID (flowInfo)，仅展示流程信息\",\"_fc_id\":\"id_F6oxmfnrnywlazc\",\"name\":\"ref_Fr63mfnrnywlb0c\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"LfFormTodoInfo\"},{\"type\":\"select\",\"field\":\"kind\",\"title\":\"请假类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"自己请假\",\"value\":\"1\"},{\"label\":\"帮助同事\",\"value\":\"2\"}],\"_fc_id\":\"id_F39tmfnr9f9zafc\",\"name\":\"ref_Fvftmfnr9f9zagc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"select\",\"field\":\"type\",\"title\":\"假期类型\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":true,\"options\":[{\"label\":\"事假\",\"value\":\"1\"},{\"label\":\"病假\",\"value\":\"2\"},{\"label\":\"年假\",\"value\":\"3\"},{\"label\":\"婚假\",\"value\":\"4\"},{\"label\":\"丧葬\",\"value\":\"5\"}],\"_fc_id\":\"id_F0b5mfnuhx8oacc\",\"name\":\"ref_Fmcomfnuhx8oadc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"select\"},{\"type\":\"input\",\"field\":\"name\",\"title\":\"姓名\",\"info\":\"也可以代为他人请假，多个人按逗号隔开\",\"$required\":true,\"_fc_id\":\"id_F65smfnr80h5acc\",\"name\":\"ref_Frjqmfnr80h5adc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\"},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假理由\",\"info\":\"\",\"$required\":\"必须填写请假理由\",\"props\":{\"type\":\"textarea\"},\"_fc_id\":\"id_Fgs1mfnrb2k5aic\",\"name\":\"ref_Fx65mfnrb2k5ajc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"textarea\"},{\"type\":\"fcRow\",\"children\":[{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"input\",\"field\":\"howLong\",\"title\":\"时长\",\"info\":\"\",\"$required\":true,\"props\":{\"type\":\"number\",\"min\":0.5},\"_fc_id\":\"id_Fzkpmfnrd1ujalc\",\"name\":\"ref_Flo5mfnrd1ujamc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"input\",\"_fc_store\":{\"props_keys\":[\"min\"]}}],\"_fc_id\":\"id_F8k6mfnrdmgxapc\",\"name\":\"ref_Fin2mfnrdmgxaqc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"},{\"type\":\"col\",\"props\":{\"span\":12},\"children\":[{\"type\":\"radio\",\"field\":\"timeUnit\",\"title\":\"\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"$required\":false,\"options\":[{\"label\":\"小时\",\"value\":\"h\"},{\"label\":\"天\",\"value\":\"d\"}],\"_fc_id\":\"id_F2tymfnrh87iaxc\",\"name\":\"ref_Fkmqmfnrh87iayc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"radio\",\"style\":{\"marginLeft\":\"20px\"}}],\"_fc_id\":\"id_Fbe7mfnrdmgxarc\",\"name\":\"ref_Ffaamfnrdmgxasc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"col\"}],\"_fc_id\":\"id_Fxvhmfnrdmgxanc\",\"name\":\"ref_Ffp1mfnrdmgxaoc\",\"display\":true,\"hidden\":false,\"_fc_drag_tag\":\"fcRow\"}]", "options": "{\"form\":{\"inline\":false,\"hideRequiredAsterisk\":false,\"labelPosition\":\"right\",\"size\":\"default\",\"labelWidth\":\"125px\"},\"resetBtn\":{\"show\":false,\"innerText\":\"重置\"},\"submitBtn\":{\"show\":true,\"innerText\":\"提交\"},\"formData\":{\"kind\":\"1\",\"howLong\":\"4\",\"timeUnit\":\"h\"}}"}', 20250917183737, 'form', NULL);

-- ----------------------------
-- Table structure for lf_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_history";
CREATE TABLE "public"."lf_history" (
  "id" int8 NOT NULL,
  "time" timestamp(6) NOT NULL,
  "user_id" int8,
  "dept_id" int8,
  "process_id" int8 NOT NULL,
  "action" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int8 NOT NULL,
  "data" jsonb,
  "node_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "post_id" int8,
  "node_type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."lf_history"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_history"."time" IS '操作时间';
COMMENT ON COLUMN "public"."lf_history"."user_id" IS '操作人 id';
COMMENT ON COLUMN "public"."lf_history"."dept_id" IS '操作人的部门';
COMMENT ON COLUMN "public"."lf_history"."process_id" IS '流程 id';
COMMENT ON COLUMN "public"."lf_history"."action" IS '动作（节点的 text 或者单独有个 action 的属性）';
COMMENT ON COLUMN "public"."lf_history"."sort" IS '操作顺序';
COMMENT ON COLUMN "public"."lf_history"."data" IS '当前节点操作的数据';
COMMENT ON COLUMN "public"."lf_history"."node_id" IS '当前操作的节点 id';
COMMENT ON COLUMN "public"."lf_history"."post_id" IS '操作人的岗位';
COMMENT ON COLUMN "public"."lf_history"."node_type" IS '当前节点类型（字典项 lf_node_type）';
COMMENT ON TABLE "public"."lf_history" IS '流程历史';

-- ----------------------------
-- Records of lf_history
-- ----------------------------

-- ----------------------------
-- Table structure for lf_nodes
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_nodes";
CREATE TABLE "public"."lf_nodes" (
  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "process_id" int8 NOT NULL,
  "properties" jsonb,
  "text" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."lf_nodes"."id" IS '主键（节点的id，这里是使用前端生成的 uuid）';
COMMENT ON COLUMN "public"."lf_nodes"."process_id" IS '流程 id';
COMMENT ON COLUMN "public"."lf_nodes"."properties" IS '节点的属性数据';
COMMENT ON COLUMN "public"."lf_nodes"."text" IS '节点上的文字';
COMMENT ON COLUMN "public"."lf_nodes"."type" IS '节点类型（字典项 lf_node_type）';
COMMENT ON TABLE "public"."lf_nodes" IS '流程节点';

-- ----------------------------
-- Records of lf_nodes
-- ----------------------------

-- ----------------------------
-- Table structure for lf_present_process
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_present_process";
CREATE TABLE "public"."lf_present_process" (
  "process_id" int8 NOT NULL,
  "node_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "node_type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."lf_present_process"."process_id" IS '流程 id';
COMMENT ON COLUMN "public"."lf_present_process"."node_id" IS '当前节点 id';
COMMENT ON COLUMN "public"."lf_present_process"."node_type" IS '当前节点类型（字典项 lf_node_type）';
COMMENT ON COLUMN "public"."lf_present_process"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."lf_present_process" IS '当前正在进行的流程';

-- ----------------------------
-- Records of lf_present_process
-- ----------------------------

-- ----------------------------
-- Table structure for lf_process
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_process";
CREATE TABLE "public"."lf_process" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 DEFAULT 0,
  "design_id" int8 NOT NULL,
  "title" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int8 NOT NULL,
  "dept_id" int8,
  "post_id" int8,
  "data" jsonb,
  "release_id" int8 NOT NULL,
  "form_data" json,
  "status" int2,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "cause" varchar(1000) COLLATE "pg_catalog"."default",
  "type" varchar(100) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."lf_process"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_process"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."lf_process"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_process"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."lf_process"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."lf_process"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."lf_process"."design_id" IS '流程图 id（可以知道当前流程是基于什么原始设计运行的）';
COMMENT ON COLUMN "public"."lf_process"."title" IS '流程标题';
COMMENT ON COLUMN "public"."lf_process"."user_id" IS '流程发起人 id';
COMMENT ON COLUMN "public"."lf_process"."dept_id" IS '发起部门';
COMMENT ON COLUMN "public"."lf_process"."post_id" IS '岗位';
COMMENT ON COLUMN "public"."lf_process"."data" IS '流程实时数据(方便实时查看流程走向)';
COMMENT ON COLUMN "public"."lf_process"."release_id" IS '流程发布 id（可以知道当前流程是基于什么版本的设计在运行的）';
COMMENT ON COLUMN "public"."lf_process"."form_data" IS '流程运行过程中的所有表单数据';
COMMENT ON COLUMN "public"."lf_process"."status" IS '状态（1、流程进行中 0、流程已经完成 2、流程已归档 -1、流程中止）';
COMMENT ON COLUMN "public"."lf_process"."remark" IS '备注';
COMMENT ON COLUMN "public"."lf_process"."cause" IS '流程中止等原因';
COMMENT ON COLUMN "public"."lf_process"."type" IS '流程类型（字典项 lf_process_type）';
COMMENT ON COLUMN "public"."lf_process"."icon" IS '图标';
COMMENT ON TABLE "public"."lf_process" IS '流程管理';

-- ----------------------------
-- Records of lf_process
-- ----------------------------

-- ----------------------------
-- Table structure for lf_release
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_release";
CREATE TABLE "public"."lf_release" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 DEFAULT 0,
  "design_id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "data" jsonb NOT NULL,
  "version" int8 NOT NULL,
  "type" varchar(100) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."lf_release"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_release"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."lf_release"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_release"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."lf_release"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."lf_release"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."lf_release"."design_id" IS '流程图 id';
COMMENT ON COLUMN "public"."lf_release"."name" IS '发布名称';
COMMENT ON COLUMN "public"."lf_release"."status" IS '状态(0 关闭 1 打开)';
COMMENT ON COLUMN "public"."lf_release"."description" IS '备注说明';
COMMENT ON COLUMN "public"."lf_release"."data" IS '数据（每个版本的数据）';
COMMENT ON COLUMN "public"."lf_release"."version" IS '版本号（yyyyMMddHHmmss）';
COMMENT ON COLUMN "public"."lf_release"."type" IS '流程类型（字典项 lf_process_type）';
COMMENT ON COLUMN "public"."lf_release"."icon" IS '图标';
COMMENT ON TABLE "public"."lf_release" IS '流程发布表';

-- ----------------------------
-- Records of lf_release
-- ----------------------------
INSERT INTO "public"."lf_release" VALUES (1976941568435253249, 1, '2025-10-11 17:22:54.373141', 1, '2025-10-11 17:22:54.373141', 0, 1968238470464376834, '请假流程.release.20251011172246', 1, '请假审批', '{"edges": [{"id": "e7b489a08442403f9b152354a066c666", "type": "custom-edge-line", "endPoint": {"x": -290, "y": -330}, "pointsList": [{"x": -370, "y": -330}, {"x": -290, "y": -330}], "properties": {}, "startPoint": {"x": -370, "y": -330}, "sourceNodeId": "4b4ed343e6bb46c4a5d93b1f80c3903d", "targetNodeId": "c5fc27d82cc148b9b1f578e03b91dceb"}, {"id": "fb78119e22594a66ae18ce43d869e141", "type": "custom-edge-line", "endPoint": {"x": -110, "y": -330}, "pointsList": [{"x": -190, "y": -330}, {"x": -110, "y": -330}], "properties": {}, "startPoint": {"x": -190, "y": -330}, "sourceNodeId": "c5fc27d82cc148b9b1f578e03b91dceb", "targetNodeId": "64f28d65b09849999052cb9075e693be"}, {"id": "d5fe54d720f1422b8525b0891450e865", "text": {"x": 0, "y": -330, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 50, "y": -330, "id": "-100--370"}, "pointsList": [{"x": -50, "y": -330}, {"x": 50, "y": -330}], "properties": {"condition": "SpEL", "expression": "!#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$"}, "startPoint": {"x": -50, "y": -330, "id": "-200--370"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "f9156ceea6b64afba948251df990c8fa"}, {"id": "14715d7893a14edcbc61aaf8c00e31bf", "text": {"x": -80, "y": -230, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": -80, "y": -180, "id": "-230--220"}, "pointsList": [{"x": -80, "y": -280}, {"x": -80, "y": -180}], "properties": {"condition": "SpEL", "expression": "#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": -80, "y": -280, "id": "-230--320"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "a1960c98cef541ec9736d2d0830a54ab"}, {"id": "1990705cc0694e42b94044cad6861125", "type": "custom-edge-line", "endPoint": {"x": 70, "y": -140, "id": "-80--180"}, "pointsList": [{"x": -30, "y": -140}, {"x": 70, "y": -140}], "properties": {}, "startPoint": {"x": -30, "y": -140, "id": "-180--180"}, "sourceNodeId": "a1960c98cef541ec9736d2d0830a54ab", "targetNodeId": "d256a21fdbf64171a50e84b9bfae6f1e"}, {"id": "b1169e25d3454e4bab53e81677a15c3e", "text": {"x": 175, "y": -140, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 220, "y": -140, "id": "70--180"}, "pointsList": [{"x": 130, "y": -140}, {"x": 220, "y": -140}], "properties": {"condition": "SpEL", "expression": "!#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$"}, "startPoint": {"x": 130, "y": -140, "id": "-20--180"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "00a4d5f8588c450aa0df7b3132677a6a"}, {"id": "2b21609bc3e14e6bb304e7668cbc0e66", "text": {"x": 100, "y": -40, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": 100, "y": 10, "id": "-50--30"}, "pointsList": [{"x": 100, "y": -90}, {"x": 100, "y": 10}], "properties": {"condition": "SpEL", "expression": "#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": 100, "y": -90, "id": "-50--130"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "bd986a38799f4c82b265170f359764d0"}], "nodes": [{"x": -420, "y": -330, "id": "4b4ed343e6bb46c4a5d93b1f80c3903d", "text": {"x": -420, "y": -331, "value": "提交请假申请"}, "type": "custom-node-start", "properties": {"fields": [{"key": "fe7329fc03a30431797da48b3cfbc4eb7", "name": "name", "sort": 0, "type": "STRING", "title": "姓名", "value": "", "disabled": false, "readonly": false}, {"key": "f884e56cbf5584885b77ed08aa66b165c", "name": "kind", "sort": "0", "type": "STRING", "title": "请假类型", "value": "", "disabled": false, "readonly": false}, {"key": "fb7f6bdd80a9040e79d0f64742b282c53", "name": "reason", "sort": 1, "type": "STRING", "title": "请假理由", "value": "", "disabled": false, "readonly": false}, {"key": "f3a5feb6e4e864e798b47b1e204f593f9", "name": "howLong", "sort": 2, "type": "NUMBER", "title": "时长", "disabled": false, "readonly": false}, {"key": "fe62e78c6f79e462db435526424aab7c7", "name": "timeUnit", "sort": 3, "type": "STRING", "title": "时间单位", "value": "", "disabled": false, "readonly": false}], "formBind": {"id": "1968242557142536193", "name": "请假流程开始表单.release.20250917171556"}, "documentation": "请假流程"}}, {"x": -240, "y": -330, "id": "c5fc27d82cc148b9b1f578e03b91dceb", "text": {"x": -240, "y": -330, "value": "小组长审批"}, "type": "custom-node-user", "properties": {"fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "userIdList": [{"id": "1963080001398505474", "name": "小组长"}], "autoExecute": false}}, {"x": -80, "y": -330, "id": "64f28d65b09849999052cb9075e693be", "text": {"x": -80, "y": -330, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 100, "y": -330, "id": "f9156ceea6b64afba948251df990c8fa", "text": {"x": 100, "y": -330, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": -80, "y": -140, "id": "a1960c98cef541ec9736d2d0830a54ab", "text": {"x": -80, "y": -140, "value": "领导审批"}, "type": "custom-node-user", "properties": {"roles": [], "fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "deptIdList": [], "userIdList": [{"id": "1963084850613714945", "name": "部门领导"}, {"id": "1963085401439076353", "name": "总经理"}], "autoExecute": false, "isCountersign": true}}, {"x": 100, "y": -140, "id": "d256a21fdbf64171a50e84b9bfae6f1e", "text": {"x": 100, "y": -140, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 270, "y": -140, "id": "00a4d5f8588c450aa0df7b3132677a6a", "text": {"x": 270, "y": -140, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": 100, "y": 60, "id": "bd986a38799f4c82b265170f359764d0", "text": {"x": 100, "y": 60, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowAlarmArchived", "success": true, "condition": "topic", "autoExecute": true}}]}', 20251011172254, 'normal', 'ep:avatar');
INSERT INTO "public"."lf_release" VALUES (1976941846643437569, 1, '2025-10-11 17:24:00.700696', 1, '2025-10-11 17:24:00.700696', 0, 1968238470464376834, '请假流程.release.20251011172349', 1, '请假审批（可选请假类型）', '{"edges": [{"id": "e7b489a08442403f9b152354a066c666", "type": "custom-edge-line", "endPoint": {"x": -290, "y": -330}, "pointsList": [{"x": -370, "y": -330}, {"x": -290, "y": -330}], "properties": {}, "startPoint": {"x": -370, "y": -330}, "sourceNodeId": "4b4ed343e6bb46c4a5d93b1f80c3903d", "targetNodeId": "c5fc27d82cc148b9b1f578e03b91dceb"}, {"id": "fb78119e22594a66ae18ce43d869e141", "type": "custom-edge-line", "endPoint": {"x": -110, "y": -330}, "pointsList": [{"x": -190, "y": -330}, {"x": -110, "y": -330}], "properties": {}, "startPoint": {"x": -190, "y": -330}, "sourceNodeId": "c5fc27d82cc148b9b1f578e03b91dceb", "targetNodeId": "64f28d65b09849999052cb9075e693be"}, {"id": "d5fe54d720f1422b8525b0891450e865", "text": {"x": 0, "y": -330, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 50, "y": -330, "id": "-100--370"}, "pointsList": [{"x": -50, "y": -330}, {"x": 50, "y": -330}], "properties": {"condition": "SpEL", "expression": "!#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$"}, "startPoint": {"x": -50, "y": -330, "id": "-200--370"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "f9156ceea6b64afba948251df990c8fa"}, {"id": "14715d7893a14edcbc61aaf8c00e31bf", "text": {"x": -80, "y": -230, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": -80, "y": -180, "id": "-230--220"}, "pointsList": [{"x": -80, "y": -280}, {"x": -80, "y": -180}], "properties": {"condition": "SpEL", "expression": "#node_c5fc27d82cc148b9b1f578e03b91dceb_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": -80, "y": -280, "id": "-230--320"}, "sourceNodeId": "64f28d65b09849999052cb9075e693be", "targetNodeId": "a1960c98cef541ec9736d2d0830a54ab"}, {"id": "1990705cc0694e42b94044cad6861125", "type": "custom-edge-line", "endPoint": {"x": 70, "y": -140, "id": "-80--180"}, "pointsList": [{"x": -30, "y": -140}, {"x": 70, "y": -140}], "properties": {}, "startPoint": {"x": -30, "y": -140, "id": "-180--180"}, "sourceNodeId": "a1960c98cef541ec9736d2d0830a54ab", "targetNodeId": "d256a21fdbf64171a50e84b9bfae6f1e"}, {"id": "b1169e25d3454e4bab53e81677a15c3e", "text": {"x": 175, "y": -140, "value": "不予批准"}, "type": "custom-edge-line", "endPoint": {"x": 220, "y": -140, "id": "70--180"}, "pointsList": [{"x": 130, "y": -140}, {"x": 220, "y": -140}], "properties": {"condition": "SpEL", "expression": "!#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$"}, "startPoint": {"x": 130, "y": -140, "id": "-20--180"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "00a4d5f8588c450aa0df7b3132677a6a"}, {"id": "2b21609bc3e14e6bb304e7668cbc0e66", "text": {"x": 100, "y": -40, "value": "准予通过"}, "type": "custom-edge-line", "endPoint": {"x": 100, "y": 10, "id": "-50--30"}, "pointsList": [{"x": 100, "y": -90}, {"x": 100, "y": 10}], "properties": {"condition": "SpEL", "expression": "#node_a1960c98cef541ec9736d2d0830a54ab_approve_$$historyId$$", "documentation": ""}, "startPoint": {"x": 100, "y": -90, "id": "-50--130"}, "sourceNodeId": "d256a21fdbf64171a50e84b9bfae6f1e", "targetNodeId": "bd986a38799f4c82b265170f359764d0"}], "nodes": [{"x": -420, "y": -330, "id": "4b4ed343e6bb46c4a5d93b1f80c3903d", "text": {"x": -420, "y": -331, "value": "提交请假申请"}, "type": "custom-node-start", "properties": {"fields": [{"key": "fe7329fc03a30431797da48b3cfbc4eb7", "name": "name", "sort": 0, "type": "STRING", "title": "姓名", "value": "", "disabled": false, "readonly": false}, {"key": "f884e56cbf5584885b77ed08aa66b165c", "name": "kind", "sort": "0", "type": "STRING", "title": "请假类型", "value": "", "disabled": false, "readonly": false}, {"key": "fb7f6bdd80a9040e79d0f64742b282c53", "name": "reason", "sort": 1, "type": "STRING", "title": "请假理由", "value": "", "disabled": false, "readonly": false}, {"key": "f3a5feb6e4e864e798b47b1e204f593f9", "name": "howLong", "sort": 2, "type": "NUMBER", "title": "时长", "disabled": false, "readonly": false}, {"key": "fe62e78c6f79e462db435526424aab7c7", "name": "timeUnit", "sort": 3, "type": "STRING", "title": "时间单位", "value": "", "disabled": false, "readonly": false}], "formBind": {"id": "1968263061492568065", "name": "请假流程开始表单.release.20250917183731"}, "documentation": "请假流程"}}, {"x": -240, "y": -330, "id": "c5fc27d82cc148b9b1f578e03b91dceb", "text": {"x": -240, "y": -330, "value": "小组长审批"}, "type": "custom-node-user", "properties": {"fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "userIdList": [{"id": "1963080001398505474", "name": "小组长"}], "autoExecute": false}}, {"x": -80, "y": -330, "id": "64f28d65b09849999052cb9075e693be", "text": {"x": -80, "y": -330, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 100, "y": -330, "id": "f9156ceea6b64afba948251df990c8fa", "text": {"x": 100, "y": -330, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": -80, "y": -140, "id": "a1960c98cef541ec9736d2d0830a54ab", "text": {"x": -80, "y": -140, "value": "领导审批"}, "type": "custom-node-user", "properties": {"roles": [], "fields": [{"key": "f78a4356b26964ed4901f6b5e7a0ce2fe", "name": "approve", "sort": 0, "type": "STRING", "title": "是否批准", "value": "", "disabled": false, "readonly": false}, {"key": "f1e8d0c1daf6a475295655e23884f0e45", "name": "reason", "sort": 1, "type": "STRING", "title": "理由", "value": "", "disabled": false, "readonly": false}], "approved": false, "formBind": {"id": "1968260048388460545", "name": "是否审批通过表单.release.20250917182529"}, "deptIdList": [], "userIdList": [{"id": "1963084850613714945", "name": "部门领导"}, {"id": "1963085401439076353", "name": "总经理"}], "autoExecute": false, "isCountersign": true}}, {"x": 100, "y": -140, "id": "d256a21fdbf64171a50e84b9bfae6f1e", "text": {"x": 100, "y": -140, "value": "条件判断"}, "type": "custom-node-judgment", "properties": {"approved": false, "autoExecute": false}}, {"x": 270, "y": -140, "id": "00a4d5f8588c450aa0df7b3132677a6a", "text": {"x": 270, "y": -140, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowProcessAutoDealReject", "success": false, "condition": "topic", "autoExecute": true}}, {"x": 100, "y": 60, "id": "bd986a38799f4c82b265170f359764d0", "text": {"x": 100, "y": 60, "value": "结束"}, "type": "custom-node-end", "properties": {"topic": "flowAlarmArchived", "success": true, "condition": "topic", "autoExecute": true}}]}', 20251011172400, 'normal', 'ep:avatar');

-- ----------------------------
-- Table structure for lf_release_permissions
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_release_permissions";
CREATE TABLE "public"."lf_release_permissions" (
  "id" int8 NOT NULL,
  "release_id" int8 NOT NULL,
  "user_id" int8,
  "dept_id" int8
)
;
COMMENT ON COLUMN "public"."lf_release_permissions"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_release_permissions"."release_id" IS '流程发布 id';
COMMENT ON COLUMN "public"."lf_release_permissions"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."lf_release_permissions"."dept_id" IS '部门id';
COMMENT ON TABLE "public"."lf_release_permissions" IS '流程发布权限表，用于关联指定流程，可以被哪些角色或者用户看到';

-- ----------------------------
-- Records of lf_release_permissions
-- ----------------------------
INSERT INTO "public"."lf_release_permissions" VALUES (1976941568498167809, 1976941568435253249, 1, NULL);
INSERT INTO "public"."lf_release_permissions" VALUES (1976941846706352130, 1976941846643437569, 1, NULL);

-- ----------------------------
-- Table structure for lf_todo
-- ----------------------------
DROP TABLE IF EXISTS "public"."lf_todo";
CREATE TABLE "public"."lf_todo" (
  "id" int8 NOT NULL,
  "node_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" int8,
  "user_id" int8,
  "dept_id" int8,
  "create_time" timestamp(6),
  "process_id" int8 NOT NULL,
  "status" int2 NOT NULL,
  "type" varchar(100) COLLATE "pg_catalog"."default",
  "todo_status" int2,
  "done_status" int2,
  "design_id" int8,
  "todo_type" varchar(100) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."lf_todo"."id" IS '主键';
COMMENT ON COLUMN "public"."lf_todo"."node_id" IS '节点 id';
COMMENT ON COLUMN "public"."lf_todo"."role_id" IS '角色 id';
COMMENT ON COLUMN "public"."lf_todo"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."lf_todo"."dept_id" IS '部门id';
COMMENT ON COLUMN "public"."lf_todo"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."lf_todo"."process_id" IS '流程 id';
COMMENT ON COLUMN "public"."lf_todo"."status" IS '状态（1、待办、0、已办）';
COMMENT ON COLUMN "public"."lf_todo"."type" IS '流程类型（字典项 lf_process_type）';
COMMENT ON COLUMN "public"."lf_todo"."todo_status" IS '待办状态（1、待处理 2、待阅 3、驳回）';
COMMENT ON COLUMN "public"."lf_todo"."done_status" IS '已办状态（这个可以行写自动处理 bean 去自定义状态）';
COMMENT ON COLUMN "public"."lf_todo"."design_id" IS '流程图 id（这里主要是用来查询分类，不用发布 id，是因为发布有版本限制不同版本的 发布 id 是不一样的，但是类型是不变的）';
COMMENT ON COLUMN "public"."lf_todo"."todo_type" IS '待办类型（1、任务待办 2、抄送待办 ...其他类型自定义）';
COMMENT ON TABLE "public"."lf_todo" IS '待办、已办';

-- ----------------------------
-- Records of lf_todo
-- ----------------------------

-- ----------------------------
-- Table structure for oauth2_authorization
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth2_authorization";
CREATE TABLE "public"."oauth2_authorization" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "registered_client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "principal_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "authorization_grant_type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "authorized_scopes" varchar(1000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "attributes" varchar COLLATE "pg_catalog"."default",
  "state" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "authorization_code_value" varchar COLLATE "pg_catalog"."default",
  "authorization_code_issued_at" timestamp(6),
  "authorization_code_expires_at" timestamp(6),
  "authorization_code_metadata" varchar COLLATE "pg_catalog"."default",
  "access_token_value" varchar COLLATE "pg_catalog"."default",
  "access_token_issued_at" timestamp(6),
  "access_token_expires_at" timestamp(6),
  "access_token_metadata" varchar COLLATE "pg_catalog"."default",
  "access_token_type" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "access_token_scopes" varchar(1000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "oidc_id_token_value" varchar COLLATE "pg_catalog"."default",
  "oidc_id_token_issued_at" timestamp(6),
  "oidc_id_token_expires_at" timestamp(6),
  "oidc_id_token_metadata" varchar COLLATE "pg_catalog"."default",
  "refresh_token_value" varchar COLLATE "pg_catalog"."default",
  "refresh_token_issued_at" timestamp(6),
  "refresh_token_expires_at" timestamp(6),
  "refresh_token_metadata" varchar COLLATE "pg_catalog"."default",
  "user_code_value" varchar COLLATE "pg_catalog"."default",
  "user_code_issued_at" timestamp(6),
  "user_code_expires_at" timestamp(6),
  "user_code_metadata" varchar COLLATE "pg_catalog"."default",
  "device_code_value" varchar COLLATE "pg_catalog"."default",
  "device_code_issued_at" timestamp(6),
  "device_code_expires_at" timestamp(6),
  "device_code_metadata" varchar COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."oauth2_authorization" IS '登录管理-记录各个 token 的状态，刷新 token 的在线状态 信息等';

-- ----------------------------
-- Records of oauth2_authorization
-- ----------------------------
INSERT INTO "public"."oauth2_authorization" VALUES ('fdb16c7a-2636-435f-9f05-3a46b25afa8b', '1', 'root', 'taybct', 'all', '{"@class":"java.util.Collections$UnmodifiableMap"}', NULL, NULL, NULL, NULL, NULL, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImY0YTBkMzg3MzA5ODRiOGViYjNmY2Y5MThlODY5M2E4Iiwia2lkIjoiZGFhODhhZDU3NWNkNDBkM2FmY2IzOWQ3MjUxZjA4MWMifQ.eyJ1aWQiOiIxIiwibmJmIjoxNzU5OTk0NzExLCJncmFudF90eXBlIjoidGF5YmN0IiwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJhdG0iOiJ1c2VybmFtZSIsImV4cCI6MTc1OTk5ODMxMSwiaWF0IjoxNzU5OTk0NzExLCJqdGkiOiI3YTZkNTM5Ni1hNDRjLTQ5MDgtYjdkNS1kNGY2YmIyYzVhOWIiLCJjbGllbnRfaWQiOiJ0YXliY3RfcGMiLCJhdXRob3JpdGllcyI6WyIwMDAwMDA6Uk9PVCJdfQ.z-1E4ByQb9AtbGvlelagnv4yRnhVuhC3dxyGAQdPv42iV0csRYK7CMx3iRYLfIJ9G17WqhTufoDXbL8p2yniIh8YWvAkASMkgRda4VlArUV4T6YUCNIStZmDr5rqQpL936zdBS9RfgxQjacQeziwxHFPM4aQnMEkF3c1AfkaBjM3RmzrpnRZbguUjO8rsZMEgDltnvIXiIDNU1GmIZebA1eC04kNO-LlpdjPjzma2mWSBSl_EPoV7Vn5f5XyFpkabbTtAR64prsdqDImE4pYE3B8of0QF8grqguHNs8E-XAb1tmhoLXhM0PBNjXrAKKY1yOEuElzserIKl8dqpvjjw', '2025-10-09 15:25:11.661896', '2025-10-09 16:25:11.661896', '{"@class":"java.util.Collections$UnmodifiableMap","metadata.token.claims":{"@class":"java.util.Collections$UnmodifiableMap","uid":"1","nbf":["java.time.Instant",1759994711.661896000],"grant_type":"taybct","user_name":"root","scope":["java.util.Collections$UnmodifiableSet",["all"]],"atm":"username","exp":["java.time.Instant",1759998311.661896000],"iat":["java.time.Instant",1759994711.661896000],"jti":"7a6d5396-a44c-4908-b7d5-d4f6bb2c5a9b","client_id":"taybct_pc","authorities":["java.util.ArrayList",["000000:ROOT"]]},"metadata.token.invalidated":false}', 'Bearer', 'all', NULL, NULL, NULL, NULL, 'I6havEzNbhE8sf32djXTxqy-CnA6WUjLpfsR6w9g7OzH99PVPs-Cgozjdh8VIYE7NWbEMuY1vBAXgrckDw78Qz34iQMAtxA5ykeMqlGiLULUmQopHGDWwEJRTGmqL6FR', '2025-10-09 15:25:11.73007', '2025-10-10 15:25:11.73007', '{"@class":"java.util.Collections$UnmodifiableMap","metadata.token.invalidated":false}', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."oauth2_authorization" VALUES ('7ae86d6b-f9d3-4e02-86e0-c79f73e5af9a', '1', 'root', 'taybct', 'all', '{"@class":"java.util.Collections$UnmodifiableMap"}', NULL, NULL, NULL, NULL, NULL, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImEzMGQ3YTI4ODQ2MTQ3YTQ5Y2U5NDJiYzYyYTQ3ZTM1Iiwia2lkIjoiN2JiODg3ZGQxNWRhNDcxN2E0ODljOGJjOGMzZjUzODIifQ.eyJ1aWQiOiIxIiwibmJmIjoxNzYwMTc0NTU3LCJncmFudF90eXBlIjoidGF5YmN0IiwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJhdG0iOiJ1c2VybmFtZSIsImV4cCI6MTc2MDE3ODE1NywiaWF0IjoxNzYwMTc0NTU3LCJqdGkiOiIyNzJjNzgyMS1kZjNhLTRlNGQtODViZC0zNzk3NTIyZjg2YWIiLCJjbGllbnRfaWQiOiJ0YXliY3RfcGMiLCJhdXRob3JpdGllcyI6WyIwMDAwMDA6Uk9PVCJdfQ.VcxiFUTBbsDBgSUxt-yiBVQEoanXbsZ9Bz1_oDlO75h0UeFN6Ag3cRiQDM4Z3G-niupDxAIXYkQRx4MALcxxiPBIdVLbnJOprT7miABXDYxCh-RqN8p2Vh1urp-Qyle9ttV390DYJ1mhd2NJjqFlKUGNBAQcrp-SasJ18n-jJBBRnrIdstzcFy4Xwga11YDeM5M0z1VWynjGY_kk3ciOb223hGG_O6TVv3vh0h0yQZabUOzcsGFxmnLZX_BzYWF85c8T6K11fuUqchqqALikXR-czqYN9osxLW-ouk-Ko8flSl95BOY9Q0m6AaIszN4SMygQLxUwO0sMwmSJRQK0BA', '2025-10-11 17:22:37.044355', '2025-10-11 18:22:37.044355', '{"@class":"java.util.Collections$UnmodifiableMap","metadata.token.claims":{"@class":"java.util.Collections$UnmodifiableMap","uid":"1","nbf":["java.time.Instant",1760174557.044354500],"grant_type":"taybct","user_name":"root","scope":["java.util.Collections$UnmodifiableSet",["all"]],"atm":"username","exp":["java.time.Instant",1760178157.044354500],"iat":["java.time.Instant",1760174557.044354500],"jti":"272c7821-df3a-4e4d-85bd-3797522f86ab","client_id":"taybct_pc","authorities":["java.util.ArrayList",["000000:ROOT"]]},"metadata.token.invalidated":false}', 'Bearer', 'all', NULL, NULL, NULL, NULL, 'rgOc5gqbfhUAhLNFyukgwRUdLrU2Rst44xdKVi_pezxmHomBAGFdK3v3zdc3aqaBtM_Yu1SiwaM4ArQZUvmgKcpXm560emMDxxzfKKiOhXHA4r617FYpNEOPzl1sRCQw', '2025-10-11 17:22:37.062108', '2025-10-12 17:22:37.062108', '{"@class":"java.util.Collections$UnmodifiableMap","metadata.token.invalidated":false}', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth2_authorization_consent";
CREATE TABLE "public"."oauth2_authorization_consent" (
  "registered_client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "principal_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "authorities" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON TABLE "public"."oauth2_authorization_consent" IS 'oauth2 授权管理表';

-- ----------------------------
-- Records of oauth2_authorization_consent
-- ----------------------------

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth2_registered_client";
CREATE TABLE "public"."oauth2_registered_client" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id_issued_at" timestamp(6) NOT NULL,
  "client_secret" varchar(200) COLLATE "pg_catalog"."default",
  "client_secret_expires_at" timestamp(6),
  "client_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "client_authentication_methods" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "authorization_grant_types" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "redirect_uris" varchar(1000) COLLATE "pg_catalog"."default",
  "scopes" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "client_settings" varchar(2000) COLLATE "pg_catalog"."default" NOT NULL,
  "token_settings" varchar(2000) COLLATE "pg_catalog"."default" NOT NULL,
  "post_logout_redirect_uris" varchar(1000) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."oauth2_registered_client" IS 'oauth2 客户端表';

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO "public"."oauth2_registered_client" VALUES ('1', 'taybct_pc', '2023-01-03 10:14:24', '$2a$10$MOJpMmMoEcgQ.dMobC294uakWMSB68bw.vOifVq/YzwbRvf/MbQja', NULL, 'taybct_pc', 'client_secret_basic', 'taybct_refresh,refresh_token,password,client_credentials,authorization_code,taybct,sms,wechat_qr_code,pki', 'https://www.baidu.com', 'all', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}', '');
INSERT INTO "public"."oauth2_registered_client" VALUES ('1947936658897342465', 'test', '2025-07-23 16:27:45.498924', '$2a$10$BHWwbuqCvSdTCno9LBPI.OqsUDo4L8Ac8HqUoLbutGXif5uZy.5s6', NULL, 'test', 'client_secret_basic', 'code', 'https://www.baidu.com', 'all', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",604800.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}', '');

-- ----------------------------
-- Table structure for scheduled_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."scheduled_log";
CREATE TABLE "public"."scheduled_log" (
  "id" int8 NOT NULL,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default",
  "task_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "params" text COLLATE "pg_catalog"."default",
  "message" text COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL,
  "exception_info" text COLLATE "pg_catalog"."default",
  "start_time" timestamp(6) NOT NULL,
  "stop_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."scheduled_log"."id" IS '主键';
COMMENT ON COLUMN "public"."scheduled_log"."tenant_id" IS '租户id';
COMMENT ON COLUMN "public"."scheduled_log"."task_key" IS '任务键';
COMMENT ON COLUMN "public"."scheduled_log"."description" IS '任务描述';
COMMENT ON COLUMN "public"."scheduled_log"."params" IS '任务启动参数';
COMMENT ON COLUMN "public"."scheduled_log"."message" IS '日志信息';
COMMENT ON COLUMN "public"."scheduled_log"."status" IS '状态(1 正常 0 失败)';
COMMENT ON COLUMN "public"."scheduled_log"."exception_info" IS '异常信息';
COMMENT ON COLUMN "public"."scheduled_log"."start_time" IS '任务开始执行时间';
COMMENT ON COLUMN "public"."scheduled_log"."stop_time" IS '任务结束执行时间';
COMMENT ON COLUMN "public"."scheduled_log"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."scheduled_log" IS '调度日志';

-- ----------------------------
-- Records of scheduled_log
-- ----------------------------

-- ----------------------------
-- Table structure for scheduled_task
-- ----------------------------
DROP TABLE IF EXISTS "public"."scheduled_task";
CREATE TABLE "public"."scheduled_task" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default",
  "unique_key" int8,
  "task_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "cron" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "auto_start" int2 NOT NULL,
  "sort" int4 NOT NULL,
  "params" text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."scheduled_task"."id" IS '主键';
COMMENT ON COLUMN "public"."scheduled_task"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."scheduled_task"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."scheduled_task"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."scheduled_task"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."scheduled_task"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."scheduled_task"."tenant_id" IS '租户id';
COMMENT ON COLUMN "public"."scheduled_task"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."scheduled_task"."task_key" IS '任务键';
COMMENT ON COLUMN "public"."scheduled_task"."description" IS '任务描述';
COMMENT ON COLUMN "public"."scheduled_task"."cron" IS 'cron 表达式';
COMMENT ON COLUMN "public"."scheduled_task"."auto_start" IS '是否自动启动(1 是 0 否)';
COMMENT ON COLUMN "public"."scheduled_task"."sort" IS '排序';
COMMENT ON COLUMN "public"."scheduled_task"."params" IS '任务启动参数';
COMMENT ON TABLE "public"."scheduled_task" IS '调度任务';

-- ----------------------------
-- Records of scheduled_task
-- ----------------------------
INSERT INTO "public"."scheduled_task" VALUES (1972125945096101889, 1, '2025-09-28 10:27:20.302424', 1, '2025-09-29 14:11:18.253906', 0, '000000', 1972125945096101889, 'demo1', '测试任务1', '0/30 * * * * ?', 0, 1, '{
    "tenantId": "000000",
    "toUserId": "1",
    "message": "测试消息"
}');
INSERT INTO "public"."scheduled_task" VALUES (1972199489284993025, 1, '2025-09-28 15:19:34.608932', 1, '2025-09-29 14:11:20.046253', 0, '000000', 0, 'demo2', '测试任务2', '0/15 * * * * ?', 0, 2, '{
    "tenantId": "000000",
    "toUserId": "1",
    "message": "测试消息"
}');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept" (
  "id" int8 NOT NULL,
  "create_user" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 NOT NULL,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default" NOT NULL,
  "unique_key" int8 DEFAULT 0,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "full_name" varchar(100) COLLATE "pg_catalog"."default",
  "code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "pid" int8,
  "pid_all" varchar(1000) COLLATE "pg_catalog"."default",
  "type" varchar(100) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dept"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_dept"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_dept"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dept"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_dept"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dept"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_dept"."tenant_id" IS '租户id';
COMMENT ON COLUMN "public"."sys_dept"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."sys_dept"."name" IS '部门名';
COMMENT ON COLUMN "public"."sys_dept"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dept"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dept"."full_name" IS '全称';
COMMENT ON COLUMN "public"."sys_dept"."code" IS '部门，组织机构代码';
COMMENT ON COLUMN "public"."sys_dept"."pid" IS '父 id';
COMMENT ON COLUMN "public"."sys_dept"."pid_all" IS '所有的父 id（可以多级）';
COMMENT ON COLUMN "public"."sys_dept"."type" IS '部门类型';
COMMENT ON TABLE "public"."sys_dept" IS '部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO "public"."sys_dept" VALUES (0, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '占位', 0, '占位', '占位', '0', NULL, NULL, '1');
INSERT INTO "public"."sys_dept" VALUES (1, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '顶级部门', 0, '顶级部门', '顶级部门', '1', 0, '0', '1');
INSERT INTO "public"."sys_dept" VALUES (2, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '二级部门', 0, '二级部门', '二级部门', '2', 1, '0,1', '3');
INSERT INTO "public"."sys_dept" VALUES (3, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '三级部门', 0, '三级部门', '三级部门', '3', 2, '0,1,2', '3');
INSERT INTO "public"."sys_dept" VALUES (4, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '四级部门', 0, '四级部门', '四级部门', '4', 3, '0,1,2,3', '3');
INSERT INTO "public"."sys_dept" VALUES (5, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '五级部门', 0, '五级部门', '五级部门', '5', 4, '0,1,2,3,4', '3');
INSERT INTO "public"."sys_dept" VALUES (6, 1, '2024-08-31 15:52:34', NULL, '2024-08-31 15:52:38', 0, '000000', 0, '六级部门', 0, '六级部门', '六级部门', '6', 5, '0,1,2,3,4,5', '3');
INSERT INTO "public"."sys_dept" VALUES (1943511731406491650, 1, '2025-07-11 11:24:40.134908', 1, '2025-07-11 11:57:22.056', 1, '000000', 1943511731406491650, '三级二', 1, '三级第二个部门', '三级第二个部门', '0302', 2, '0,1,2', '3');
INSERT INTO "public"."sys_dept" VALUES (1943519889076113410, 1, '2025-07-11 11:57:05.069084', 1, '2025-07-11 11:57:13.724', 1, '000000', 1943519889076113410, 'ggg', 0, 'ggg', 'ggg', 'ggg', 1943511912596230146, '0,1,2,1943511731406491650,1943511912596230146', '3');
INSERT INTO "public"."sys_dept" VALUES (1943511912596230146, 1, '2025-07-11 11:25:23.32695', 1, '2025-07-11 11:25:23.32695', 1, '000000', 0, '三级二一', 0, '第级第二个部门的第一个子部门', '第级第二个部门的第一个子部门', '030201', 1943511731406491650, '0,1,2,1943511731406491650', '3');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "dict_code" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_key" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_val" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "css_class" varchar(128) COLLATE "pg_catalog"."default",
  "status_class" varchar(128) COLLATE "pg_catalog"."default",
  "unique_key" int8
)
;
COMMENT ON COLUMN "public"."sys_dict"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_dict"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_dict"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dict"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_dict"."dict_code" IS '类型';
COMMENT ON COLUMN "public"."sys_dict"."dict_key" IS '键 例：1';
COMMENT ON COLUMN "public"."sys_dict"."dict_val" IS '值 例：男';
COMMENT ON COLUMN "public"."sys_dict"."status" IS '是否可用 1可用、0不可用';
COMMENT ON COLUMN "public"."sys_dict"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict"."css_class" IS '样式名';
COMMENT ON COLUMN "public"."sys_dict"."status_class" IS '状态类型';
COMMENT ON COLUMN "public"."sys_dict"."unique_key" IS '逻辑唯一键';
COMMENT ON TABLE "public"."sys_dict" IS '字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO "public"."sys_dict" VALUES (1539854335129067522, 1, '2022-06-23 14:14:17', 1, '2022-09-23 16:29:22', 1, 'system-gender', '0', '男', 1, '', 0, '', '', 1573228430524596226);
INSERT INTO "public"."sys_dict" VALUES (1539854373901213697, 1, '2022-06-23 14:14:27', 1, '2022-09-23 16:29:27', 1, 'system-gender', '1', '女', 1, '', 1, '', '', 1573228450313322497);
INSERT INTO "public"."sys_dict" VALUES (1539854964199170050, 1, '2022-06-23 14:16:47', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'authorization_code', '授权码', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855052321497090, 1, '2022-06-23 14:17:08', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'implicit', '简化模式', 1, '', 1, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855691554398210, 1, '2022-06-23 14:19:41', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'password', '密码模式', 1, '', 2, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855762152923138, 1, '2022-06-23 14:19:57', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'client_credentials', '客户端模式', 1, '', 3, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855829513445377, 1, '2022-06-23 14:20:14', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'refresh_token', '刷新 token 令牌', 1, '', 4, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855965161431041, 1, '2022-06-23 14:20:46', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'wechat_qr_code', '微信二维码', 1, '', 6, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1539856069738012673, 1, '2022-06-23 14:21:11', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'sms', '手机号(短信)', 1, '', 7, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1570619878492454913, 1, '2022-09-16 11:45:34', 1, '2022-09-23 16:26:11', 0, 'user-type', '00', '系统用户', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1571756343934873601, 1, '2022-09-19 15:01:29', 1, '2022-09-23 16:26:11', 0, 'menu-type', 'M', '目录', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1571756398519545858, 1, '2022-09-19 15:01:42', 1, '2022-09-23 16:26:11', 0, 'menu-type', 'C', '菜单', 1, '', 1, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1572496927817646082, 1, '2022-09-21 16:04:18', 1, '2022-09-23 16:26:11', 0, 'system-is', 'true', '是', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1572497045488844801, 1, '2022-09-21 16:04:46', 1, '2022-09-23 16:26:11', 0, 'system-is', 'false', '否', 1, '', 1, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1573228332004589570, 1, '2022-09-23 16:30:38', 1, '2022-09-23 16:29:04', 1, 'system-gender', '3', '未知', 1, '', 0, '', '', 1573228352636370946);
INSERT INTO "public"."sys_dict" VALUES (1573228535860346881, 1, '2022-09-23 16:31:27', 1, '2022-09-23 16:31:27', 0, 'system-gender', '1', '男', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1573229306135887873, 1, '2022-09-23 16:34:30', 1, '2022-09-23 16:34:30', 0, 'system-gender', '2', '女', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1579411248996839425, 1, '2022-10-10 17:59:20', 1, '2022-10-10 18:35:54', 0, 'notice-type', '1', 'userId', 1, '使用 userId 关联了用户的数据', 0, '', 'warning', 0);
INSERT INTO "public"."sys_dict" VALUES (1579412543744929794, 1, '2022-10-10 18:04:29', 1, '2022-10-10 18:36:04', 0, 'notice-type', '2', 'roleId', 1, '使用了 roleId 参数关联了角色的数据', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1640961624411418625, 1, '2023-03-29 14:18:33.507', 1, '2023-03-29 14:18:33.508', 0, 'user-type', '03', '微信用户', 1, '', 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1570619926655647745, 1, '2022-09-16 11:45:46', 1, '2023-03-29 14:21:39.053', 0, 'user-type', '01', '临时用户', 1, '', 1, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1640968617578713090, 1, '2023-03-29 14:46:20.806', 1, '2023-03-29 14:46:20.807', 0, 'user-type', '04', '外来用户', 1, '', 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1668869050355597314, 1, '2023-06-14 14:32:42.348', 1, '2023-06-14 14:32:42.348', 0, 'is', '1', '是', 1, '', 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1668869084832776194, 1, '2023-06-14 14:32:50.568', 1, '2023-06-14 14:32:50.568', 0, 'is', '0', '否', 1, '', 1, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652455565914113, 1, '2023-07-11 14:28:27.92', 1, '2023-07-11 14:28:27.92', 0, 'lf_node_type', 'custom-node-start', '开始节点', 1, '', 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652511895416834, 1, '2023-07-11 14:28:41.349', 1, '2023-07-11 14:28:41.349', 0, 'lf_node_type', 'custom-node-user', '用户任务', 1, '', 1, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652564928196609, 1, '2023-07-11 14:28:53.992', 1, '2023-07-11 14:28:53.992', 0, 'lf_node_type', 'custom-node-service', '系统任务', 1, '', 2, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652638152355842, 1, '2023-07-11 14:29:11.451', 1, '2023-07-11 14:29:11.451', 0, 'lf_node_type', 'custom-node-judgment', '条件判断', 1, '', 3, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652695337496577, 1, '2023-07-11 14:29:25.085', 1, '2023-07-11 14:29:25.085', 0, 'lf_node_type', 'custom-node-end', '结束节点', 1, '', 4, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1678652766699384834, 1, '2023-07-11 14:29:42.099', 1, '2023-07-11 14:29:42.099', 0, 'lf_node_type', 'custom-group', '分组', 1, '', 5, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1539855889231945730, 1, '2022-06-23 14:20:28', 1, '2022-09-23 16:26:11', 0, 'authorized-grant-type', 'taybct', '同步密码模式', 1, '', 5, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1633006373893042178, 1, '2023-03-07 15:27:13.981285', 1, '2023-03-07 15:27:13.982937', 0, 'authorized-grant-type', 'taybct_refresh', 'taybct_refresh', 1, '', 0, '', '', 0);
INSERT INTO "public"."sys_dict" VALUES (1679680520972615681, 1, '2023-07-14 10:33:37.811', 1, '2025-07-17 16:52:35.030509', 0, 'lf_process_type', 'normal', '普通流程', 1, '', 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1538729077403729921, 1, '2022-06-20 11:42:55', 1, '2025-07-18 11:52:39.294871', 0, 'system-status', '0', '禁用', 1, '', 1, '', 'danger', 0);
INSERT INTO "public"."sys_dict" VALUES (1538729015877484545, 1, '2022-06-20 11:42:40', 1, '2025-07-18 11:52:45.974772', 0, 'system-status', '1', '启用', 1, '', 0, '', 'success', 0);
INSERT INTO "public"."sys_dict" VALUES (1964007849411747841, 1, '2025-09-06 00:48:55.458958', 1, '2025-09-06 00:48:55.459958', 0, 'lf_form_type', 'form', '表单', 1, NULL, 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1960154603698368513, 1, '2025-08-26 09:37:30.095657', 1, '2025-08-26 09:37:30.095657', 0, 'notice-topic', 'SYS_NOTICE', '系统通知', 1, NULL, 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1960154704332304385, 1, '2025-08-26 09:37:54.096884', 1, '2025-08-26 09:38:01.372155', 0, 'notice-topic', 'SYS_MESSAGE', '系统消息', 1, NULL, 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1960154806581047297, 1, '2025-08-26 09:38:18.467045', 1, '2025-08-26 09:38:18.467045', 0, 'notice-topic', 'SYS_TODO', '系统待办', 1, NULL, 0, ' ', ' ', 0);
INSERT INTO "public"."sys_dict" VALUES (1964008370499493889, 1, '2025-09-06 00:50:59.696286', 1, '2025-09-08 14:33:08.52568', 0, 'lf_form_type', 'component', '组件', 1, NULL, 0, ' ', ' ', 0);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_type";
CREATE TABLE "public"."sys_dict_type" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "title" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_code" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "unique_key" int8
)
;
COMMENT ON COLUMN "public"."sys_dict_type"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_dict_type"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_type"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_dict_type"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dict_type"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_dict_type"."title" IS '标题';
COMMENT ON COLUMN "public"."sys_dict_type"."dict_code" IS '代码';
COMMENT ON COLUMN "public"."sys_dict_type"."status" IS '是否可用 1可用、0不可用';
COMMENT ON COLUMN "public"."sys_dict_type"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict_type"."unique_key" IS '逻辑唯一键';
COMMENT ON TABLE "public"."sys_dict_type" IS '字段类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO "public"."sys_dict_type" VALUES (1539853897667354625, 1, '2022-06-23 14:12:33', 1, '2022-09-23 16:26:04', 0, '性别', 'system-gender', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1539854788155842562, 1, '2022-06-23 14:16:05', 1, '2022-09-23 16:26:04', 0, '客户端授权模式', 'authorized-grant-type', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1570614325850521601, 1, '2022-09-16 11:23:31', 1, '2022-09-23 16:26:04', 0, '用户类型', 'user-type', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1571756222585270273, 1, '2022-09-19 15:01:00', 1, '2022-09-23 16:26:04', 0, '菜单类型', 'menu-type', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1572496669654040577, 1, '2022-09-21 16:03:16', 1, '2022-09-23 16:26:04', 0, '系统断言', 'system-is', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1579408522250448898, 1, '2022-10-10 17:48:30', 1, '2022-10-10 17:48:30', 0, '通知类型', 'notice-type', 1, '通知类型', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1668869003685576706, 1, '2023-06-14 14:32:31.218', 1, '2023-06-14 14:32:31.218', 0, '是否', 'is', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1678652327878717442, 1, '2023-07-11 14:27:57.476', 1, '2023-07-11 14:27:57.476', 0, '流程节点类型', 'lf_node_type', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1679680386712944641, 1, '2023-07-14 10:33:05.801', 1, '2025-07-17 16:52:35.04473', 0, '流程类型', 'lf_process_type', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1537328288877662209, 1, '2022-06-16 14:56:41', 1, '2025-07-18 11:52:39.299656', 0, '系统状态', 'system-status', 1, '', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1960154447280189441, 1, '2025-08-26 09:36:52.80263', 1, '2025-08-26 09:36:52.80263', 0, '通知主题（类型）', 'notice-topic', 1, '区分通知/消息/待办等通知的类型', 0);
INSERT INTO "public"."sys_dict_type" VALUES (1964007608625143810, 1, '2025-09-06 00:47:58.051979', 1, '2025-09-06 00:47:58.051979', 0, '流程表单类型', 'lf_form_type', 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_file";
CREATE TABLE "public"."sys_file" (
  "id" int8 NOT NULL,
  "path" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "upload_time" timestamp(6) NOT NULL,
  "upload_user" int8,
  "update_time" timestamp(6) NOT NULL,
  "linked" int2 NOT NULL,
  "linked_table" varchar(100) COLLATE "pg_catalog"."default",
  "linked_table_id" int8,
  "is_deleted" int2 NOT NULL,
  "manage_type" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "file_type" varchar(255) COLLATE "pg_catalog"."default",
  "update_user" int8,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_file"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_file"."path" IS '文件名（路径）';
COMMENT ON COLUMN "public"."sys_file"."upload_time" IS '上传时间';
COMMENT ON COLUMN "public"."sys_file"."upload_user" IS '上传人';
COMMENT ON COLUMN "public"."sys_file"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sys_file"."linked" IS '是否在使用中';
COMMENT ON COLUMN "public"."sys_file"."linked_table" IS '关联的表';
COMMENT ON COLUMN "public"."sys_file"."linked_table_id" IS '关联的表的 id';
COMMENT ON COLUMN "public"."sys_file"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_file"."manage_type" IS '文件管理服务器类型（local,oss,fdfs,minio）';
COMMENT ON COLUMN "public"."sys_file"."file_type" IS '文件类型';
COMMENT ON COLUMN "public"."sys_file"."update_user" IS '更新人';
COMMENT ON COLUMN "public"."sys_file"."tenant_id" IS '租户id';
COMMENT ON TABLE "public"."sys_file" IS '文件管理';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO "public"."sys_file" VALUES (1968197430646771713, '/2025/09/17/7cde326c747b4708b26d55810ab6b58b.png', '2025-09-17 14:16:49.477207', 1, '2025-09-17 14:16:49.477207', 0, NULL, NULL, 0, 'local', 'png', 1, NULL);
INSERT INTO "public"."sys_file" VALUES (1968197620992675841, '/2025/09/17/3f3d0e9ea16b40ea90543e85dfa2b7fa.png', '2025-09-17 14:17:34.848572', 1, '2025-09-17 14:17:34.848572', 0, NULL, NULL, 0, 'local', 'png', 1, NULL);
INSERT INTO "public"."sys_file" VALUES (1969954430866968578, '/2025/09/22/559ee50d2d3640ddbb2f468612d22323.docx', '2025-09-22 10:38:30.973013', 1, '2025-09-22 10:38:30.977184', 0, NULL, NULL, 0, 'local', NULL, 1, NULL);
INSERT INTO "public"."sys_file" VALUES (1969958015533072385, '/2025/09/22/68b86331dd154e22a0cf98562838abb0.docx', '2025-09-22 10:52:45.619617', 1, '2025-09-22 10:52:45.619617', 0, NULL, NULL, 0, 'local', 'zip', 1, NULL);

-- ----------------------------
-- Table structure for sys_history_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_history_record";
CREATE TABLE "public"."sys_history_record" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "table_name" varchar(100) COLLATE "pg_catalog"."default",
  "primary_key" varchar(32) COLLATE "pg_catalog"."default",
  "json_data" json,
  "operate_type" int2,
  "primary_value" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_history_record"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_history_record"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_history_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_history_record"."table_name" IS '表名';
COMMENT ON COLUMN "public"."sys_history_record"."primary_key" IS '主键名';
COMMENT ON COLUMN "public"."sys_history_record"."json_data" IS '历史记录（JSON）';
COMMENT ON COLUMN "public"."sys_history_record"."operate_type" IS '操作类型（修改2，删除3）';
COMMENT ON COLUMN "public"."sys_history_record"."primary_value" IS '主键值';
COMMENT ON TABLE "public"."sys_history_record" IS '历史记录表';

-- ----------------------------
-- Records of sys_history_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" int8,
  "always_show" int2,
  "props" varchar(500) COLLATE "pg_catalog"."default",
  "sort" int4,
  "route_name" varchar(32) COLLATE "pg_catalog"."default",
  "route_path" varchar(255) COLLATE "pg_catalog"."default",
  "component" varchar(255) COLLATE "pg_catalog"."default",
  "redirect" varchar(255) COLLATE "pg_catalog"."default",
  "is_cache" int2,
  "menu_type" char(1) COLLATE "pg_catalog"."default",
  "hidden" int2,
  "status" int2,
  "icon" varchar(100) COLLATE "pg_catalog"."default",
  "is_blank" int2,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2
)
;
COMMENT ON COLUMN "public"."sys_menu"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_menu"."name" IS '菜单名';
COMMENT ON COLUMN "public"."sys_menu"."parent_id" IS '父级菜单';
COMMENT ON COLUMN "public"."sys_menu"."always_show" IS '是否收缩子菜单（当所有子菜单只有一个时，1、收缩，0不收缩）';
COMMENT ON COLUMN "public"."sys_menu"."props" IS '路由参数,JSON 字符串';
COMMENT ON COLUMN "public"."sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_menu"."route_name" IS '路由名';
COMMENT ON COLUMN "public"."sys_menu"."route_path" IS '路由路径';
COMMENT ON COLUMN "public"."sys_menu"."component" IS '组件路径';
COMMENT ON COLUMN "public"."sys_menu"."redirect" IS '外链地址';
COMMENT ON COLUMN "public"."sys_menu"."is_cache" IS '是否缓存（1缓存 0不缓存）';
COMMENT ON COLUMN "public"."sys_menu"."menu_type" IS '菜单类型（M目录 C菜单 L外部连接）';
COMMENT ON COLUMN "public"."sys_menu"."hidden" IS '菜单状态（0显示 1隐藏）';
COMMENT ON COLUMN "public"."sys_menu"."status" IS '菜单状态（1正常 0停用）';
COMMENT ON COLUMN "public"."sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."sys_menu"."is_blank" IS '是否新开窗口 1 是 0 否';
COMMENT ON COLUMN "public"."sys_menu"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_menu"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_menu"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_menu"."is_deleted" IS '是否已删除';
COMMENT ON TABLE "public"."sys_menu" IS '菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO "public"."sys_menu" VALUES (1963544670945005572, '我提交的', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{},"params":{}}', 5, 'FlowSubmitted', '/lf/process/userRequestList', 'lf/process/me', NULL, 1, 'C', 0, 1, 'ion:push', 1, 1, '2025-09-04 18:08:25.108724', 1, '2025-09-15 16:21:06.469596', 0);
INSERT INTO "public"."sys_menu" VALUES (7, '用户管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'user', 'user', 'system/user/index', NULL, 1, 'C', 0, 1, 'bxs:user', 0, NULL, NULL, 1, '2022-08-24 15:48:43', 0);
INSERT INTO "public"."sys_menu" VALUES (1475197863846027266, '客户端管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 7, 'client', 'client', 'system/client/index', NULL, 1, 'C', 0, 1, 'gridicons:share-computer', 0, 1, '2022-06-17 10:39:06', 1, '2022-09-21 16:18:12', 0);
INSERT INTO "public"."sys_menu" VALUES (1572500323467444226, '字典信息', 4, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 5, 'dict', 'dict/:dictCode', 'system/dict/index', NULL, 0, 'C', 1, 1, 'arcticons:colordict', 0, 1, '2022-09-21 16:17:47', 1, '2022-10-10 17:51:59', 0);
INSERT INTO "public"."sys_menu" VALUES (1537625854055940097, '参数管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 6, 'config', 'config', 'system/config/index', NULL, 1, 'C', 0, 1, 'icon-park-solid:config', 0, 1, '2022-06-17 10:39:06', 1, '2022-09-21 16:18:02', 0);
INSERT INTO "public"."sys_menu" VALUES (1575299088586797058, '租户管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 8, 'tenant', 'tenant', 'system/tenant/index', NULL, 1, 'C', 0, 1, 'fa-solid:house-user', 0, 1, '2022-09-29 09:39:05', 1, '2022-09-29 09:39:05', 0);
INSERT INTO "public"."sys_menu" VALUES (4, '系统管理', 0, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 99, 'system', '/system', 'Layout', '/system/user', 1, 'M', 0, 1, 'grommet-icons:system', 0, NULL, NULL, 1, '2023-06-28 13:49:36.402', 0);
INSERT INTO "public"."sys_menu" VALUES (1563061288516792322, '日志管理', 1627485035375833090, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 10, 'adminLog', 'admin-log', 'system/log/index', NULL, 1, 'C', 0, 1, 'bx:log-in', 0, 1, '2022-08-26 15:10:26', 1, '2022-09-29 09:39:14', 0);
INSERT INTO "public"."sys_menu" VALUES (1627485035375833090, '系统监控', 0, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 99, 'monitoring', '/monitoring', 'Layout', '/monitoring/scheduling-task', 1, 'M', 0, 1, 'eos-icons:monitoring', 0, 1, '2023-02-20 09:47:24', 1, '2023-06-14 16:04:56.904', 0);
INSERT INTO "public"."sys_menu" VALUES (1631227390958325762, '任务调度日志', 1627485035375833090, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 1, 'SchedulingTaskLog', 'scheduling-task-log', 'scheduling/log', NULL, 1, 'C', 0, 1, 'icon-park-solid:log', 0, 1, '2023-03-02 17:38:11.406762', 1, '2023-03-02 17:38:29.894264', 0);
INSERT INTO "public"."sys_menu" VALUES (1529380639935463426, '百度AI', 0, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":1,"transition":{}}', 1001, 'BaiduAI', 'baidu-ai', 'https://chat.baidu.com/', '', 1, 'C', 0, 1, 'ri:baidu-fill', 0, 1, '2022-06-25 12:08:43', 1, '2025-09-17 15:29:02.799588', 0);
INSERT INTO "public"."sys_menu" VALUES (1547818807554617346, '二级菜单2', 1547787400883089410, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'ceshi2', 'ceshi2-2', 'nested/menu2/index', NULL, 1, 'C', 0, 1, 'bx:menu-alt-left', 0, 1, '2022-07-15 13:42:15', 1, '2022-08-26 11:44:51', 0);
INSERT INTO "public"."sys_menu" VALUES (1547835699233173505, '二级菜单1', 1547787400883089410, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'ceshi1', 'ceshi2-1', 'Layout', NULL, 1, 'M', 0, 1, 'bx:menu-alt-left', 0, 1, '2022-07-15 14:49:23', 1, '2022-08-26 14:04:50', 0);
INSERT INTO "public"."sys_menu" VALUES (6, '角色管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 1, 'role', 'role', 'system/role/index', NULL, 1, 'C', 0, 1, 'carbon:user-role', 0, NULL, NULL, 1, '2022-08-24 16:06:27', 0);
INSERT INTO "public"."sys_menu" VALUES (1547787400883089410, '一级菜单', 0, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 97, 'ceshi', '/ceshi', 'Layout', NULL, 1, 'M', 0, 1, 'bx:menu-alt-left', 0, 1, '2022-07-15 11:37:28', 1, '2022-08-26 14:26:03', 0);
INSERT INTO "public"."sys_menu" VALUES (5, '菜单管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'menu', 'menu', 'system/menu/index', NULL, 1, 'C', 0, 1, 'bi:menu-button-fill', 0, NULL, NULL, 1, '2022-08-24 16:08:14', 0);
INSERT INTO "public"."sys_menu" VALUES (1795660210988138497, '部门管理', 4, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'Dept', 'dept', 'system/dept/index', NULL, 1, 'C', 0, 1, 'eos-icons:organization', 0, 1, '2024-05-29 11:35:30.712', 1, '2024-05-29 11:35:30.712', 0);
INSERT INTO "public"."sys_menu" VALUES (1962731950251192321, '流程发布列表', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowDesignRL', '/lf/rl/:id', 'lf/release/index', NULL, 0, 'C', 1, 1, NULL, 1, 1, '2025-09-02 12:18:57.395281', 1, '2025-09-04 18:04:03.318503', 0);
INSERT INTO "public"."sys_menu" VALUES (1963542266031755265, '我收到的', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 4, 'FlowReceived', '/lf/received', 'Layout', NULL, 1, 'M', 0, 1, 'material-symbols:call-received', 1, 1, '2025-09-04 17:58:51.730793', 1, '2025-09-04 18:04:16.398395', 0);
INSERT INTO "public"."sys_menu" VALUES (1963544670945005570, '已办', 1963542266031755265, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowDone', '/lf/received/done', 'lf/received/done', NULL, 1, 'C', 0, 1, 'ant-design:file-done-outlined', 1, 1, '2025-09-04 18:08:25.108724', 1, '2025-09-04 18:09:00.294234', 0);
INSERT INTO "public"."sys_menu" VALUES (1963544670945005569, '待办', 1963542266031755265, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 1, 'FlowTodo', '/lf/received/todo', 'lf/received/todo', NULL, 1, 'C', 0, 1, 'wpf:todo-list', 1, 1, '2025-09-04 18:08:25.108724', 1, '2025-09-04 18:09:00.294234', 0);
INSERT INTO "public"."sys_menu" VALUES (1963544670945005571, '抄送', 1963542266031755265, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 3, 'FlowCC', '/lf/received/cc', 'lf/received/cc', NULL, 1, 'C', 0, 1, 'lucide:rotate-ccw-square', 1, 1, '2025-09-04 18:08:25.108724', 1, '2025-09-04 18:09:00.294234', 0);
INSERT INTO "public"."sys_menu" VALUES (1961001205065428993, '流程设计', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 1, 'FlowDesign', '/lf/design', 'lf/design/index', NULL, 1, 'C', 0, 1, 'iconoir:design-nib', 1, 1, '2025-08-28 17:41:35.587189', 1, '2025-09-04 11:51:31.391115', 0);
INSERT INTO "public"."sys_menu" VALUES (1963450958801895426, '发起申请', 1963450958801895425, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 1, 'FlowProcessInitiate', '/lf/process/initiate', 'lf/process/initiate', NULL, 1, 'C', 0, 1, 'lucide:send', 1, 1, '2025-09-04 11:56:02.39554', 1, '2025-09-04 18:04:10.983161', 0);
INSERT INTO "public"."sys_menu" VALUES (1963450958801895425, '流程申请', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 3, 'FlowProcess', '/lf/process', 'Layout', NULL, 1, 'M', 0, 1, 'carbon:ibm-event-processing', 1, 1, '2025-09-04 11:56:02.39554', 1, '2025-09-04 18:04:10.983161', 0);
INSERT INTO "public"."sys_menu" VALUES (1964010182879223809, '表单设计', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowFormDesign', '/lf/form/design', 'lf/form/design/index', NULL, 1, 'C', 0, 1, 'fluent:form-sparkle-20-regular', 1, 1, '2025-09-06 00:58:11.800388', 1, '2025-09-06 00:58:25.561666', 0);
INSERT INTO "public"."sys_menu" VALUES (1962731950251192333, '表单发布列表', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowFormDesignRL', '/lf/frl/:id', 'lf/form/release/index', NULL, 0, 'C', 1, 1, NULL, 1, 1, '2025-09-02 12:18:57.395281', 1, '2025-09-04 18:04:03.318503', 0);
INSERT INTO "public"."sys_menu" VALUES (0, 'Layout', NULL, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'Layout', 'Layout', 'Layout', NULL, 1, 'M', 1, 0, NULL, 0, NULL, NULL, NULL, '2022-06-22 09:14:06', 0);
INSERT INTO "public"."sys_menu" VALUES (1961000320654487554, '流程管理', 0, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 100, 'flow-manage', '/flow-manage', 'Layout', NULL, 1, 'M', 0, 1, 'ri:flow-chart', 1, 1, '2025-08-28 17:38:04.730677', 1, '2025-08-28 17:40:36.577463', 0);
INSERT INTO "public"."sys_menu" VALUES (1572502356924743682, '个人中心', 4, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 97, 'personal', 'personal', 'system/personal/index', NULL, 1, 'C', 1, 1, 'akar-icons:laptop-device', 0, 1, '2022-09-21 16:25:52', 1, '2022-09-21 16:25:52', 0);
INSERT INTO "public"."sys_menu" VALUES (1631227390958325768, 'mangocrisp', 0, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":2,"transition":{}}', 999, 'mangocrisp', NULL, 'https://mangocrisp.top', NULL, 1, 'C', 0, 1, 'entypo-social:github-with-circle', 1, 1, '2023-03-02 17:38:11.406762', 1, '2025-09-17 15:48:18.828784', 0);
INSERT INTO "public"."sys_menu" VALUES (1962440040961822722, '流程图设计', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowDesignD', '/lf/d/:source/:id', 'lf/design/components/flow-designer/index', NULL, 0, 'C', 1, 1, NULL, 1, 1, '2025-09-01 16:59:00.804062', 1, '2025-09-04 18:03:58.897567', 0);
INSERT INTO "public"."sys_menu" VALUES (1968222484428328962, 'PureAdmin', 0, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"transition":{},"menuType":2}', 998, 'pureadmin', NULL, 'https://pure-admin.cn', NULL, 1, 'C', 0, 1, 'ep:link', 1, 1, '2025-09-17 15:56:22.760388', 1, '2025-09-17 15:56:22.761899', 0);
INSERT INTO "public"."sys_menu" VALUES (1963450958801895427, '申请列表', 1963450958801895425, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{},"params":{},"extraIcon":""}', 2, 'FlowProcessList', '/lf/process/list', 'lf/process/list', NULL, 1, 'C', 0, 1, 'lucide:list-start', 1, 1, '2025-09-04 11:56:02.39554', 1, '2025-09-15 16:30:54.462305', 0);
INSERT INTO "public"."sys_menu" VALUES (1475111131305844738, '权限管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 3, 'permission', 'permission', 'system/permission/index', NULL, 1, 'C', 0, 1, 'icon-park-twotone:permissions', 0, NULL, NULL, 1, '2022-08-24 16:09:04', 0);
INSERT INTO "public"."sys_menu" VALUES (1537320455847165953, '字典管理', 4, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 4, 'dictType', 'dict-type', 'system/dict-type/index', NULL, 1, 'C', 0, 1, 'arcticons:colordict', 0, 1, '2022-06-16 14:25:33', 1, '2022-08-26 16:42:41', 0);
INSERT INTO "public"."sys_menu" VALUES (1537698325769674753, '在线用户', 1627485035375833090, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 9, 'onlineUser', 'online-user', 'system/online-user/index', NULL, 1, 'C', 0, 1, 'carbon:user-online', 0, 1, '2022-06-17 15:27:05', 1, '2022-09-29 09:39:11', 0);
INSERT INTO "public"."sys_menu" VALUES (1627485430827397122, '任务调度', 1627485035375833090, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'SchedulingTask', 'scheduling-task', 'scheduling/index', NULL, 1, 'C', 0, 1, 'material-symbols:schedule-outline', 0, 1, '2023-02-20 09:48:59', 1, '2023-02-20 09:53:16', 0);
INSERT INTO "public"."sys_menu" VALUES (1547835850341363713, '三级菜单', 1547835699233173505, 1, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 0, 'ceshi3', 'ceshi3', 'nested/menu1/menu1-1/index', '', 0, 'C', 0, 1, 'bx:menu-alt-left', 0, 1, '2022-07-15 14:49:59', 1, '2022-08-26 13:54:16', 0);
INSERT INTO "public"."sys_menu" VALUES (1964010182879223810, '自定义表单组件', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowFormCustomComponents', '/lf/form/custom-components', 'lf/form/custom-components/index', NULL, 1, 'C', 0, 1, 'uiw:component', 1, 1, '2025-09-06 00:58:11.800388', 1, '2025-09-06 00:58:25.561666', 0);
INSERT INTO "public"."sys_menu" VALUES (1964010182879223811, '动态表单设计', 1961000320654487554, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"menuType":0,"transition":{}}', 2, 'FlowFormDesignD', '/lf/fd/:source/:id', 'lf/form/components/form-designer/index', NULL, 0, 'C', 1, 1, NULL, 1, 1, '2025-09-01 16:59:00.804062', 1, '2025-09-04 18:03:58.897567', 0);
INSERT INTO "public"."sys_menu" VALUES (1944928279526395906, '豆包', 0, 1, '{"hiddenTag":false,"fixedTag":false,"menuType":1,"transition":{"enterTransition":"tada","leaveTransition":"swing"},"frameLoading":true}', 1000, 'Doubao', 'doubao', 'https://www.doubao.com/chat', NULL, 1, 'C', 0, 1, 'hugeicons:ai-search', 1, 1, '2025-07-15 09:13:31.528387', 1, '2025-09-17 15:47:17.818072', 0);
INSERT INTO "public"."sys_menu" VALUES (1969941885586255873, '在线文档', 0, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"transition":{},"menuType":0}', 96, 'OnlineDocument', '/onlineDoc', 'Layout', NULL, 1, 'C', 0, 1, 'ep:document', 1, 1, '2025-09-22 09:48:39.944954', 1, '2025-09-23 10:45:05.86194', 0);
INSERT INTO "public"."sys_menu" VALUES (1969942162087358465, '在线文档', 1969941885586255873, 0, '{"frameLoading":true,"hiddenTag":false,"fixedTag":false,"transition":{},"menuType":0}', 0, 'OnlyOffice', '/online-doc', 'online-doc/index.vue', NULL, 1, 'C', 0, 1, 'ep:document-copy', 1, 1, '2025-09-22 09:49:45.854123', 1, '2025-09-23 10:49:04.148867', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_notice";
CREATE TABLE "public"."sys_notice" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "title" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "message" text COLLATE "pg_catalog"."default" NOT NULL,
  "level" varchar(100) COLLATE "pg_catalog"."default",
  "positive" int2 NOT NULL,
  "data" jsonb,
  "topic" varchar(100) COLLATE "pg_catalog"."default",
  "sub_type" varchar(100) COLLATE "pg_catalog"."default",
  "from_user" int8,
  "from_user_name" varchar(255) COLLATE "pg_catalog"."default",
  "from_user_avatar" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_notice"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_notice"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_notice"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_notice"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_notice"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_notice"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_notice"."title" IS '标题';
COMMENT ON COLUMN "public"."sys_notice"."message" IS '通知消息';
COMMENT ON COLUMN "public"."sys_notice"."level" IS '级别(字典 notice_level)';
COMMENT ON COLUMN "public"."sys_notice"."positive" IS '是否是确定指定的通知(1是 0 否)';
COMMENT ON COLUMN "public"."sys_notice"."data" IS '通知数据';
COMMENT ON COLUMN "public"."sys_notice"."topic" IS '通知类型（订阅主题）';
COMMENT ON COLUMN "public"."sys_notice"."sub_type" IS '子类型';
COMMENT ON COLUMN "public"."sys_notice"."from_user" IS '发送人';
COMMENT ON COLUMN "public"."sys_notice"."from_user_name" IS '发送人名称';
COMMENT ON COLUMN "public"."sys_notice"."from_user_avatar" IS '发送人头像';
COMMENT ON TABLE "public"."sys_notice" IS '消息通知';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO "public"."sys_notice" VALUES (1960231481448706050, 1945059926271860737, '2025-08-26 14:42:59.182269', 1945059926271860737, '2025-08-26 14:42:59.182269', 0, '通知', '通知18', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231496460120066, 1945059926271860737, '2025-08-26 14:43:02.755458', 1945059926271860737, '2025-08-26 14:43:02.756965', 0, '通知', '通知19', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960155764673327106, 1945059926271860737, '2025-08-26 09:42:06.897135', 1945059926271860737, '2025-08-26 09:42:06.897135', 0, '简单消息', '如果是发给指定的人', '1', 0, '{"extra": "消息", "status": "success"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960156433891307522, 1945059926271860737, '2025-08-26 09:44:46.444202', 1945059926271860737, '2025-08-26 09:44:46.444202', 0, '消息啊', '消息2', '1', 0, '{"extra": "消息", "status": "success"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960156852499623937, 1945059926271860737, '2025-08-26 09:46:26.249796', 1945059926271860737, '2025-08-26 09:46:26.249796', 0, '消息啊', '消息3', '1', 0, '{"extra": "消息", "status": "success"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960156901019332610, 1945059926271860737, '2025-08-26 09:46:37.818572', 1945059926271860737, '2025-08-26 09:46:37.818572', 0, '消息啊', '消息4', '1', 0, '{"extra": "消息", "status": "success"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231518476021762, 1945059926271860737, '2025-08-26 14:43:08.011074', 1945059926271860737, '2025-08-26 14:43:08.011074', 0, '通知', '通知20', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159873002483713, 1945059926271860737, '2025-08-26 09:58:26.395391', 1945059926271860737, '2025-08-26 09:58:26.395391', 0, '通知', '通知6', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159614281035778, 1945059926271860737, '2025-08-26 09:57:24.719685', 1945059926271860737, '2025-08-26 09:57:24.719685', 0, '通知', '通知4', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159686263681026, 1945059926271860737, '2025-08-26 09:57:41.875542', 1945059926271860737, '2025-08-26 09:57:41.875542', 0, '通知', '通知5', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1, 1, '2025-08-25 18:03:48', NULL, NULL, 0, '消息通知', '简单的消息通知', '1', 1, '{"extra": "简单消息", "status": "primary"}', 'SYS_NOTICE', NULL, 1, 'Amy', NULL);
INSERT INTO "public"."sys_notice" VALUES (1960158885201948673, 1945059926271860737, '2025-08-26 09:54:30.888896', 1945059926271860737, '2025-08-26 09:54:30.888896', 0, '通知', '通知2', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159400845488130, 1945059926271860737, '2025-08-26 09:56:33.824181', 1945059926271860737, '2025-08-26 09:56:33.825701', 0, '通知', '通知3', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231289991311361, 1945059926271860737, '2025-08-26 14:42:13.538897', 1945059926271860737, '2025-08-26 14:42:13.538897', 0, '通知', '通知7', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231311231266817, 1945059926271860737, '2025-08-26 14:42:18.605283', 1945059926271860737, '2025-08-26 14:42:18.605283', 0, '通知', '通知8', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231331154210817, 1945059926271860737, '2025-08-26 14:42:23.353111', 1945059926271860737, '2025-08-26 14:42:23.353111', 0, '通知', '通知9', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231351681134594, 1945059926271860737, '2025-08-26 14:42:28.248826', 1945059926271860737, '2025-08-26 14:42:28.248826', 0, '通知', '通知10', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231369402068994, 1945059926271860737, '2025-08-26 14:42:32.469361', 1945059926271860737, '2025-08-26 14:42:32.469361', 0, '通知', '通知11', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231383738200065, 1945059926271860737, '2025-08-26 14:42:35.880347', 1945059926271860737, '2025-08-26 14:42:35.880347', 0, '通知', '通知12', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231398535704578, 1945059926271860737, '2025-08-26 14:42:39.421711', 1945059926271860737, '2025-08-26 14:42:39.421711', 0, '通知', '通知13', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231412779560961, 1945059926271860737, '2025-08-26 14:42:42.805826', 1945059926271860737, '2025-08-26 14:42:42.805826', 0, '通知', '通知14', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231430857011202, 1945059926271860737, '2025-08-26 14:42:47.114455', 1945059926271860737, '2025-08-26 14:42:47.114455', 0, '通知', '通知15', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231447034441729, 1945059926271860737, '2025-08-26 14:42:50.975516', 1945059926271860737, '2025-08-26 14:42:50.97704', 0, '通知', '通知16', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960236790028283905, 1945059926271860737, '2025-08-26 15:04:04.847517', 1945059926271860737, '2025-08-26 15:04:04.847517', 0, '通知', '通知21', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960237341302435842, 1945059926271860737, '2025-08-26 15:06:16.282814', 1945059926271860737, '2025-08-26 15:06:16.284327', 0, '通知', '通知22', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960239443273605121, 1945059926271860737, '2025-08-26 15:14:37.423255', 1945059926271860737, '2025-08-26 15:14:37.423255', 0, '通知', '通知23', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240011853455362, 1945059926271860737, '2025-08-26 15:16:52.981861', 1945059926271860737, '2025-08-26 15:16:52.981861', 0, '通知', '通知24', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240127318450177, 1945059926271860737, '2025-08-26 15:17:20.523879', 1945059926271860737, '2025-08-26 15:17:20.524881', 0, '通知', '通知25', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240153931309058, 1945059926271860737, '2025-08-26 15:17:26.870125', 1945059926271860737, '2025-08-26 15:17:26.870125', 0, '通知', '通知26', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240211116449793, 1945059926271860737, '2025-08-26 15:17:40.500352', 1945059926271860737, '2025-08-26 15:17:40.500352', 0, '通知', '通知27', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240238505254914, 1945059926271860737, '2025-08-26 15:17:47.02666', 1945059926271860737, '2025-08-26 15:17:47.02666', 0, '通知', '通知28', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960158444405764097, 1945059926271860737, '2025-08-26 09:52:45.792064', 1945059926271860737, '2025-08-26 09:52:45.792064', 0, '待办', '待办2', '1', 1, '{"extra": "加快处理", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960158286964174850, 1945059926271860737, '2025-08-26 09:52:08.254074', 1945059926271860737, '2025-08-26 09:52:08.254074', 0, '待办', '待办1', '1', 1, '{"extra": "加快处理", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159235019485186, 1945059926271860737, '2025-08-26 09:55:54.286552', 1945059926271860737, '2025-08-26 09:55:54.286552', 0, '消息', '消息6', '1', 1, '{"extra": "你的朋友", "status": "info"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960240270616846338, 1945059926271860737, '2025-08-26 15:17:54.688669', 1945059926271860737, '2025-08-26 15:17:54.688669', 0, '通知', '通知29', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960241197428006913, 1945059926271860737, '2025-08-26 15:21:35.655874', 1945059926271860737, '2025-08-26 15:21:35.655874', 0, '通知', '通知30', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960241245045940225, 1945059926271860737, '2025-08-26 15:21:47.00945', 1945059926271860737, '2025-08-26 15:21:47.01045', 0, '通知', '通知31', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960243192352235521, 1945059926271860737, '2025-08-26 15:29:31.27971', 1945059926271860737, '2025-08-26 15:29:31.27971', 0, '通知', '通知32', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960243596582477826, 1945059926271860737, '2025-08-26 15:31:07.661129', 1945059926271860737, '2025-08-26 15:31:07.661129', 0, '通知', '通知33', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960243745857757185, 1945059926271860737, '2025-08-26 15:31:43.249057', 1945059926271860737, '2025-08-26 15:31:43.249057', 0, '通知', '通知34', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960243792578109442, 1945059926271860737, '2025-08-26 15:31:54.384645', 1945059926271860737, '2025-08-26 15:31:54.384645', 0, '通知', '通知35', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960244721427701761, 1945059926271860737, '2025-08-26 15:35:35.845782', 1945059926271860737, '2025-08-26 15:35:35.845782', 0, '通知', '通知36', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960244808342069250, 1945059926271860737, '2025-08-26 15:35:56.553912', 1945059926271860737, '2025-08-26 15:35:56.553912', 0, '通知', '通知37', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960244894014922753, 1945059926271860737, '2025-08-26 15:36:16.984018', 1945059926271860737, '2025-08-26 15:36:16.984018', 0, '通知', '通知38', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960245059492798466, 1945059926271860737, '2025-08-26 15:36:56.439966', 1945059926271860737, '2025-08-26 15:36:56.439966', 0, '通知', '通知39', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960245221158051841, 1945059926271860737, '2025-08-26 15:37:34.987826', 1945059926271860737, '2025-08-26 15:37:34.987826', 0, '通知', '通知40', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960245553665695746, 1945059926271860737, '2025-08-26 15:38:54.264237', 1945059926271860737, '2025-08-26 15:38:54.264237', 0, '通知', '通知41', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960245852543410177, 1945059926271860737, '2025-08-26 15:40:05.522775', 1945059926271860737, '2025-08-26 15:40:05.522775', 0, '通知', '通知42', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960245985095999490, 1945059926271860737, '2025-08-26 15:40:37.124283', 1945059926271860737, '2025-08-26 15:40:37.124283', 0, '通知', '通知43', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960246291875782657, 1945059926271860737, '2025-08-26 15:41:50.267203', 1945059926271860737, '2025-08-26 15:41:50.267203', 0, '通知', '通知44', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960247299297271809, 1945059926271860737, '2025-08-26 15:45:50.455613', 1945059926271860737, '2025-08-26 15:45:50.455613', 0, '通知', '通知45', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960247364053131265, 1945059926271860737, '2025-08-26 15:46:05.8978', 1945059926271860737, '2025-08-26 15:46:05.8978', 0, '通知', '通知46', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960247608039989250, 1945059926271860737, '2025-08-26 15:47:04.067792', 1945059926271860737, '2025-08-26 15:47:04.067792', 0, '通知', '通知47', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960247750356918274, 1945059926271860737, '2025-08-26 15:47:37.98635', 1945059926271860737, '2025-08-26 15:47:37.98635', 0, '通知', '通知48', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960249944787410945, 1945059926271860737, '2025-08-26 15:56:21.191178', 1945059926271860737, '2025-08-26 15:56:21.191178', 0, '通知', '通知49', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960250154607468545, 1945059926271860737, '2025-08-26 15:57:11.204084', 1945059926271860737, '2025-08-26 15:57:11.204084', 0, '通知', '通知 50', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960250346253606914, 1945059926271860737, '2025-08-26 15:57:56.901039', 1945059926271860737, '2025-08-26 15:57:56.901039', 0, '通知', '通知 51', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960250645693358081, 1945059926271860737, '2025-08-26 15:59:08.287758', 1945059926271860737, '2025-08-26 15:59:08.287758', 0, '通知', '通知 52', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960250751977021442, 1945059926271860737, '2025-08-26 15:59:33.634256', 1945059926271860737, '2025-08-26 15:59:33.634256', 0, '通知', '通知 53', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960250988036644865, 1945059926271860737, '2025-08-26 16:00:29.912961', 1945059926271860737, '2025-08-26 16:00:29.912961', 0, '通知', '通知 54', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960251421174030337, 1945059926271860737, '2025-08-26 16:02:13.18802', 1945059926271860737, '2025-08-26 16:02:13.18802', 0, '通知', '通知 55', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960251564753444865, 1945059926271860737, '2025-08-26 16:02:47.417926', 1945059926271860737, '2025-08-26 16:02:47.417926', 0, '通知', '通知 56', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960285147794882562, 1, '2025-08-26 18:16:14.236622', 1, '2025-08-26 18:16:14.236622', 0, '消息', '消息8', '1', 1, '{"extra": "你的朋友", "status": "info"}', 'SYS_MESSAGE', NULL, 1, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960285192837513218, 1, '2025-08-26 18:16:24.972765', 1, '2025-08-26 18:16:24.972765', 0, '消息', '消息9', '1', 1, '{"extra": "你的朋友", "status": "info"}', 'SYS_MESSAGE', NULL, 1, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960159482667970561, 1945059926271860737, '2025-08-26 09:56:53.33684', 1945059926271860737, '2025-08-26 09:56:53.33684', 0, '待办', '待办3', '1', 1, '{"extra": "加快处理", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960285053746003969, 1945059926271860737, '2025-08-26 18:15:51.821015', 1945059926271860737, '2025-08-26 18:15:51.823014', 0, '消息', '消息7', '1', 1, '{"extra": "你的朋友", "status": "info"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960524845255610369, 1945059926271860737, '2025-08-27 10:08:42.570315', 1945059926271860737, '2025-08-27 10:08:42.570315', 0, '来自你的朋友的一条私发消息', '这里是内容', '1', 1, '{"extra": "我是普通用户啊", "status": "info"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960251716956348417, 1945059926271860737, '2025-08-26 16:03:23.698357', 1945059926271860737, '2025-08-26 16:03:23.698357', 0, '通知通知通知通知通知通知通知通知通知通知通知通知通知通知', '通知 57通知 57通知 57通知 57通知 57通知 57通知 57通知 57', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960524962998112258, 1945059926271860737, '2025-08-27 10:09:10.634832', 1945059926271860737, '2025-08-27 10:09:10.634832', 0, '待办', '待办4', '1', 1, '{"extra": "这里发一个待办给你看看吧", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960552633870118913, 1945059926271860737, '2025-08-27 11:59:07.880244', 1945059926271860737, '2025-08-27 11:59:07.880244', 0, '通知', '通知58', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960231465065754625, 1945059926271860737, '2025-08-26 14:42:55.268135', 1945059926271860737, '2025-08-26 14:42:55.268135', 0, '通知', '通知17', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960552680502390786, 1945059926271860737, '2025-08-27 11:59:19.007001', 1945059926271860737, '2025-08-27 11:59:19.008527', 0, '通知', '通知59', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960552716476936193, 1945059926271860737, '2025-08-27 11:59:27.586425', 1945059926271860737, '2025-08-27 11:59:27.586939', 0, '通知', '通知60', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960879554160050177, 1945059926271860737, '2025-08-28 09:38:11.752674', 1945059926271860737, '2025-08-28 09:38:11.752674', 0, '通知', '通知6', '1', 0, '{"extra": "通知", "status": "warning"}', 'SYS_NOTICE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960879599261401090, 1945059926271860737, '2025-08-28 09:38:22.502428', 1945059926271860737, '2025-08-28 09:38:22.502428', 0, '来自你的朋友的一条私发消息', '这里是内容', '1', 1, '{"extra": "我是普通用户啊", "status": "info"}', 'SYS_MESSAGE', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960879622615285762, 1945059926271860737, '2025-08-28 09:38:28.084487', 1945059926271860737, '2025-08-28 09:38:28.085005', 0, '待办', '待办4', '1', 1, '{"extra": "这里发一个待办给你看看吧", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);
INSERT INTO "public"."sys_notice" VALUES (1960885095737413634, 1945059926271860737, '2025-08-28 10:00:12.965705', 1945059926271860737, '2025-08-28 10:00:12.967219', 0, '待办', '待办4', '1', 1, '{"extra": "这里发一个待办给你看看吧", "status": "danger"}', 'SYS_TODO', NULL, 1945059926271860737, NULL, NULL);

-- ----------------------------
-- Table structure for sys_notice_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_notice_user";
CREATE TABLE "public"."sys_notice_user" (
  "notice_id" int8 NOT NULL,
  "related_id" int8 NOT NULL,
  "notice_type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_notice_user"."notice_id" IS '通知 id';
COMMENT ON COLUMN "public"."sys_notice_user"."related_id" IS '用于关联的 id，可能是用户，角色，租户 等 id';
COMMENT ON COLUMN "public"."sys_notice_user"."notice_type" IS '通知类型(字典 notice_type)1是用户，其他的不管';
COMMENT ON COLUMN "public"."sys_notice_user"."status" IS '状态(0不可见 1 已读 2待办)';
COMMENT ON TABLE "public"."sys_notice_user" IS '通知用户关联表
删除消息通知，只能是把 status 指定为 0 ，因为还有其他类型的消息通知，直接删除是不行的';

-- ----------------------------
-- Records of sys_notice_user
-- ----------------------------
INSERT INTO "public"."sys_notice_user" VALUES (1, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960158286964174850, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960158444405764097, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960159235019485186, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960159482667970561, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960285053746003969, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960285147794882562, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960285192837513218, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960524845255610369, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960524962998112258, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960879599261401090, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960879622615285762, 1, '1', 2);
INSERT INTO "public"."sys_notice_user" VALUES (1960885095737413634, 1, '1', 2);

-- ----------------------------
-- Table structure for sys_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_oauth2_client";
CREATE TABLE "public"."sys_oauth2_client" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "client_secret" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "resource_ids" varchar(500) COLLATE "pg_catalog"."default",
  "scope" varchar(200) COLLATE "pg_catalog"."default",
  "authorized_grant_types" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "web_server_redirect_uri" varchar(1000) COLLATE "pg_catalog"."default",
  "authorities" varchar(2000) COLLATE "pg_catalog"."default",
  "access_token_validity" int4,
  "refresh_token_validity" int4,
  "additional_information" varchar(255) COLLATE "pg_catalog"."default",
  "auto_approve" varchar(10) COLLATE "pg_catalog"."default",
  "unique_key" int8,
  "client_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_oauth2_client"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_oauth2_client"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_oauth2_client"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_oauth2_client"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_oauth2_client"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_oauth2_client"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_oauth2_client"."client_id" IS '客户端 id';
COMMENT ON COLUMN "public"."sys_oauth2_client"."client_secret" IS '客户端密钥';
COMMENT ON COLUMN "public"."sys_oauth2_client"."resource_ids" IS '资源id列表';
COMMENT ON COLUMN "public"."sys_oauth2_client"."scope" IS '域逗号隔开';
COMMENT ON COLUMN "public"."sys_oauth2_client"."authorized_grant_types" IS '授权模式';
COMMENT ON COLUMN "public"."sys_oauth2_client"."web_server_redirect_uri" IS '回调地址';
COMMENT ON COLUMN "public"."sys_oauth2_client"."authorities" IS '权限列表';
COMMENT ON COLUMN "public"."sys_oauth2_client"."access_token_validity" IS '认证令牌时效';
COMMENT ON COLUMN "public"."sys_oauth2_client"."refresh_token_validity" IS '刷新令牌时效';
COMMENT ON COLUMN "public"."sys_oauth2_client"."additional_information" IS '扩展信息';
COMMENT ON COLUMN "public"."sys_oauth2_client"."auto_approve" IS '是否自动放行';
COMMENT ON COLUMN "public"."sys_oauth2_client"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."sys_oauth2_client"."client_name" IS '客户端名,可以描述客户端信息';
COMMENT ON TABLE "public"."sys_oauth2_client" IS '客户端';

-- ----------------------------
-- Records of sys_oauth2_client
-- ----------------------------
INSERT INTO "public"."sys_oauth2_client" VALUES (1947936658897342465, 1, '2025-07-23 16:27:45.061381', 1, '2025-07-23 17:36:18.3107', 0, 'test', 'db979badee0415662c82b59953f0dc07', NULL, 'all', 'code', 'https://www.baidu.com', NULL, 3600, 604800, NULL, 'true', 0, 'test');
INSERT INTO "public"."sys_oauth2_client" VALUES (1, 1, '2023-04-21 14:02:23.49', 1, '2025-08-25 18:02:13.569695', 0, 'taybct_pc', 'e10adc3949ba59abbe56e057f20f883e', NULL, 'all', 'authorization_code,taybct,password,refresh_token,sms,wechat_qr_code,client_credentials,pki,taybct_refresh', 'https://www.baidu.com', NULL, 3600, 86400, NULL, 'true', 0, 'taybct_pc');

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_params";
CREATE TABLE "public"."sys_params" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "type" varchar(128) COLLATE "pg_catalog"."default",
  "title" varchar(128) COLLATE "pg_catalog"."default",
  "params_key" varchar(128) COLLATE "pg_catalog"."default",
  "params_val" varchar(128) COLLATE "pg_catalog"."default",
  "status" int2,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "unique_key" int8
)
;
COMMENT ON COLUMN "public"."sys_params"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_params"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_params"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_params"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_params"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_params"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_params"."type" IS '类型（字典-系统参数类型）';
COMMENT ON COLUMN "public"."sys_params"."title" IS '标题：系统标题';
COMMENT ON COLUMN "public"."sys_params"."params_key" IS '键 例：title';
COMMENT ON COLUMN "public"."sys_params"."params_val" IS '值 例：冠宣';
COMMENT ON COLUMN "public"."sys_params"."status" IS '是否可用 1可用、0不可用';
COMMENT ON COLUMN "public"."sys_params"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_params"."unique_key" IS '逻辑唯一键';
COMMENT ON TABLE "public"."sys_params" IS '系统参数';

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO "public"."sys_params" VALUES (1570681338925006849, 1, '2022-09-16 15:49:48', 1, '2022-12-07 15:32:30', 0, 'STRING', '重置密码', 'user_passwd', '123456', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600420241626038273, 1, '2022-12-07 17:21:35', 1, '2022-12-07 17:21:35', 0, 'STRING', '默认角色，游客', 'user_role', 'TOURIST', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600420305106829314, 1, '2022-12-07 17:21:50', 1, '2022-12-07 17:21:50', 0, 'NUMBER', '默认的游客角色 id', 'user_role_id', '5', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600420365202817025, 1, '2022-12-07 17:22:04', 1, '2022-12-07 17:22:04', 0, 'NUMBER', '用户默认状态', 'user_status', '1', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600420452104601602, 1, '2022-12-07 17:22:25', 1, '2022-12-07 17:22:25', 0, 'STRING', '默认租户 id', 'tenant_id', '000000', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600420622523367425, 1, '2022-12-07 17:23:05', 1, '2022-12-07 17:55:42', 0, 'STRING', '验证码的类型', 'captcha_type', 'GIF', 1, '可以查看枚举：io.github.mangocrisp.spring.taybct.common.constants.CaptchaType。
可选：CIRCLE，GIF，LINE，SHEAR，默认 GIF', 0);
INSERT INTO "public"."sys_params" VALUES (1573217163437035523, 1, '2022-09-23 15:46:15', 1, '2023-03-01 12:14:47.857079', 0, 'BOOLEAN', '是否允许重复登录同一个客户端', 'allow_multiple_token_one_client', 'true', 1, '这个配置，如果不配置，或者是禁用，默认系统是允许重复登录同一个客户端的', 0);
INSERT INTO "public"."sys_params" VALUES (1600420532530380801, 1, '2022-12-07 17:22:44', 1, '2023-03-09 10:12:58.866714', 0, 'BOOLEAN', '是否需要验证码登录', 'enable_captcha', 'false', 1, '', 0);
INSERT INTO "public"."sys_params" VALUES (1600419490103230466, 1, '2022-12-07 17:18:35', 1, '2022-12-07 17:16:55', 1, 'NUMBER', '菜单默认的 Layout', 'menu_layout', '0', 1, '', 1600420040123285505);
INSERT INTO "public"."sys_params" VALUES (1600419754042392578, 1, '2022-12-07 17:19:38', 1, '2022-12-07 17:16:55', 1, 'NUMBER', '默认的 ROOT 角色 id', 'role_root_id', '1', 1, '', 1600420040123285505);
INSERT INTO "public"."sys_params" VALUES (1537637376811536386, 1, '2022-06-17 11:24:53', 1, '2025-07-18 11:50:49.191031', 0, 'STRING', '系统标题', 'sys_title', 'TayBct平台', 1, '', 0);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_permission";
CREATE TABLE "public"."sys_permission" (
  "id" int8 NOT NULL,
  "group_id" int8,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" int8,
  "url_perm" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "btn_perm" varchar(64) COLLATE "pg_catalog"."default",
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_permission"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_permission"."group_id" IS '分组';
COMMENT ON COLUMN "public"."sys_permission"."name" IS '权限名';
COMMENT ON COLUMN "public"."sys_permission"."menu_id" IS '菜单id';
COMMENT ON COLUMN "public"."sys_permission"."url_perm" IS 'url 权限';
COMMENT ON COLUMN "public"."sys_permission"."btn_perm" IS '按钮权限';
COMMENT ON COLUMN "public"."sys_permission"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_permission"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_permission"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."sys_permission" IS '权限管理表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO "public"."sys_permission" VALUES (1572153139278434305, NULL, '角色新增', 6, 'POST:/system/role', 'system:role:add', NULL, NULL, 1, '2023-01-13 17:03:10');
INSERT INTO "public"."sys_permission" VALUES (1572153139278434306, NULL, '角色编辑', 6, 'PUT:/system/role', 'system:role:edit', NULL, NULL, 1, '2023-01-13 17:03:10');
INSERT INTO "public"."sys_permission" VALUES (1572155394710884354, 1580491239876640770, '菜单新增', 5, 'POST:/system/menu', 'system:menu:add', NULL, NULL, 1, '2022-10-13 17:31:20');
INSERT INTO "public"."sys_permission" VALUES (1572155394710884355, 1580491239876640770, '菜单删除', 5, 'DELETE:/system/menu', 'system:menu:del', NULL, NULL, 1, '2022-10-13 17:31:28');
INSERT INTO "public"."sys_permission" VALUES (1572155394710884356, 1580491239876640770, '菜单关联权限', 5, 'POST:/system/permission/batch', 'system:menuPerm:batch', NULL, NULL, 1, '2022-10-13 17:31:33');
INSERT INTO "public"."sys_permission" VALUES (1572155394710884357, 1580491239876640770, '菜单关联角色', 5, 'POST:/system/roleMenu/batch', 'system:menuRole:batch', NULL, NULL, 1, '2022-10-13 17:31:40');
INSERT INTO "public"."sys_permission" VALUES (1572155394710884358, 1580491239876640770, '菜单编辑', 5, 'PUT:/system/menu', 'system:menu:edit', NULL, NULL, 1, '2022-10-13 17:30:58');
INSERT INTO "public"."sys_permission" VALUES (1572158818093420546, NULL, '权限关联角色', 1475111131305844738, 'POST:/system/rolePerm/batch', 'system:permRole:batch', NULL, NULL, 1, '2022-09-22 13:47:18');
INSERT INTO "public"."sys_permission" VALUES (1572824840752533505, NULL, '权限批量删除', 1475111131305844738, 'DELETE:/system/permission/{ids}/batch', 'system:permission:del:batch', 1, '2022-09-22 13:47:18', 1, '2022-09-22 13:47:18');
INSERT INTO "public"."sys_permission" VALUES (1572825466106482689, NULL, '字典类型删除', 1537320455847165953, 'DELETE:/system/dictType', 'system:dict-type:del', 1, '2022-09-22 13:49:47', 1, '2022-09-22 13:49:47');
INSERT INTO "public"."sys_permission" VALUES (1572825466106482690, NULL, '字典类型批量删除', 1537320455847165953, 'DELETE:/system/dictType/{ids}/batch', 'system:dict-type:del:batch', 1, '2022-09-22 13:49:47', 1, '2022-09-22 13:49:47');
INSERT INTO "public"."sys_permission" VALUES (1572825466114871297, NULL, '字典类型编辑', 1537320455847165953, 'PUT:/system/dictType', 'system:dict-type:edit', 1, '2022-09-22 13:49:47', 1, '2022-09-22 13:49:47');
INSERT INTO "public"."sys_permission" VALUES (1572825466114871298, NULL, '字典类型新增', 1537320455847165953, 'POST:/system/dictType', 'system:dict-type:add', 1, '2022-09-22 13:49:47', 1, '2022-09-22 13:49:47');
INSERT INTO "public"."sys_permission" VALUES (1572826445522604033, NULL, '参数编辑', 1537625854055940097, 'PUT:/system/params', 'system:params:edit', 1, '2022-09-22 13:53:41', 1, '2022-09-22 13:53:41');
INSERT INTO "public"."sys_permission" VALUES (1572826445522604034, NULL, '参数删除', 1537625854055940097, 'DELETE:/system/params', 'system:params:del', 1, '2022-09-22 13:53:41', 1, '2022-09-22 13:53:41');
INSERT INTO "public"."sys_permission" VALUES (1572826445522604035, NULL, '参数批量删除', 1537625854055940097, 'DELETE:/system/params/{ids}/batch', 'system:params:del:batch', 1, '2022-09-22 13:53:41', 1, '2022-09-22 13:53:41');
INSERT INTO "public"."sys_permission" VALUES (1572826445522604036, NULL, '参数新增', 1537625854055940097, 'POST:/system/params', 'system:params:add', 1, '2022-09-22 13:53:41', 1, '2022-09-22 13:53:41');
INSERT INTO "public"."sys_permission" VALUES (1572827171908947969, NULL, '客户端新增', 1475197863846027266, 'POST:/system/oauth2Client', 'system:client:add', 1, '2022-09-22 13:56:34', 1, '2022-09-22 13:56:34');
INSERT INTO "public"."sys_permission" VALUES (1572827171917336578, NULL, '客户端设置密钥', 1475197863846027266, 'PATCH:/system/oauth2Client', 'system:client:secret', 1, '2022-09-22 13:56:34', 1, '2022-09-22 13:56:34');
INSERT INTO "public"."sys_permission" VALUES (1572827171917336579, NULL, '客户端删除', 1475197863846027266, 'DELETE:/system/oauth2Client', 'system:client:del', 1, '2022-09-22 13:56:34', 1, '2022-09-22 13:56:34');
INSERT INTO "public"."sys_permission" VALUES (1572827171917336580, NULL, '客户端批量删除', 1475197863846027266, 'DELETE:/system/oauth2Client/{ids}/batch', 'system:client:del:batch', 1, '2022-09-22 13:56:34', 1, '2022-09-22 13:56:34');
INSERT INTO "public"."sys_permission" VALUES (1572827171917336581, NULL, '客户端编辑', 1475197863846027266, 'PUT:/system/oauth2Client', 'system:client:edit', 1, '2022-09-22 13:56:34', 1, '2022-09-22 13:56:34');
INSERT INTO "public"."sys_permission" VALUES (1572827421113520129, NULL, '用户强制下线', 1537698325769674753, 'PUT:/system/user/forceAll', 'system:user:force-all', 1, '2022-09-22 13:57:34', 1, '2022-09-22 13:57:34');
INSERT INTO "public"."sys_permission" VALUES (1572827619025948673, NULL, '清空日志', 1563061288516792322, 'DELETE:/admin-log/apiLog/all', 'admin-log:api-log:del:all', 1, '2022-09-22 13:58:21', 1, '2022-09-23 17:26:50');
INSERT INTO "public"."sys_permission" VALUES (1578999516344270849, NULL, '租户批量删除', 1575299088586797058, 'DELETE:/system/tenant/{ids}/batch', 'system:tenant:del:batch', 1, '2022-10-09 14:43:16', 1, '2022-10-13 17:28:26');
INSERT INTO "public"."sys_permission" VALUES (1578999516344270850, NULL, '租户编辑', 1575299088586797058, 'PUT:/system/tenant', 'system:tenant:edit', 1, '2022-10-09 14:43:16', 1, '2022-10-13 17:28:26');
INSERT INTO "public"."sys_permission" VALUES (1578999516344270851, NULL, '租户新增', 1575299088586797058, 'POST:/system/tenant', 'system:tenant:add', 1, '2022-10-09 14:43:16', 1, '2022-10-13 17:28:26');
INSERT INTO "public"."sys_permission" VALUES (1578999516344270852, NULL, '租户删除', 1575299088586797058, 'DELETE:/system/tenant', 'system:tenant:del', 1, '2022-10-09 14:43:16', 1, '2022-10-13 17:28:26');
INSERT INTO "public"."sys_permission" VALUES (1580490637109018626, NULL, '租户分配到用户', 1575299088586797058, 'POST:/system/tenant/user', 'system:tenant:user:allot', 1, '2022-10-13 17:28:26', 1, '2022-10-13 17:28:26');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881537, 1631121175154610177, '启动', 1627485430827397122, 'PUT:/scheduling/{v}/scheduling/start', 'scheduling:task:start', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881538, 1631121175154610177, '新增', 1627485430827397122, 'POST:/scheduling/{v}/scheduling', 'scheduling:task:add', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881539, 1631121175154610177, '重启', 1627485430827397122, 'PUT:/scheduling/{v}/scheduling/restart', 'scheduling:task:restart', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881540, 1631121175154610177, '编辑', 1627485430827397122, 'PATCH:/scheduling/{v}/scheduling', 'scheduling:task:edit', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881541, 1631121175154610177, '批量删除', 1627485430827397122, 'DELETE:/scheduling/{v}/scheduling/batch', 'scheduling:task:del:batch', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881542, 1631121175154610177, '删除', 1627485430827397122, 'DELETE:/scheduling/{v}/scheduling/{id}', 'scheduling:task:del', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1631122862690881543, 1631121175154610177, '停止', 1627485430827397122, 'PUT:/scheduling/{v}/scheduling/stop', 'scheduling:task:stop', 1, '2023-03-02 10:42:49.901605', 1, '2023-03-02 10:42:49.901605');
INSERT INTO "public"."sys_permission" VALUES (1633718324323414018, 1633718264189677569, '测试权限', NULL, 'GET:/system/permDemo/test1', 'aaa:bbb:ccc', 1, '2023-03-09 14:36:16.193', 1, '2023-03-09 14:36:16.193');
INSERT INTO "public"."sys_permission" VALUES (1633718704742592514, 1633718264189677569, '测试权限2', NULL, 'GET:/system/permDemo/test1', 'ddd:eee:fff', 1, '2023-03-09 14:37:46.893', 1, '2023-03-09 14:37:46.893');
INSERT INTO "public"."sys_permission" VALUES (1633718821759479809, 1633718264189677569, '测试权限3', NULL, 'GET:/system/permDemo/test1', 'ggg:hhh:iii', 1, '2023-03-09 14:38:14.791', 1, '2023-03-09 14:38:14.791');
INSERT INTO "public"."sys_permission" VALUES (1572825926288740354, NULL, '字典信息删除', 1572500323467444226, 'DELETE:/system/dict', 'system:dict:del', 1, '2023-06-02 16:51:28.675', 1660854347478024193, '2023-06-02 16:51:28.68');
INSERT INTO "public"."sys_permission" VALUES (1572825926288740353, NULL, '字典信息新增', 1572500323467444226, 'POST:/system/dict', 'system:dict:add', 1, '2023-06-02 16:51:28.675', 1660854347478024193, '2023-06-02 16:51:28.68');
INSERT INTO "public"."sys_permission" VALUES (1572141139009785861, NULL, '用户重置密码', 7, 'PUT:/system/user/passwd', 'system:user:passwd', NULL, '2023-06-02 17:03:02.515', 1660854347478024193, '2023-06-02 17:03:02.52');
INSERT INTO "public"."sys_permission" VALUES (1572825926288740356, NULL, '字典信息批量删除', 1572500323467444226, 'DELETE:/system/dict/{ids}/batch', 'system:dict:del:batch', 1, '2023-06-02 16:51:28.675', 1660854347478024193, '2023-06-02 16:51:28.68');
INSERT INTO "public"."sys_permission" VALUES (1572825926288740355, NULL, '字典信息编辑', 1572500323467444226, 'PUT:/system/dict', 'system:dict:edit', 1, '2023-06-02 16:51:28.675', 1660854347478024193, '2023-06-02 16:51:28.68');
INSERT INTO "public"."sys_permission" VALUES (1572141139009785859, NULL, '用户新增', 7, 'POST:/system/user', 'system:user:add', NULL, '2023-06-02 17:03:02.515', 1660854347478024193, '2023-06-02 17:03:02.52');
INSERT INTO "public"."sys_permission" VALUES (1572141139009785860, NULL, '用户编辑', 7, 'PUT:/system/user', 'system:user:edit', NULL, '2023-06-02 17:03:02.515', 1660854347478024193, '2023-06-02 17:03:02.52');
INSERT INTO "public"."sys_permission" VALUES (1572141139009785858, NULL, '用户删除', 7, 'DELETE:/system/user', 'system:user:del', NULL, '2023-06-02 17:03:02.515', 1660854347478024193, '2023-06-02 17:03:02.52');
INSERT INTO "public"."sys_permission" VALUES (1572417317352460289, NULL, '不关联菜单', NULL, 'GET:/asdf', 'bbb:aaa:ccc', 1, '2022-09-21 10:47:57', 1664579188647510018, '2023-06-02 18:54:20.35');
INSERT INTO "public"."sys_permission" VALUES (1572157879118778371, 1633718264189677569, '权限删除', 1475111131305844738, 'DELETE:/system/permission', 'system:permission:del', NULL, NULL, 1664579188647510018, '2023-06-02 18:55:37.807');
INSERT INTO "public"."sys_permission" VALUES (1572157879118778369, NULL, '权限新增', 1475111131305844738, 'POST:/system/permission', 'system:permission:add', NULL, NULL, 1660854347478024193, '2023-06-05 09:57:44.901');
INSERT INTO "public"."sys_permission" VALUES (1572157879118778370, NULL, '权限编辑', 1475111131305844738, 'PUT:/system/permission', 'system:permission:edit', NULL, NULL, 1660854347478024193, '2023-06-05 09:58:03.626');
INSERT INTO "public"."sys_permission" VALUES (1572824375696494593, NULL, '用户批量删除', 7, 'DELETE:/system/user/{ids}/batch', 'system:user:del:batch', 1, '2023-06-02 17:03:02.515', 1660854347478024193, '2023-06-02 17:03:02.52');
INSERT INTO "public"."sys_permission" VALUES (1665540128268255233, 1633718264189677569, 'asdf', NULL, 'GET:/asdf', '', 1660854347478024193, '2023-06-05 10:04:45.475', 1660854347478024193, '2023-06-05 10:04:45.475');
INSERT INTO "public"."sys_permission" VALUES (1572824564767330306, NULL, '角色批量删除', 6, 'DELETE:/system/role/{ids}/batch', 'system:role:del:batch', 1, '2022-09-22 13:46:13', 1, '2023-01-13 17:03:10');
INSERT INTO "public"."sys_permission" VALUES (1773170931628363777, 1633718264189677569, 'hell添加', 1563061288516792322, 'POST:/hell/add', 'hell:add', 1, '2024-03-28 11:27:40.859', 1, '2024-03-28 11:27:40.859');
INSERT INTO "public"."sys_permission" VALUES (1572153139278434307, NULL, '角色删除', 6, 'DELETE:/system/role', 'system:role:del', NULL, NULL, 1, '2023-01-13 17:03:10');

-- ----------------------------
-- Table structure for sys_permission_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_permission_group";
CREATE TABLE "public"."sys_permission_group" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_permission_group"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_permission_group"."name" IS '组名';
COMMENT ON COLUMN "public"."sys_permission_group"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_permission_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_permission_group"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_permission_group"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."sys_permission_group" IS '权限分组';

-- ----------------------------
-- Records of sys_permission_group
-- ----------------------------
INSERT INTO "public"."sys_permission_group" VALUES (1580491239876640770, '菜单管理', 1, '2022-10-13 17:30:50', 1, '2022-10-13 17:30:50');
INSERT INTO "public"."sys_permission_group" VALUES (1581928598270799873, '用户管理', 1, '2022-10-17 16:42:23', 1, '2022-10-17 16:42:23');
INSERT INTO "public"."sys_permission_group" VALUES (1631121175154610177, '任务调度', 1, '2023-03-02 10:36:07.581031', 1, '2023-03-02 10:36:07.581031');
INSERT INTO "public"."sys_permission_group" VALUES (1633718264189677569, '测试测试', 1, '2023-03-09 14:36:01.857', 1, '2023-03-09 14:36:01.857');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4,
  "status" int2 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default",
  "unique_key" int8
)
;
COMMENT ON COLUMN "public"."sys_role"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_role"."name" IS '角色名';
COMMENT ON COLUMN "public"."sys_role"."code" IS '角色代码';
COMMENT ON COLUMN "public"."sys_role"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_role"."status" IS '状态(1 有效 0 无效  2 冻结)';
COMMENT ON COLUMN "public"."sys_role"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_role"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_role"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_role"."tenant_id" IS '租户id';
COMMENT ON COLUMN "public"."sys_role"."unique_key" IS '逻辑唯一键';
COMMENT ON TABLE "public"."sys_role" IS '角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES (1, '超级管理员', 'ROOT', 0, 1, 1, '2021-12-26 22:02:26', 1, '2022-10-13 16:15:51', 0, '000000', 0);
INSERT INTO "public"."sys_role" VALUES (2, '管理员', 'ADMIN', 0, 1, 1, '2022-12-15 14:01:22', 1, '2023-05-31 16:15:26.754', 0, '000000', 0);
INSERT INTO "public"."sys_role" VALUES (5, '游客', 'TOURIST', 5, 1, 1, '2022-12-15 14:01:22', 1, '2022-10-19 15:37:36', 0, '000000', 0);
INSERT INTO "public"."sys_role" VALUES (4, '普通用户', 'NORMAL', 4, 1, 1, '2022-12-15 14:01:22', 1, '2023-06-05 14:18:08', 0, '000000', 0);
INSERT INTO "public"."sys_role" VALUES (1963080442572177409, '流程角色1', 'FLOW_1', 6, 1, 1, '2025-09-03 11:23:44.450037', 1, '2025-09-03 11:23:59.502755', 0, '000000', 0);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_dept";
CREATE TABLE "public"."sys_role_dept" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "dept_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_role_dept"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_role_dept"."role_id" IS '角色 id';
COMMENT ON COLUMN "public"."sys_role_dept"."dept_id" IS '部门 id';
COMMENT ON TABLE "public"."sys_role_dept" IS '角色部门关联表，可以知道角色有多少部门，也可以知道部门有多少角色';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_menu";
CREATE TABLE "public"."sys_role_menu" (
  "role_id" int8 NOT NULL,
  "menu_id" int8 NOT NULL,
  "checked" int2
)
;
COMMENT ON COLUMN "public"."sys_role_menu"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."sys_role_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "public"."sys_role_menu"."checked" IS '是否选中，因为有上下级关系，这里要确定是否是选中的，没选中的说明是上级（半选/全选）';
COMMENT ON TABLE "public"."sys_role_menu" IS '角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO "public"."sys_role_menu" VALUES (5, 1537320455847165953, 1);
INSERT INTO "public"."sys_role_menu" VALUES (5, 0, 1);
INSERT INTO "public"."sys_role_menu" VALUES (5, 1572500323467444226, 1);
INSERT INTO "public"."sys_role_menu" VALUES (5, 4, 0);
INSERT INTO "public"."sys_role_menu" VALUES (2, 0, 1);
INSERT INTO "public"."sys_role_menu" VALUES (2, 6, 1);
INSERT INTO "public"."sys_role_menu" VALUES (2, 4, 0);
INSERT INTO "public"."sys_role_menu" VALUES (2, 7, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 4, 0);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1572502356924743682, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1627485035375833090, 0);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1631227390958325768, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1537698325769674753, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1529380639935463426, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1627485430827397122, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 0, 1);
INSERT INTO "public"."sys_role_menu" VALUES (4, 1563061288516792322, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1961000320654487554, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963544670945005572, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1964010182879223809, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963450958801895425, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963450958801895427, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963544670945005569, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963450958801895426, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963544670945005570, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1962440040961822722, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 0, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1961001205065428993, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1962731950251192321, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963544670945005571, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1964010182879223810, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1962731950251192333, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1964010182879223811, 1);
INSERT INTO "public"."sys_role_menu" VALUES (1963080442572177409, 1963542266031755265, 1);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_permission";
CREATE TABLE "public"."sys_role_permission" (
  "role_id" int8 NOT NULL,
  "permission_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_role_permission"."role_id" IS '角色id';
COMMENT ON COLUMN "public"."sys_role_permission"."permission_id" IS '权限id';
COMMENT ON TABLE "public"."sys_role_permission" IS '角色权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572141139009785860);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572824375696494593);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572824564767330306);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572141139009785858);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572141139009785859);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572155394710884356);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572155394710884354);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572155394710884355);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572153139278434307);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572153139278434306);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572157879118778369);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572155394710884357);
INSERT INTO "public"."sys_role_permission" VALUES (2, 1572153139278434305);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881542);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881543);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881540);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881541);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881538);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881539);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1631122862690881537);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1572827619025948673);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1572827421113520129);
INSERT INTO "public"."sys_role_permission" VALUES (4, 1773170931628363777);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_tenant";
CREATE TABLE "public"."sys_tenant" (
  "id" int8 NOT NULL,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_manager" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "unique_key" numeric(20,0),
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" int2 NOT NULL,
  "sort" int4
)
;
COMMENT ON COLUMN "public"."sys_tenant"."id" IS '主键 id';
COMMENT ON COLUMN "public"."sys_tenant"."tenant_id" IS '租户 id';
COMMENT ON COLUMN "public"."sys_tenant"."tenant_name" IS '租户名';
COMMENT ON COLUMN "public"."sys_tenant"."tenant_manager" IS '租户管理员';
COMMENT ON COLUMN "public"."sys_tenant"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_tenant"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_tenant"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_tenant"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_tenant"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_tenant"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."sys_tenant"."icon" IS '图标';
COMMENT ON COLUMN "public"."sys_tenant"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_tenant"."status" IS '状态（1 启动 0 禁用）';
COMMENT ON COLUMN "public"."sys_tenant"."sort" IS '排序';
COMMENT ON TABLE "public"."sys_tenant" IS '租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO "public"."sys_tenant" VALUES (1, '000000', '系统默认租户', 'root', 1, '2022-08-17 03:09:45', 1, '2022-10-18 17:48:20', 0, 0, 'ant-design:cloud-server-outlined', NULL, 1, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" int8 NOT NULL,
  "username" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "real_name" varchar(64) COLLATE "pg_catalog"."default",
  "nickname" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "gender" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(32) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "user_type" varchar(2) COLLATE "pg_catalog"."default",
  "login_ip" varchar(128) COLLATE "pg_catalog"."default",
  "login_date" timestamp(6),
  "status" int2 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "openid" varchar(50) COLLATE "pg_catalog"."default",
  "unique_key" numeric(20,0),
  "new_col" varchar(100) COLLATE "pg_catalog"."default",
  "passwd_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sys_user"."id" IS '主键id';
COMMENT ON COLUMN "public"."sys_user"."username" IS '登录用户名';
COMMENT ON COLUMN "public"."sys_user"."real_name" IS '真实姓名';
COMMENT ON COLUMN "public"."sys_user"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."sys_user"."gender" IS '性别';
COMMENT ON COLUMN "public"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "public"."sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "public"."sys_user"."phone" IS '电话';
COMMENT ON COLUMN "public"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sys_user"."user_type" IS '用户类型（00系统用户）';
COMMENT ON COLUMN "public"."sys_user"."login_ip" IS '最后登录IP';
COMMENT ON COLUMN "public"."sys_user"."login_date" IS '最后登录时间';
COMMENT ON COLUMN "public"."sys_user"."status" IS '状态(1 有效 0 无效  2 冻结)';
COMMENT ON COLUMN "public"."sys_user"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_user"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_user"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."sys_user"."openid" IS '微信小程序 openid';
COMMENT ON COLUMN "public"."sys_user"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."sys_user"."new_col" IS '新字段';
COMMENT ON COLUMN "public"."sys_user"."passwd_time" IS '密码更新时间';
COMMENT ON TABLE "public"."sys_user" IS '用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES (2, 'admin', '管*员', 'Admin', '1', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13737370337', 'admin@taybct.com', '00', NULL, NULL, 1, 0, '2022-01-02 10:50:06', 1, '2025-07-16 11:36:24.517958', 0, NULL, 0, NULL, '2022-01-02 10:50:06');
INSERT INTO "public"."sys_user" VALUES (1945059926271860737, 'user', '用户', '普通用户', '1', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13636360636', 'user@taybct.com', '00', ' ', NULL, 1, 1, '2025-07-15 17:56:38.553154', 1, '2025-07-16 11:38:43.368999', 0, NULL, 0, NULL, '2025-07-16 11:38:36.340245');
INSERT INTO "public"."sys_user" VALUES (1, 'root', '艾米', 'Amy', '2', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13838380438', 'amy@taybct.com', '00', NULL, NULL, 1, 0, '2022-01-02 10:50:06', 1, '2025-09-06 00:07:01.252278', 0, NULL, 0, NULL, '2025-07-24 17:33:13.933135');
INSERT INTO "public"."sys_user" VALUES (1963085401439076353, 'boos', NULL, '总经理', '1', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13888880003', NULL, '00', ' ', NULL, 1, 1, '2025-09-03 11:43:26.736085', 1, '2025-09-03 11:43:59.675314', 0, NULL, 0, NULL, '2025-09-03 11:43:26.736085');
INSERT INTO "public"."sys_user" VALUES (1963084850613714945, 'leader1', NULL, '部门领导', '1', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13888880002', NULL, '00', ' ', NULL, 1, 1, '2025-09-03 11:41:15.407096', 1, '2025-09-03 11:43:35.200496', 0, NULL, 0, NULL, '2025-09-03 11:41:15.405578');
INSERT INTO "public"."sys_user" VALUES (1963080001398505474, 'leader2', '', '小组长', '1', 'G1l7pDflJ8GyWTm5BzsMY/I0ZBHtY+J7mqfKNuCD4Ub9v5rPZUhCtW2zotJBjsKH39CXjvjC8ysGg/bukgtF35XEJczveNHI8+q2hpuOvDQ=', NULL, '13888880001', NULL, '00', ' ', NULL, 1, 1, '2025-09-03 11:21:59.261628', 1, '2025-09-17 14:17:34.874101', 0, NULL, 0, NULL, '2025-09-03 11:21:59.260117');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_dept";
CREATE TABLE "public"."sys_user_dept" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "dept_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_user_dept"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_user_dept"."user_id" IS '用户 id';
COMMENT ON COLUMN "public"."sys_user_dept"."dept_id" IS '部门 id';
COMMENT ON TABLE "public"."sys_user_dept" IS '用户部门关联表，可以知道用户有多少部门，也可以知道部门有多少用户';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO "public"."sys_user_dept" VALUES (1945059926527713281, 1945059926271860737, 2);
INSERT INTO "public"."sys_user_dept" VALUES (1945294810911367169, 2, 1);
INSERT INTO "public"."sys_user_dept" VALUES (1963080001792770050, 1963080001398505474, 2);
INSERT INTO "public"."sys_user_dept" VALUES (1963085437195517953, 1963084850613714945, 3);
INSERT INTO "public"."sys_user_dept" VALUES (1963085539876274177, 1963085401439076353, 4);
INSERT INTO "public"."sys_user_dept" VALUES (1963080001792770051, 1963080001398505474, 3);
INSERT INTO "public"."sys_user_dept" VALUES (1963080001792770052, 1963080001398505474, 4);

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_online";
CREATE TABLE "public"."sys_user_online" (
  "id" int8 NOT NULL,
  "jti" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "ip" varchar(20) COLLATE "pg_catalog"."default",
  "client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(255) COLLATE "pg_catalog"."default",
  "login_time" timestamp(6) NOT NULL,
  "exp" int8 NOT NULL,
  "exp_time" timestamp(6),
  "user_id" int8,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default",
  "auth_method" varchar(100) COLLATE "pg_catalog"."default",
  "access_token_value" varchar COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user_online"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_user_online"."jti" IS 'jwt token id';
COMMENT ON COLUMN "public"."sys_user_online"."ip" IS 'ip 地址';
COMMENT ON COLUMN "public"."sys_user_online"."client_id" IS '客户端 id';
COMMENT ON COLUMN "public"."sys_user_online"."user_name" IS '用户名';
COMMENT ON COLUMN "public"."sys_user_online"."login_time" IS '登录时间';
COMMENT ON COLUMN "public"."sys_user_online"."exp" IS '超时时间';
COMMENT ON COLUMN "public"."sys_user_online"."exp_time" IS '在什么时候超时';
COMMENT ON COLUMN "public"."sys_user_online"."user_id" IS '用户 id';
COMMENT ON COLUMN "public"."sys_user_online"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."sys_user_online"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user_online"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."sys_user_online"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_user_online"."tenant_id" IS '租户id';
COMMENT ON COLUMN "public"."sys_user_online"."auth_method" IS '认证方式';
COMMENT ON COLUMN "public"."sys_user_online"."access_token_value" IS '访问的 token 值';
COMMENT ON TABLE "public"."sys_user_online" IS '在线用户';

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------
INSERT INTO "public"."sys_user_online" VALUES (1976940172646395906, '127a492b751a46ffb2f123cb3f6a0c66', '127.0.0.1', 'taybct_pc', 'root', '2025-10-11 17:17:21.598578', 1760177841000, '2025-10-11 18:17:21.589512', 1, 1, '2025-10-11 17:17:21.598578', 1, '2025-10-11 17:17:21.598578', '000000', 'username', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjEyN2E0OTJiNzUxYTQ2ZmZiMmYxMjNjYjNmNmEwYzY2Iiwia2lkIjoiOGUzZjNjNzkwNTgwNDI5MTljMmRiNzNlMWZlMTdjNGEifQ.eyJ1aWQiOiIxIiwibmJmIjoxNzYwMTc0MjQxLCJncmFudF90eXBlIjoidGF5YmN0IiwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJhdG0iOiJ1c2VybmFtZSIsImV4cCI6MTc2MDE3Nzg0MSwiaWF0IjoxNzYwMTc0MjQxLCJqdGkiOiIzNTBiMTg3ZS01YTE3LTRjYWQtODVkOS0wYzU1NjcwOWYxNWIiLCJjbGllbnRfaWQiOiJ0YXliY3RfcGMiLCJhdXRob3JpdGllcyI6WyIwMDAwMDA6Uk9PVCJdfQ.UX-GsJLa5DIq5ygQbEAk7PyfBEd3CTPn539YmG8ivjxkl_zQFPl39S_VFUjpyAUaHE-ICXaI2VzJJSLtnD5zb6dHl0DUEUtuzkM_pHoBp5JzcqerV9Y_Nnlm1L_Eh-f1eGWN-rGedORZMtFhKpM8G9i0NiJzpUjg5tSdthl3mJVm8iaqZNPhKxNIAipeoEvkqYE9Go8WoCs17UvPVinf9zaSQZv9ooOjK-SgKQ99iUY4m8vUC38rzwfSIGtlG9P5lO3JiAr7HgqSULqxiGGogrW2II8yk1j9uJy8ypC5dpZbVgQib7QZehoSMl75bO-Zrx5o50Kv87QOTpjtO0DRKA');
INSERT INTO "public"."sys_user_online" VALUES (1976941496259670017, 'a30d7a28846147a49ce942bc62a47e35', '127.0.0.1', 'taybct_pc', 'root', '2025-10-11 17:22:37.160849', 1760178157000, '2025-10-11 18:22:37.101521', 1, 1, '2025-10-11 17:22:37.160849', 1, '2025-10-11 17:22:37.160849', '000000', 'username', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImEzMGQ3YTI4ODQ2MTQ3YTQ5Y2U5NDJiYzYyYTQ3ZTM1Iiwia2lkIjoiN2JiODg3ZGQxNWRhNDcxN2E0ODljOGJjOGMzZjUzODIifQ.eyJ1aWQiOiIxIiwibmJmIjoxNzYwMTc0NTU3LCJncmFudF90eXBlIjoidGF5YmN0IiwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJhdG0iOiJ1c2VybmFtZSIsImV4cCI6MTc2MDE3ODE1NywiaWF0IjoxNzYwMTc0NTU3LCJqdGkiOiIyNzJjNzgyMS1kZjNhLTRlNGQtODViZC0zNzk3NTIyZjg2YWIiLCJjbGllbnRfaWQiOiJ0YXliY3RfcGMiLCJhdXRob3JpdGllcyI6WyIwMDAwMDA6Uk9PVCJdfQ.VcxiFUTBbsDBgSUxt-yiBVQEoanXbsZ9Bz1_oDlO75h0UeFN6Ag3cRiQDM4Z3G-niupDxAIXYkQRx4MALcxxiPBIdVLbnJOprT7miABXDYxCh-RqN8p2Vh1urp-Qyle9ttV390DYJ1mhd2NJjqFlKUGNBAQcrp-SasJ18n-jJBBRnrIdstzcFy4Xwga11YDeM5M0z1VWynjGY_kk3ciOb223hGG_O6TVv3vh0h0yQZabUOzcsGFxmnLZX_BzYWF85c8T6K11fuUqchqqALikXR-czqYN9osxLW-ouk-Ko8flSl95BOY9Q0m6AaIszN4SMygQLxUwO0sMwmSJRQK0BA');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_user_role"."role_id" IS '角色id';
COMMENT ON TABLE "public"."sys_user_role" IS '用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO "public"."sys_user_role" VALUES (1, 1);
INSERT INTO "public"."sys_user_role" VALUES (1945059926271860737, 4);
INSERT INTO "public"."sys_user_role" VALUES (2, 2);
INSERT INTO "public"."sys_user_role" VALUES (1963080001398505474, 1963080442572177409);
INSERT INTO "public"."sys_user_role" VALUES (1963084850613714945, 1963080442572177409);
INSERT INTO "public"."sys_user_role" VALUES (1963085401439076353, 1963080442572177409);

-- ----------------------------
-- Table structure for sys_user_tenant
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_tenant";
CREATE TABLE "public"."sys_user_tenant" (
  "user_id" int8 NOT NULL,
  "tenant_id" varchar(34) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_user_tenant"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_user_tenant"."tenant_id" IS '租户id';
COMMENT ON TABLE "public"."sys_user_tenant" IS '用户_租户关联';

-- ----------------------------
-- Records of sys_user_tenant
-- ----------------------------
INSERT INTO "public"."sys_user_tenant" VALUES (1, '000000');
INSERT INTO "public"."sys_user_tenant" VALUES (2, '000000');
INSERT INTO "public"."sys_user_tenant" VALUES (1945059926271860737, '000000');
INSERT INTO "public"."sys_user_tenant" VALUES (1963080001398505474, '000000');
INSERT INTO "public"."sys_user_tenant" VALUES (1963084850613714945, '000000');
INSERT INTO "public"."sys_user_tenant" VALUES (1963085401439076353, '000000');

-- ----------------------------
-- Table structure for t_online_doc
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_online_doc";
CREATE TABLE "public"."t_online_doc" (
  "id" int8 NOT NULL,
  "create_user" int8,
  "create_time" timestamp(6),
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2,
  "unique_key" int8,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "share" int2,
  "unique_field" varchar(255) COLLATE "pg_catalog"."default",
  "properties" jsonb,
  "dept_id" int8,
  "dept_name" varchar(255) COLLATE "pg_catalog"."default",
  "data" jsonb,
  "create_user_name" varchar(255) COLLATE "pg_catalog"."default",
  "update_user_name" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_online_doc"."id" IS '主键';
COMMENT ON COLUMN "public"."t_online_doc"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."t_online_doc"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."t_online_doc"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."t_online_doc"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."t_online_doc"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."t_online_doc"."unique_key" IS '逻辑唯一键';
COMMENT ON COLUMN "public"."t_online_doc"."name" IS '表单名称';
COMMENT ON COLUMN "public"."t_online_doc"."share" IS '表单是否共享';
COMMENT ON COLUMN "public"."t_online_doc"."unique_field" IS '唯一字段';
COMMENT ON COLUMN "public"."t_online_doc"."properties" IS '表单属性设置（字段等）';
COMMENT ON COLUMN "public"."t_online_doc"."dept_id" IS '所属部门id';
COMMENT ON COLUMN "public"."t_online_doc"."dept_name" IS '所属部门名称';
COMMENT ON COLUMN "public"."t_online_doc"."data" IS '数据';
COMMENT ON COLUMN "public"."t_online_doc"."create_user_name" IS '创建人姓名';
COMMENT ON COLUMN "public"."t_online_doc"."update_user_name" IS '修改人姓名';
COMMENT ON TABLE "public"."t_online_doc" IS '在线文档';

-- ----------------------------
-- Records of t_online_doc
-- ----------------------------
INSERT INTO "public"."t_online_doc" VALUES (1969953734679613442, 1, '2025-09-22 10:35:45.157617', 1, '2025-09-22 10:35:45.157617', 0, 0, 'xxx', 0, NULL, NULL, NULL, NULL, '{"url": "/2025/09/22/68b86331dd154e22a0cf98562838abb0.docx", "title": "docker 相关操作.docx", "fileType": "docx", "originalUrl": "/2025/09/22/68b86331dd154e22a0cf98562838abb0.docx", "documentType": "word"}', '艾米', '艾米');
INSERT INTO "public"."t_online_doc" VALUES (1970036619021402113, 1, '2025-09-22 16:05:06.310317', 1, '2025-09-22 16:05:06.311836', 0, 0, 'cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc', 0, NULL, NULL, NULL, NULL, '{"url": "/2025/09/22/28989f3e3d4743bbb36787293ac1d389.xlsx", "title": "test.xlsx", "fileType": "xlsx", "originalUrl": "/2025/09/22/28989f3e3d4743bbb36787293ac1d389.xlsx", "documentType": "cell"}', '艾米', '艾米');

-- ----------------------------
-- Table structure for t_online_doc_permit
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_online_doc_permit";
CREATE TABLE "public"."t_online_doc_permit" (
  "id" int8 NOT NULL,
  "doc_id" int8 NOT NULL,
  "dept_id" int8 NOT NULL,
  "user_id" int8,
  "permissions" jsonb,
  "is_admin" int2
)
;
COMMENT ON COLUMN "public"."t_online_doc_permit"."id" IS '主键';
COMMENT ON COLUMN "public"."t_online_doc_permit"."doc_id" IS '表单id';
COMMENT ON COLUMN "public"."t_online_doc_permit"."dept_id" IS '部门id';
COMMENT ON COLUMN "public"."t_online_doc_permit"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."t_online_doc_permit"."permissions" IS '文档的操作权限';
COMMENT ON COLUMN "public"."t_online_doc_permit"."is_admin" IS '是否是管理员';
COMMENT ON TABLE "public"."t_online_doc_permit" IS '在线文档操作权限';

-- ----------------------------
-- Records of t_online_doc_permit
-- ----------------------------

-- ----------------------------
-- Table structure for t_vue_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_vue_template";
CREATE TABLE "public"."t_vue_template" (
  "id" int8 NOT NULL,
  "create_user" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_user" int8,
  "update_time" timestamp(6),
  "is_deleted" int2 NOT NULL,
  "string" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "number_int" int4,
  "number_long" int8,
  "date" date,
  "date_time" timestamp(6),
  "number_byte" int2,
  "bool_type" bool,
  "text_type" text COLLATE "pg_catalog"."default",
  "json_type" json,
  "float_type" float4,
  "double_type" float8
)
;
COMMENT ON COLUMN "public"."t_vue_template"."id" IS '主键';
COMMENT ON COLUMN "public"."t_vue_template"."create_user" IS '创建人';
COMMENT ON COLUMN "public"."t_vue_template"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."t_vue_template"."update_user" IS '修改人';
COMMENT ON COLUMN "public"."t_vue_template"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."t_vue_template"."is_deleted" IS '是否已删除';
COMMENT ON COLUMN "public"."t_vue_template"."string" IS '字符串类型';
COMMENT ON COLUMN "public"."t_vue_template"."number_int" IS '整数类型';
COMMENT ON COLUMN "public"."t_vue_template"."number_long" IS '长整数类型';
COMMENT ON COLUMN "public"."t_vue_template"."date" IS '日期类型';
COMMENT ON COLUMN "public"."t_vue_template"."date_time" IS '日期时间类型';
COMMENT ON COLUMN "public"."t_vue_template"."number_byte" IS '字节类型';
COMMENT ON COLUMN "public"."t_vue_template"."bool_type" IS '布尔类型';
COMMENT ON COLUMN "public"."t_vue_template"."text_type" IS '长文本类型';
COMMENT ON COLUMN "public"."t_vue_template"."json_type" IS 'JSON 类型';
COMMENT ON COLUMN "public"."t_vue_template"."float_type" IS '单精度浮点类型';
COMMENT ON COLUMN "public"."t_vue_template"."double_type" IS '双精度浮点类型';
COMMENT ON TABLE "public"."t_vue_template" IS '前端通用模板';

-- ----------------------------
-- Records of t_vue_template
-- ----------------------------
INSERT INTO "public"."t_vue_template" VALUES (1819664597403734017, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (6, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (7, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (8, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (9, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (10, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (11, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (12, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (2, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (3, 1819664597403734017, '2024-12-03 16:44:20', 1819664597403734017, '2024-12-03 16:44:22', 0, '字符串', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', 'fff', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (4, 1819664597403734017, '2024-12-03 16:44:20', 1, '2024-12-06 11:44:56.876249', 0, '字符串fff', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', '繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁繁', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (5, 1819664597403734017, '2024-12-03 16:44:20', 1, '2024-12-06 11:45:42.608236', 0, '字符串fff', 12345678, 1819664597403734017, '2024-12-03', '2024-12-03 16:44:42', 1, 'f', '我这一生好吃懒做九死一生', '{"name":"张三"}', 1.2, 1.234567891);
INSERT INTO "public"."t_vue_template" VALUES (1864881528770252801, 1, '2024-12-06 11:56:18.947714', 1, '2024-12-06 11:56:18.947714', 0, 'fff', NULL, NULL, NULL, NULL, NULL, 'f', NULL, NULL, NULL, NULL);
INSERT INTO "public"."t_vue_template" VALUES (1864965168116023298, 1, '2024-12-06 17:28:40.115064', 1, '2024-12-06 17:28:40.115064', 0, 'fsadfasdf', 11122, 23344, '2024-12-06', '2024-12-06 17:22:00', 1, 'f', '体体体体体体体体体体体体体体', '{"name":"李四"}', 3.1, 3.14159265);
INSERT INTO "public"."t_vue_template" VALUES (1864965218049212418, 1, '2024-12-06 17:28:52.027903', 1, '2025-08-18 09:29:33.815', 0, 'fsadfasdf', 11122, 23344, '2024-12-06', '2024-12-06 17:22:00', 1, 'f', '体体体体体体体体体体体体体体', '{"name":"李四"}', 3.1, 3.14159265);
INSERT INTO "public"."t_vue_template" VALUES (1956285340134690817, 1, '2025-08-15 17:22:25.773192', 1, '2025-08-18 09:29:33.815', 0, 'test', 11, 22, '2025-08-15', '2025-08-15 17:22:04', 33, 'f', '44', '{"name":"demo","age":31}', 55, 66);
INSERT INTO "public"."t_vue_template" VALUES (1957300498730598402, 1, '2025-08-18 12:36:18.446456', 1, '2025-08-18 12:36:24.52', 1, '1', 2, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."undo_log";
CREATE TABLE "public"."undo_log" (
  "id" int8 NOT NULL,
  "branch_id" int8 NOT NULL,
  "xid" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "context" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "rollback_info" bytea NOT NULL,
  "log_status" int4 NOT NULL,
  "log_created" timestamp(6) NOT NULL,
  "log_modified" timestamp(6) NOT NULL,
  "ext" varchar(100) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "username" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" int2 NOT NULL
)
;
COMMENT ON TABLE "public"."users" IS 'spring security 用户表';

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Indexes structure for table api_log
-- ----------------------------
CREATE INDEX "idx_api_log_title" ON "public"."api_log" USING btree (
  "title" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_api_log_title" IS '模块名';

-- ----------------------------
-- Primary Key structure for table api_log
-- ----------------------------
ALTER TABLE "public"."api_log" ADD CONSTRAINT "api_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table authorities
-- ----------------------------
CREATE UNIQUE INDEX "ix_auth_username" ON "public"."authorities" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "authority" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table lf_design
-- ----------------------------
CREATE INDEX "idx_design_name" ON "public"."lf_design" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_design
-- ----------------------------
ALTER TABLE "public"."lf_design" ADD CONSTRAINT "lf_design_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_design_permissions
-- ----------------------------
CREATE INDEX "idx_design_d_id" ON "public"."lf_design_permissions" USING btree (
  "design_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_design_permissions
-- ----------------------------
ALTER TABLE "public"."lf_design_permissions" ADD CONSTRAINT "lf_design_permissions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_edges
-- ----------------------------
CREATE INDEX "idx_edges_process_id" ON "public"."lf_edges" USING btree (
  "process_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_edges_s_node_id" ON "public"."lf_edges" USING btree (
  "source_node_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_edges_t_node_id" ON "public"."lf_edges" USING btree (
  "target_node_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_edges
-- ----------------------------
ALTER TABLE "public"."lf_edges" ADD CONSTRAINT "lf_edges_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_form
-- ----------------------------
CREATE INDEX "idx_lf_form_name" ON "public"."lf_form" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_form
-- ----------------------------
ALTER TABLE "public"."lf_form" ADD CONSTRAINT "lf_form_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_form_release
-- ----------------------------
CREATE INDEX "idx_form_release_f_i" ON "public"."lf_form_release" USING btree (
  "form_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_form_release_name" ON "public"."lf_form_release" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_form_release
-- ----------------------------
ALTER TABLE "public"."lf_form_release" ADD CONSTRAINT "lf_form_release_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_history
-- ----------------------------
CREATE INDEX "idx_history_dept_id" ON "public"."lf_history" USING btree (
  "dept_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_history_user_id" ON "public"."lf_history" USING btree (
  "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_node_id" ON "public"."lf_history" USING btree (
  "node_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_process_id" ON "public"."lf_history" USING btree (
  "process_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_history
-- ----------------------------
ALTER TABLE "public"."lf_history" ADD CONSTRAINT "lf_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_nodes
-- ----------------------------
CREATE INDEX "idx_nodes_process_id" ON "public"."lf_nodes" USING btree (
  "process_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_nodes
-- ----------------------------
ALTER TABLE "public"."lf_nodes" ADD CONSTRAINT "lf_nodes_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_present_process
-- ----------------------------
CREATE INDEX "idx_present_n_id" ON "public"."lf_present_process" USING btree (
  "node_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_present_p_id" ON "public"."lf_present_process" USING btree (
  "process_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_present_process
-- ----------------------------
ALTER TABLE "public"."lf_present_process" ADD CONSTRAINT "lf_present_process_pkey" PRIMARY KEY ("process_id", "node_id");

-- ----------------------------
-- Indexes structure for table lf_process
-- ----------------------------
CREATE INDEX "idx_process_dept_id" ON "public"."lf_process" USING btree (
  "dept_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_process_dept_id" IS '部门 id';
CREATE INDEX "idx_process_design_id" ON "public"."lf_process" USING btree (
  "design_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_process_design_id" IS '流程设计图 id';
CREATE INDEX "idx_process_release_id" ON "public"."lf_process" USING btree (
  "release_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_process_release_id" IS '流程版本 id';
CREATE INDEX "idx_process_user_id" ON "public"."lf_process" USING btree (
  "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_process_user_id" IS '用户 id';

-- ----------------------------
-- Primary Key structure for table lf_process
-- ----------------------------
ALTER TABLE "public"."lf_process" ADD CONSTRAINT "lf_process_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_release
-- ----------------------------
CREATE INDEX "idx_release_design_id" ON "public"."lf_release" USING btree (
  "design_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_release_name" ON "public"."lf_release" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_release
-- ----------------------------
ALTER TABLE "public"."lf_release" ADD CONSTRAINT "lf_release_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_release_permissions
-- ----------------------------
CREATE INDEX "idx_release_p_re_id" ON "public"."lf_release_permissions" USING btree (
  "release_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_release_permissions
-- ----------------------------
ALTER TABLE "public"."lf_release_permissions" ADD CONSTRAINT "lf_release_permissions_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table lf_todo
-- ----------------------------
CREATE INDEX "idx_todo_d_id" ON "public"."lf_todo" USING btree (
  "design_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_todo_n_id" ON "public"."lf_todo" USING btree (
  "node_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_todo_p_id" ON "public"."lf_todo" USING btree (
  "process_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table lf_todo
-- ----------------------------
ALTER TABLE "public"."lf_todo" ADD CONSTRAINT "lf_release_permissions_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table oauth2_authorization
-- ----------------------------
ALTER TABLE "public"."oauth2_authorization" ADD CONSTRAINT "oauth2_authorization_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table oauth2_authorization_consent
-- ----------------------------
ALTER TABLE "public"."oauth2_authorization_consent" ADD CONSTRAINT "oac_pkey" PRIMARY KEY ("principal_name", "registered_client_id");

-- ----------------------------
-- Primary Key structure for table oauth2_registered_client
-- ----------------------------
ALTER TABLE "public"."oauth2_registered_client" ADD CONSTRAINT "oauth2_registered_client_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table scheduled_log
-- ----------------------------
ALTER TABLE "public"."scheduled_log" ADD CONSTRAINT "scheduled_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table scheduled_task
-- ----------------------------
CREATE UNIQUE INDEX "scheduled_task_un" ON "public"."scheduled_task" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "task_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table scheduled_task
-- ----------------------------
ALTER TABLE "public"."scheduled_task" ADD CONSTRAINT "scheduled_task_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dept
-- ----------------------------
CREATE INDEX "idx_dept_code" ON "public"."sys_dept" USING btree (
  "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_dept_name" ON "public"."sys_dept" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_dept_pid" ON "public"."sys_dept" USING btree (
  "pid" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "un_dept_code" UNIQUE ("tenant_id", "unique_key", "code");

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict
-- ----------------------------
CREATE INDEX "idx_dict_code" ON "public"."sys_dict" USING btree (
  "dict_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_dict_dict_code" ON "public"."sys_dict" USING btree (
  "dict_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "dict_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_type
-- ----------------------------
CREATE INDEX "idx_type_dict_code" ON "public"."sys_dict_type" USING btree (
  "dict_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_dict_type_dict_code" ON "public"."sys_dict_type" USING btree (
  "dict_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "public"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_file
-- ----------------------------
CREATE INDEX "idx_linked_table" ON "public"."sys_file" USING btree (
  "linked_table" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_linked_table_id" ON "public"."sys_file" USING btree (
  "linked_table_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_sys_file_path" ON "public"."sys_file" USING btree (
  "path" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_file
-- ----------------------------
ALTER TABLE "public"."sys_file" ADD CONSTRAINT "sys_file_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_history_record
-- ----------------------------
CREATE INDEX "idx_operator" ON "public"."sys_history_record" USING btree (
  "create_user" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_table_name" ON "public"."sys_history_record" USING btree (
  "table_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_table_pk" ON "public"."sys_history_record" USING btree (
  "primary_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "primary_value" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_history_record
-- ----------------------------
ALTER TABLE "public"."sys_history_record" ADD CONSTRAINT "sys_history_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_menu
-- ----------------------------
CREATE INDEX "fk_pid" ON "public"."sys_menu" USING btree (
  "parent_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_sys_menu_name" ON "public"."sys_menu" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice
-- ----------------------------
ALTER TABLE "public"."sys_notice" ADD CONSTRAINT "sys_notice_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice_user
-- ----------------------------
ALTER TABLE "public"."sys_notice_user" ADD CONSTRAINT "sys_notice_user_pkey" PRIMARY KEY ("notice_id", "related_id", "notice_type");

-- ----------------------------
-- Indexes structure for table sys_oauth2_client
-- ----------------------------
CREATE UNIQUE INDEX "sys_oauth2_client_un" ON "public"."sys_oauth2_client" USING btree (
  "client_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_oauth2_client
-- ----------------------------
ALTER TABLE "public"."sys_oauth2_client" ADD CONSTRAINT "sys_oauth2_client_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_params
-- ----------------------------
CREATE UNIQUE INDEX "uk_params_params_key" ON "public"."sys_params" USING btree (
  "params_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_params
-- ----------------------------
ALTER TABLE "public"."sys_params" ADD CONSTRAINT "sys_params_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_permission
-- ----------------------------
CREATE INDEX "idx_perm_menuid" ON "public"."sys_permission" USING btree (
  "menu_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_permission
-- ----------------------------
ALTER TABLE "public"."sys_permission" ADD CONSTRAINT "sys_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_permission_group
-- ----------------------------
CREATE UNIQUE INDEX "uk_permission_group_name" ON "public"."sys_permission_group" USING btree (
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_permission_group
-- ----------------------------
ALTER TABLE "public"."sys_permission_group" ADD CONSTRAINT "sys_permission_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE INDEX "idx_sys_role_code" ON "public"."sys_role" USING btree (
  "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_sys_role_code" ON "public"."sys_role" USING btree (
  "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role_dept
-- ----------------------------
CREATE INDEX "idx_role_dept_dept_id" ON "public"."sys_role_dept" USING btree (
  "dept_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_role_dept_role_id" ON "public"."sys_role_dept" USING btree (
  "role_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_role_dept
-- ----------------------------
ALTER TABLE "public"."sys_role_dept" ADD CONSTRAINT "uk_role_dept_id" UNIQUE ("role_id", "dept_id");
COMMENT ON CONSTRAINT "uk_role_dept_id" ON "public"."sys_role_dept" IS '一个角色只能关联一个部门一次';

-- ----------------------------
-- Primary Key structure for table sys_role_dept
-- ----------------------------
ALTER TABLE "public"."sys_role_dept" ADD CONSTRAINT "sys_role_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("role_id", "menu_id");

-- ----------------------------
-- Primary Key structure for table sys_role_permission
-- ----------------------------
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "sys_role_permission_pkey" PRIMARY KEY ("role_id", "permission_id");

-- ----------------------------
-- Indexes structure for table sys_tenant
-- ----------------------------
CREATE INDEX "idx_tenant_tenant_id" ON "public"."sys_tenant" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_sys_tenant_id" ON "public"."sys_tenant" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."numeric_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_sys_tenant_name" ON "public"."sys_tenant" USING btree (
  "tenant_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."numeric_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "uk_tenant_tenant_id" UNIQUE ("tenant_id");
COMMENT ON CONSTRAINT "uk_tenant_tenant_id" ON "public"."sys_tenant" IS '租户名不能重复';

-- ----------------------------
-- Primary Key structure for table sys_tenant
-- ----------------------------
ALTER TABLE "public"."sys_tenant" ADD CONSTRAINT "sys_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE INDEX "idx_sys_user_phone" ON "public"."sys_user" USING btree (
  "phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_sys_user_username" ON "public"."sys_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_user_name" ON "public"."sys_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."numeric_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."uk_user_name" IS '用户名重复';
CREATE UNIQUE INDEX "uk_user_phone" ON "public"."sys_user" USING btree (
  "phone" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "unique_key" "pg_catalog"."numeric_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."uk_user_phone" IS '用户手机号重复';

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_dept
-- ----------------------------
CREATE INDEX "idx_user_dept_dept_id" ON "public"."sys_user_dept" USING btree (
  "dept_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_user_dept_user_id" ON "public"."sys_user_dept" USING btree (
  "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table sys_user_dept
-- ----------------------------
ALTER TABLE "public"."sys_user_dept" ADD CONSTRAINT "uk_user_dept_id" UNIQUE ("user_id", "dept_id");
COMMENT ON CONSTRAINT "uk_user_dept_id" ON "public"."sys_user_dept" IS '一个用户和一个部门只能关联一次';

-- ----------------------------
-- Primary Key structure for table sys_user_dept
-- ----------------------------
ALTER TABLE "public"."sys_user_dept" ADD CONSTRAINT "用户部门关联表_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_online
-- ----------------------------
CREATE INDEX "idx_online_username" ON "public"."sys_user_online" USING btree (
  "user_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."idx_online_username" IS '用户名，会用来查询';
CREATE INDEX "uni_online_jti" ON "public"."sys_user_online" USING btree (
  "jti" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."uni_online_jti" IS 'jwt token 只能有一个管理';

-- ----------------------------
-- Primary Key structure for table sys_user_online
-- ----------------------------
ALTER TABLE "public"."sys_user_online" ADD CONSTRAINT "sys_user_online_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("user_id", "role_id");

-- ----------------------------
-- Indexes structure for table sys_user_tenant
-- ----------------------------
CREATE UNIQUE INDEX "uk_user_tenant" ON "public"."sys_user_tenant" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user_tenant
-- ----------------------------
ALTER TABLE "public"."sys_user_tenant" ADD CONSTRAINT "sys_user_tenant_pkey" PRIMARY KEY ("user_id", "tenant_id");

-- ----------------------------
-- Indexes structure for table t_online_doc
-- ----------------------------
CREATE UNIQUE INDEX "uk_tif_form_name" ON "public"."t_online_doc" USING btree (
  "unique_key" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMENT ON INDEX "public"."uk_tif_form_name" IS '表单名是唯一的';

-- ----------------------------
-- Primary Key structure for table t_online_doc
-- ----------------------------
ALTER TABLE "public"."t_online_doc" ADD CONSTRAINT "t_investigation_form_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_online_doc_permit
-- ----------------------------
CREATE INDEX "idx_tifs_form_id" ON "public"."t_online_doc_permit" USING btree (
  "doc_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table t_online_doc_permit
-- ----------------------------
ALTER TABLE "public"."t_online_doc_permit" ADD CONSTRAINT "t_investigation_form_share_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_vue_template
-- ----------------------------
ALTER TABLE "public"."t_vue_template" ADD CONSTRAINT "t_vue_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table undo_log
-- ----------------------------
CREATE UNIQUE INDEX "ux_undo_log" ON "public"."undo_log" USING btree (
  "xid" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "branch_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table undo_log
-- ----------------------------
ALTER TABLE "public"."undo_log" ADD CONSTRAINT "undo_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("username");

-- ----------------------------
-- Foreign Keys structure for table api_log
-- ----------------------------
ALTER TABLE "public"."api_log" ADD CONSTRAINT "fk_api_log_tenid" FOREIGN KEY ("tenant_id") REFERENCES "public"."sys_tenant" ("tenant_id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_api_log_tenid" ON "public"."api_log" IS '关联租户表';

-- ----------------------------
-- Foreign Keys structure for table authorities
-- ----------------------------
ALTER TABLE "public"."authorities" ADD CONSTRAINT "fk_authorities_users" FOREIGN KEY ("username") REFERENCES "public"."users" ("username") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_design_permissions
-- ----------------------------
ALTER TABLE "public"."lf_design_permissions" ADD CONSTRAINT "fk_design_d_id" FOREIGN KEY ("design_id") REFERENCES "public"."lf_design" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_edges
-- ----------------------------
ALTER TABLE "public"."lf_edges" ADD CONSTRAINT "fk_edges_process_id" FOREIGN KEY ("process_id") REFERENCES "public"."lf_process" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_edges" ADD CONSTRAINT "fk_edges_s_node_id" FOREIGN KEY ("source_node_id") REFERENCES "public"."lf_nodes" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_edges" ADD CONSTRAINT "fk_edges_t_node_id" FOREIGN KEY ("target_node_id") REFERENCES "public"."lf_nodes" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_form_release
-- ----------------------------
ALTER TABLE "public"."lf_form_release" ADD CONSTRAINT "fk_form_release_f_i" FOREIGN KEY ("form_id") REFERENCES "public"."lf_form" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_history
-- ----------------------------
ALTER TABLE "public"."lf_history" ADD CONSTRAINT "fk_node_id" FOREIGN KEY ("node_id") REFERENCES "public"."lf_nodes" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_history" ADD CONSTRAINT "fk_process_id" FOREIGN KEY ("process_id") REFERENCES "public"."lf_process" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_nodes
-- ----------------------------
ALTER TABLE "public"."lf_nodes" ADD CONSTRAINT "fk_nodes_process_id" FOREIGN KEY ("process_id") REFERENCES "public"."lf_process" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_present_process
-- ----------------------------
ALTER TABLE "public"."lf_present_process" ADD CONSTRAINT "fk_present_n_id" FOREIGN KEY ("node_id") REFERENCES "public"."lf_nodes" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_present_process" ADD CONSTRAINT "fk_present_p_id" FOREIGN KEY ("process_id") REFERENCES "public"."lf_process" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_process
-- ----------------------------
ALTER TABLE "public"."lf_process" ADD CONSTRAINT "fk_design_id" FOREIGN KEY ("design_id") REFERENCES "public"."lf_design" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_process" ADD CONSTRAINT "fk_release_id" FOREIGN KEY ("release_id") REFERENCES "public"."lf_release" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_release
-- ----------------------------
ALTER TABLE "public"."lf_release" ADD CONSTRAINT "fk_release_design_id" FOREIGN KEY ("design_id") REFERENCES "public"."lf_design" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_release_permissions
-- ----------------------------
ALTER TABLE "public"."lf_release_permissions" ADD CONSTRAINT "fk_release_p_re_id" FOREIGN KEY ("release_id") REFERENCES "public"."lf_release" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table lf_todo
-- ----------------------------
ALTER TABLE "public"."lf_todo" ADD CONSTRAINT "fk_todo_d_id" FOREIGN KEY ("design_id") REFERENCES "public"."lf_design" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_todo" ADD CONSTRAINT "fk_todo_n_id" FOREIGN KEY ("node_id") REFERENCES "public"."lf_nodes" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."lf_todo" ADD CONSTRAINT "fk_todo_p_id" FOREIGN KEY ("process_id") REFERENCES "public"."lf_process" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "fk_dept_pid" FOREIGN KEY ("pid") REFERENCES "public"."sys_dept" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "fk_menu_id" FOREIGN KEY ("parent_id") REFERENCES "public"."sys_menu" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_menu_id" ON "public"."sys_menu" IS '级联关联菜单表';

-- ----------------------------
-- Foreign Keys structure for table sys_notice_user
-- ----------------------------
ALTER TABLE "public"."sys_notice_user" ADD CONSTRAINT "fk_notice_user_n_id" FOREIGN KEY ("notice_id") REFERENCES "public"."sys_notice" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_notice_user_n_id" ON "public"."sys_notice_user" IS '关联通知表';

-- ----------------------------
-- Foreign Keys structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "fk_role_tenantid" FOREIGN KEY ("tenant_id") REFERENCES "public"."sys_tenant" ("tenant_id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_role_tenantid" ON "public"."sys_role" IS '关联租户表';

-- ----------------------------
-- Foreign Keys structure for table sys_role_dept
-- ----------------------------
ALTER TABLE "public"."sys_role_dept" ADD CONSTRAINT "fk_role_dept_dept_id" FOREIGN KEY ("dept_id") REFERENCES "public"."sys_dept" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_role_dept" ADD CONSTRAINT "fk_role_dept_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_role_dept_dept_id" ON "public"."sys_role_dept" IS '关联部门表';
COMMENT ON CONSTRAINT "fk_role_dept_role_id" ON "public"."sys_role_dept" IS '关联角色表';

-- ----------------------------
-- Foreign Keys structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "fk_role_menu_menu_id" FOREIGN KEY ("menu_id") REFERENCES "public"."sys_menu" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "fk_role_menu_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_role_menu_menu_id" ON "public"."sys_role_menu" IS '关联菜单表';
COMMENT ON CONSTRAINT "fk_role_menu_role_id" ON "public"."sys_role_menu" IS '关联角色表';

-- ----------------------------
-- Foreign Keys structure for table sys_role_permission
-- ----------------------------
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "fk_role_perm_permid" FOREIGN KEY ("permission_id") REFERENCES "public"."sys_permission" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "fk_role_perm_roleid" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_role_perm_permid" ON "public"."sys_role_permission" IS '关联权限表';
COMMENT ON CONSTRAINT "fk_role_perm_roleid" ON "public"."sys_role_permission" IS '关联角色表';

-- ----------------------------
-- Foreign Keys structure for table sys_user_dept
-- ----------------------------
ALTER TABLE "public"."sys_user_dept" ADD CONSTRAINT "fk_user_dept_dept_id" FOREIGN KEY ("dept_id") REFERENCES "public"."sys_dept" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_user_dept" ADD CONSTRAINT "fk_user_dept_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."sys_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_user_dept_dept_id" ON "public"."sys_user_dept" IS '关联部门表';
COMMENT ON CONSTRAINT "fk_user_dept_user_id" ON "public"."sys_user_dept" IS '关联用户表';

-- ----------------------------
-- Foreign Keys structure for table sys_user_online
-- ----------------------------
ALTER TABLE "public"."sys_user_online" ADD CONSTRAINT "fk_user_ol_tenid" FOREIGN KEY ("tenant_id") REFERENCES "public"."sys_tenant" ("tenant_id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_user_online" ADD CONSTRAINT "fk_user_ol_userid" FOREIGN KEY ("user_id") REFERENCES "public"."sys_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_user_ol_tenid" ON "public"."sys_user_online" IS '关联租户表';
COMMENT ON CONSTRAINT "fk_user_ol_userid" ON "public"."sys_user_online" IS '关联用户表';

-- ----------------------------
-- Foreign Keys structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "fk_user_role_roleid" FOREIGN KEY ("role_id") REFERENCES "public"."sys_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "fk_user_role_userid" FOREIGN KEY ("user_id") REFERENCES "public"."sys_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_user_role_roleid" ON "public"."sys_user_role" IS '关联角色表';
COMMENT ON CONSTRAINT "fk_user_role_userid" ON "public"."sys_user_role" IS '关联用户表';

-- ----------------------------
-- Foreign Keys structure for table sys_user_tenant
-- ----------------------------
ALTER TABLE "public"."sys_user_tenant" ADD CONSTRAINT "fk_user_tenant_tenid" FOREIGN KEY ("tenant_id") REFERENCES "public"."sys_tenant" ("tenant_id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."sys_user_tenant" ADD CONSTRAINT "fk_user_tenant_userid" FOREIGN KEY ("user_id") REFERENCES "public"."sys_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
COMMENT ON CONSTRAINT "fk_user_tenant_tenid" ON "public"."sys_user_tenant" IS '关联租户表';
COMMENT ON CONSTRAINT "fk_user_tenant_userid" ON "public"."sys_user_tenant" IS '关联用户表';
