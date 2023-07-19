package com.aienuo.agile.util;

import com.aienuo.agile.constant.base.BaseTreeVO;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * BuildingTreeData<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月23日 09:10
 */
public class BuildingTreeData<T extends BaseTreeVO> {

    /**
     * 递归查找子节点
     *
     * @param paren    - 父对象
     * @param treeList - 集合对象
     * @return T - 对象
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/23 15:37
     */
    public T findChildData(final T paren, final List<T> treeList) {
        for (T tree : treeList) {
            // 1.判断父对象的编号是不是等于子对象的父编号
            if (paren.getId().equals(tree.getParentId())) {
                //  2_1.判断父对象内的字段项为空时的操作
                if (paren.getChildren() == null) {
                    paren.setChildren(new ArrayList<>());
                }
                //  2_1.递归查找子节点
                paren.getChildren().add(findChildData(tree, treeList));
            }
        }
        return paren;
    }

    /**
     * 生成 Tree
     *
     * @param treeList - 对象集合
     * @return List<T> - Tree
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/17 14:54
     */
    public List<T> buildingTreeData(final List<T> treeList) {
        List<T> treeArrayList = new ArrayList<>();
        if (AgileUtil.isNotEmpty(treeList)) {
            for (T tree : treeList) {
                if (StringPool.ZERO.equals(tree.getParentId()) || AgileUtil.isEmpty(tree.getParentId())) {
                    treeArrayList.add(findChildData(tree, treeList));
                }
            }
        }
        return treeArrayList;
    }

}
