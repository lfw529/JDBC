package com.lfw.jdbc;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FiveConnection {
    //连接方式1：显示使用第三方数据库的API
    @Test
    public void testConnection1() {
        try {
            //MYSQL5.7
            Driver driver = new com.mysql.jdbc.Driver();

            String url = "jdbc:mysql://hadoop102:3306/jdbc?characterEncoding=utf-8";
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "1234");
            Connection conn = driver.connect(url, info);
            System.out.println("连接成功 " + conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //连接方式2：使用反射实例化Driver,不在代码中体现第三方库
    @Test
    public void testConnection2() {
        try {
            String className = "com.mysql.jdbc.Driver";
            Class<?> clazz = Class.forName(className);
            Driver driver = (Driver) clazz.newInstance();
            String url = "jdbc:mysql://hadoop102:3306/jdbc?characterEncoding=utf-8";
            Properties info = new Properties();
            info.setProperty("user", "root");
            info.setProperty("password", "1234");
            Connection conn = driver.connect(url, info);
            System.out.println("连接成功 " + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接方式3：使用DriverManager实现数据库的连接。体会获取连接必要的4个基本要素。
    //  编译运行后，如果出现WARN：Establishing SSL connection without server’s identity verification is not recommended.
    //  可以改为以下(红字为增加内容)。jdbc:mysql://localhost:3306/jdbc?characterEncoding=utf-8&useSSL=false
    @Test
    public void testConnection3() {
        try {
            //四个基本要素
            String url = "jdbc:mysql://hadoop102:3306/jdbc?characterEncoding=utf-8";
            String user = "root";
            String password = "1234";
            String driverName = "com.mysql.jdbc.Driver";

            Class<?> clazz = Class.forName(driverName);
            Driver driver = (Driver) clazz.newInstance();

            DriverManager.registerDriver(driver);
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("连接成功 " + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接方式4：不必显式的注册驱动了。因为在DriverManager的源码中已经存在静态代码块，实现了驱动的注册
    @Test
    public void testConnection4() {
        try {
            //四个基本要素
            String url = "jdbc:mysql://hadoop102:3306/jdbc?characterEncoding=utf-8&useSSL=false";
            String user = "root";
            String password = "1234";
            String driverName = "com.mysql.jdbc.Driver";

            //加载驱动
            Class.forName(driverName);
            //Driver driver=(Driver)clazz.newInstance();
            //注册驱动
            // DriverManager.registerDriver(driver);
	         /*
	         可以注释掉上述代码的原因，是因为在mysql的Driver类中声明有：
	         static{
	             try{
	                 DriverManager.registerDriver(newDriver());
	             }catch(SQLException var1){
	                 throw new RuntimeException("Can't register driver!");
	             }
	         }
	         */

            //获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功 " + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接方式5：最终版

    /**
     * 说明：使用配置文件的方式保存配置信息，在代码中加载配置文件
     * 使用配置文件的好处：
     * ①实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码
     * ②如果修改了配置信息，省去重新编译的过程
     */
    @Test
    public void testConnection5() {
        try {
            //加载配置文件
            InputStream is = FiveConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            //读取配置信息
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            //加载驱动
            Class.forName(driverClass);

            //获取链接
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功" + conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
