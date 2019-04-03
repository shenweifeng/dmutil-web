package com.dianmic.dmutil.config;

import java.util.Map;

import org.springframework.util.AntPathMatcher;

/**
 * 
 * 
 * @date 2019年3月31日
 * 
 * @author swf
 *
 * @Description 请求路径URI忽略大小写
 *
 */
public class CaseInsenseticePathMatcher extends AntPathMatcher {

    @Override
    protected boolean doMatch(String pattern, String path, boolean fullMatch,
            Map<String, String> uriTemplateVariables) {
        return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), fullMatch, uriTemplateVariables);
    }

}
