package com.imis.agile.module.online.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.api.model.vo.ItemVO;
import com.imis.agile.module.online.model.converter.TableConverter;
import com.imis.agile.module.online.model.dto.FieldAddDTO;
import com.imis.agile.module.online.model.dto.IndexAddDTO;
import com.imis.agile.module.online.model.dto.PagingTableBasicDTO;
import com.imis.agile.module.online.model.dto.TableAddDTO;
import com.imis.agile.module.online.model.entity.TableFields;
import com.imis.agile.module.online.model.entity.TableHead;
import com.imis.agile.module.online.model.entity.TableIndex;
import com.imis.agile.module.online.model.vo.TableBasicPageVO;
import com.imis.agile.module.online.service.BuildPageApiService;
import com.imis.agile.module.online.service.ITableFieldsService;
import com.imis.agile.module.online.service.ITableHeadService;
import com.imis.agile.module.online.service.ITableIndexService;
import com.imis.agile.module.system.service.IDictItemService;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * DevelopPageApiBus<br>
 * 表单开发相关 业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月08日 16:51
 */
@Slf4j
@Service
public class DevelopPageApiBus extends BaseBus {

    /**
     * 在线开发 - 数据库表表信息 服务类
     */
    private ITableHeadService tableHeadService;

    @Autowired
    public void setTableHeadService(ITableHeadService tableHeadService) {
        this.tableHeadService = tableHeadService;
    }

    /**
     * 字典 - 值 服务类
     */
    private IDictItemService dictItemService;

    @Autowired
    public void setDictItemService(IDictItemService dictItemService) {
        this.dictItemService = dictItemService;
    }

    /**
     * 在线开发 - 数据库表字段信息 服务类
     */
    private ITableFieldsService tableFieldsService;

    @Autowired
    public void setTableFieldsService(ITableFieldsService tableFieldsService) {
        this.tableFieldsService = tableFieldsService;
    }

    /**
     * 在线开发 - 数据库表表索引 服务类
     */
    private ITableIndexService tableIndexService;

