package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.service.FileService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DefaultFileService implements FileService {

    @Override
    @SneakyThrows
    public File getFile(MultipartFile multipartFile) {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(multipartFile.getOriginalFilename());
        Files.deleteIfExists(path);
        Files.write(path, bytes);
        return path.toFile();
    }
}
