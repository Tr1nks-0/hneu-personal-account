package edu.hneu.studentsportal.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student_profiles")
public class StudentProfile extends Profile {

    private String name;
    private String surname;
    private String faculty;
    private Integer incomeYear;
    private List<String> contactInfo;
    private String speciality;
    private String group;
    private String groupId;
    private List<Course> courses;
    private String password;
    private String profileImage;
    private String passportNumber;
    private String filePath;
    private long modificationTime;

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
}
