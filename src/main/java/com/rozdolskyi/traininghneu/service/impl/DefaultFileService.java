package com.rozdolskyi.traininghneu.service.impl;

import com.rozdolskyi.traininghneu.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Service
public class DefaultFileService implements FileService {

    @Override
    public Map<String, Boolean> reduceForEachUploadedFile(List<MultipartFile> filesToUpload, String path,
                                                          Consumer<File> forEachFileConsumer) throws IOException {
        if (nonNull(filesToUpload) && filesToUpload.size() > 0) {
            return reduceForEachUploadedFileWithResults(filesToUpload, path, forEachFileConsumer);
        }
        return Collections.emptyMap();
    }

    private Map<String, Boolean> reduceForEachUploadedFileWithResults(List<MultipartFile> filesToUpload, String pathToSave,
                                                                      Consumer<File> forEachFileConsumer) throws IOException {
        Map<String, Boolean> fileNames = new LinkedHashMap<>();
        for (MultipartFile multipartFile : filesToUpload) {
            boolean result = reduceForUploadedFileWithResult(multipartFile, forEachFileConsumer, pathToSave);
            fileNames.put(multipartFile.getOriginalFilename(), result);
        }
        return fileNames;
    }

    private boolean reduceForUploadedFileWithResult(MultipartFile multipartFile, Consumer<File> forEachFileConsumer, String pathToSave) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        try {
            if (StringUtils.isNotEmpty(fileName)) {
                File fileToSave = new File(getFullFilePath(fileName, pathToSave));
                createDirectoryIfNotExist(fileToSave.getParentFile());
                multipartFile.transferTo(fileToSave);

                forEachFileConsumer.accept(fileToSave);

                return true;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private String getFullFilePath(String fileName, String pathToSave) {
        return pathToSave + "/" + new Date().getTime() + "_" + fileName;
    }

    @Override
    public boolean createDirectoryIfNotExist(File file) {
        if(file.exists())
            return false;
        return file.mkdirs();
    }
}
