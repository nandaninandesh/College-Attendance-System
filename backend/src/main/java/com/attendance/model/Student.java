
package com.attendance.model;

public class Student {

    private int id;
    private String rollNo;
    private String name;
    private String email;
    private String phone;
    private String className;

    public Student() {}

    public Student(int id, String rollNo, String name,
                   String email, String phone,
                   String className) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.className = className;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassName() { return className; }
    public void setClassName(String className) {
        this.className = className;
    }
}
