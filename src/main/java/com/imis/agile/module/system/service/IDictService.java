package com.imis.agile.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.api.model.vo.DictVO;
import com.imis.agile.module.system.model.dto.PagingQueryDictDTO;
import com.imis.agile.module.system.model.entity.Dict;
import com.imis.agile.module.system.model.vo.DictPageVO;

import java.util.List;

/**
 * <p>
 * 字典 - 项 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
public interface IDictService extends IService<Dict> {

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<DictPageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    Page<DictPageVO> pagingQueryListByParameter(final PagingQueryDictDTO pagingQuery);

    /**
     * 列表查询
     *
     * 
     * @return List<DictVO> - 字典项
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    List<DictVO> queryList();

}