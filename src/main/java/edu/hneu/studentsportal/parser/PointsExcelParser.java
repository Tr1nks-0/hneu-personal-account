package edu.hneu.studentsportal.parser;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import edu.hneu.studentsportal.parser.dto.PointsDto;

public class PointsExcelParser extends AbstractExcelParser<PointsDto> {

    private static final int MAX_NUMBER_OF_ITERATIONS = 100;
    private static final int GROUP_ID_ROW_INDEX = 5;
    private static final int SEMESTER_NUMBER_ROW_INDEX = 3;
    private static final String NUMBER_MARKER = "№";
    private static final String FIRST_DISCIPLINE_MARKER = "Фін";
    private static final String AVERAGE_SCORE_MARKER = "Середній бал";
    private static final String INVALID_SYSTEM_MARKER = "(5б)";
    private static final String GROUP_PREFIX = "групи:";
    private static final String SEMESTER_NUMBER_PREFIX = "Навчальний семестр:";
    private static final int GROUP_NAME_CELL = 8;

    private int currentIndex = 0;

    @Override
    public PointsDto extractModel() {
        final PointsDto pointsDto = new PointsDto();
        pointsDto.setMap(getStudentsDisciplinesScores());
        pointsDto.setSemester(getSemester());
        return pointsDto;
    }

    private Map<String, Map<String, String>> getStudentsDisciplinesScores() {
        final Map<String, Map<String, String>> studentsDisciplinesScores = new HashMap<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().contains(INVALID_SYSTEM_MARKER))
                continue;
            final String groupId = getGroupId();
            final int headerIndex = getCurrentIndex();
            final int firstDisciplineNameIndex = getFirstDisciplineNameIndex(headerIndex - 1);
            for (currentIndex = headerIndex + 2; currentIndex < MAX_NUMBER_OF_ITERATIONS; currentIndex++) {
                if (isEmpty(getStringCellValue(currentIndex, 1)))
                    break;
                final String studentName = getStringCellValue(currentIndex, 1);
                studentsDisciplinesScores.put(getKey(studentName + "$" + groupId), getScoresForDisciplines(headerIndex, firstDisciplineNameIndex));
            }
        }
        return studentsDisciplinesScores;
    }

    private String getGroupId() {
        return getStringCellValue(GROUP_ID_ROW_INDEX, GROUP_NAME_CELL)
                .toLowerCase()
                .replace(GROUP_PREFIX, StringUtils.EMPTY)
                .trim();
    }

    private String getKey(final String key) {
        return key.toLowerCase().replaceAll(" ", StringUtils.EMPTY).trim();
    }

    private Map<String, String> getScoresForDisciplines(final int headerIndex, final int firstDisciplineNameIndex) {
        final Map<String, String> mapMarks = new LinkedHashMap<>();
        for (int j = firstDisciplineNameIndex; j < MAX_NUMBER_OF_ITERATIONS; j++) {
            if (getStringCellValue(headerIndex - 1, j).contains(AVERAGE_SCORE_MARKER))
                break;
            final String disciplineName = getStringCellValue(headerIndex, j);
            final String disciplineScore = getStringCellValue(currentIndex, j);
            mapMarks.put(disciplineName, disciplineScore);
        }
        return mapMarks;
    }

    private int getCurrentIndex() {
        for (int i = 0; i < MAX_NUMBER_OF_ITERATIONS; i++)
            if (getStringCellValue(i, 0).contains(NUMBER_MARKER))
                return ++i;
        throw new RuntimeException();
    }

    private int getFirstDisciplineNameIndex(final int headerIndex) {
        for (int j = 0; j < MAX_NUMBER_OF_ITERATIONS; j++)
            if (getStringCellValue(headerIndex, j).contains(FIRST_DISCIPLINE_MARKER))
                return ++j;
        throw new RuntimeException();
    }

    private String getSemester() {
        return getStringCellValue(SEMESTER_NUMBER_ROW_INDEX, 2).replace(SEMESTER_NUMBER_PREFIX, StringUtils.EMPTY).trim();
    }
}
