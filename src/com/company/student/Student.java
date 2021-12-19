package com.company.student;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:22
 */
public class Student {
    // 创建学生类，包含学生对象的各种属性信息
    private String studentName;
    private long studentId;
    private String studentSex;
    private int studentAge;
    private int studentScore;
    // 学生学院、专业信息
    private String studentDepartment;
    private String studentMajor;

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentId=" + studentId +
                ", studentSex='" + studentSex + '\'' +
                ", studentAge=" + studentAge +
                ", studentScore=" + studentScore +
                ", studentDepartment='" + studentDepartment + '\'' +
                ", studentMajor='" + studentMajor + '\'' +
                '}';
    }

    public Student() {
    }

    public Student(String studentName, long studentId, String studentSex, int studentAge, int studentScore, String studentDepartment, String studentMajor) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentSex = studentSex;
        this.studentAge = studentAge;
        this.studentScore = studentScore;
        this.studentDepartment = studentDepartment;
        this.studentMajor = studentMajor;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }
}
