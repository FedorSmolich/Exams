package main.java.com.exams.entity;

public class ExamMarks {
    private Long IdApplicant;
    private Long IdExam;
    private int Mark;

    public Long getIdApplicant() {return IdApplicant;}
    public void setIdApplicant(Long idApplicant) {IdApplicant = idApplicant;}

    public Long getIdExam() {return IdExam;}
    public void setIdExam(Long idExam) {IdExam = idExam;}

    public int getMark() {return Mark;}
    public void setMark(int mark) {Mark = mark;}
}
