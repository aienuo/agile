package com.imis.agile.module.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.module.online.model.dto.PagingTableBasicDTO;
import com.imis.agile.module.online.model.entity.TableHead;
import com.imis.agile.module.online.model.vo.TableBasicPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 在线开发 - 数据库表表信息 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
public interface TableHeadMapper extends BaseMapper<TableHead> {

    /**
     * 分页查询 - 表单基础信息
     *
     * @param pagingQueryList - 分页对象
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < TableBasicPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    Page<TableBasicPageVO> pagingQueryListByParameter(@Param("pg") final Page<TableBasicPageVO> pagingQueryList, @Param("param") final PagingTableBasicDTO pagingQuery);

}