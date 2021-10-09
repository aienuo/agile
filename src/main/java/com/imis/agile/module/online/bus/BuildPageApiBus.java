package com.imis.agile.module.online.bus;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.module.api.model.vo.ButtonVO;
import com.imis.agile.module.online.model.dto.PagingQueryDataDTO;
import com.imis.agile.module.online.model.vo.FormElementVO;
import com.imis.agile.module.online.model.vo.PageElementsVO;
import com.imis.agile.module.online.model.vo.SearchElementVO;
import com.imis.agile.module.online.model.vo.TableColumnElementVO;
import com.imis.agile.module.online.service.BuildPageApiService;
import com.imis.agile.module.system.service.IDictItemService;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * BuildPageApiBus<br>
 * 构建页面相关 业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 14:45
 */
@Slf4j
@Service
public class BuildPageApiBus extends BaseBus {

    /**
     * CPU核数
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            // 核心线程数
            AVAILABLE_PROCESSORS,
            // 最大线程数
            3 * AVAILABLE_PROCESSORS,
            // keepAliveTime
            3,
            TimeUnit.SECONDS,
            // 阻塞队列
            new LinkedBlockingDeque<>(20));

    /**
     * 字典 - 值 服务类
     */
    private IDictItemService dictItemService;

    @Autowired
    public void setDictItemService(IDictItemService dictItemService) {
        this.dictItemService = dictItemService;
    }

    /**
     * 构建页面相关 服务类（第二数据源）
     */
    private BuildPageApiService buildPageApiService;

    @Autowired
    public void setBuildPageApiService(BuildPageApiService buildPageApiService) {
        this.buildPageApiService = buildPageApiService;
    }

