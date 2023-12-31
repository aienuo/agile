package com.aienuo.agile.module.system.service;

import com.aienuo.agile.module.api.model.vo.ItemVO;
import com.aienuo.agile.module.system.model.dto.QueryDictItemDTO;
import com.aienuo.agile.module.system.model.entity.DictItem;
import com.aienuo.agile.module.system.model.vo.DictItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典 - 值 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-23
 */
public interface IDictItemService extends IService<DictItem> {

    /**
     * 字典 - 值 查询
     *
     * @param query - 查询参数
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<DictItemVO> queryDictItemList(final QueryDictItemDTO query);

    /**
     * 字典 - 值 查询
     *
     * @param dictIdList - 字典 - 项 ID
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<ItemVO> queryDictItemListByDictIdList(final List<String> dictIdList);

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
    List<ItemVO> queryTableColumnItemListByTableName(final String tableName);

    /**
     * 字典 - 值 查询
     *
     * @param dictCode - 字典 Code
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<DictItemVO> queryDictItemListByDictCode(final String dictCode);

}
