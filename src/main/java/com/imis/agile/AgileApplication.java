package com.imis.agile;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * SpringBootApplication
 * </p>
 *
 * @author XinLau
 * @since 2021-07-15
 */
@Slf4j
@SpringBootApplication
public class AgileApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(AgileApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("UnknownHostException:", e.getCause());
        }
        String port = env.getProperty("server.port");
        if (port == null || StringPool.EMPTY.equals(port) || StringPool.NULL.equals(port)) {
            port = "8080";
        }
        String path = env.getProperty("server.servlet.context-path");
        if (path == null || StringPool.EMPTY.equals(path) || StringPool.NULL.equals(path)) {
            path = "/";
        }
        log.info("\n=========================程序已经启动完毕==========================\n\t" +
                "\n-----------------------------------------------------------------\n\t" +
                "本机访问地址: \t\thttp://" + ip + ":" + port + path + "\n\t" +
                "外部访问地址: \t\thttp://" + ip + ":" + port + path + "\n\t" +
                "接口文档地址: \t\thttp://" + ip + ":" + port + path + "doc.html\n" +
                "-----------------------------------------------------------------");
    }

}
