package com.dianmic.dmutil.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
    static Logger log = Logger.getLogger(XmlUtil.class);

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(String xml) {
        Document doc = null;
        Map<String, Object> map = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            Iterator<Element> it = rootElt.elementIterator();
            map = new HashMap<String, Object>();
            while (it.hasNext()) {
                Element item = it.next();
                map.put(item.getName(), item.getText());
            }
        } catch (Exception e) {
            log.error("xml convert map fail: " + e.getMessage());
        }
        return map;
    }
}
