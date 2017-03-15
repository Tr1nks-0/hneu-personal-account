package edu.hneu.studentsportal.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Map<String, Boolean> reduceForEachUploadedFile(MultipartFile filesToUpload, Consumer<File> forEachFileConsumer) throws IOException;

    boolean createDirectoryIfNotExist(File file);

    File getFile(MultipartFile multipartFile);
}
