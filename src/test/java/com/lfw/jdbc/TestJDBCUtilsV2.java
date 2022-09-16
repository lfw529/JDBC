package com.lfw.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBCUtilsV2 {
    //Druid-JDBC 测试
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
            connection = JDBCUtilsV2.getConnection();
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
            JDBCUtilsV2.close(set, preparedStatement, connection);
        }
    }
}
