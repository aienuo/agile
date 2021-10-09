package com.imis.agile.module.online.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.online.model.dto.PagingTableBasicDTO;
import com.imis.agile.module.online.model.entity.TableHead;
import com.imis.agile.module.online.model.vo.TableBasicPageVO;

/**
 * <p>
 * 在线开发 - 数据库表表信息 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
public interface ITableHeadService extends IService<TableHead> {

    /**
     * 分页查询 - 表单基础信息
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < TableBasicPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    Page<TableBasicPageVO> pagingQueryListByParameter(final PagingTableBasicDTO pagingQuery);

}