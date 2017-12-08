package edu.hneu.studentsportal.service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

@Log4j
@Service
public class FileService {

    @SneakyThrows
    public File getFile(MultipartFile multipartFile) {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(multipartFile.getOriginalFilename());
        Files.deleteIfExists(path);
        Files.write(path, bytes);
        return path.toFile();
    }

    @SneakyThrows
    public byte[] getProfilePhoto(MultipartFile multipartFile) {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));

        // change the image size of a profile's photo
        final int width = 90;
        final int height = 120;
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();

        // convert to array of bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write( resizedImage, "png", byteArrayOutputStream );
        byteArrayOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return bytes;
    }

    @SneakyThrows
    public <E> E perform(File file, Function<File, E> function) {
        try {
            return function.apply(file);
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }
}
