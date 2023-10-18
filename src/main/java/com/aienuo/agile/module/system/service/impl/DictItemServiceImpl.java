package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.api.model.vo.ItemVO;
import com.aienuo.agile.module.system.mapper.DictItemMapper;
import com.aienuo.agile.module.system.model.dto.QueryDictItemDTO;
import com.aienuo.agile.module.system.model.entity.DictItem;
import com.aienuo.agile.module.system.model.vo.DictItemVO;
import com.aienuo.agile.module.system.service.IDictItemService;
import com.aienuo.agile.util.AgileUtil;
import com.aienuo.agile.util.BuildingTreeData;
import com.baomidou.dynamic.datasource.annotation.DS;
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
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements IDictItemService {

    /**
     * 字典 - 值 查询
     *
     * @param query - 查询参数
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<DictItemVO> queryDictItemList(final QueryDictItemDTO query) {
        // 查询数据
        List<DictItemVO> dictItemList = this.baseMapper.queryDictItemList(query);
        if (AgileUtil.isNotEmpty(dictItemList)) {
            // 构建数据
            return new BuildingTreeData<DictItemVO>().buildingTreeData(dictItemList);
        }
        return dictItemList;
    }

    /**
     * 字典 - 值 查询
     *
     * @param dictIdList - 字典 - 项 ID
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<ItemVO> queryDictItemListByDictIdList(final List<String> dictIdList) {
        List<ItemVO> dictItemList = this.baseMapper.queryDictItemListByDictIdList(dictIdList);
        if (AgileUtil.isNotEmpty(dictItemList)) {
            // 构建数据
            return new BuildingTreeData<ItemVO>().buildingTreeData(dictItemList);
        }
        return dictItemList;
    }

    /**
     * 字典 - 数据库表名称
     *
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<ItemVO> queryTableItemList(){
        return this.baseMapper.queryTableItemList();
    }

    /**
     * 字典 - 数据库表字段名称
     *
     * @param tableName - 数据库表名称
     * @return List<ItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<ItemVO> queryTableColumnItemListByTableName(final String tableName){
        return this.baseMapper.queryTableColumnItemListByTableName(tableName);
    }

    /**
     * 字典 - 值 查询
     *
     * @param dictCode - 字典 Code
     * @return List<DictItemVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<DictItemVO> queryDictItemListByDictCode(final String dictCode) {
        return this.baseMapper.queryDictItemListByDictCode(dictCode);
    }

}
