package com.imis.agile;

import com.imis.agile.util.AgileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * District<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2023年07月17日16时12分
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class District implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String citycode;

    private String adcode;

    private String name;

    private String center;

    private String level;

    private List<District> districts;

    @Override
    public String toString() {

        String baiDuCenter = "";

        if(AgileUtil.isNotEmpty(this.center)){
            String [] centerArray = this.center.split(",");
            double doubleStringA = Double.parseDouble(centerArray[0]);
            double doubleStringB = Double.parseDouble(centerArray[1]);
            double[] baiDuCenterArray = LngLonUtil.gcj02_To_Bd09(doubleStringA, doubleStringB);
            baiDuCenter = baiDuCenterArray[0] + "," + baiDuCenterArray[1];
        }

        return "{" +
                "\"citycode\":\"" + citycode + '\"' +
                ", \"adcode\":\"" + adcode + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"center\":\"" + (center == null ? "" : center) + '\"' +
                ", \"baiDuCenter\":\"" + baiDuCenter + '\"' +
                ", \"level\":\"" + level + '\"' +
                ", \"districts\":" + districts +
                '}';
    }
}
