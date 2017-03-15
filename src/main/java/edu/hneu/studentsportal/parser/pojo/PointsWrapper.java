package edu.hneu.studentsportal.parser.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class PointsWrapper {

    private Map<String, Map<String, String>> map;
    private String semester;

}
