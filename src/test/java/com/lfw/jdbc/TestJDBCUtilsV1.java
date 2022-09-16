package com.lfw.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBCUtilsV1 {

    @Test
    public void testSelect() {
        //得到链接
        Connection connection = null;
        //组织一个sql
        String sql = "select * from student";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtilsV1.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //执行，得到遍历结果集
            set = preparedStatement.executeQuery();
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                int age = set.getInt("age");
                System.out.println(id + "\t" + name + "\t" + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtilsV1.close(set, preparedStatement, connection);
        }
    }

    @Test
    public void testDML() {  //insert,update,delete
        //1.得到连接
        Connection connection = JDBCUtilsV1.getConnection();

        //2.组织一个sql
        String sql = "update student set name = ? where id = ?";
        PreparedStatement preparedStatement = null;
        //3.创建PreparedStatement 对象
        try {
            preparedStatement = connection.prepareStatement(sql);
            //给占位符赋值
            preparedStatement.setString(1, "lifuwen111");
            preparedStatement.setInt(2, 2);
            //执行
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsV1.close(null, preparedStatement, connection);
        }
    }
}

