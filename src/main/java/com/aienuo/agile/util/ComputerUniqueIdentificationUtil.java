package com.aienuo.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * ComputerUniqueIdentificationUtil<br>
 * 计算机唯一标识
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年10月10日 17:14
 */
@Slf4j
public class ComputerUniqueIdentificationUtil {

    /**
     * Windows
     */
    public static final String WINDOWS = "windows";
    /**
     * Linux
     */
    public static final String LINUX = "linux";
    /**
     * Unix
     */
    public static final String UNIX = "unix";
    /**
     * 正则表达式
     */
    public static final String REGEX = "\\b\\w+:\\w+:\\w+:\\w+:\\w+:\\w+\\b";

    /**
     * 获取 Windows 主板序列号
     *
     * @return String - 计算机主板序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getWindowsMainBoardSerialNumber() {
        StringBuilder result = new StringBuilder();
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process process = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
        } catch (Exception e) {
            log.error("获取 Windows 主板信息错误", e);
        }
        return result.toString().trim();
    }

    /**
     * 获取 Linux 主板序列号
     *
     * @return String - 计算机主板序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getLinuxMainBoardSerialNumber() {
        StringBuilder result = new StringBuilder();
        try {
            // 管道
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1"});
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
                break;
            }
            br.close();
        } catch (IOException e) {
            log.error("获取 Linux 主板信息错误", e);
        }
        return result.toString();
    }

    /**
     * 从字节获取 MAC
     *
     * @param bytes - 字节
     * @return String - MAC
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/12 8:55
     */
    private static String getMacFromBytes(byte[] bytes) {
        StringBuilder mac = new StringBuilder();
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append(StringPool.DASH);
            }
            byte currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }

    /**
     * 获取 Windows 网卡的 MAC 地址
     *
     * @return String - MAC 地址
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getWindowsMacAddress() {
        List<String> macList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = netInterfaces.nextElement();
                // 遍历所有 IP 特定情况，可以考虑用 ni.getName() 判断
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    // 非 127.0.0.1
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                        macList.add(getMacFromBytes(networkInterface.getHardwareAddress()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取 Windows MAC 错误", e);
        }
        if (!macList.isEmpty()) {
            return macList.get(0);
        } else {
            return "";
        }
    }

    /**
     * 获取 Linux 网卡的 MAC 地址 （如果 Linux 下有 eth0 这个网卡）
     *
     * @return String - MAC 地址
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getLinuxMacAddressForEth0() {
        String mac = null;
        try {
            // Linux下的命令，一般取eth0作为本地主网卡
            Process process = Runtime.getRuntime().exec("ifconfig eth0");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            try (
                    // 显示信息中包含有 MAC 地址信息
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // 寻找标示字符串[hwaddr]
                    int index = line.toLowerCase().indexOf("hwaddr");
                    if (index >= 0) {
                        // 找到并取出 MAC 地址并去除2边空格
                        mac = line.substring(index + "hwaddr".length() + 1).trim();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error("获取 Linux MAC 信息错误 {}", e.getMessage());
        }
        return mac;
    }

    /**
     * 获取 Linux 网卡的 MAC 地址
     *
     * @return String - MAC 地址
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getLinuxMacAddress() {
        String mac = null;
        try {
            // Linux 下的命令 显示或设置网络设备
            Process process = Runtime.getRuntime().exec("ifconfig");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            try (
                    // 显示信息中包含有 MAC 地址信息
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Pattern pat = Pattern.compile(REGEX);
                    Matcher mat = pat.matcher(line);
                    if (mat.find()) {
                        mac = mat.group(0);
                    }
                }
            }
        } catch (IOException e) {
            log.error("获取 Linux MAC 信息错误 {}", e.getMessage());
        }
        return mac;
    }

    /**
     * 获取 Windows 的 CPU 序列号
     *
     * @return String - CPU 序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getWindowsProcessorIdentification() {
        StringBuilder result = new StringBuilder();
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process process = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            try (
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            }
            file.delete();
        } catch (Exception e) {
            log.error("获取 Windows CPU 信息错误 {}", e.getMessage());
        }
        return result.toString().trim();
    }

    /**
     * 获取 Linux 的 CPU 序列号
     *
     * @return String - CPU 序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    private static String getLinuxProcessorIdentification() {
        String result = "";
        try {
            // 管道
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "dmidecode"});
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            try (
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    int index = line.toLowerCase().indexOf("uuid");
                    if (index >= 0) {
                        result = line.substring(index + "uuid".length() + 1).trim();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error("获取 Linux CPU 信息错误 {}", e.getMessage());
        }
        return result.trim();
    }

    /**
     * 获取当前计算机操作系统名称 例如:windows,Linux,Unix等.
     *
     * @return String - 计算机操作系统名称
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取当前计算机操作系统名称前缀 例如:windows,Linux,Unix等.
     *
     * @return String - 计算机操作系统名称
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    public static String getOsNamePrefix() {
        String name = getOsName();
        if (name.startsWith(WINDOWS)) {
            return WINDOWS;
        } else if (name.startsWith(LINUX)) {
            return LINUX;
        } else if (name.startsWith(UNIX)) {
            return UNIX;
        } else {
            return StringPool.EMPTY;
        }
    }

    /**
     * 获取当前计算机主板序列号
     *
     * @return String - 计算机主板序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    public static String getMainBoardSerialNumber() {
        switch (getOsNamePrefix()) {
            case WINDOWS:
                return getWindowsMainBoardSerialNumber();
            case LINUX:
                return getLinuxMainBoardSerialNumber();
            default:
                return StringPool.EMPTY;
        }
    }

    /**
     * 获取当前计算机网卡的 MAC 地址
     *
     * @return String - 网卡的 MAC 地址
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    public static String getMacAddress() {
        switch (getOsNamePrefix()) {
            case WINDOWS:
                return getWindowsMacAddress();
            case LINUX:
                String macAddressForEth0 = getLinuxMacAddressForEth0();
                if (macAddressForEth0 == null || StringPool.EMPTY.equals(macAddressForEth0.trim()) || StringPool.NULL.equals(macAddressForEth0.trim())) {
                    macAddressForEth0 = getLinuxMacAddress();
                }
                return macAddressForEth0;
            default:
                return StringPool.EMPTY;
        }
    }

    /**
     * 获取当前计算机的 CPU 序列号
     *
     * @return String - CPU 序列号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/10 17:15
     */
    public static String getCpuIdentification() {
        switch (getOsNamePrefix()) {
            case WINDOWS:
                return getWindowsProcessorIdentification();
            case LINUX:
                return getLinuxProcessorIdentification();
            default:
                return StringPool.EMPTY;
        }
    }

    /**
     * 获取计算机唯一标识
     *
     * @return ComputerUniqueIdentification - 计算机唯一标识
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/14 8:50
     */
    public static ComputerUniqueIdentification getComputerUniqueIdentification() {
        return new ComputerUniqueIdentification(getOsName(), getMainBoardSerialNumber(), getMacAddress(), getCpuIdentification());
    }

    /**
     * 获取计算机唯一标识
     *
     * @return String - 计算机唯一标识
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/14 8:50
     */
    public static String getComputerUniqueIdentificationString() {
        return getComputerUniqueIdentification().toString();
    }

    /**
     * 计算机唯一标识
     */
    @Data
    private static class ComputerUniqueIdentification {
        private String namePrefix;
        private String mainBoardSerialNumber;
        private String macAddress;
        private String cpuIdentification;

        public ComputerUniqueIdentification(String namePrefix, String mainBoardSerialNumber, String macAddress, String cpuIdentification) {
            this.namePrefix = namePrefix;
            this.mainBoardSerialNumber = mainBoardSerialNumber;
            this.macAddress = macAddress;
            this.cpuIdentification = cpuIdentification;
        }

        @Override
        public String toString() {
            return JacksonUtils.toJsonString(this);
        }
    }

}
