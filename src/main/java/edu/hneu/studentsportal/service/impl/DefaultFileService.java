package edu.hneu.studentsportal.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.hneu.studentsportal.service.FileService;
import lombok.SneakyThrows;

@Service
public class DefaultFileService implements FileService {

    @Value("${uploaded.files.location}")
    private String uploadedExcelFilesPath;


    private static Logger LOG = Logger.getLogger(DefaultFileService.class.getName());

    @Override
    public Map<String, Boolean> reduceForEachUploadedFile(MultipartFile filesToUpload,
                                                          Consumer<File> forEachFileConsumer) throws IOException {
        return reduceForEachUploadedFileWithResults(filesToUpload, forEachFileConsumer);
    }

    private Map<String, Boolean> reduceForEachUploadedFileWithResults(MultipartFile filesToUpload,
                                                                      Consumer<File> forEachFileConsumer) throws IOException {
        Map<String, Boolean> fileNames = new LinkedHashMap<>();
        boolean result = reduceForUploadedFileWithResult(filesToUpload, forEachFileConsumer);
        fileNames.put(filesToUpload.getOriginalFilename(), result);
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
        return !file.exists() && file.mkdirs();
    }

    @Override
    @SneakyThrows
    public File getFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
}
