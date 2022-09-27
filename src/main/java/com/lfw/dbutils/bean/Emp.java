package com.lfw.dbutils.bean;

import java.sql.Date;

public class Emp {
    private int id;
    private String name;
    private String gender;
    private int salary;
    private Date join_date;

    public Emp() {
    }

    public Emp(int id, String name, String gender, int salary, Date join_date) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.join_date = join_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", join_date=" + join_date +
                '}';
    }
}
