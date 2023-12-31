package com.aienuo.agile.module.system.model.converter;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.model.dto.RoleAddDTO;
import com.aienuo.agile.module.system.model.dto.RoleUpdateDTO;
import com.aienuo.agile.module.system.model.entity.Role;
import com.aienuo.agile.module.system.model.entity.RoleMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * RoleConverter<br>
 * 系统角色转换类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月15日 11:54
 */
@Mapper
public interface RoleConverter {

    /**
     * 系统角色 实例
     */
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 角色添加对象
     * @return Role
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
    })
    Role getAddEntity(final RoleAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 角色更新对象
     * @param role   - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, expression = "java(null)"),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, expression = "java(null)"),
            @Mapping(target = "roleCode", ignore = true),
    })
    void getUpdateEntity(@MappingTarget final Role role, final RoleUpdateDTO update);

    /**
     * 角色菜单权限关联
     *
     * @param roleId   - 角色编号
     * @param menuList - 菜单编号
     * @return List<RoleMenu> - 角色菜单权限关联
     */
    default List<RoleMenu> getRoleMenuEntity(final Long roleId, final List<Long> menuList) {
        List<RoleMenu> roleMenuList = new ArrayList<>();
        menuList.forEach(
                menu -> roleMenuList.add(new RoleMenu().setRoleId(roleId).setMenuId(menu))
        );
        return roleMenuList;
    }

}
