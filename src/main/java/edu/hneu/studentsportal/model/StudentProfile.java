package edu.hneu.studentsportal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class StudentProfile {

    @Id
    private String id;
    private String email;
    private String role;
    private String name;
    private String surname;
    private String faculty;
    private Integer incomeYear;
    @ElementCollection
    @CollectionTable(name="student_contacts", joinColumns=@JoinColumn(name="STUDENT_ID"))
    @Column(name="contact")
    private List<String> contactInfo;
    private String speciality;
    private String group;
    private String groupId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2course",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID")
    )
    private List<Course> courses;
    private String password;
    private String profileImage;
    private String passportNumber;
    private String filePath;
    private long modificationTime;
    private Double average;
    private Integer specialityPlace;

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Integer getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(final Integer incomeYear) {
        this.incomeYear = incomeYear;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(final String faculty) {
        this.faculty = faculty;
    }

    public List<String> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(final List<String> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(final String speciality) {
        this.speciality = speciality;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(final List<Course> courses) {
        this.courses = courses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public void setProfileImage(final String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(final long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(final String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(final Double average) {
        this.average = average;
    }

    public Integer getSpecialityPlace() {
        return specialityPlace;
    }

    public void setSpecialityPlace(final Integer specialityPlace) {
        this.specialityPlace = specialityPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }
}
