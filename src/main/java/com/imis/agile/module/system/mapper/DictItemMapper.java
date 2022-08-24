package com.imis.agile.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imis.agile.module.api.model.vo.ItemVO;
import com.imis.agile.module.system.model.dto.QueryDictItemDTO;
import com.imis.agile.module.system.model.entity.DictItem;
import com.imis.agile.module.system.model.vo.DictItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典 - 值 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-03-24
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

    /**
     * 字典 - 值查询
     *
     * @param query - 值查询参数
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<DictItemVO> queryDictItemList(@Param("param") final QueryDictItemDTO query);

    /**
     * 字典 - 值 查询
     *
     * @param dictIdList - 字典 - 项 ID
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<ItemVO> queryDictItemListByDictIdList(@Param("dictIdList") final List<String> dictIdList);

    /**
     * 字典 - 数据库表名称
     *
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<ItemVO> queryTableItemList();

    /**
     * 字典 - 数据库表字段名称
     *
     * @param tableName - 数据库表名称
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<ItemVO> queryTableColumnItemListByTableName(@Param("tableName") final String tableName);

    /**
     * 字典 - 值 查询
     *
     * @param dictCode - 字典 Code
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<DictItemVO> queryDictItemListByDictCode(@Param("dictCode") final String dictCode);

}