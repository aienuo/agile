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
public class AreaReturn implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String status;

    private String info;

    private String infocode;

    private String count;

    private Suggestion suggestion;

    private List<District> districts;

}
