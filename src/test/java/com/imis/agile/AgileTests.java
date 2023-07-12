package com.imis.agile;

import com.imis.agile.util.ComputerUniqueIdentificationUtil;
import com.imis.agile.util.IdCardUtil;
import com.imis.agile.util.PasswordUtil;
import com.imis.agile.util.UnitConversion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
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

    /**
     * 行政区划每一级别的名称
     */
    String[] ADMINISTRATIVE_AREA_LIVE_NAME = {"province", "city", "county", "town", "village"};

    @Test
    void doTestForCityData() {

        AdministrativeArea administrativeArea = new AdministrativeArea()
                .setLevel(0)
                .setCode("000000000000")
                .setName("中华人民共和国")
                .setChildHref("http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2022/index.html")
                .setChildName(ADMINISTRATIVE_AREA_LIVE_NAME[0]);

        structureAdministrativeArea(administrativeArea);

        System.out.println(administrativeArea);
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
            // System.out.println(area.getChildHref());
            Document document = Jsoup.connect(area.getChildHref()).get();
            // 像 js 一样，通过 class 获取列表下的 省会列表 table
            Element provinceTable = document.getElementsByClass(classname + "table").first();
            if (provinceTable != null) {
                // System.out.println(provinceTable);
                // 像 js 一样，通过 class 获取列表下的 tr
                Elements provinceTrList = provinceTable.getElementsByClass(classname + "tr");
                List<AdministrativeArea> administrativeAreaList = new ArrayList<>();
                // 循环处理每一行的数据 tr
                for (Element provinceTr : provinceTrList) {
                    // 像 js 一样，通过 tagName 获取列表下的 td
                    Elements provinceTdList = provinceTr.getElementsByTag("td");
                    // System.out.println(provinceTdList);
                    // 1 - 省级单位
                    if (ADMINISTRATIVE_AREA_LIVE_NAME[0].equals(classname)) {
                        for (Element provinceTd : provinceTdList) {
                            // System.out.println(provinceTd);
                            // 像 js 一样，通过 tagName 获取列表下的 a
                            Elements provinceA = provinceTd.getElementsByTag("a");
                            String name = provinceTd.text();
                            String aHref = provinceA.first().attr("href");
                            // 行政区划 级别
                            AdministrativeArea administrativeArea = new AdministrativeArea().setLevel(area.getLevel() + 1);
                            administrativeArea.setName(name);
                            if (aHref != null && aHref.length() > 0) {
                                // System.out.println(" - 地址:" + aHref);
                                String currentCode = aHref.substring(0, aHref.indexOf('.'));
                                String code = currentCode + area.getCode().substring(currentCode.length());
                                // System.out.println(area.getCode());
                                // System.out.println(code);
                                administrativeArea.setCode(code);
                            }
                            String currentHref = area.getChildHref();
                            // 子级别 区划内容链接
                            administrativeArea.setChildHref(currentHref.substring(0, currentHref.lastIndexOf('/') + 1) + aHref);
                            // 字节别 名称
                            administrativeArea.setChildName(ADMINISTRATIVE_AREA_LIVE_NAME[administrativeArea.getLevel()]);
                            administrativeAreaList.add(administrativeArea);
                            // 递归
                            structureAdministrativeArea(administrativeArea);
                        }
                        // 2、 市级、区县、街道镇、居-村委会 单位
                    } else {
                        // 行政区划 级别
                        AdministrativeArea administrativeArea = new AdministrativeArea().setLevel(area.getLevel() + 1);
                        for (int i = 0; i < provinceTdList.size(); i++) {
                            Element provinceTd = provinceTdList.get(i);
                            // 像 js 一样，通过 tagName 获取列表下的 a
                            Elements provinceA = provinceTd.getElementsByTag("a");
                            if (provinceA != null && provinceA.size() > 0) {
                                // 拥有下一级 从 a 标签 内获取内容
                                String nameOrCode = provinceA.text();
                                String aHref = provinceA.first().attr("href");
                                if (i == 0) {
                                    // 第一级 获取 code
                                    administrativeArea.setCode(nameOrCode);
                                } else {
                                    administrativeArea.setName(nameOrCode);
                                    String currentHref = area.getChildHref();
                                    // 子级别 区划内容链接
                                    administrativeArea.setChildHref(currentHref.substring(0, currentHref.lastIndexOf('/') + 1) + aHref);
                                    // 子级别 名称
                                    administrativeArea.setChildName(ADMINISTRATIVE_AREA_LIVE_NAME[administrativeArea.getLevel()]);
                                    administrativeAreaList.add(administrativeArea);
                                    // 递归
                                    // structureAdministrativeArea(administrativeArea);
                                }
                            } else {
                                String nameOrCode = provinceTd.text();
                                if (0 == i) {
                                    // 获取 code
                                    administrativeArea.setCode(nameOrCode);
                                } else if (1 == i) {
                                    // 居-村委会 级别单位 有 城乡分类代码（3位）
                                    administrativeArea.setClassification(nameOrCode);
                                } else {
                                    administrativeArea.setName(nameOrCode);
                                }
                            }
                        }
                    }
                }
                area.setChild(administrativeAreaList);
            } else {
                System.out.println(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMessageFormat() {
        System.out.println(MessageFormat.format("{1} 上传失败：{0}", "ABCD", "EFGH"));

        int[] arr = {1, 5, 6, 0, 7, 4, 9, 3};
        int[] index = {0, 1, 2, 3, 4, 0, 5, 1, 2, 6, 7}; // 这些是角标，按照角标数  从 上面的数组取值就是了

        StringBuilder tel = new StringBuilder();

        for (int i : index) {
            tel.append(arr[i]);
        }

        System.out.println(tel);

    }

    static List<String> urlList = List.of(
            "https://cn.bing.com/,2022年度全国统计用区划代码和城乡划分代码.html",
            "http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2022/65.html,2022年度新疆维吾尔自治区统计用区划代码和城乡划分代码.html"
    );


    /**
     * 测试 HTML 下载
     */
    @Test
    void testHtmlDownload() {

        for (String urlString : urlList) {
            String[] urlName = urlString.split(",");
            // 想要读取的 url 地址
            try {
                URL url = new URL(urlName[0]);
                File fileDownloadPath = new File("D:/MyDownloda/new/" + urlName[1]);
                // 打开url连接
                URLConnection urlConnection = url.openConnection();
                OutputStream outputStream = Files.newOutputStream(fileDownloadPath.toPath());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder string = new StringBuilder();
                String current;
                while ((current = bufferedReader.readLine()) != null) {
                    string.append(current);
                }
                outputStream.write(string.toString().getBytes());
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
