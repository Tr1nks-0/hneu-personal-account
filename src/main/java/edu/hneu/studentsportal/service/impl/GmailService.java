package edu.hneu.studentsportal.service.impl;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

@Service
public class GmailService {

    @Autowired
    private OAuth2RestTemplate oauth2RestTemplate;

    public Gmail api() {
        final GoogleCredential credential = new GoogleCredential().setAccessToken(oauth2RestTemplate.getAccessToken().getValue());
        return new Gmail.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName("hneu-students").build();
    }

    @SneakyThrows
    public Message convertToGmailMessage(final MimeMessage email) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        email.writeTo(byteArrayOutputStream);
        return new Message().setRaw(Base64.encodeBase64URLSafeString(byteArrayOutputStream.toByteArray()));
    }

}
