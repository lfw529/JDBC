package com.lfw.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.1. 将 mysql-connector-java-8.0.26.jar 的 maven 引入项目
        //2.注册驱动类Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");

        //3.通过DriverManager工具类获取数据库连接对象,使用里面静态方法getConnection
        //JDBC:mysql://IP地址:端口号/数据库名字
        String url = "jdbc:mysql://hadoop102:3306/jdbc";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        //4.通过Connection对象获取SQL语句发送对象Statement,实例方法createStatement()
        Statement st = conn.createStatement();

        //5.定义并初始化SQL语句
        String sql = "insert into student values (null,'张三',18)";

        //6.通过SQL语句发送对象Statement的实例方法executeUpdate()向数据库发送SQL语句
        st.executeUpdate(sql);

        //7.关闭资源
        st.close();
        conn.close();
    }
}

