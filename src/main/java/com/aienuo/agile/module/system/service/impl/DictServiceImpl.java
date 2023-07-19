package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.api.model.vo.DictVO;
import com.aienuo.agile.module.system.mapper.DictMapper;
import com.aienuo.agile.module.system.model.dto.PagingQueryDictDTO;
import com.aienuo.agile.module.system.model.entity.Dict;
import com.aienuo.agile.module.system.model.vo.DictPageVO;
import com.aienuo.agile.module.system.service.IDictService;
import com.aienuo.agile.util.AgileUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典 - 项 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
@Service
@Slf4j
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<DictPageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public Page<DictPageVO> pagingQueryListByParameter(final PagingQueryDictDTO pagingQuery) {
        // 1、页码、页长
        Page<DictPageVO> pagingQueryList = new Page<>(pagingQuery.getPageNumber(), pagingQuery.getPageSize());
        // 2、排序字段
        if (AgileUtil.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 列表查询
     *
     * @return List<DictVO> - 字典项
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<DictVO> queryList() {
        return this.baseMapper.queryList();
    }

}
