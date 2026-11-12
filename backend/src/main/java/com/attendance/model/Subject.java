
package com.attendance.model;

public class Subject {

    private int id;
    private String subjectName;
    private String subjectCode;
    private int teacherId;

    public Subject() {}

    public Subject(int id, String subjectName,
                   String subjectCode, int teacherId) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