    /**
     * 并行执行 构建页面数据 - 页面参数
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageParameter(final Long id, PageElementsVO pageElements) {
        // 假数据
        pageElements.setId(id.toString());
        pageElements.setName(id + "号页面");
        pageElements.setPaging(Boolean.TRUE);
        pageElements.setInsertStyle("dialog");
        pageElements.setUpdateStyle("drawer");
    }

    /**
     * 并行执行 构建页面数据 - 查询参数列表
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageSearchList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<SearchElementVO> searchList = new ArrayList<>();
        searchList.add(new SearchElementVO().setType("radio").setName("sex").setTextName("性别").setOptionList(this.dictItemService.queryDictItemListByDictCode("sex")));
        searchList.add(new SearchElementVO().setType("input").setName("name").setTextName("名字").setMin(2).setMax(20).setPlaceholder("请输入名字").setSize("medium").setPrefixIcon("el-icon-search").setSuffixIcon("el-icon-search"));
        searchList.add(new SearchElementVO().setType("number").setName("age").setTextName("年龄").setMin(0).setMax(200).setStep(1).setPrecision(1).setPlaceholder("请输入年龄").setSize("medium"));
        searchList.add(new SearchElementVO().setType("datetimerange").setName("dataTime").setTextName("年月日时分秒").setSize("medium").setPlaceholder("选择日期时间范围"));
        pageElements.setSearchList(searchList);
    }

    /**
     * 并行执行 构建页面数据 - 标题按钮列表
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageHeaderButtonList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<ButtonVO> headerButtonList = new ArrayList<>();
        headerButtonList.add(new ButtonVO().setType("primary").setSize("medium").setId(IdWorker.getIdStr()).setName("添加").setIcon("el-icon-circle-plus-outline").setUrl("add"));
        headerButtonList.add(new ButtonVO().setType("danger").setSize("medium").setId(IdWorker.getIdStr()).setName("删除").setIcon("el-icon-remove-outline").setUrl("delete"));
        pageElements.setHeaderButtonList(headerButtonList);
    }

    /**
     * 并行执行 构建页面数据 - 表格标题列表
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageTableColumnList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<TableColumnElementVO> tableColumnList = new ArrayList<>();
        tableColumnList.add(new TableColumnElementVO().setName("性别").setProp("sex").setWidth("150").setMinWidth("80").setAlign("center"));
        tableColumnList.add(new TableColumnElementVO().setName("名字").setProp("name").setWidth("150").setMinWidth("80").setAlign("center"));
        tableColumnList.add(new TableColumnElementVO().setName("年龄").setProp("age").setWidth("150").setMinWidth("80").setAlign("center"));
        tableColumnList.add(new TableColumnElementVO().setName("年月日时分秒").setProp("dataTime").setWidth("300").setMinWidth("150").setAlign("center"));
        pageElements.setTableColumnList(tableColumnList);
    }

    /**
     * 并行执行 构建页面数据 - 表格按钮列表
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageTableButtonList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<ButtonVO> tableButtonList = new ArrayList<>();
        tableButtonList.add(new ButtonVO().setType("success").setSize("mini").setId(IdWorker.getIdStr()).setName("修改").setIcon("el-icon-edit").setUrl("update"));
        pageElements.setTableButtonList(tableButtonList);
    }

    /**
     * 并行执行 构建页面数据 - 新增表单元素
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageInsertColumnList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<FormElementVO> insertColumnList = new ArrayList<>();
        insertColumnList.add(new FormElementVO().setType("radio").setName("sex").setTextName("性别").setOptionList(this.dictItemService.queryDictItemListByDictCode("sex")));
        insertColumnList.add(new FormElementVO().setType("input").setName("name").setTextName("名字").setMin(2).setMax(20).setPlaceholder("请输入名字").setSize("medium").setPrefixIcon("el-icon-search").setSuffixIcon("el-icon-search"));
        insertColumnList.add(new FormElementVO().setType("number").setName("age").setTextName("年龄").setMin(0).setMax(200).setStep(1).setPrecision(1).setPlaceholder("请输入年龄").setSize("medium"));
        insertColumnList.add(new FormElementVO().setType("datetimerange").setName("dataTime").setTextName("年月日时分秒").setSize("medium").setPlaceholder("选择日期时间范围"));
        pageElements.setInsertColumnList(insertColumnList);
    }

    /**
     * 并行执行 构建页面数据 - 新增表单元素
     *
     * @param id           - id - 页面编号
     * @param pageElements - 页面元素
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    private void buildPageUpdateColumnList(final Long id, PageElementsVO pageElements) {
        // 假数据
        List<FormElementVO> updateColumnList = new ArrayList<>();
        updateColumnList.add(new FormElementVO().setType("radio").setName("sex").setTextName("性别").setOptionList(this.dictItemService.queryDictItemListByDictCode("sex")));
        updateColumnList.add(new FormElementVO().setType("input").setName("name").setTextName("名字").setMin(2).setMax(20).setPlaceholder("请输入名字").setSize("medium").setPrefixIcon("el-icon-search").setSuffixIcon("el-icon-search"));
        updateColumnList.add(new FormElementVO().setType("number").setName("age").setTextName("年龄").setMin(0).setMax(200).setStep(1).setPrecision(1).setPlaceholder("请输入年龄").setSize("medium"));
        updateColumnList.add(new FormElementVO().setType("datetimerange").setName("dataTime").setTextName("年月日时分秒").setSize("medium").setPlaceholder("选择日期时间范围"));
        pageElements.setUpdateColumnList(updateColumnList);
    }

    /**
     * 页面元素查看
     *
     * @param id - 页面编号
     * @return CommonResponse<PageElementsVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<PageElementsVO> queryElementsById(final Long id) {
        // 页面元素
        PageElementsVO pageElements = new PageElementsVO();
        // 构建页面数据 - 页头数据
        this.buildPageParameter(id, pageElements);
        // 并行执行 构建页面数据
        if (AgileUtil.isNotEmpty(pageElements)) {
            // 并行执行 构建页面数据 - 查询参数列表
            CompletableFuture<Void> pageSearchList = CompletableFuture.runAsync(() -> this.buildPageSearchList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 并行执行 构建页面数据 - 标题按钮列表
            CompletableFuture<Void> pageHeaderButtonList = CompletableFuture.runAsync(() -> this.buildPageHeaderButtonList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 并行执行 构建页面数据 - 标题按钮列表
            CompletableFuture<Void> pageTableColumnList = CompletableFuture.runAsync(() -> this.buildPageTableColumnList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 并行执行 构建页面数据 - 标题按钮列表
            CompletableFuture<Void> pageTableButtonList = CompletableFuture.runAsync(() -> this.buildPageTableButtonList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 并行执行 构建页面数据 - 新增表单元素
            CompletableFuture<Void> pageInsertColumnList = CompletableFuture.runAsync(() -> this.buildPageInsertColumnList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 并行执行 构建页面数据 - 更新表单元素
            CompletableFuture<Void> pageUpdateColumnList = CompletableFuture.runAsync(() -> this.buildPageUpdateColumnList(id, pageElements), THREAD_POOL_EXECUTOR);
            // 使用allOf方法来表示所有的并行任务
            CompletableFuture.allOf(pageSearchList, pageHeaderButtonList, pageTableColumnList, pageTableButtonList, pageInsertColumnList, pageUpdateColumnList).join();
        }
        // 返回值
        return new CommonResponse<>(pageElements);
    }

    /**
     * 分页查询
     *
     * @param id          - 页面编号
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<?>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    public CommonResponse<?> pagingQueryListByParameter(final Long id, final PagingQueryDataDTO pagingQuery) {

        if (pagingQuery.getPaging()) {
            Page<Map<String, Object>> page = new Page<>();
            List<Map<String, Object>> list = new ArrayList<>();
            for (long i = pagingQuery.getPageNumber() * pagingQuery.getPageSize(); i < pagingQuery.getPageSize() * (pagingQuery.getPageNumber() + 1); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", i);
                map.put("sex", 0);
                map.put("name", i);
                map.put("age", i + 1);
                map.put("dataTime", LocalDateTime.now());
                list.add(map);
            }
            page.setTotal(100);
            page.setRecords(list);
            // 分页哟
            return new CommonResponse<>(page);
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", i);
                map.put("sex", 0);
                map.put("name", i);
                map.put("age", i + 1);
                map.put("dataTime", LocalDateTime.now());
                list.add(map);
            }
            // 正常获取列表
            return new CommonResponse<>(list);
        }
    }

    /**
     * 添加
     *
     * @param id  - 页面编号
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse add(final Long id, final Map<String, Object> add) {

        return new CommonResponse<>();
    }

    /**
     * 查看
     *
     * @param id     - 页面编号
     * @param dataId - 数据库表数据编号
     * @return CommonResponse<Map < String, Object>> - 数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<Map<String, Object>> queryById(final Long id, final Long dataId) {
        Map<String, Object> tableData = new HashMap<>(16);
        tableData.put("id", dataId);
        tableData.put("sex", 0);
        tableData.put("name", dataId);
        tableData.put("age", dataId + 1);
        tableData.put("dataTime", LocalDateTime.now());
        return new CommonResponse<>(tableData);
    }

    /**
     * 更新
     *
     * @param id     - 页面编号
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse updateById(final Long id, final Map<String, Object> update) {

        return new CommonResponse<>();
    }

    /**
     * 删除
     *
     * @param id     - 页面编号
     * @param idList - 数据库表数据编号
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse deleteByIdList(final Long id, final List<Long> idList) {

        return new CommonResponse<>();
    }

}
