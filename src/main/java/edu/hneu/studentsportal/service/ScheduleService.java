package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.pojo.Schedule;

import java.util.Map;

import static edu.hneu.studentsportal.pojo.Schedule.ScheduleElements.ScheduleElement;

public interface ScheduleService {

    Schedule load(long groupId, long week);

    long getWeekOrDefault(Long week);

    Map<Integer, Map<Integer, ScheduleElement>> getPairs(Schedule schedule);

    int getCurrentCourse(Student studentProfile);
}
