INSERT INTO user(id, role) VALUES ('oleksandr.rozdolskyi2012@gmail.com', 0);

INSERT INTO student_group (id, name) VALUES (21382, '8.04.51.16.04');
INSERT INTO faculty (id, name) VALUE (0, 'Економічної інформатики');
INSERT INTO speciality (id, name) VALUES (0, '122 КОМП\'ЮТЕРНІ НАУКИ ТА ІНФОРМАЦІЙНІ ТЕХНОЛОГІЇ');
INSERT INTO speciality2faculty (faculty_id, speciality_id) VALUES (0, 0);

INSERT INTO education_program(id, NAME) VALUES (0, 'ІНФОРМАЦІЙНІ СИСТЕМИ УПРАВЛІННЯ ТА ТЕХНОЛОГІЇ ОБРОБКИ ДАНИХ');
INSERT INTO education_program2faculty(speciality_id, education_program_id) VALUES (0, 0);

INSERT INTO discipline VALUES (0,1,1,5,'Інтелектуальні методи та засоби обробки інформації',NULL,1,3);
INSERT INTO discipline VALUES (1,0,1,5,'Методології наукових досліджень',NULL,1,3);
INSERT INTO discipline VALUES (2,1,1,5,'Мережеві технології',NULL,1,3);
INSERT INTO discipline VALUES (3,1,1,5,'Розподілені сховища даних',NULL,1,3);
INSERT INTO discipline VALUES (4,0,1,4,'Сучасна теорія управління',NULL,2,3);
INSERT INTO discipline VALUES (5,1,1,5,'Технології обробки даних в інформаційно-комунікаційних системах',NULL,2,3);
INSERT INTO discipline VALUES (6,1,1,5,'Хмарні обчислення',NULL,2,3);
INSERT INTO discipline VALUES (7,1,1,5,'Методи оптимізації в задачах управління',NULL,2,3);
INSERT INTO discipline VALUES (8,2,1,1,'Науково-дослідна практика: Науковий семінар',NULL,2,3);
INSERT INTO discipline VALUES (9,2,2,3,'Комплексний тренінг',NULL,1,3);
INSERT INTO discipline VALUES (10,2,2,8,'Науково-дослідна практика: Консультаційний проект',NULL,1,3);
INSERT INTO discipline VALUES (11,2,2,4,'Переддипломна практика',NULL,1,3);
INSERT INTO discipline VALUES (12,3,2,15,'Дипломна робота',NULL,1,3);

INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (0, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (1, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (2, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (3, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (4, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (5, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (6, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (7, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (8, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (9, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (10, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (11, 0);
INSERT INTO discipline2speciality(discipline_id, speciality_id) VALUES (12, 0);

INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (0, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (1, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (2, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (3, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (4, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (5, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (6, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (7, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (8, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (9, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (10, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (11, 0);
INSERT INTO discipline2education_program(discipline_id, education_program_id) VALUES (12, 0);
