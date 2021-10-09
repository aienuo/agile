package com.imis.agile.module.online.model.converter;

import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.online.model.dto.FieldAddDTO;
import com.imis.agile.module.online.model.dto.IndexAddDTO;
import com.imis.agile.module.online.model.dto.TableAddDTO;
import com.imis.agile.module.online.model.entity.TableFields;
import com.imis.agile.module.online.model.entity.TableHead;
import com.imis.agile.module.online.model.entity.TableIndex;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 在线开发 - 数据库表 Converter 转换器
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月22日 11:57
 */
@Mapper
public interface TableConverter {

    /**
     * 数据库表 转换器实例
     */
    TableConverter INSTANCE = Mappers.getMapper(TableConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 数据库表 - 表单信息添加对象
     * @return TableHead
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "tableVersion" , constant = "0"),
            @Mapping(target = "synchronize" , constant = "1"),
    })
    TableHead getAddEntity(final TableAddDTO add);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add     - 数据库表 - 字段信息添加对象
     * @param tableId - 数据库表 - 编号
     * @return TableFields
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "synchronize" , constant = "1"),
            @Mapping(target = "tableHeadId" , source = "tableId"),
    })
    TableFields getAddEntity(final FieldAddDTO add, final Long tableId);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param fieldData - 数据库表 - 字段信息添加对象
     * @param tableId   - 数据库表 - 编号
     * @return List<TableFields>
     */
    @Mappings({})
    default List<TableFields> getAddEntity(final List<FieldAddDTO> fieldData, final Long tableId) {
        List<TableFields> tableFieldList = new ArrayList<>();
        fieldData.forEach(
                field -> tableFieldList.add(getAddEntity(field, tableId))
        );
        return tableFieldList;
    }

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add     - 数据库表 - 数据库索引添加对象
     * @param tableId - 数据库表 - 编号
     * @return TableFields
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "synchronize" , constant = "1"),
            @Mapping(target = "tableHeadId" , source = "tableId"),
    })
    TableIndex getAddEntity(final IndexAddDTO add, final Long tableId);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param indexData - 数据库表 - 数据库索引添加对象
     * @param tableId   - 数据库表 - 编号
     * @return List<TableFields>
     */
    @Mappings({})
    default List<TableIndex> getIndexAddEntity(final List<IndexAddDTO> indexData, final Long tableId) {
        List<TableIndex> tableIndexList = new ArrayList<>();
        indexData.forEach(
                field -> tableIndexList.add(getAddEntity(field, tableId))
        );
        return tableIndexList;
    }

}
