package edu.hneu.studentsportal.service;


import java.time.LocalDate;

public interface TimeService {

    long getCurrentTime();
    
    LocalDate getCurrentDate();

    int getCurrentEducationWeek();
}
