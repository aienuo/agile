package com.aienuo.agile.module.system.service;


import com.aienuo.agile.module.system.model.dto.PagingQueryUserDTO;
import com.aienuo.agile.module.system.model.entity.User;
import com.aienuo.agile.module.system.model.vo.UserInfoVO;
import com.aienuo.agile.module.system.model.vo.UserPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询条件
     * @return Page<UserPageVO> - 分页查询返回值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/17 14:54
     */
    Page<UserPageVO> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return UserInfoVO - 用户信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    UserInfoVO queryById(final Long id);

}
