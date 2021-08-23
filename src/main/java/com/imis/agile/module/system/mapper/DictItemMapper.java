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

}