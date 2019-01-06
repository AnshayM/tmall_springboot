package pers.anshay.tmall.util;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * 判断端口是否启动
 *
 * @author: Anshay
 * @date: 2019/1/6
 */
public class PortUtil {
    public static boolean testPort(int port) {
        try {
            ServerSocket severSocket = new ServerSocket(port);
            severSocket.close();
            return false;
        } catch (java.net.BindException e) {
            return true;
        } catch (IOException e) {
            return true;
        }
    }

    /**
     * 判断端口是否启动，未启动则终止运行程序
     *
     * @param port     端口
     * @param server   服务器名
     * @param shutdown 是否关闭状态
     */
    public static void checkPort(int port, String server, boolean shutdown) {
        if (!testPort(port)) {
            if (shutdown) {
                String message = String.format("在端口 %d 未检查得到 %s 启动 %n,是否继续？", port, server);
                if (JOptionPane.OK_CANCEL_OPTION != JOptionPane.showConfirmDialog(null, message)) {
                    System.exit(1);
                }
            }
        }
    }
}
