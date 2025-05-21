package main.java.com.exams.entity;

public class Applicants {
    private Long Id;
    private String Name;
    private String Lastname;
    private String Patronymic;
    private String Telephone;

    public Long getId() {return Id;}
    public void setId(Long Id) {this.Id = Id;}

    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}

    public String getLastname() {return Lastname;}
    public void setLastname(String Lastname) {this.Lastname = Lastname;}

    public String getPatronymic() {return Patronymic;}
    public void setPatronymic(String Patronymic) {this.Patronymic = Patronymic;}

    public String getTelephone() {return Telephone;}
    public void setTelephone(String Telephone) {this.Telephone = Telephone;}
}
