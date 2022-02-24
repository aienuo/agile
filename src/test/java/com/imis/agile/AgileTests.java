package com.imis.agile;

import com.imis.agile.constant.CommonConstant;
import com.imis.agile.util.ComputerUniqueIdentificationUtil;
import com.imis.agile.util.IdCardUtil;
import com.imis.agile.util.PasswordUtil;
import com.imis.agile.util.UnitConversion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    void getAge() {
        System.out.println(IdCardUtil.getAge("110101201803075152"));
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

    @Test
    void doTestForUnitConversion() {
        System.out.println(UnitConversion.conversion(new BigDecimal(1), UnitConversion.UnitsEnum.TS_MACH, UnitConversion.UnitsEnum.TS_KM, 8));
        List<Map<String, String>> list = UnitConversion.getCategoryList();
        Map<String, List<Map<String, String>>> listMap = UnitConversion.getUnitListMap();
        list.forEach(
                stringStringMap -> {
                    System.out.println(stringStringMap.get("code") + "--" + stringStringMap.get("name"));
                    List<Map<String, String>> mapList = listMap.get(stringStringMap.get("code"));
                    mapList.forEach(
                            stringStringMap1 -> {
                                System.out.println(stringStringMap1.get("code") + "---" + stringStringMap1.get("name"));
                            }
                    );
                }
        );


    }

    @Test
    void doTestForCityData() {


        AdministrativeArea administrativeArea = new AdministrativeArea()
                .setLevel(0)
                .setCode("000000000000")
                .setName("中华人民共和国")
                .setChildHref("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/index.html")
                .setChildName(CommonConstant.ADMINISTRATIVE_AREA_LIVE_NAME[0]);


        structureAdministrativeArea(administrativeArea);

        System.out.println(administrativeArea.toString());
    }

    /**
     * 构建行政区数据
     *
     * @param area - AdministrativeArea
     */
    private void structureAdministrativeArea(AdministrativeArea area) {
        try {
            String classname = area.getChildName();
            // Jsoup 解析 HTML
            Document document = Jsoup.connect(area.getChildHref()).get();
            // 像 js 一样，通过 class 获取列表下的 省会列表
            Element provinceTable = document.getElementsByClass(classname + "table").first();
            if (provinceTable != null) {
                // System.out.println(provinceTable);
                // 像 js 一样，通过 class 获取列表下的 tr
                Elements provinceTrList = provinceTable.getElementsByClass(classname + "tr");
                List<AdministrativeArea> administrativeAreaList = new ArrayList<>();
                // 循环处理每篇博客
                for (Element provinceTr : provinceTrList) {
                    // 像 js 一样，通过 tagName 获取列表下的 td
                    Elements provinceTdList = provinceTr.getElementsByTag("td");
                    // 行政区划
                    AdministrativeArea administrativeArea = new AdministrativeArea().setLevel(area.getLevel() + 1);
                    for (int i = 0; i < provinceTdList.size(); i++) {
                        Element provinceTd = provinceTdList.get(i);
                        // 像 js 一样，通过 tagName 获取列表下的 a
                        Elements provinceAList = provinceTd.getElementsByTag("a");
                        if (provinceAList != null && provinceAList.size() > 0) {
                            // 有 a 标签 说明就还有下一级
                            if (0 == i) {
                                String code = provinceTd.text();
                                // System.out.print("编码: " + code);
                                administrativeArea.setCode(code);
                            }
                            if (1 == i) {
                                String name = provinceTd.text();
                                // System.out.print(" - 名称: " + name);
                                administrativeArea.setName(name);
                                String aHref = provinceAList.first().attr("href");
                                // System.out.println(" - 地址:" + aHref);
                                String currentHref = area.getChildHref();
                                administrativeArea.setChildHref(currentHref.substring(0, currentHref.lastIndexOf('.')) + aHref);
                            }
                            administrativeArea.setChildName(CommonConstant.ADMINISTRATIVE_AREA_LIVE_NAME[administrativeArea.getLevel()]);
                            // this.structureAdministrativeArea(area);
                        } else {
                            // 最后一级
                            if (0 == i) {
                                String code = provinceTd.text();
                                // System.out.print("编码: " + code);
                                administrativeArea.setCode(code);
                            }
                            if (1 == i) {
                                String classification = provinceTd.text();
                                // System.out.print(" - 城乡分类代码:: " + classification);
                                administrativeArea.setClassification(classification);
                            }
                            if (2 == i) {
                                String name = provinceTd.text();
                                // System.out.println(" - 名称: " + name);
                                administrativeArea.setName(name);
                            }
                        }
                    }
                    administrativeAreaList.add(administrativeArea);
                }
                area.setChild(administrativeAreaList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMessageFormat(){
        System.out.println(MessageFormat.format("{1} 上传失败：{0}", "ABCD", "EFGH"));
    }

}
