package edu.hneu.studentsportal.service;


import java.time.LocalDate;

public interface TimeService {
    LocalDate getCurrentDay();

    int getCurrentEducationWeek();
}
