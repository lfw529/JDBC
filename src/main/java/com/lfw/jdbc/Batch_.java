package com.lfw.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Batch_ {

    //传统方法，添加5000条数据到admin
    @Test
    public void noBatch() throws Exception {
        Connection connection = JDBCUtilsV1.getConnection();
        String sql = "insert into admin values(null,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();  //开始时间
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jack" + i);
            preparedStatement.setString(2, "666");
            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("传统的方式 耗时=" + (end - start)); //传统的方式 耗时=10702
        //关闭连接
        JDBCUtilsV1.close(null, preparedStatement, connection);
    }

    @Test
    public void batch() throws Exception {
        Connection connection = JDBCUtilsV1.getConnection();
        String sql = "insert into admin values(null,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();  //开始时间
        for (int i = 0; i < 5000; i++) {
            preparedStatement.setString(1, "jack" + i);
            preparedStatement.setString(2, "666");
            //将 sql 语句加入到批处理包中 -> 看源码
            //1.第一就创建 ArrayList - elementDAta => Object[]
            //2.elementData => Object[] 就会存放我们预处理的sql语句
            //3.当elementData满后,就按照1.5扩容
            //4.当添加到指定的值后，就executeBatch
            //5.批量处理会减少我们发送sql语句的网络开销
            /*
            public void addBatch() throws SQLException {
                synchronized (checkClosed().getConnectionMutex()) {
                    if (this.batchedArgs == null) {
                        this.batchedArgs = new ArrayList<Object>();
                    }

                    for (int i = 0; i < this.parameterValues.length; i++) {
                        checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
                    }

                    this.batchedArgs.add(new BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
                }
            }
            */
            preparedStatement.addBatch();
            //当有1000条记录时，在批量执行
            if ((i + 1) % 1000 == 0) { //满1000条sql
                preparedStatement.executeBatch();
                //清空一把
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批处理的方式 耗时=" + (end - start));  //批量方式 耗时=108
        JDBCUtilsV1.close(null, preparedStatement, connection);
    }
}
