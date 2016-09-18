package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Service
public class DefaultFileService implements FileService {

    @Value("${uploaded.files.location}")
    private String uploadedExcelFilesPath;


    private static Logger LOG = Logger.getLogger(DefaultFileService.class.getName());

    @Override
    public Map<String, Boolean> reduceForEachUploadedFile(List<MultipartFile> filesToUpload,
                                                          Consumer<File> forEachFileConsumer) throws IOException {
        if (nonNull(filesToUpload) && filesToUpload.size() > 0) {
            return reduceForEachUploadedFileWithResults(filesToUpload, forEachFileConsumer);
        }
        return Collections.emptyMap();
    }

    private Map<String, Boolean> reduceForEachUploadedFileWithResults(List<MultipartFile> filesToUpload,
                                                                      Consumer<File> forEachFileConsumer) throws IOException {
        Map<String, Boolean> fileNames = new LinkedHashMap<>();
        for (MultipartFile multipartFile : filesToUpload) {
            boolean result = reduceForUploadedFileWithResult(multipartFile, forEachFileConsumer);
            fileNames.put(multipartFile.getOriginalFilename(), result);
        }
        return fileNames;
    }

    private boolean reduceForUploadedFileWithResult(MultipartFile multipartFile, Consumer<File> forEachFileConsumer) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                String classPath = this.getClass().getResource("/").getPath();
                File fileToSave = new File(getFullFilePath(fileName, classPath + uploadedExcelFilesPath));
                createDirectoryIfNotExist(fileToSave.getParentFile());
                multipartFile.transferTo(fileToSave);

                forEachFileConsumer.accept(fileToSave);

                return true;
            }
            return false;
        } catch (RuntimeException e) {
            LOG.error("File uploaded error: ", e);
            return false;
        }
    }

    private String getFullFilePath(String fileName, String pathToSave) {
        return pathToSave + "/" + new Date().getTime() + "_" + fileName;
    }

    @Override
    public boolean createDirectoryIfNotExist(File file) {
        if (file.exists())
            return false;
        return file.mkdirs();
    }
}
