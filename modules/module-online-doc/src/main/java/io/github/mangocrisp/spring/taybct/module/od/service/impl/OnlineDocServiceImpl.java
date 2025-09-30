package io.github.mangocrisp.spring.taybct.module.od.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.constant.YesOrNo;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDocPermit;
import io.github.mangocrisp.spring.taybct.module.od.dto.add.OnlineDocAddDTO;
import io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice.*;
import io.github.mangocrisp.spring.taybct.module.od.dto.update.OnlineDocUpdateDTO;
import io.github.mangocrisp.spring.taybct.module.od.mapper.OnlineDocMapper;
import io.github.mangocrisp.spring.taybct.module.od.service.IOnlineDocPermitService;
import io.github.mangocrisp.spring.taybct.module.od.service.IOnlineDocService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.ObjectUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.github.mangocrisp.spring.taybct.tool.file.util.FileServiceBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.hc.core5.http.ContentType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <pre>
 * 针对表【t_online_doc(在线文档)】的数据库操作Service实现
 * </pre>
 *
 * @author SuMuYue
 * @see OnlineDoc
 * @since 2025-03-04 14:59:19
 */
@AutoConfiguration
@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineDocServiceImpl extends ServiceImpl<OnlineDocMapper, OnlineDoc>
        implements IOnlineDocService {

    final IOnlineDocPermitService onlineDocPermitService;

    final SysDeptMapper sysDeptMapper;

    final SysUserMapper sysUserMapper;

    final SysUserDeptMapper sysUserDeptMapper;

    final ISecurityUtil securityUtil;

    final ISysParamsObtainService sysParamsObtainService;

    /**
     * OnlyOffice 历史记录缓存最大数量
     */
    static final String ONLY_OFFICE_HISTORY_CACHE_MAX_COUNT = "ONLY_OFFICE_HISTORY_CACHE_MAX_COUNT";

    static {
        try {
            // 创建一个信任所有证书的 TrustManager
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        }
                    }
            };
            // 初始化 SSLContext
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // 创建一个信任所有主机名的 HostnameVerifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String body = scanner.hasNext() ? scanner.next() : "";
        JSONObject jsonObj = JSONObject.parseObject(body);
        int status = jsonObj.getInteger("status");
            /*
            1 - 正在编辑文档，
            2 - 文档已准备好保存，
            3 - 发生文档保存错误，
            4 - 文档已关闭，没有任何更改，
            6 - 正在编辑文档，但保存了当前文档状态，
            7 - 强制保存文档时发生错误。
             */
        if (status == 2 || status == 3 || status == 6) {
            //System.out.println(jsonObj.toJSONString());
            String id = jsonObj.getString("key").split("_")[0];
            OnlineDoc onlineDoc = getOne(Wrappers.<OnlineDoc>lambdaQuery()
                    .select(OnlineDoc::getId, OnlineDoc::getData)
                    .eq(OnlineDoc::getId, Convert.toLong(id)));
            if (onlineDoc != null) {
                if (StringUtil.isBlank((String) onlineDoc.getData())) {
                    throw new NullPointerException("数据异常，文档数据为空【" + onlineDoc.getId() + "】");
                }
                FileData fileData = JSONObject.parseObject((String) onlineDoc.getData(), FileData.class);
                String fileName = fileData.getTitle();
                // 能查询到才做保存
                String downloadUri = jsonObj.getString("url");
                String path = saveFile(downloadUri, fileName);
                fileData.setUrl(path);

                onlineDoc.setUpdateTime(LocalDateTime.now());
                JSONArray users = jsonObj.getJSONArray("users");
                if (users != null && !users.isEmpty()) {
                    // 最后一次修改的用户
                    onlineDoc.setUpdateUser(users.getLong(users.size() - 1));
                    SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                            .select(SysUser::getId, SysUser::getUsername, SysUser::getNickname, SysUser::getRealName)
                            .eq(SysUser::getId, onlineDoc.getUpdateUser()));
                    SysUserDept sysUserDept = sysUserDeptMapper.selectOne(Wrappers.<SysUserDept>lambdaQuery()
                            .eq(SysUserDept::getUserId, onlineDoc.getUpdateUser())
                            .orderByAsc(SysUserDept::getId)
                            .last("limit 1"));
                    SysDept sysDept = null;
                    if (sysUserDept != null){
                        sysDept = sysDeptMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                                .select(SysDept::getId, SysDept::getName, SysDept::getFullName)
                                .eq(SysDept::getId, sysUserDept.getDeptId()));
                    }
                    String realName = Optional.ofNullable(sysUser.getRealName()).orElse(sysUser.getNickname());
                    if (ObjectUtil.isNotEmpty(sysDept)) {
                        String deptName = Optional.ofNullable(sysDept.getName()).orElse(sysDept.getFullName());
                        realName = realName + "(" + deptName + ")";
                    }
                    onlineDoc.setUpdateUserName(realName);
                }

                if (status != 6) {
                    // 不是强制保存才保存历史记录
                    saveHistory(onlineDoc, fileData, jsonObj);
                }

                onlineDoc.setData(JSONObject.toJSONString(fileData, JSONWriter.Feature.WriteMapNullValue));
                getBaseMapper().updateOnlyOfficeFileUrl(onlineDoc);
            }
        }
        return "{\"error\":0}";
    }

    /**
     * 保存历史版本
     *
     * @param onlineDoc 文档信息
     * @param fileData  文件数据
     * @param jsonData  onlyOffice 回调返回的结果数据
     */
    public void saveHistory(OnlineDoc onlineDoc, FileData fileData, JSONObject jsonData) throws Exception {
        // 历史记录最大缓存次数
        Long onlyOfficeHistoryCacheMaxCount = sysParamsObtainService.getObject(ONLY_OFFICE_HISTORY_CACHE_MAX_COUNT, Long.class, 10L);
        RefreshHistoryDTO refreshHistoryDTO = fileData.getRefreshHistoryDTO();
        // 历史版本记录（用于显示在历史版本列表）
        if (ObjectUtil.isEmpty(refreshHistoryDTO)) {
            refreshHistoryDTO = new RefreshHistoryDTO();
            refreshHistoryDTO.setHistory(new ArrayList<>());
        }

        // 历史版本记录数据（需要显示版本数据的时候需要的数据）
        List<HistoryData> historyData = fileData.getHistoryData();
        if (CollectionUtil.isEmpty(historyData)) {
            historyData = new ArrayList<>();
        }

        String lastsave = jsonData.getString("lastsave");
        String key = jsonData.getString("key");
        JSONObject history = jsonData.getJSONObject("history");
        String serverVersion = history.getString("serverVersion");
        JSONArray changes = history.getJSONArray("changes");

        String version = formatTime(lastsave, "yyyyMMddHHmmss");
        String created = formatTime(lastsave, "yyyy-MM-dd HH:mm:ss");
        refreshHistoryDTO.setCurrentVersion(version);
        refreshHistoryDTO.getHistory().add(History.builder()
                .key(key)
                .created(created)
                .serverVersion(serverVersion)
                .changes(changes)
                .user(HistoryUser.builder()
                        .id(Convert.toStr(onlineDoc.getUpdateUser()))
                        .name(onlineDoc.getUpdateUserName())
                        .build())
                .version(version)
                .build());

        if (refreshHistoryDTO.getHistory().size() > onlyOfficeHistoryCacheMaxCount) {
            // 如果大于这个次数，就删除第一个
            refreshHistoryDTO.getHistory().remove(0);
        }

        fileData.setRefreshHistoryDTO(refreshHistoryDTO);

        String filetype = jsonData.getString("filetype");
        String changesurl = jsonData.getString("changesurl");
        String changesPath = null;
        if (StringUtil.isNoneBlank(changesurl)) {
            String fileType = changesurl.substring(changesurl.lastIndexOf("."));
            changesPath = saveFile(changesurl, key + fileType);
        }

        historyData.add(HistoryData.builder()
                .changesUrl(changesPath)
                .fileType(filetype)
                .key(key)
                .previous(Previous.builder()
                        .key(key)
                        .fileType(fileData.getFileType())
                        .url(fileData.getUrl())
                        .build())
                .token("")
                .url(fileData.getUrl())
                .version(version)
                .build());

        if (historyData.size() > onlyOfficeHistoryCacheMaxCount) {
            // 如果大于这个次数，就删除第一个
            HistoryData data1 = historyData.get(0);
            String url = data1.getUrl();
            String changesUrl = data1.getChangesUrl();
            String previousUrl = data1.getPrevious().getUrl();
            FileServiceBuilder.delete(url);
            if (!url.equalsIgnoreCase(previousUrl)) {
                FileServiceBuilder.delete(previousUrl);
            }
            FileServiceBuilder.delete(changesUrl);
            historyData.remove(0);
        }

        fileData.setHistoryData(historyData);
    }

    private String formatTime(String str, String format) {
        Instant instant = Instant.parse(str);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        return formatter.format(instant.atZone(zoneId));
    }

    /**
     * 保存文件
     *
     * @param downloadUri OnlyOffice 的文件地址
     * @param fileName    上传的文件名（主要用来获取文件类型）
     * @return 上传后的文件地址
     * @throws Exception 异常
     */
    private static String saveFile(String downloadUri, String fileName) throws Exception {
        URL url = new URL(downloadUri);
        HttpURLConnection connection = null;
        HttpsURLConnection connections = null;
        if (downloadUri.startsWith("https://")) {
            connections = (HttpsURLConnection) url.openConnection();
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }
        if (connections == null) {
            connection = (HttpURLConnection) url.openConnection();
        }
        String path;
        try (InputStream stream = connections != null ? connections.getInputStream() : connection.getInputStream()) {
            path = FileServiceBuilder.upload(stream, ContentType.MULTIPART_FORM_DATA, fileName);
        }
        if (connection != null) {
            connection.disconnect();
        }
        if (connections != null) {
            connections.disconnect();
        }
        return path;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(OnlineDocAddDTO dto, MultipartFile file) {
        OnlineDoc onlineDoc = dto.bean();
        // 是否是新增的文档
        boolean isNew;
        // 区分是新增文档还是修改文档
        if (dto.getId() == null) {
            isNew = true;
            // 如果没有 id 就是新增
            // 这里设置一个 id
            onlineDoc.setId(IdWorker.getId());
        }
        if (file != null) {
            String filename = file.getOriginalFilename();
            if (StringUtil.isBlank(file.getOriginalFilename())) {
                throw new BaseException("文件名不能为空！", HttpStatus.BAD_REQUEST);
            }
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!Constant.fileType.contains("." + extension)) {
                throw new BaseException("文件格式错误", HttpStatus.BAD_REQUEST);
            }
            String path;
            try {
                path = FileServiceBuilder.upload(file);
            } catch (Exception e) {
                throw new BaseException(String.format("文件[%s]上传失败！", filename));
            }
            FileData fileData = new FileData();
            Constant.documentType.forEach((k, v) -> {
                if (v.contains("." + extension)) {
                    fileData.setDocumentType(k);
                }
            });
            if (StringUtil.isBlank(fileData.getDocumentType())) {
                throw new BaseException("文件格式错误", HttpStatus.BAD_REQUEST);
            }
            fileData.setUrl(path);
            fileData.setOriginalUrl(path);
            fileData.setTitle(filename);
            fileData.setFileType(extension);
            onlineDoc.setData(JSONObject.toJSONString(fileData));
        }
        // 如果是设置了需要共享的，这里就需要把共享范围也一起设置了
        docShare(onlineDoc, dto.getOnlineDocPermitSet());
        // TODO 保存 excel，会将 excel 上传到文件服务器
        //saveExcel(file, onlineDoc.getId());
        return saveOrUpdate(onlineDoc);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean update(OnlineDocUpdateDTO dto) {
        OnlineDoc onlineDoc = dto.bean();
        if (dto.getShare() != null) {
            // 如果有设置共享属性
            docShare(onlineDoc, dto.getOnlineDocPermitSet());
        }
        return updateById(onlineDoc);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean remove(Long id) {
        checkOperatePermission(id);
        removeById(id);
        onlineDocPermitService.remove(Wrappers.<OnlineDocPermit>lambdaQuery()
                .eq(OnlineDocPermit::getDocId, id));
        return true;
    }

    @Override
    public void checkOperatePermission(Long id) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        OnlineDoc onlineDoc = getOne(Wrappers.<OnlineDoc>lambdaQuery()
                .select(OnlineDoc::getId, OnlineDoc::getDeptId, OnlineDoc::getCreateUser)
                .eq(OnlineDoc::getId, id));
        if (!loginUser.getUserId().equals(onlineDoc.getCreateUser())) {
            throw new BaseException("非文档拥有者，不能操作!").setHttpStatus(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 文档共享
     *
     * @param onlineDoc          文档信息
     * @param onlineDocPermitSet 文档共享范围
     */
    public void docShare(OnlineDoc onlineDoc, Set<OnlineDocPermit> onlineDocPermitSet) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        // 不管共享不共享，都先把共享范围删除
        LambdaQueryWrapper<OnlineDocPermit> queryWrapper = Wrappers.<OnlineDocPermit>lambdaQuery()
                .eq(OnlineDocPermit::getDocId, onlineDoc.getId())
                // 不能把自己删除
                .ne(OnlineDocPermit::getUserId, loginUser.getUserId());
        if (ObjectUtil.isNotEmpty(onlineDoc.getCreateUser()) && !onlineDoc.getCreateUser().equals(loginUser.getUserId())) {
            // 如果不是文档的创建者，不能删除管理员
            queryWrapper.ne(OnlineDocPermit::getIsAdmin, YesOrNo.YES);
        }
        onlineDocPermitService.remove(queryWrapper);
        // 如果是设置了需要共享的，这里就需要把共享范围也一起设置了
        if (onlineDoc.getShare() != null && onlineDoc.getShare().equals(YesOrNo.YES)) {
            // 如果是需要共享的，就需要检查有没有选择共享范围
            if (CollectionUtil.isEmpty(onlineDocPermitSet)) {
                throw new BaseException("设置文档共享需要设置共享范围！").setHttpStatus(HttpStatus.BAD_REQUEST);
            }
            // 把共享范围转成 OnlineDocPermit 对象
            List<OnlineDocPermit> onlineDocPermitList = onlineDocPermitSet.stream().map(share -> {
                share.setId(null);
                share.setDocId(onlineDoc.getId());
                return share;
            }).toList();
            // 批量保存共享范围
            onlineDocPermitService.saveBatch(onlineDocPermitList);
        }
    }

    @Override
    public List<Map<String, Object>> listMap(List<String> fields, JSONObject params, SqlPageParams sqlPageParams) {
        return getBaseMapper().listMap(MyBatisUtil.<OnlineDoc>mybatisOptional()
                .select(fields)
                .params(params)
                .page(sqlPageParams));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <UM extends ModelConvertible<? extends OnlineDoc>, QM extends ModelConvertible<? extends OnlineDoc>> boolean update(UpdateModel<OnlineDoc, UM, QM> model) {
        model.getUpdateList().forEach(condition -> getBaseMapper().updateBatchByCondition(MyBatisUtil
                .<OnlineDoc>mybatisOptional()
                .bean(condition.getBean())
                .params(condition.getParams())));
        return true;
    }

    @Override
    public <E extends OnlineDoc> long total(JSONObject params) {
        MybatisOptional<E> mybatisOptional = MyBatisUtil
                .<E>mybatisOptional()
                .params(params);
        return getBaseMapper().total(mybatisOptional);
    }

    @Override
    public <E extends OnlineDoc> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams) {
        IPage<E> page = sqlPageParams.genPage();
        MybatisOptional<E> mybatisOptional = MyBatisUtil
                .<E>mybatisOptional()
                .params(params)
                .page(sqlPageParams);
        if (sqlPageParams.getCountTotal()) {
            page.setTotal(getBaseMapper().total(mybatisOptional));
            if (page.getTotal() == 0) {
                return page;
            }
        }
        List<? extends OnlineDoc> list = getBaseMapper().page(mybatisOptional);
        if (CollectionUtil.isNotEmpty(list)) {
            page.setRecords(list.stream().map(e -> (E) e).toList());
        }
        return page;
    }

    @Override
    public <E extends OnlineDoc> List<E> list(JSONObject params, SqlPageParams sqlPageParams) {
        return getBaseMapper().page(MyBatisUtil
                .<E>mybatisOptional()
                .params(params)
                .page(sqlPageParams)).stream().map(e -> (E) e).toList();
    }

    @Override
    public <E extends OnlineDoc> E detail(JSONObject params) {
        return getBaseMapper().detail(MyBatisUtil
                .<E>mybatisOptional()
                .params(params));
    }

}
