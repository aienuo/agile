package com.imis.agile;

import com.imis.agile.util.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 工具测试
 */
@Slf4j
class AgileTests {

    @Test
    void contextLoads() {
        log.info("{} 字符串日期转日期： {}", "19970229", LocalDate.parse("19970229", DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    void testRegexp() {
        String regexp1 = "(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)";
        // 假身份证件号
        String id1 = "370911199702294811";

        log.info("身份证合法性校验通过： {}", IdCardUtil.isIdCard(id1));

        log.info("身份证合法性正则表达式校验通过： {}", id1.matches(regexp1));

        String regexp2 = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[1|8|9]))\\d{8}$";

        String phone1 = "17605481537";
        String phone2 = "12345678910";

        log.info("手机号合法性正则表达式校验通过： {}", phone1.matches(regexp2));
        log.info("手机号合法性正则表达式校验通过： {}", phone2.matches(regexp2));

    }

    @Test
    void testPassword() {
        String salt = PasswordUtil.getStringSalt();
        log.info("密码盐： {}", salt);
        String password = PasswordUtil.encrypt("admin", "admin", salt);
        log.info("编码后密码： {}", password);

    }

    @Test
    void doTestForCryptoUtils() {
        String password = "root";
        String encodePassword = PasswordUtil.encrypt(password);
        log.info("编码后密码： {}", encodePassword);
        password = PasswordUtil.decrypt(encodePassword);
        log.info("解码后密码： {}", password);
    }

    @Test
    void getAge() {
        log.info("： {}", IdCardUtil.getAge("110101201803075152"));
    }

    @Test
    void doTestForComputerUniqueIdentificationUtil() {
        log.info("当前计算机操作系统名称： {}", ComputerUniqueIdentificationUtil.getOsName());
        log.info("当前计算机的 CPU 序列号： {}", ComputerUniqueIdentificationUtil.getCpuIdentification());
        log.info("当前计算机网卡的 MAC 地址： {}", ComputerUniqueIdentificationUtil.getMacAddress());
        log.info("当前计算机主板序列号： {}", ComputerUniqueIdentificationUtil.getMainBoardSerialNumber());
        log.info("当前计算机唯一标识： {}", ComputerUniqueIdentificationUtil.getComputerUniqueIdentification());
        log.info("当前计算机唯一标识： {}", ComputerUniqueIdentificationUtil.getComputerUniqueIdentificationString());
    }

    @Test
    void doTestForUnitConversion() {
        log.info("速度转换： {}", UnitConversion.conversion(new BigDecimal(1), UnitConversion.UnitsEnum.TS_MACH, UnitConversion.UnitsEnum.TS_KM, 8));

        List<Map<String, String>> list = UnitConversion.getCategoryList();
        Map<String, List<Map<String, String>>> listMap = UnitConversion.getUnitListMap();
        list.forEach(
                stringStringMap -> {
                    List<Map<String, String>> mapList = listMap.get(stringStringMap.get("code"));
                    mapList.forEach(
                            stringStringMap1 -> {
                                log.info("{} 计量单位({})： {} --- {}", stringStringMap.get("name"), stringStringMap.get("code"), stringStringMap1.get("code"), stringStringMap1.get("name"));
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

        log.info("行政区划数据： {}", administrativeArea);

        writeFile(administrativeArea.toString(), "./json.json");
    }

    /**
     * 省市区县街道名称、高德地图坐标、百度地图坐标、行政区划编号
     * citycode 城市编码
     * adcode 区域编码 街道没有独有的adcode，均继承父类（区县）的adcode
     * name 行政区名称
     * polyline 行政区边界坐标点  当一个行政区范围，由完全分隔两块或者多块的地块组成，每块地的 polyline 坐标串以 | 分隔 。 如北京 的 朝阳区
     * center 区域中心点
     * level 行政区划级别 country:国家 province:省份（直辖市会在province显示） city:市（直辖市会在province显示） district:区县 street:街道
     * districts 级行政区列表，包含district元素
     */
    @Test
    void doTestGoDe() {

        String key = "4f3555325e5471897c6ac3c9ff9410a2";
        String url = "https://restapi.amap.com/v3/config/district";

        String[] list = {
                "北京市",
                "天津市",
                "河北省",
                "山西省",
                "内蒙古自治区",
                "辽宁省",
                "吉林省",
                "黑龙江省",
                "上海市",
                "江苏省",
                "浙江省",
                "安徽省",
                "福建省",
                "江西省",
                "山东省",
                "河南省",
                "湖北省",
                "湖南省",
                "广东省",
                "广西壮族自治区",
                "海南省",
                "重庆市",
                "四川省",
                "贵州省",
                "云南省",
                "西藏自治区",
                "陕西省",
                "甘肃省",
                "青海省",
                "宁夏回族自治区",
                "新疆维吾尔自治区"
        };

        List<District> districtAll = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();

        for (String name : list) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString("key=" + key + "&keywords=" + name + "&subdistrict=3&showbiz=false&extensions=base"))
                    .build();
            try {
                String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

                // 高德地图 BUG citycode 有值的情况 是字符串，无值的时候 返回空数组，
                String regex = "\"citycode\":\\[]";

                body = body.replaceAll(regex, "\"citycode\":\"\"");


                AreaReturn areaReturn = JacksonUtils.parse(body, AreaReturn.class);

                assert areaReturn != null;
                districtAll.addAll(areaReturn.getDistricts());

            } catch (IOException | InterruptedException e) {
                log.error(e.getMessage(), e);
            }

        }


        writeFile(districtAll.toString(), "./省市区县街道名称坐标.json");

    }


    /**
     * 将 json 字符串 写入 指定文件中
     *
     * @param json     - 数据
     * @param filePath - 文件路径
     */
    private void writeFile(final String json, final String filePath) {

        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            boolean create = file.createNewFile();
            if (create) {
                FileWriter fileWritter = new FileWriter(file, false);
                BufferedWriter bufferWriter = new BufferedWriter(fileWritter);
                bufferWriter.write(json);
                bufferWriter.close();
                log.info("文件路径： {}", file.getPath());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
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
            // log.info("： {}",area.getChildHref());
            Document document = Jsoup.connect(area.getChildHref()).get();
            // 像 js 一样，通过 class 获取列表下的 省会列表 table
            Element provinceTable = document.getElementsByClass(classname + "table").first();
            if (provinceTable != null) {
                // log.info("： {}",provinceTable);
                // 像 js 一样，通过 class 获取列表下的 tr
                Elements provinceTrList = provinceTable.getElementsByClass(classname + "tr");
                List<AdministrativeArea> administrativeAreaList = new ArrayList<>();
                // 循环处理每一行的数据 tr
                for (Element provinceTr : provinceTrList) {
                    // 像 js 一样，通过 tagName 获取列表下的 td
                    Elements provinceTdList = provinceTr.getElementsByTag("td");
                    // log.info("： {}",provinceTdList);
                    // 1 - 省级单位
                    if (ADMINISTRATIVE_AREA_LIVE_NAME[0].equals(classname)) {
                        for (Element provinceTd : provinceTdList) {
                            // log.info("： {}",provinceTd);
                            // 像 js 一样，通过 tagName 获取列表下的 a
                            Elements provinceA = provinceTd.getElementsByTag("a");
                            String name = provinceTd.text();
                            String aHref = Objects.requireNonNull(provinceA.first()).attr("href");
                            // 行政区划 级别
                            AdministrativeArea administrativeArea = new AdministrativeArea().setLevel(area.getLevel() + 1);
                            administrativeArea.setName(name);
                            if (aHref.length() > 0) {
                                // log.info("： {}"," - 地址:" + aHref);
                                String currentCode = aHref.substring(0, aHref.indexOf('.'));
                                String code = currentCode + area.getCode().substring(currentCode.length());
                                // log.info("： {}",area.getCode());
                                // log.info("： {}",code);
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
                            if (provinceA.size() > 0) {
                                // 拥有下一级 从 a 标签 内获取内容
                                String nameOrCode = provinceA.text();
                                String aHref = Objects.requireNonNull(provinceA.first()).attr("href");
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
                                    structureAdministrativeArea(administrativeArea);
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
                log.info("table 空： {}", document);

            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void testMessageFormat() {
        log.info("测试 MessageFormat ： {} 999", MessageFormat.format("{1} 上传失败：{0}", "ABCD", "EFGH"));

        int[] arr = {1, 5, 6, 0, 7, 4, 9, 3};
        int[] index = {0, 1, 2, 3, 4, 0, 5, 1, 2, 6, 7}; // 这些是角标，按照角标数  从 上面的数组取值就是了

        StringBuilder tel = new StringBuilder();

        for (int i : index) {
            tel.append(arr[i]);
        }

        log.info("随机生成电话号码： {}", tel);

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
                log.error(e.getMessage(), e);
            }

        }
    }

}
