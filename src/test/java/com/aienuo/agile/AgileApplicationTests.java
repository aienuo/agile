package com.aienuo.agile;

import com.aienuo.agile.module.system.model.entity.RichText;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SpringBoot 测试
 */
@SpringBootTest
class AgileApplicationTests {

    @Test
    void doTestActiveRecord() {
        new RichText().setTextType(0).setPrimaryTableId(0L).setContent("doTestActiveRecord").setDelFlag(0).setCreateBy("test").insert();
    }

}
