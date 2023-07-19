package com.aienuo.agile.module.system.mapper;

import com.aienuo.agile.module.system.model.dto.PagingQueryUserDTO;
import com.aienuo.agile.module.system.model.entity.User;
import com.aienuo.agile.module.system.model.vo.UserInfoVO;
import com.aienuo.agile.module.system.model.vo.UserPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页查询返回值
     * @param pagingQuery - 分页查询条件
     * @return Page<UserPageVO> - 分页查询返回值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/17 14:54
     */
    Page<UserPageVO> pagingQueryListByParameter(@Param("pg") final Page<UserPageVO> pagingQueryList, @Param("param") final PagingQueryUserDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return UserInfoVO - 用户信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    UserInfoVO queryById(@Param("id") final Long id);

}
