package edu.hneu.studentsportal.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FilesUploadModel {

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
