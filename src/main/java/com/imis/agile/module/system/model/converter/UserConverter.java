package com.imis.agile.module.system.model.converter;

import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.api.model.vo.UserVO;
import com.imis.agile.module.system.model.dto.UserAddDTO;
import com.imis.agile.module.system.model.dto.UserOrganizationDTO;
import com.imis.agile.module.system.model.dto.UserUpdateDTO;
import com.imis.agile.module.system.model.entity.User;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.model.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * UserConverter<br>
 * 系统用户转换类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月15日 11:54
 */
@Mapper
public interface UserConverter {

    /**
     * 系统用户 实例
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * DTO 转 PO
     * 将添加对象生成新的数据库对象
     *
     * @param add - 用户添加对象
     * @return User
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "salt", expression = "java(com.imis.agile.util.PasswordUtil.getStringSalt())"),
            @Mapping(target = "password", expression = "java(com.imis.agile.util.PasswordUtil.encrypt(user.getUsername(), add.getPassword(), user.getSalt()))"),
            @Mapping(target = "status", constant = "0"),
            @Mapping(target = "delFlag", constant = "0"),
    })
    User getAddEntity(final UserAddDTO add);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 用户更新对象
     * @param user   - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "salt", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "delFlag", ignore = true),
            @Mapping(target = "realname", source = "update.realname"),
            @Mapping(target = "avatar", source = "update.avatar"),
            @Mapping(target = "identityNumber", source = "update.identityNumber"),
            @Mapping(target = "birthday", source = "update.birthday"),
            @Mapping(target = "sex", source = "update.sex"),
            @Mapping(target = "email", source = "update.email"),
            @Mapping(target = "phone", source = "update.phone"),
    })
    void getUpdateEntity(@MappingTarget final User user, final UserUpdateDTO update);

    /**
     * DTO 合并到 PO
     * 将更新对象与数据库对象合并成新的数据库更新对象
     *
     * @param update - 用户更新对象
     * @param user   - 数据库对象
     */
    @Mappings({
            @Mapping(target = DataBaseConstant.P_KEY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.CREATE_TIME, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_BY, ignore = true),
            @Mapping(target = DataBaseConstant.UPDATE_TIME, ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "salt", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "delFlag", ignore = true),
            @Mapping(target = "realname", source = "update.realname"),
            @Mapping(target = "avatar", source = "update.avatar"),
            @Mapping(target = "identityNumber", source = "update.identityNumber"),
            @Mapping(target = "birthday", source = "update.birthday"),
            @Mapping(target = "sex", source = "update.sex"),
            @Mapping(target = "email", source = "update.email"),
            @Mapping(target = "phone", source = "update.phone"),
    })
    void getUserUpdateEntity(@MappingTarget final User user, final com.imis.agile.module.api.model.dto.UserUpdateDTO update);

    /**
     * PO 转 VO
     *
     * @param user - 用户
     * @return UserVO
     */
    @Mappings({
            @Mapping(target = "age", expression = "java(com.imis.agile.util.IdCardUtil.getAge(user.getIdentityNumber()))"),
    })
    UserVO getReturnValue(User user);

    /**
     * 用户角色关联
     *
     * @param userId     - 用户编号
     * @param roleIdList - 角色编号列表
     * @return List<UserRole> - 用户角色关联
     */
    default List<UserRole> getUserRoleEntity(final Long userId, final List<Long> roleIdList) {
        List<UserRole> userRoleList = new ArrayList<>();
        roleIdList.forEach(
                roleId -> userRoleList.add(new UserRole().setUserId(userId).setRoleId(roleId))
        );
        return userRoleList;
    }

    /**
     * 用户组织机构关联
     *
     * @param userId             - 用户编号
     * @param organizationIdList - 组织机构列表
     * @return List<UserOrganization> - 用户组织机构关联
     */
    default List<UserOrganization> getUserOrganizationEntity(final Long userId, final List<UserOrganizationDTO> organizationIdList) {
        List<UserOrganization> userOrganizationList = new ArrayList<>();
        organizationIdList.forEach(
                organization -> userOrganizationList.add(
                        new UserOrganization().setUserId(userId)
                                .setOrganizationId(organization.getOrganizationId())
                                .setResponsible(organization.getResponsible())
                )
        );
        return userOrganizationList;
    }

}
