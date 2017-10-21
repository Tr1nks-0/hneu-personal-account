package edu.hneu.studentsportal.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelValidator {

    private static final String[] EXTENSIONS = {".xlsx", ".xls"};

    @SneakyThrows
    public static void validate(File file) {
        Path path = Paths.get(file.toURI());
        if (Stream.of(EXTENSIONS).noneMatch(path.getFileName().toString()::endsWith)) {
            Files.deleteIfExists(path);
            throw new IllegalArgumentException(String.format("File[%s] is not an excel file", path.getFileName()));
        }
    }
}
