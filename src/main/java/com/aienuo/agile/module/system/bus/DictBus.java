package com.aienuo.agile.module.system.bus;

import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.base.BaseBus;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.system.model.converter.DictConverter;
import com.aienuo.agile.module.system.model.dto.*;
import com.aienuo.agile.module.system.model.entity.Dict;
import com.aienuo.agile.module.system.model.entity.DictItem;
import com.aienuo.agile.module.system.model.vo.DictItemVO;
import com.aienuo.agile.module.system.model.vo.DictPageVO;
import com.aienuo.agile.module.system.service.IDictItemService;
import com.aienuo.agile.module.system.service.IDictService;
import com.aienuo.agile.response.CommonResponse;
import com.aienuo.agile.util.AgileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class DictBus extends BaseBus {

    /**
     * 字典 - 项 服务类
     */
    private final IDictService dictService;

    /**
     * 字典 - 值 服务类
     */
    private final IDictItemService dictItemService;

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
        Dict dict = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictName, add.getDictName()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(dict, "字典项", "字典项名称存在重复");
        // 验证 字典 - 项编码 是否存在重复
        dict = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, add.getDictCode()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(dict, "字典项", "字典项名称存在重复");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(dict, "字典项", "字典项信息不存在");
        if (AgileUtil.isNotEmpty(update.getDictName()) && !dict.getDictName().equals(update.getDictName())) {
            // 验证 字典 - 项名称 是否存在重复
            Dict dictByDictName = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictName, update.getDictName()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(dictByDictName, "字典项", "字典项名称存在重复");
        }
        if (AgileUtil.isNotEmpty(update.getDictCode()) && !dict.getDictCode().equals(update.getDictCode())) {
            // 验证 字典 - 项编码 是否存在重复
            Dict dictByDictCode = this.dictService.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, update.getDictCode()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(dictByDictCode, "字典项", "字典项编码存在重复");
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
        // 验证 字典 - 项 是否存在
        Dict dict = this.dictService.getById(add.getDictId());
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertNotNull(dict, "字典值", "字典项信息不存在");
        if (CommonConstant.DICT_TYPE_1.equals(dict.getDictType())) {
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(AgileUtil.isNumeric(add.getValue()), "字典值",  "字典值类型不正确");
        }
        // 验证 字典 - 值名称 是否存在重复
        DictItem dictItem = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, add.getDictId()).eq(DictItem::getName, add.getName()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(dictItem, "字典值", "字典值名称存在重复");
        // 验证 字典 - 值内容 是否存在重复
        dictItem = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, add.getDictId()).eq(DictItem::getValue, add.getValue()), Boolean.FALSE);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(dictItem, "字典值", "字典值内容存在重复");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(dict, "字典值", "字典项信息不存在");
        if (CommonConstant.DICT_TYPE_1.equals(dict.getDictType())) {
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(AgileUtil.isNumeric(update.getValue()), "字典值",  "字典值类型不正确");
        }
        DictItem dictItem = this.dictItemService.getById(update.getId());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(dictItem, "字典值", "字典值信息不存在");
        if (AgileUtil.isNotEmpty(update.getName()) && !dictItem.getName().equals(update.getName())) {
            // 验证 字典 - 值名称 是否存在重复
            DictItem dictItemByName = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, update.getDictId()).eq(DictItem::getName, update.getName()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(dictItemByName, "字典值", "值名称存在重复");
        }
        if (AgileUtil.isNotEmpty(update.getValue()) && !dictItem.getValue().equals(update.getValue())) {
            // 验证 字典 - 值内容 是否存在重复
            DictItem dictItemByValue = this.dictItemService.getOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, update.getDictId()).eq(DictItem::getValue, update.getValue()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(dictItemByValue, "字典值", "值内容存在重复");
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
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "字典项", "请确认信息正确无误后重新添加");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "字典项", "请确认信息正确无误后重新更新");
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
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "字典项", "请确认信息正确无误后重新删除");
        List<DictItem> dictItemList = this.dictItemService.list(Wrappers.<DictItem>lambdaQuery().in(DictItem::getDictId, idList));
        if (AgileUtil.isNotEmpty(dictItemList)) {
            // 删除字典 - 值
            List<Long> dictItemIdList = dictItemList.stream().map(DictItem::getId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            delete = this.dictItemService.removeByIds(dictItemIdList);
            ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "字典项下的字典值", "请确认信息正确无误后重新删除");
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
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "字典值", "请确认信息正确无误后重新添加");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "字典值", "请确认信息正确无误后重新更新");
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
        long count = this.dictItemService.count(Wrappers.<DictItem>lambdaQuery().in(DictItem::getId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(count == 0, "字典值", "请确认信息正确无误后重新删除");
        boolean delete = this.dictItemService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "字典值", "请确认信息正确无误后重新删除");
        return new CommonResponse<>();
    }

}
