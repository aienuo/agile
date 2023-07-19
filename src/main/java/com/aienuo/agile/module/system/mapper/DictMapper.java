package com.aienuo.agile.module.system.mapper;

import com.aienuo.agile.module.api.model.vo.DictVO;
import com.aienuo.agile.module.system.model.dto.PagingQueryDictDTO;
import com.aienuo.agile.module.system.model.entity.Dict;
import com.aienuo.agile.module.system.model.vo.DictPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典 - 项 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery     - 分页查询对象
     * @return Page<DictPageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    Page<DictPageVO> pagingQueryListByParameter(@Param("pg") final Page<DictPageVO> pagingQueryList, @Param("param") final PagingQueryDictDTO pagingQuery);

    /**
     * 列表查询
     *
     * @return List<DictVO> - 字典项
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    List<DictVO> queryList();

}
