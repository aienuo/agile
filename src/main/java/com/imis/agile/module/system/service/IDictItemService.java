package com.imis.agile.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.api.model.vo.ItemVO;
import com.imis.agile.module.system.model.dto.QueryDictItemDTO;
import com.imis.agile.module.system.model.entity.DictItem;
import com.imis.agile.module.system.model.vo.DictItemVO;

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
     * 字典 - 值 查询
     *
     * @param dictCode - 字典 - 项 编码
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<ItemVO> queryDictItemListByDictCode(final String dictCode);

}