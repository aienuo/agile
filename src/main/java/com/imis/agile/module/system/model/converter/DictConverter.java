package com.imis.agile.module.system.model.converter;

import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.model.dto.DictAddDTO;
import com.imis.agile.module.system.model.dto.DictItemAddDTO;
import com.imis.agile.module.system.model.dto.DictItemUpdateDTO;
import com.imis.agile.module.system.model.dto.DictUpdateDTO;
import com.imis.agile.module.system.model.entity.Dict;
import com.imis.agile.module.system.model.entity.DictItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * 字典管理 Converter 转换器
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Mapper
public interface DictConverter {

    /**
     * 字典管理 转换器实例
     */
    DictConverter INSTANCE = Mappers.getMapper(DictConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 字典 - 项添加对象
     * @return Dict
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    Dict getAddEntity(final DictAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 字典 - 项更新对象
     * @param dict   - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Dict dict, final DictUpdateDTO update);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 字典 - 值添加对象
     * @return DictItem
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "disabled", expression = "java(add.getDisabled() == 1)"),
    })
    DictItem getAddEntity(final DictItemAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update   - 字典 - 值更新对象
     * @param dictItem - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "disabled", expression = "java(update.getDisabled() == 1)"),
    })
    void getUpdateEntity(@MappingTarget final DictItem dictItem, final DictItemUpdateDTO update);

}
