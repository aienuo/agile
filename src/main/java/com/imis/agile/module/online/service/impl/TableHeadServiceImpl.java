package com.imis.agile.module.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.online.mapper.TableHeadMapper;
import com.imis.agile.module.online.model.dto.PagingTableBasicDTO;
import com.imis.agile.module.online.model.entity.TableHead;
import com.imis.agile.module.online.model.vo.TableBasicPageVO;
import com.imis.agile.module.online.service.ITableHeadService;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在线开发 - 数据库表表信息 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class TableHeadServiceImpl extends ServiceImpl<TableHeadMapper, TableHead> implements ITableHeadService {

    /**
     * 分页查询 - 表单基础信息
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < TableBasicPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    public Page<TableBasicPageVO> pagingQueryListByParameter(final PagingTableBasicDTO pagingQuery) {
        // 1、页码、页长
        Page<TableBasicPageVO> pagingQueryList = new Page<>(pagingQuery.getPageNumber(), pagingQuery.getPageSize());
        // 2、排序字段
        if (AgileUtil.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

}