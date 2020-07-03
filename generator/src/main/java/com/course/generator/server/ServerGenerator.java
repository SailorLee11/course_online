package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * class:ServerGenerator
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */
public class ServerGenerator {
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain = "section";
        Map<String,Object>map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);

//        读test.ftl 生成Test.java
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);
    }
}
