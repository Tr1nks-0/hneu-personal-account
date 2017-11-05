package edu.hneu.studentsportal.service;

import com.google.api.client.util.Maps;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.schedule.Schedule;
import edu.hneu.studentsportal.domain.dto.schedule.ScheduleElement;
import javaslang.control.Option;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.WEEKS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ScheduleService {

    @Value("${integration.service.schedule.url}")
    public String scheduleProviderServiceUrl;

    public Schedule load(long groupId, long week, Long studentId) {
        String url = String.format(scheduleProviderServiceUrl, groupId, week);
        if (nonNull(studentId))
            url = url + "&student=" + studentId;
        return load(url);
    }

    private Schedule load(final String url) {
        return Try.of(() -> new RestTemplate().getForObject(url, Schedule.class)).getOrElse(() -> null);
    }

    public long getWeekOrDefault(Long week) {
        if (isNull(week)) {
            LocalDate currentDate = LocalDate.now();
            int educationYear = getEducationYear(currentDate);
            week = WEEKS.between(LocalDate.of(educationYear, Month.SEPTEMBER, 1), LocalDate.now()) + 1;
        }
        return week;
    }

    private int getEducationYear(LocalDate currentDate) {
        Month currentMonth = currentDate.getMonth();
        if(currentMonth.compareTo(Month.JANUARY) >= 0 && currentMonth.compareTo(Month.AUGUST) <= 0)
            return currentDate.getYear() - 1;
        return currentDate.getYear();
    }

    public Map<Integer, Map<Integer, ScheduleElement>> getPairs(Schedule schedule) {
        Map<Integer, Map<Integer, ScheduleElement>> pairs = Maps.newHashMap();
        IntStream.range(0, 7).forEach(i -> pairs.put(i, Maps.newHashMap()));
        schedule.getScheduleElements().getScheduleElement().forEach(groupByDayAndPairNumber(pairs));
        return pairs;
    }

    public int getCurrentCourse(Student studentProfile) {
        return LocalDate.now().getYear() - studentProfile.getIncomeYear() + 1;
    }

    private Consumer<ScheduleElement> groupByDayAndPairNumber(Map<Integer, Map<Integer, ScheduleElement>> pairs) {
        return scheduleElement -> {
            int pairNumber = Integer.valueOf(scheduleElement.getPair());
            Map<Integer, ScheduleElement> pairsPerDays = pairs.get(pairNumber);
            pairsPerDays.put(Integer.valueOf(scheduleElement.getDay()), scheduleElement);
            pairs.put(pairNumber, pairsPerDays);
        };
    }

    public Map<String, Long> getPairsCountPerDay(Schedule schedule) {
        return schedule.getScheduleElements().getScheduleElement()
                .stream()
                .collect(groupingBy(s -> s.getDay().toString(), counting()));
    }
}
