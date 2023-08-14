package com.dd.demo.demo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author Bryce_dd 2023/8/9 22:38
 */
public class Nio {

    public static void main(String[] args) throws IOException {
        // 创建选择器
        Selector selector = Selector.open();
        // 打开监听通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 如果为true，则此通道将被置于阻塞模式；
        // 如果为false， 则此通道将被置于非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定端口， backlog设为1024
        serverSocketChannel.socket().bind(new InetSocketAddress(7777), 1024);
        // 监听客户端连接请求
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }
}
