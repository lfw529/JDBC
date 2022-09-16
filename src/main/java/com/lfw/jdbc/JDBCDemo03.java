package com.lfw.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCDemo03 {

    //DML 语句测试
    @Test
    public void test01() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://hadoop102:3306/jdbc?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        //定义并初始化SQL语句
        String sql = "insert into student values (null,?,?)";

        //通过Connection对象获取SQL语句发送对象PreparedStatement,实例方法prepareStatement(String sql)
        PreparedStatement pst = conn.prepareStatement(sql);

        //完成SQL语句的预编译信息
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入你的姓名:");
        String name = sc.nextLine();
        System.out.print("请输入你的年龄:");
        int age = sc.nextInt();

        pst.setString(1, name);
        pst.setInt(2, age);

        //发送预编译SQL语句,调用executeUpdate()
        pst.executeUpdate();

        //关闭资源
        sc.close();
        pst.close();
        conn.close();
    }

    //DQL 语句测试
    @Test
    public void test02() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://hadoop102:3306/jdbc?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        //定义并初始化SQL语句
        String sql = "select * from student where id = ?";

        //通过Connection对象获取SQL语句发送对象PreparedStatement,实例方法prepareStatement(String sql)
        PreparedStatement pst = conn.prepareStatement(sql);

        //完成SQL语句的预编译信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要查找ID:");
        String userID = sc.nextLine();
        pst.setString(1, userID);

        //发送预编译SQL语句,调用executeQuery()
        ResultSet rs = pst.executeQuery();

        //解析executeQuery()返回的结果集对象
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            System.out.println(id + "~" + name + "~" + age);
        }

        //关闭资源
        rs.close();
        sc.close();
        pst.close();
        conn.close();
    }
}
