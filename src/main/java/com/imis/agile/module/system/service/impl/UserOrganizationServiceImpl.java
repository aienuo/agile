package com.imis.agile.module.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.mapper.UserOrganizationMapper;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.service.IUserOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组织机构关联 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-06-18
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class UserOrganizationServiceImpl extends ServiceImpl<UserOrganizationMapper, UserOrganization> implements IUserOrganizationService {
    
}