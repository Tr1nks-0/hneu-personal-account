package edu.hneu.studentsportal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface FileService {
    Map<String, Boolean> reduceForEachUploadedFile(List<MultipartFile> filesToUpload, String path,
                                                   Consumer<File> forEachFileConsumer) throws IOException;

    boolean createDirectoryIfNotExist(File file);
}
