package edu.hneu.studentsportal.enums;

import java.util.Arrays;
import java.util.Objects;

public enum MaritalStatus {
    UNDEFINED("", ""),
    SINGLE("Неодружений", "Незаміжня", "(?iu)неодружений|незаміжня"),
    DIVORCED("Розведений", "Розведена", "(?iu)розведений|розведена"),
    MARRIED("Заміжня", "Одружений", "(?iu)заміжня|одружений"),
    CIVIL_MARRIAGE("у цивільному шлюбі", "(?iu)цивільний шлюб");


    private String maleStatusName;
    private String femaleStatusName;
    private String pattern;

    MaritalStatus(String name, String pattern) {
        this.maleStatusName = name;
        this.pattern = pattern;
    }

    MaritalStatus(String maleStatusName, String femaleStatusName, String pattern) {
        this.maleStatusName = maleStatusName;
        this.femaleStatusName = femaleStatusName;
        this.pattern = pattern;
    }

    public String getStatusNameByGender(Gender gender) {
        if (Objects.isNull(gender)) {
            return "";
        }
        if (Objects.nonNull(femaleStatusName) && gender.equals(Gender.FEMALE)) {
            return femaleStatusName;
        }
        return maleStatusName;
    }

    public static MaritalStatus getStatusByString(String str) {
        return Arrays.stream(MaritalStatus.values())
                .filter(status -> !status.equals(UNDEFINED))
                .filter(status -> str.matches(status.pattern)).findAny().orElse(UNDEFINED);
    }

}
