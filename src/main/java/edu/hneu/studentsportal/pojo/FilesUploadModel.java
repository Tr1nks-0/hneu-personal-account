package edu.hneu.studentsportal.pojo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FilesUploadModel {

    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(final List<MultipartFile> files) {
        this.files = files;
    }
}
