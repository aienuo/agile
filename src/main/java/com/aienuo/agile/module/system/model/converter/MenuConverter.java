package com.aienuo.agile.module.system.model.converter;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.model.dto.MenuAddDTO;
import com.aienuo.agile.module.system.model.dto.MenuUpdateDTO;
import com.aienuo.agile.module.system.model.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * MenuConverter<br>
 * 菜单权限转换类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月15日 11:54
 */
@Mapper
public interface MenuConverter {

    /**
     * 系统菜单权限 实例
     */
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 菜单权限添加对象
     * @return Menu
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    Menu getAddEntity(final MenuAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 菜单权限更新对象
     * @param menu   - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, expression = "java(null)"),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, expression = "java(null)"),
    })
    void getUpdateEntity(@MappingTarget final Menu menu, final MenuUpdateDTO update);

}
