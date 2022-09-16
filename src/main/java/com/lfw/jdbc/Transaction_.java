package com.lfw.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction_ {

    @Test
    public void noTransaction() {
        //得到链接
        Connection connection = null;
        //组织一个sql
        String sql = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        PreparedStatement preparedStatement = null;
        //3.创建PreparedStatement对象
        try {
            connection = JDBCUtilsV1.getConnection();
            //将 connection 设置为不自动提交
            connection.setAutoCommit(false);  //相当于开启事务
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();  //执行第1条 sql

            int i = 1 / 0;  //抛出异常
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();  //执行第2条 sql2

            //这里提交事务
            connection.commit();

        } catch (SQLException e) {
            //这里我们可以进行回滚，即撤销执行的SQL
            //默认回滚到事务的开始的状态
            System.out.println("执行发生了异常，撤销执行的sql");
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtilsV1.close(null, preparedStatement, connection);
        }
    }
}