package com.example.boyraztalha.sql_training_app;

public class People_info {

    String name;
    int salary;

    public People_info(){

    }

    public People_info(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String toString(){
        return "" + name + " " + salary;
    }
}
