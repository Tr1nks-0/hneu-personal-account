package edu.hneu.studentsportal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    File getFile(MultipartFile multipartFile);
}
