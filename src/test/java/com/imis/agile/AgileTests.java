package com.imis.agile;

import com.imis.agile.util.ComputerUniqueIdentificationUtil;
import com.imis.agile.util.IdCardUtil;
import com.imis.agile.util.PasswordUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 工具测试
 */
class AgileTests {

    @Test
    void contextLoads() {
        System.out.println(LocalDate.parse("19970229", DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    void testRegexp() {
        String regexp1 = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";
        // 假身份证件号
        String id1 = "370911199702294811";

        System.out.println(IdCardUtil.isIdCard(id1));

        System.out.println(id1.matches(regexp1));

        String regexp2 = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";

        String phone1 = "17605481537";
        String phone2 = "12345678910";

        System.out.println(phone1.matches(regexp2));
        System.out.println(phone2.matches(regexp2));

    }

    @Test
    void testPassword() {

        String salt = PasswordUtil.getStringSalt();
        System.out.println(salt);
        String password = PasswordUtil.encrypt("admin", "admin", salt);
        System.out.println(password);

    }

    @Test
    void doTestForCryptoUtils() {
        String password = "root";
        String encodePassword = PasswordUtil.encrypt(password);
        System.out.println(encodePassword);
        password = PasswordUtil.decrypt(encodePassword);
        System.out.println(password);
    }

    @Test
    void doTestForComputerUniqueIdentificationUtil() {
        System.out.println("当前计算机操作系统名称：" + ComputerUniqueIdentificationUtil.getOsName());
        System.out.println("当前计算机的 CPU 序列号：" + ComputerUniqueIdentificationUtil.getCpuIdentification());
        System.out.println("当前计算机网卡的 MAC 地址：" + ComputerUniqueIdentificationUtil.getMacAddress());
        System.out.println("当前计算机主板序列号：" + ComputerUniqueIdentificationUtil.getMainBoardSerialNumber());
        System.out.println("当前计算机唯一标识：" + ComputerUniqueIdentificationUtil.getComputerUniqueIdentification());
        System.out.println("当前计算机唯一标识：" + ComputerUniqueIdentificationUtil.getComputerUniqueIdentificationString());
    }

}
