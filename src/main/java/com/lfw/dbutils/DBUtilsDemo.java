package com.lfw.dbutils;

import com.lfw.dbutils.bean.Emp;
import com.lfw.jdbc.JDBCUtilsV2;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBUtilsDemo {

    @Test
    //使用apache-DBUtils 工具类 + druid 完成对表的 crud 操作
    public void testQueryMany() throws SQLException { //返回结果是多行的情况
        //1.得到连接(druid)
        Connection connection = JDBCUtilsV2.getConnection();
        //2.使用 DBUtils 类和接口，先引入 DBUtils 相关的 jar，加入到 Project
        //3.创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        /**
         * 分析：queryRunner.query 方法
         *  private <T> T query(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
         *         PreparedStatement stmt = null;  //定义PreparedStatement
         *         ResultSet rs = null;  //接收返回的ResultSet
         *         T result = null;    //返回ArrayList
         *
         *         try {
         *             stmt = this.prepareStatement(conn, sql);  //创建PreparedStatement
         *             this.fillStatement(stmt, params);   //对 sql 进行 ？ 赋值
         *             rs = this.wrap(stmt.executeQuery());  //返回resultSet --> arrayList[result] [使用到反射，对传入的class对象处理]
         *             result = rsh.handle(rs);
         *
         *         } catch (SQLException e) {
         *             this.rethrow(e, sql, params);
         *
         *         } finally {
         *             try {
         *                 close(rs);  //关闭resultSet
         *             } finally {
         *                 close(stmt);  //关闭Statement
         *                 if (closeConn) {
         *                     close(conn);
         *                 }
         *             }
         *         }
         *
         *         return result;
         *     }
         */
        //4.就可以执行相关的方法，返回 ArrayList 结果集
        String sql = "select * from emp where id >= ?";
        //（1）query 方法就是执行sql语句，得到resultSet --- 封装到 ---> ArrayList 集合中
        //（2）返回集合
        //（3）connection: 连接
        //（4）sql: 执行的sql语句
        //（5）new BeanListHandler<>(Emp.class): 在将resultSet -> Emp 对象 -> 封装到 ArrayList
        // 底层使用反射机制，去获取Emp类的属性，然后进行封装
        //（6）1 就是给 sql 语句中的 ？赋值，可以由多个值，因为是可变参数 Object... params
        //（7）底层得到的resultSet，会在query关闭，关闭PreparedStatement
        List<Emp> list = queryRunner.query(connection, sql, new BeanListHandler<>(Emp.class), 1);  //返回结合对象
        System.out.println("输出集合的信息");
        for (Emp emp : list) {
            System.out.println(emp);
        }

        DbUtils.closeQuietly(connection);
    }

    //演示 apache-dbutils + druid 完成返回的结果是单行记录（单个对象）
    @Test
    public void testQuerySingle() throws SQLException {
        //1.得到连接 (druid)
        Connection connection = JDBCUtilsV2.getConnection();
        //2.使用 DBUtils 类和接口，先引入 DBUtils 相关的 jar，加入到 Project
        //3.创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回单个对象
        String sql = "select * from emp where id = ?";

        //因为返回的单行记录 <---> 单个对象，使用的 Handler 是 BeanHandler
        Emp emp = queryRunner.query(connection, sql, new BeanHandler<>(Emp.class), 3);
        System.out.println(emp);

        //释放资源
        DbUtils.closeQuietly(connection);
    }

    //演示 apache-dbutils + druid 完成查询结果是单行单列
    @Test
    public void testScalar() throws SQLException {
        //1.得到连接(druid)
        Connection connection = JDBCUtilsV2.getConnection();
        //2.使用 DBUtils 类和接口，先引入 DBUtils 相关的 jar,加入到 Project
        //3.创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回 单行单列
        String sql = "select name from emp where id = ?";

        Object obj = queryRunner.query(connection, sql, new ScalarHandler(), 4);
        System.out.println(obj);

        //释放资源
        JDBCUtilsV2.close(null, null, connection);
    }

    @Test
    //演示 apache-dbutils + druid 完成 dml(update,insert,delete)
    public void testDML() throws SQLException {
        //1.得到连接(druid)
        Connection connection = JDBCUtilsV2.getConnection();
        //2.使用 DBUtils 类和接口，先引入 DBUtils 相关的 jar,加入到 Project
        //3.创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4.这里组织sql 完成 update insert delete
        String sql = "update emp set name = ? where id = ?";
        //（1）执行dml操作是 queryRunner.update()
        //（2）返回的值是受影响的行数 (affected:受影响)
        int affectedRow = queryRunner.update(connection, sql, "lfw", 5);
        System.out.println(affectedRow > 0 ? "执行成功" : "执行没有影响到表");
        //释放资源
        JDBCUtilsV2.close(null, null, connection);
    }
}
