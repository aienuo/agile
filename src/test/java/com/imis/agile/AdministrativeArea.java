package com.imis.agile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 行政区划
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdministrativeArea implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名字
     */
    private String name;

    /**
     * 区划代码（12位）
     */
    private String code;

    /**
     * 行政级别
     */
    private Integer level;

    /**
     * 城乡分类代码（3位）
     */
    private String classification;

    /**
     * 子级别数据链接
     */
    private String childHref;

    /**
     * 子级别数据名称
     */
    private String childName;

    /**
     * 子级别数据
     */
    private List<AdministrativeArea> child;

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"code\":\"" + code + '\"' +
                ", \"level:" + level +
                ", \"classification\":\"" + (classification == null ? "" : classification) + '\"' +
                ", \"childHref\":\"" + childHref + '\"' +
                ", \"childName\":\"" + childName + '\"' +
                ", \"child\":" + child +
                '}';
    }
}
