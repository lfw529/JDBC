package com.lfw.dao;

import com.lfw.dbutils.bean.Emp;
import org.junit.Test;

import java.util.List;

public class TestDAO {
    //测试 EmpDAO 对 emp 表的 crud 操作
    @Test
    public void testEmpDAO() {
        EmpDAO empDAO = new EmpDAO();
        //1.查询
        List<Emp> emps = empDAO.queryMulti("select * from emp where id >= ?", Emp.class, 1);
        System.out.println("===查询结果===");
        for (Emp emp : emps) {
            System.out.println(emp);
        }

        //2.查询单行记录
        Emp emp = empDAO.querySingle("select * from emp where id = ?", Emp.class, 5);
        System.out.println("===查询单行记录===");
        System.out.println(emp);

        //3.查询单行单列
        Object o = empDAO.queryScalar("select name from emp where id = ?", 4);
        System.out.println("===查询单行单列值===");
        System.out.println(o);

        //4.dml 操作 insert,update,delete
        int update = empDAO.update("insert into emp values(null, ?, ?, ?, ?)", "XHL", "女", 8000, "2000-05-08");

        System.out.println(update > 0 ? "执行成功" : "没有影响表");
    }
}
