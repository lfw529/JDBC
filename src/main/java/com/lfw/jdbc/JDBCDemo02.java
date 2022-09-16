package com.lfw.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo02 {
    public static void main(String[] args) throws Exception {
        //1.注册驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //2.通过DriverManager工具类获取数据库连接对象,使用里面静态方法getConnection
        String url = "jdbc:mysql://hadoop102:3306/jdbc";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        //3.通过Connection对象获取SQL语句发送对象Statement,实例方法createStatement()
        Statement st = conn.createStatement();

        //4.定义并初始化SQL语句
        String sql = "select * from student";

        //5.通过SQL语句发送对象Statement的实例方法executeQuery()向数据库发送SQL语句
        ResultSet rs = st.executeQuery(sql);

        //6.因为不知道结果集对象里面有多少数据,需要用while循环针对结果集进行遍历,如果结果集.next()为false,代表没有数据
        while (rs.next()) {
            //获取当前行第一列的数据值
            //7.通过ResultSet对象的实例方法getXxx(当前行指定列从1开始)获取当前行指定列的数据值
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            System.out.println(id + "~" + name + "~" + age);
        }

        //8.关闭资源
        rs.close();
        st.close();
        conn.close();
    }
}
