package edu.hneu.studentsportal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface FileService {

    File getFile(MultipartFile multipartFile);

    <E> E perform(File file, Supplier<E> supplier) throws IOException;
}