    @Autowired
    public void setTableIndexService(ITableIndexService tableIndexService) {
        this.tableIndexService = tableIndexService;
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
     * 数据库表添加校验
     *
     * @param add - 添加参数
     * @return TableHead - 在线开发 - 数据库表表信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private TableHead tableHeadAddVerification(final TableAddDTO add) {
        // 验证 在线开发 - 数据库表表名 是否存在重复
        TableHead tableHead = this.tableHeadService.getOne(Wrappers.<TableHead>lambdaQuery().eq(TableHead::getTableName, add.getTableName()), Boolean.FALSE);
        ArgumentResponseEnum.TABLE_HEAD_VALID_ERROR_ADD_02.assertIsNull(tableHead);
        // 构建 数据库表
        return TableConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 判断外键主表名、外键主键字段 是否存在
     *
     * @param mainTableList - 外键主表名、外键主键字段
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private void judgeTableFieldExists(final Map<String, Set<String>> mainTableList) {
        mainTableList.forEach(
                // 外键主表名， 外键主键字段
                (mainTableName, mainApiModelPropertySet) -> {
                    // 有表名、无字段 抛出异常
                    ArgumentResponseEnum.TABLE_FIELD_VALID_ERROR_ADD_04.assertNotEmpty(mainApiModelPropertySet);
                    // 主数据源
                    int countMaster = tableFieldsService.countMainFieldByParameter(mainTableName, mainApiModelPropertySet);
                    // 第二数据源
                    int countSlave = buildPageApiService.countMainFieldByParameter(mainTableName, mainApiModelPropertySet);
                    ArgumentResponseEnum.TABLE_FIELD_VALID_ERROR_ADD_05.assertIsFalse((countMaster + countSlave) < 1);
                }
        );
    }

    /**
     * 数据库表字段添加校验
     *
     * @param fieldData - 添加参数
     * @param tableId   - 数据库表ID
     * @return List<TableFields> - 在线开发 - 数据库表字段信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private List<TableFields> tableFieldAddVerification(final List<FieldAddDTO> fieldData, final Long tableId) {
        // 1、判断 字段名 是否存在重复
        List<String> repeatFieldName = fieldData.stream().map(FieldAddDTO::getFieldName).distinct().collect(Collectors.toList());
        ArgumentResponseEnum.TABLE_FIELD_VALID_ERROR_ADD_03.assertIsTrue(repeatFieldName.size() == fieldData.size());
        // 2、判断 外键主表名、外键主键字段 是否存在
        Map<String, Set<String>> mainTableList = fieldData.stream().collect(Collectors.groupingBy(FieldAddDTO::getMainTableName, Collectors.mapping(FieldAddDTO::getMainTableField, Collectors.toSet())));
        if (AgileUtil.isNotEmpty(mainTableList)) {
            judgeTableFieldExists(mainTableList);
        }
        // 3、判断 字典表表名、字典Code字段、字典Text字段 是否存在
        Map<String, Set<String>> dictTableList = fieldData.stream().collect(Collectors.groupingBy(FieldAddDTO::getDictTableName, Collectors.mapping(FieldAddDTO::getDictCodeField, Collectors.toSet())));
        fieldData.forEach(
                field -> {
                    if (AgileUtil.isNotEmpty(field.getDictTableName()) && dictTableList.containsKey(field.getDictTableName())) {
                        dictTableList.get(field.getDictTableName()).add(field.getDictTextField());
                    }
                }
        );
        if (AgileUtil.isNotEmpty(dictTableList)) {
            judgeTableFieldExists(dictTableList);
        }
        // 构建 数据库表字段
        return TableConverter.INSTANCE.getAddEntity(fieldData, tableId);
    }

    /**
     * 数据库表索引添加校验
     *
     * @param indexData      - 添加参数
     * @param tableId        - 数据库表ID
     * @param tableName      - 数据库表名称
     * @param tableFieldList - 在线开发 - 数据库表字段信息
     * @return List<TableIndex> - 在线开发 - 数据库表表索引
     */
    private List<TableIndex> tableIndexAddVerification(final List<IndexAddDTO> indexData, final Long tableId, final String tableName, final List<TableFields> tableFieldList) {
        // 1、判断 索引名称 是否存在重复
        List<String> indexNameList = indexData.stream().map(IndexAddDTO::getIndexName).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
        long tableIndexListNumber = this.tableIndexService.count(Wrappers.<TableIndex>lambdaQuery().in(TableIndex::getIndexName, indexNameList));
        ArgumentResponseEnum.TABLE_INDEX_VALID_ERROR_ADD_02.assertIsFalse(tableIndexListNumber != 0);
        // TODO
        // buildPageApiService.isExistIndex();


        List<TableIndex> tableIndexList = new ArrayList<>();


        return tableIndexList;
    }

    /**
     * 分页查询 - 表单基础信息
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < TableBasicPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<Page<TableBasicPageVO>> pagingQueryListByParameter(final PagingTableBasicDTO pagingQuery) {
        Page<TableBasicPageVO> page = tableHeadService.pagingQueryListByParameter(pagingQuery);
        return new CommonResponse<>(page);
    }

    /**
     * 字典 - 数据库表字段名称
     *
     * @param tableName - 数据库表名称
     * @return CommonResponse<List < ItemVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<ItemVO>> queryMainTableFieldListByTableName(final String tableName) {
        List<ItemVO> itemList = this.dictItemService.queryTableColumnItemListByTableName(tableName);
        return new CommonResponse<>(itemList);
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(final TableAddDTO add) {
        // 1、在线开发 - 数据库表表信息 - 添加校验
        TableHead tableHead = this.tableHeadAddVerification(add);
        // 2、创建新 在线开发 - 数据库表表信息
        boolean save = this.tableHeadService.save(tableHead);
        ArgumentResponseEnum.TABLE_HEAD_VALID_ERROR_ADD_01.assertIsTrue(save);
        // 3、在线开发 - 数据库表字段信息 - 添加校验
        List<TableFields> tableFieldList = this.tableFieldAddVerification(add.getFieldData(), tableHead.getId());
        ArgumentResponseEnum.TABLE_FIELD_VALID_ERROR_ADD_01.assertNotEmpty(tableFieldList);
        // 4、创建新 在线开发 - 数据库表字段信息
        boolean saveField = this.tableFieldsService.saveBatch(tableFieldList);
        ArgumentResponseEnum.TABLE_FIELD_VALID_ERROR_ADD_02.assertIsTrue(saveField);
        // 5、在线开发 - 数据库表表索引 - 添加校验
        List<TableIndex> tableIndexList = this.tableIndexAddVerification(add.getIndexData(), tableHead.getId(), tableHead.getTableName(), tableFieldList);
        if (AgileUtil.isNotEmpty(tableIndexList)) {
            // 6、创建新 在线开发 - 数据库表表索引
            boolean saveIndex = this.tableIndexService.saveBatch(tableIndexList);
            ArgumentResponseEnum.TABLE_INDEX_VALID_ERROR_ADD_01.assertIsTrue(saveIndex);
        }
        return new CommonResponse<>();
    }

}
