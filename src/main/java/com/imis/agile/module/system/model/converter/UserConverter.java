package com.imis.agile.module.system.model.converter;

import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.api.model.vo.UserVO;
import com.imis.agile.module.system.model.dto.UserAddDTO;
import com.imis.agile.module.system.model.dto.UserUpdateDTO;
import com.imis.agile.module.system.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
     * @param user
     * @return UserVO
     */
    @Mappings({
            @Mapping(target = "age", expression = "java(com.imis.agile.util.IdCardUtil.getAge(user.getIdentityNumber()))"),
    })
    UserVO getReturnValue(User user);

}
