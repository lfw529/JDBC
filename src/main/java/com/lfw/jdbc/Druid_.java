package com.lfw.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class Druid_ {

    @Test
    public void testDruid() throws Exception {
        //1. 加入 Druid jar 包
        //2. 加入配置文件 druid.properties, 将该文件拷贝项目的src目录
        //3. 创建 Properties 对象，读取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/druid.properties"));

        //4.创建一个指定参数的数据库连接池，Druid连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        long start = System.currentTimeMillis();  //开始时间
        for (int i = 0; i < 500000; i++) {
            Connection connection = dataSource.getConnection();
//            System.out.println("连接成功");
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("druid连接池操作500000 耗时=" + (end - start));  //druid连接池操作500000 耗时=982
    }
}
