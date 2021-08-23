package com.imis.agile.module.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.mapper.UserMapper;
import com.imis.agile.module.system.model.dto.PagingQueryUserDTO;
import com.imis.agile.module.system.model.entity.User;
import com.imis.agile.module.system.model.vo.UserInfoVO;
import com.imis.agile.module.system.model.vo.UserPageVO;
import com.imis.agile.module.system.service.IUserService;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
@Service
@Slf4j
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询条件
     * @return Page<UserPageVO> - 分页查询返回值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/17 14:54
     */
    @Override
    public Page<UserPageVO> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery) {
        // 1、页码、页长
        Page<UserPageVO> pagingQueryList = new Page<>(pagingQuery.getPageNumber(), pagingQuery.getPageSize());
        // 2、排序字段
        if (AgileUtil.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return UserInfoVO - 用户信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    public UserInfoVO queryById(final Long id) {
        return baseMapper.queryById(id);
    }

}
