package com.imis.agile.module.system.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.system.model.converter.DictConverter;
import com.imis.agile.module.system.model.dto.*;
import com.imis.agile.module.system.model.entity.Dict;
import com.imis.agile.module.system.model.entity.DictItem;
import com.imis.agile.module.system.model.vo.DictItemVO;
import com.imis.agile.module.system.model.vo.DictPageVO;
import com.imis.agile.module.system.service.IDictItemService;
import com.imis.agile.module.system.service.IDictService;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * DictBus<br>
 * 字典相关功能业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月16日 10:49
 */
@Service
public class DictBus extends BaseBus {

    /**
     * 字典 - 项 服务类
     */
    private IDictService dictService;

    @Autowired
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }

    /**
     * 字典 - 值 服务类
     */
    private IDictItemService dictItemService;

    @Autowired
    public void setDictItemService(IDictItemService dictItemService) {
        this.dictItemService = dictItemService;
    }

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return Dict - 字典 - 项
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Dict dictAddVerification(final DictAddDTO add) {
        // 验证 字典 - 项名称 是否存在重复
        Dict dict = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictName, add.getDictName()));
        ArgumentResponseEnum.DICT_VALID_ERROR_ADD_02.assertIsNull(dict);
        // 验证 字典 - 项编码 是否存在重复
        dict = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, add.getDictCode()));
        ArgumentResponseEnum.DICT_VALID_ERROR_ADD_03.assertIsNull(dict);
        return DictConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return Dict - 字典 - 项
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Dict dictUpdateVerification(final DictUpdateDTO update) {
        Dict dict = this.dictService.getById(update.getId());
        ArgumentResponseEnum.DICT_VALID_ERROR_UPDATE_02.assertNotNull(dict);
        if (AgileUtil.isNotEmpty(update.getDictName()) && !dict.getDictName().equals(update.getDictName())) {
            // 验证 字典 - 项名称 是否存在重复
            Dict dictByDictName = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictName, update.getDictName()));
            ArgumentResponseEnum.DICT_VALID_ERROR_UPDATE_03.assertIsNull(dictByDictName);
        }
        if (AgileUtil.isNotEmpty(update.getDictCode()) && !dict.getDictCode().equals(update.getDictCode())) {
            // 验证 字典 - 项编码 是否存在重复
            Dict dictByDictCode = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, update.getDictCode()));
            ArgumentResponseEnum.DICT_VALID_ERROR_UPDATE_04.assertIsNull(dictByDictCode);
        }
        DictConverter.INSTANCE.getUpdateEntity(dict, update);
        return dict;
    }

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return Dict - 字典 - 值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private DictItem dictItemAddVerification(final DictItemAddDTO add) {
        // 验证 字典 - 项名称 是否存在重复
        Dict dict = this.dictService.getById(add.getDictId());
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_ADD_02.assertNotNull(dict);
        if (CommonConstant.DICT_TYPE_1.equals(dict.getDictType())){
            ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_ADD_03.assertIsTrue(AgileUtil.isNumeric(add.getValue()));
        }
        // 验证 字典 - 值名称 是否存在重复
        DictItem dictItem = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getName, add.getName()));
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_ADD_04.assertIsNull(dictItem);
        return DictConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return Dict - 字典 - 值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private DictItem dictItemUpdateVerification(final DictItemUpdateDTO update) {
        // 验证 字典 - 项名称 是否存在重复
        Dict dict = this.dictService.getById(update.getDictId());
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_UPDATE_02.assertNotNull(dict);
        if (CommonConstant.DICT_TYPE_1.equals(dict.getDictType())){
            ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_UPDATE_03.assertIsTrue(AgileUtil.isNumeric(update.getValue()));
        }
        DictItem dictItem = this.dictItemService.getById(update.getId());
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_UPDATE_04.assertNotNull(dictItem);
        if (AgileUtil.isNotEmpty(update.getName()) && !dictItem.getName().equals(update.getName())) {
            // 验证 字典 - 值名称 是否存在重复
            DictItem dictItemByName = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getName, update.getName()));
            ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_UPDATE_05.assertIsNull(dictItemByName);
        }
        DictConverter.INSTANCE.getUpdateEntity(dictItem, update);
        return dictItem;
    }

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < DictPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    public CommonResponse<Page<DictPageVO>> pagingQueryListByParameter(final PagingQueryDictDTO pagingQuery) {
        Page<DictPageVO> pagingQueryList = this.dictService.pagingQueryListByParameter(pagingQuery);
        return new CommonResponse<>(pagingQueryList);
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse add(final DictAddDTO add) {
        // 1、添加校验
        Dict dict = this.dictAddVerification(add);
        // 2、创建新字典 - 项
        boolean save = this.dictService.save(dict);
        ArgumentResponseEnum.DICT_VALID_ERROR_ADD_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse updateById(final DictUpdateDTO update) {
        // 1、更新校验
        Dict dict = this.dictUpdateVerification(update);
        // 2、更新字典 - 项
        boolean save = this.dictService.updateById(dict);
        ArgumentResponseEnum.DICT_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 删除
     *
     * @param idList - 字典 - 项标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteByIdList(final List<Long> idList) {
        boolean delete = this.dictService.removeByIds(idList);
        ArgumentResponseEnum.DICT_VALID_ERROR_DELETE_01.assertIsTrue(delete);
        List<DictItem> dictItemList = this.dictItemService.list(Wrappers.<DictItem>lambdaQuery().in(DictItem::getDictId, idList));
        if (AgileUtil.isNotEmpty(dictItemList)) {
            // 删除字典 - 值
            List<Long> dictItemIdList = dictItemList.stream().map(DictItem::getId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            delete = this.dictItemService.removeByIds(dictItemIdList);
            ArgumentResponseEnum.DICT_VALID_ERROR_DELETE_02.assertIsTrue(delete);
        }
        return new CommonResponse<>();
    }

    /**
     * 字典 - 值查询
     *
     * @param query - 查询参数
     * @return CommonResponse<List < DictItemVO>> - 字典 - 值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<DictItemVO>> queryDictItemList(final QueryDictItemDTO query) {
        return new CommonResponse<>(this.dictItemService.queryDictItemList(query));
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse add(final DictItemAddDTO add) {
        // 1、添加校验
        DictItem dictItem = this.dictItemAddVerification(add);
        // 2、创建新字典 - 值
        boolean save = this.dictItemService.save(dictItem);
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_ADD_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse updateById(final DictItemUpdateDTO update) {
        // 1、更新校验
        DictItem dictItem = this.dictItemUpdateVerification(update);
        // 2、更新字典 - 值
        boolean save = this.dictItemService.updateById(dictItem);
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 删除
     *
     * @param idList - 字典 - 值标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse deleteDictItemByIdList(final List<Long> idList) {
        long count = this.dictItemService.count(Wrappers.<DictItem>lambdaQuery().in(DictItem::getParentId, idList));
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_DELETE_02.assertIsTrue(count == 0);
        boolean delete = this.dictItemService.removeByIds(idList);
        ArgumentResponseEnum.DICT_ITEM_VALID_ERROR_DELETE_01.assertIsTrue(delete);
        return new CommonResponse<>();
    }

}
