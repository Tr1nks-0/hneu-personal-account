package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.annotation.Message;

public interface MessageService {

    //emails

    @Message("mails.subject.new.user")
    String userWasCreatedEmailSubject();

    @Message("mails.subject.changed.user")
    String userWasChangedEmailSubject();

    @Message("mails.body.changed.user")
    String userWasChangedEmailBody();

    @Message("mails.subject.contact.us.email")
    String contactUsEmailSubject();

    //errors

    @Message("invalid.file.not.found")
    String fileNotFoundError();

    @Message("invalid.file.not.found.by.path")
    String fileNotFoundError(String path);

    @Message("error.student.profile.exists")
    String studentExistsError();

    @Message("error.group.exists")
    String groupExistsError();

    @Message("error.faculty.exists")
    String facultyExistsError();

    @Message("error.speciality.exists")
    String specialityExistsError();

    @Message("error.education.program.exists")
    String educationProgramExistsError();

    @Message("error.disciplines.exist")
    String disciplinesExistError();

    @Message("invalid.file")
    String invalidFile();

    @Message("error.student.email.not.found")
    String emailNotFoundForStudent(String studentName);

    @Message("error.student.disciplines.not.found")
    String cannotFindDisciplines(String speciality, String educationProgram);

    @Message("error.admin.exists")
    String adminExistsError();

    @Message("error.cannot.delete.admin")
    String cannotDeleteAdmin();

    @Message("error.something.went.wrong")
    String somethingWentWrong();
}
