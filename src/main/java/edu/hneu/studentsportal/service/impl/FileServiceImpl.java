package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.service.FileService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

@Service
public class FileServiceImpl implements FileService {

    @Override
    @SneakyThrows
    public File getFile(MultipartFile multipartFile) {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(multipartFile.getOriginalFilename());
        Files.deleteIfExists(path);
        Files.write(path, bytes);
        return path.toFile();
    }

    @Override
    public <E> E perform(File file, Supplier<E> supplier) throws IOException {
        try {
            return supplier.get();
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }
}
