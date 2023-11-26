delete
from admin;
delete
from assignment;
delete
from assignment_submission;
delete
from attendance;
delete
from attendance_submission;
delete
from course;
delete
from course_sa;
delete
from course_student;
delete
from course_teacher;
delete
from material;
delete
from post;
delete
from project;
delete
from project_submission;
delete
from reply;
delete
from student;
delete
from teacher;
delete
from team;
delete
from team_student;


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


--! gradeBook date

-- insert into grade_book(course_id, student_id)
-- values (1, 1);
--
-- insert into grade_book(course_id, student_id)
-- values (2, 1);
--
-- insert into grade_book(course_id, student_id)
-- values (3, 1);
--
-- insert into grade_book(course_id, student_id)
-- values (2, 2);
--
-- insert into grade_book(course_id, student_id)
-- values (3, 2);
--
-- insert into grade_book(course_id, student_id)
-- values (1, 3);
--
-- insert into grade_book(course_id, student_id)
-- values (2, 3);

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

--! project data

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion)
Values ('project1', 'project_description', 11, 1, 'Started', '2021-01-01', '2021-01-01', 3, 100, 0.5);

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion)
Values ('project2', 'project_description', 22, 1, 'Started', '2021-01-01', '2021-01-01', 3, 100, 0.5);

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion)
Values ('project3', 'project_description', 11, 2, 'Started', '2021-01-01', '2021-01-01', 3, 100, 0.5);

--! Assignment data

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id)
values ('assignment1', 'assignment_description', '2021-01-01', 'Started', 100, 0.5, 11, 1);

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id)
values ('assignment2', 'assignment_description', '2021-01-01', 'Started', 100, 0.5, 11, 1);

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id)
values ('assignment3', 'assignment_description', '2021-01-01', 'Started', 100, 0.5, 22, 2);

--! Attendance data

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2021-01-01', '10:00', '11:00', 'Started', 100, 0.5, 1);

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2021-01-02', '10:00', '11:00', 'Started', 100, 0.5, 1);

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2021-01-01', '10:00', '11:00', 'Started', 100, 0.5, 2);

--! material data

insert into material(material_name, material_description, course_id)
values ('material1', 'material_description', 1);

insert into material(material_name, material_description, course_id)
values ('material2', 'material_description', 1);

insert into material(material_name, material_description, course_id)
values ('material3', 'material_description', 2);

--! post data
insert into post(post_title, post_content, post_author, author_type, time, course_id)
values ('post1', 'post_content', 1, 'STUDENT', '2021-01-01', 1);

insert into post(post_title, post_content, post_author, author_type, time, course_id)
values ('post2', 'post_content', 2, 'STUDENT', '2021-01-01', 1);

insert into post(post_title, post_content, post_author, author_type, time, course_id)
values ('post3', 'post_content', 3, 'STUDENT', '2021-01-01', 2);



