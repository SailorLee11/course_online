package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * class:ServerGenerator
 * author: sailor lee
 * mail:18962364008@163.com
 * description:
 */
public class ServerGenerator {
    static String toPath = "generator\\src\\main\\java\\com\\course\\generator\\test\\";
    public static void main(String[] args) throws IOException, TemplateException {
//        读test.ftl 生成Test.java
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator(toPath+"Test.java");
    }
}
