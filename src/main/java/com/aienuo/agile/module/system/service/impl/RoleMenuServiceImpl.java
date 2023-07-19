package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.mapper.RoleMenuMapper;
import com.aienuo.agile.module.system.model.entity.RoleMenu;
import com.aienuo.agile.module.system.service.IRoleMenuService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单权限关联 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-06-18
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
