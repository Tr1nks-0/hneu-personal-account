package edu.hneu.studentsportal.service;


import edu.hneu.studentsportal.pojo.Schedule;

import java.util.Map;

import static edu.hneu.studentsportal.pojo.Schedule.ScheduleElements.ScheduleElement;

public interface ScheduleService {

    Schedule load(long groupId, Long week);

    Map<Integer, Map<Integer, ScheduleElement>> getPairs(Schedule schedule);
}
