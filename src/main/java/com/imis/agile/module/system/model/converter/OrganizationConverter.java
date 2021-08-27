package com.imis.agile.module.system.model.converter;

import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.model.dto.OrganizationAddDTO;
import com.imis.agile.module.system.model.dto.OrganizationUpdateDTO;
import com.imis.agile.module.system.model.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * OrganizationConverter<br>
 * 组织机构转换类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:01
 */
@Mapper
public interface OrganizationConverter {

    /**
     * 系统组织机构 实例
     */
    OrganizationConverter INSTANCE = Mappers.getMapper(OrganizationConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 组织机构添加对象
     * @return Organization
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    Organization getAddEntity(final OrganizationAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update       - 组织机构更新对象
     * @param organization - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Organization organization, final OrganizationUpdateDTO update);

}
