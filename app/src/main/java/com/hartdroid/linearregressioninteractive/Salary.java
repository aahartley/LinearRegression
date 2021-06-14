package com.hartdroid.linearregressioninteractive;


public class Salary {
    private double year;
    private double salary;

    public Salary(double year, double salary){
        this.year = year;
        this.salary = salary;
    }

    public double getYear() {
        return year;
    }

    public void setYear(double year) {
        this.year = year;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "year=" + year +
                ", salary=" + salary +
                '}';
    }
}
