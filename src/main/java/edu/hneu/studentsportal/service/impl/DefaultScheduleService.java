package edu.hneu.studentsportal.service.impl;

import com.google.api.client.util.Maps;
import edu.hneu.studentsportal.pojo.Schedule;
import edu.hneu.studentsportal.service.ScheduleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.stream.IntStream;

import static edu.hneu.studentsportal.pojo.Schedule.ScheduleElements.ScheduleElement;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.util.Objects.isNull;

@Service
public class DefaultScheduleService implements ScheduleService {


    @Value("${schedule.url}")
    public String scheduleUrl;

    @Override
    public Schedule load(long groupId, Long week) {
        if(isNull(week)) {
            LocalDate currentDate = LocalDate.now();
            int educationYear = currentDate.getMonth().compareTo(Month.JANUARY) >= 0 ? currentDate.getYear() - 1 : currentDate.getYear();
            week = WEEKS.between(LocalDate.of(educationYear, Month.SEPTEMBER, 1), LocalDate.now()) + 1;
        }
        String url = String.format(scheduleUrl, groupId, week);
        return new RestTemplate().getForObject(url, Schedule.class);
    }

    @Override
    public Map<Integer, Map<Integer, ScheduleElement>> getPairs(Schedule schedule) {
        Map<Integer, Map<Integer, ScheduleElement>> pairs = Maps.newHashMap();
        IntStream.range(0, 7).forEach(i -> pairs.put(i, Maps.newHashMap()));
        schedule.getScheduleElements().getScheduleElement().forEach(scheduleElement -> {
            int pairNumber = Integer.valueOf(scheduleElement.getPair());
            Map<Integer, ScheduleElement> days = pairs.get(pairNumber);
            days.put(Integer.valueOf(scheduleElement.getDay()), scheduleElement);
            pairs.put(pairNumber, days);
        });
        return pairs;
    }
}
