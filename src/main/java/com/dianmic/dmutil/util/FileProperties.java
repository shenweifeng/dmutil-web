package com.dianmic.dmutil.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件的处理类. 用来处理诸如:coufigure.property之类的文件.
 */
public class FileProperties extends Properties {
    private static final long serialVersionUID = -8751721578625489508L;

    public FileProperties() {
    }

    /** 根据给出的文件构造FileProperties 类 */
    public FileProperties(String s) {
        try {
            InputStream inputstream = getClass().getResourceAsStream(s);
            load(inputstream);
            inputstream.close();
        } catch (Exception exception) {
            // System.err.println("Exception in FileProperties(String): " +
            // exception.toString() + " for filename=" + s);
        }
    }

    /** 根据给出的属性集合构造相应的FileProperties类 */
    public FileProperties(Properties properties) {
        super(properties);
    }
}
