package com.rozdolskyi.traininghneu.parser;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class PointsExcelParser extends AbstractExcelParser<Map<String, Map<String, String>>> {

    private static final int MAX_NUMBER_OF_ITERATIONS = 100;
    private static final String NUMBER_MARKER = "№";
    private static final String FIRST_DISCIPLINE_MARKER = "Фін";
    private static final String AVERAGE_SCORE_MARKER = "Середній бал";
    private static final String INVALID_SYSTEM_MARKER = "(5б)";

    @Override
    public Map<String, Map<String, String>> extractModel() {
        Map<String, Map<String, String>> studentsDisciplinesScores = new HashMap<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().contains(INVALID_SYSTEM_MARKER)) {
                continue;
            }
            int headerIndex = getCurrentIndex();
            int firstDisciplineNameIndex = getFirstDisciplineNameIndex(headerIndex - 1);
            for(currentIndex = headerIndex + 2; currentIndex < MAX_NUMBER_OF_ITERATIONS; currentIndex++) {
                if (isEmpty(getStringCellValue(currentIndex, 1)))
                    break;
                String studentName = getStringCellValue(currentIndex, 1);
                studentsDisciplinesScores.put(studentName, getScoresForDisciplines(headerIndex, firstDisciplineNameIndex));
            }
        }
        return studentsDisciplinesScores;
    }

    private Map<String, String> getScoresForDisciplines(int headerIndex, int firstDisciplineNameIndex) {
        Map<String, String> mapMarks = new HashMap<>();
        for (int j = firstDisciplineNameIndex; j < MAX_NUMBER_OF_ITERATIONS; j++) {
            if (getStringCellValue(headerIndex - 1, j).contains(AVERAGE_SCORE_MARKER))
                break;
            String disciplineName = getStringCellValue(headerIndex, j);
            String disciplineScore = getStringCellValue(currentIndex, j);
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

    private int getFirstDisciplineNameIndex(int headerIndex) {
        for (int j = 0; j < MAX_NUMBER_OF_ITERATIONS; j++)
            if (getStringCellValue(headerIndex, j).contains(FIRST_DISCIPLINE_MARKER))
                return ++j;
        throw new RuntimeException();
    }
}
