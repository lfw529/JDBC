package com.lfw.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/* JDBC 工具类 */
public class JDBCUtilsV1 {
    private static String url;
    private static String user;
    private static String password;
    private static String driverClass;

    /**文件读取，只会执行一次，使用静态代码*/
    static {
        //读取文件
        try {
            //1.创建Properties集合类
            Properties pro = new Properties();
            //注意文件流的相对目录
            pro.load(new FileInputStream("src/main/resources/jdbc.properties"));

            //2.获取数据
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driverClass = pro.getProperty("driverClass");
        } catch (IOException e) {
            //在实际开发中，可以这样处理
            //将编译异常转成运行异常，这时调用者可以选择捕获异常或者默认处理该异常
            throw new RuntimeException();
        }
    }

    /**
     * 获取连接
     *
     * @return 连接对象
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            //在实际开发中，可以这样处理
            //将编译异常转成运行异常，这时调用者可以选择捕获异常或者默认处理该异常
            throw new RuntimeException();
        }
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

