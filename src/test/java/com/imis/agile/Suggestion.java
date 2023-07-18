package com.imis.agile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Suggestion<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2023年07月17日16时10分
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Suggestion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private List<String> keywords;

    private List<String> cities;

}
