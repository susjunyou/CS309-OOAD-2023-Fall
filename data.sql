-- delete from admin;
-- delete from student;
-- delete from teacher;


--! admin data
insert into admin (id, name, account, password, email, phone_number, sex)
values (20, 'admin', '123', '123456', '123456@qq.com', '13636364747', true);

insert into admin (id, name, account, password, email, phone_number, sex)
values (21, 'admin2', '124', '123456', '123457@qq.com', '13636364747', true);

insert into admin (id, name, account, password, email, phone_number, sex)
values (22, 'admin3', '125', '123456', '123458@qq.com', '13636364747', true);


--! student data
insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department)
values (1, 'student1', '123', '123456', '123456@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', true,
        'COMPUTER_SCIENCE');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department)
values (2, 'student2', '124', '123456', '123457@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', false,
        'COMPUTER_SCIENCE');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department)
values (3, 'student3', '125', '123456', '123458@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', true,
        'COMPUTER_SCIENCE');

--! teacher data
insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (11, 'teacher1', '123', '123456', '123456@qq.com', 'self_Introduction', '13636364747', true, 'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (22, 'teacher2', '124', '123456', '123457@qq.com', 'self_Introduction', '13636364747', true, 'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (33, 'teacher3', '125', '123456', '123458@qq.com', 'self_Introduction', '13636364747', true, 'COMPUTER_SCIENCE',
        'PROFESSOR');


--! course data
insert into course(course_code, course_name, course_description)
values ('CS101', 'Introduction to Computer Science', 'This course introduces the basic concepts of computer science.');

insert into course(course_code, course_name, course_description)
values ('CS201', 'Data Structures and Algorithms', 'This course introduces the data structures and algorithms.');

insert into course(course_code, course_name, course_description)
values ('CS301', 'Operating Systems', 'This course introduces operate system.');

--! course_student data

insert into course_student(course_id, student_id)
values (1, 1);

insert into course_student(course_id, student_id)
values (2, 1);

insert into course_student(course_id, student_id)
values (3, 1);

insert into course_student(course_id, student_id)
values (2, 2);

insert into course_student(course_id, student_id)
values (3, 2);

insert into course_student(course_id, student_id)
values (1, 3);

insert into course_student(course_id, student_id)
values (2, 3);

--! course_teacher data

insert into course_teacher(course_id, teacher_id)
values (1, 11);

insert into course_teacher(course_id, teacher_id)
values (2, 11);

insert into course_teacher(course_id, teacher_id)
values (3, 22);
