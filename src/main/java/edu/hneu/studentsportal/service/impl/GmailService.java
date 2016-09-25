package edu.hneu.studentsportal.service.impl;

import static com.google.common.collect.Lists.newArrayList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

@Service
public class GmailService {

    private static final String APPLICATION_NAME = "hneu-stud-portal";
    private static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(new File("/", "/credentials/hneu-stud-portal"));
        } catch (final Exception e) {
            throw new RuntimeException("Initialization error", e);
        }
    }

    public Credential authorize(final String userEmail) {
        try {
            final InputStream applicationSchemaStream = this.getClass().getResourceAsStream("/client_secret.json");
            final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(applicationSchemaStream));
            //@formatter:off
            final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JACKSON_FACTORY, clientSecrets, newArrayList(GmailScopes.GMAIL_SEND))
                    .setDataStoreFactory(DATA_STORE_FACTORY)
                    .setAccessType("offline")
                    .build();
            //@formatter:on
            final LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
            return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize(userEmail);
        } catch (final Exception e) {
            throw new RuntimeException("Cannot properly authorize the user.", e);
        }
    }


    public Gmail apiForUser(final String userEmail) {
        final Credential credential = authorize(userEmail);
        return new Gmail.Builder(HTTP_TRANSPORT, JACKSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public Message convertToGmailMessage(final MimeMessage email) throws MessagingException, IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        email.writeTo(byteArrayOutputStream);
        return new Message().setRaw(Base64.encodeBase64URLSafeString(byteArrayOutputStream.toByteArray()));
    }
}
