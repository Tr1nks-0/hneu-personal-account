package edu.hneu.studentsportal.parser.pojo;

import java.util.Map;

public class PointsDto {
    private Map<String, Map<String, String>> map;
    private String semester;

    public Map<String, Map<String, String>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
