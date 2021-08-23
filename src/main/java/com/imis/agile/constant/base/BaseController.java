package com.imis.agile.constant.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Controller 基类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Slf4j
public abstract class BaseController<S extends BaseBus> {

    /**
     * 基于传值泛型注入Mapper实现，此处加入 required = false 是为了避免IDEA对泛型注入的检查，其他地方不可以像这样注入
     */
    protected S service;

    @Autowired(required = false)
    public void setService(S service) {
        this.service = service;
    }

}
