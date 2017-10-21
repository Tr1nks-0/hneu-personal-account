package edu.hneu.studentsportal.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

@Service
public class GmailService {

    @SneakyThrows
    public Message convertToGmailMessage(MimeMessage mimeMessage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        mimeMessage.writeTo(byteArrayOutputStream);
        return new Message().setRaw(Base64.encodeBase64URLSafeString(byteArrayOutputStream.toByteArray()));
    }

    @SneakyThrows
    public void sendEmail(String from, String tokenValue, Message message) {
        api(tokenValue).users().messages().send(from, message).execute();
    }

    private Gmail api(String tokenValue) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(tokenValue);
        return new Gmail.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("hneu-students")
                .build();
    }

}
