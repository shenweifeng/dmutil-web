package com.dianmic.dmutil.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebsocketConstant {
    // 存放所有的ChannelHandlerContext
    public static Map<String, ChannelHandlerContext> pushCtxMap     = new ConcurrentHashMap<String, ChannelHandlerContext>();

    // 存放某一类的channel
    public static ChannelGroup                       aaChannelGroup = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

}
