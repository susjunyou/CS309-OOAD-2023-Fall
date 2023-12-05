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
values (30001, 'admin', '123', '123456', '123456@qq.com', '13636364747', true);

insert into admin (id, name, account, password, email, phone_number, sex)
values (30002, 'admin2', '124', '123456', '123457@qq.com', '13636364747', true);

insert into admin (id, name, account, password, email, phone_number, sex)
values (30003, 'admin3', '125', '123456', '123458@qq.com', '13636364747', true);


--! student data
insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (1, 'student1', '10001', '123456', '10001@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', true,
        'COMPUTER_SCIENCE', 'vue', 'C++', 'Li Hua');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (2, 'student2', '10002', '123456', '10002@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', false,
        'COMPUTER_SCIENCE', 'sprint boot', 'java', 'zhang san');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (3, 'student3', '10003', '123456', '10003@qq.com', 'self_Introduction', '13636364747', true, 'cs', '1', true,
        'COMPUTER_SCIENCE', 'evaluation algorithm', 'python', 'Li Ming');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (4, 'student4', '10004', '123456', '10004@qq.com', 'self_Introduction', '13636364747', false, 'ece', '2', false,
        'ELECTRICAL_ENGINEERING', 'embedded systems', 'C', 'Wang Xiao');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (5, 'student5', '10005', '123456', '10005@qq.com', 'self_Introduction', '13636364747', true, 'mech', '3', true,
        'MECHANICAL_ENGINEERING', 'CAD design', 'Python', 'Zhang Wu');

insert into student (id, name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values (6, 'student6', '10006', '123456', '10006@qq.com', 'self_Introduction', '13636364747', false, 'ee', '2', true,
        'ELECTRICAL_ENGINEERING', 'signal processing', 'Java', 'Chen Mei');


--! teacher data
insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (20001, 'teacher1', '20001', '123456', '20001@qq.com', 'self_Introduction', '13636364747', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (20002, 'teacher2', '20002', '123456', '20002@qq.com', 'self_Introduction', '13636364747', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (id, name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values (20003, 'teacher3', '20003', '123456', '20003@qq.com', 'self_Introduction', '13636364747', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');


--! course data
insert into course(course_code, course_name, course_description)
values ('CS101', 'Introduction to Computer Science', 'This course introduces the basic concepts of computer science.');

insert into course(course_code, course_name, course_description)
values ('CS201', 'Data Structures and Algorithms', 'This course introduces the data structures and algorithms.');

insert into course(course_code, course_name, course_description)
values ('CS301', 'Operating Systems', 'This course introduces operate system.');

insert into course(course_code, course_name, course_description)
values ('CS102', 'Programming in Java', 'This course focuses on programming in the Java language.');

insert into course(course_code, course_name, course_description)
values ('CS202', 'Database Management Systems',
        'This course covers the principles and practices of database management.');

insert into course(course_code, course_name, course_description)
values ('CS302', 'Software Engineering', 'This course explores principles and practices of software development.');

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

--! course_student data sorted by student_id
insert into course_student(course_id, student_id)
values (1, 1);
insert into course_student(course_id, student_id)
values (2, 1);
insert into course_student(course_id, student_id)
values (3, 1);

insert into course_student(course_id, student_id)
values (2, 2);
insert into course_student(course_id, student_id)
values (4, 2);
insert into course_student(course_id, student_id)
values (6, 2);

insert into course_student(course_id, student_id)
values (1, 3);
insert into course_student(course_id, student_id)
values (2, 3);
insert into course_student(course_id, student_id)
values (6, 3);

insert into course_student(course_id, student_id)
values (3, 4);
insert into course_student(course_id, student_id)
values (4, 4);
insert into course_student(course_id, student_id)
values (5, 4);

insert into course_student(course_id, student_id)
values (3, 5);
insert into course_student(course_id, student_id)
values (4, 5);
insert into course_student(course_id, student_id)
values (5, 5);

insert into course_student(course_id, student_id)
values (1, 6);
insert into course_student(course_id, student_id)
values (3, 6);
insert into course_student(course_id, student_id)
values (6, 6);


--! course_sa data student 1 3 5 6 is SA

insert into course_sa(student_id, course_id)
values (1, 4);
insert into course_sa(student_id, course_id)
values (1, 5);
insert into course_sa(student_id, course_id)
values (1, 6);

insert into course_sa(student_id, course_id)
values (3, 3);
insert into course_sa(student_id, course_id)
values (3, 4);
insert into course_sa(student_id, course_id)
values (3, 5);

insert into course_sa(student_id, course_id)
values (5, 1);
insert into course_sa(student_id, course_id)
values (5, 2);
insert into course_sa(student_id, course_id)
values (5, 6);

insert into course_sa(student_id, course_id)
values (6, 2);
insert into course_sa(student_id, course_id)
values (6, 4);
insert into course_sa(student_id, course_id)
values (6, 5);


--! course_teacher data

insert into course_teacher(course_id, teacher_id)
values (1, 20001);
insert into course_teacher(course_id, teacher_id)
values (1, 20002);
insert into course_teacher(course_id, teacher_id)
values (2, 20001);
insert into course_teacher(course_id, teacher_id)
values (2, 20003);
insert into course_teacher(course_id, teacher_id)
values (3, 20001);
insert into course_teacher(course_id, teacher_id)
values (3, 20002);
insert into course_teacher(course_id, teacher_id)
values (4, 20002);
insert into course_teacher(course_id, teacher_id)
values (4, 20003);
insert into course_teacher(course_id, teacher_id)
values (5, 20002);
insert into course_teacher(course_id, teacher_id)
values (6, 20003);

--! project data

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
Values ('project1', 'project_description1', 20001, 1, 'Finished', '2023-09-30', '2023-10-31', 3, 100, 15, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
Values ('project2', 'project_description2', 20002, 1, 'Started', '2023-09-01', '2024-01-01', 3, 100, 15, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project3', 'project_description3', 20001, 1, 'Started', '2023-10-01', '2023-12-15', 4, 80, 20, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
Values ('project4', 'project_description4', 20001, 2, 'Started', '2023-09-01', '2024-01-01', 3, 100, 15, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project5', 'project_description5', 20003, 2, 'UNStarted', '2023-12-30', '2024-1-30', 3, 90, 30, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project6', 'project_description6', 20001, 3, 'Started', '2023-11-01', '2023-12-15', 5, 120, 15, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project7', 'project_description7', 20002, 4, 'Started', '2023-11-15', '2023-12-31', 4, 100, 15, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project8', 'project_description8', 20002, 5, 'UNStarted', '2023-12-21', '2024-01-15', 3, 80, 20, 'TEACHER');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type)
values ('project9', 'project_description9', 20003, 6, 'Finished', '2023-01-01', '2023-02-15', 5, 90, 30, 'TEACHER');


--! project submission data

-- Project 1 Submissions for Students 1, 2, and 3
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (1, 1, 100, 'Excellent work from student 1', '2023-10-31', 'Impressive implementation');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (1, 2, 95, 'Great effort from student 2', '2023-10-30', 'Well-documented code');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (1, 3, 80, 'Good work from student 3', '2023-10-29', 'Implemented key features');

-- Project 2 Submissions for Students 1, 2, and 3
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (2, 1, 85, 'Impressive work from student 1', '2023-11-15', 'Met project requirements');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (2, 2, 90, 'Great effort from student 2', '2023-11-12', 'Innovative features implemented');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (2, 3, 75, 'Some improvements needed for student 3', '2023-11-10', 'Functional but needs refinement');

-- Project 3 Submissions for Students 1, 2, and 3
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (3, 1, 92, 'Outstanding work from student 1', '2023-12-01', 'Met and exceeded project goals');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (3, 2, 88, 'Good effort from student 2', '2023-11-28', 'Well-structured code');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (3, 3, 78, 'Some logical errors for student 3', '2023-11-25', 'Needs debugging');

-- Project 4 Submissions for Students 1, 2, and 3
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (4, 1, 94, 'Excellent work from student 1', '2023-11-30', 'Met project requirements');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (4, 2, 87, 'Great performance from student 2', '2023-11-28', 'Implemented additional features');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (4, 3, 82, 'Some improvements needed for student 3', '2023-11-25', 'Needs optimization');

-- Project 6 Submissions for Students 1, 4, 5, and 6
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (6, 1, 91, 'Impressive work from student 1', '2023-12-01', 'Implemented innovative features');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (6, 4, 96, 'Exceptional work from student 4', '2023-12-05', 'Met and exceeded project expectations');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (6, 5, 89, 'Good effort from student 5', '2023-11-28', 'Well-documented and tested code');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (6, 6, 79, 'Some logical errors for student 6', '2023-11-25', 'Needs further testing and debugging');

-- Project 7 Submissions for Students 4 and 6
insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (7, 4, 85, 'Good work from student 4', '2023-12-20', 'Implemented core features');

insert into project_submission(project_id, student_id, grade, grade_description, submission_date, content)
values (7, 6, 92, 'Impressive contribution from student 6', '2023-12-10', 'Implemented additional functionalities');


--! Assignment data

-- Assignment for Course 1
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment1', 'assignment_description1', '2023-12-01', 'Finished', 100, 5, 20001, 1, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment7', 'assignment_description7', '2024-01-10', 'Started', 95, 15, 5, 1, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment13', 'assignment_description13', '2024-02-10', 'Started', 80, 10, 20001, 1, 'TEACHER');


-- Assignment for Course 2
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment8', 'assignment_description8', '2024-01-15', 'Started', 80, 10, 5, 2, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment2', 'assignment_description2', '2023-12-10', 'Started', 80, 10, 5, 2, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment14', 'assignment_description14', '2024-02-15', 'Started', 120, 15, 6, 2, 'SA');


-- Assignment for Course 3
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment15', 'assignment_description15', '2024-02-20', 'Started', 100, 12, 20001, 3, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment3', 'assignment_description3', '2023-12-15', 'Started', 120, 15, 20001, 3, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment9', 'assignment_description9', '2024-01-20', 'Started', 100, 12, 20002, 3, 'TEACHER');


-- Assignment for Course 4
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment10', 'assignment_description10', '2024-01-25', 'Started', 85, 8, 20002, 4, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment4', 'assignment_description4', '2023-12-22', 'Started', 90, 8, 20003, 4, 'TEACHER');


-- Assignment for Course 5
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment5', 'assignment_description5', '2023-12-28', 'Started', 100, 12, 20002, 5, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment11', 'assignment_description11', '2024-01-30', 'Started', 110, 10, 20002, 5, 'TEACHER');


-- Assignment for Course 6
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment12', 'assignment_description12', '2024-02-05', 'Started', 95, 15, 20003, 6, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment6', 'assignment_description6', '2024-01-05', 'Started', 110, 10, 20003, 6, 'TEACHER');


--! assignment submission data

insert into assignment_submission(assignment_id, student_id, submission_date, grade, grade_description, content)
VALUES (1, 1, '2023-11-01', 90, 'grade_description', 'ass');

insert into assignment_submission(assignment_id, student_id, submission_date, grade, grade_description, content)
VALUES (1, 2, '2023-11-12', 80, 'grade_description2', 'bss');

insert into assignment_submission(assignment_id, student_id, submission_date, grade, grade_description, content)
VALUES (1, 3, '2023-11-21', NULL, NULL, NULL);


--! Attendance data

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2023-11-01', '10:00', '11:00', 'Finished', 100, 1, 1);

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2023-11-02', '10:00', '11:00', 'Finished', 100, 1, 1);

insert into attendance(attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score,
                       proportion, course_id)
values ('2023-11-01', '10:00', '11:00', 'Finished', 100, 1, 2);


--! attendance_submission data

insert into attendance_submission(student_id, attendance_id, is_attended)
values (1, 1, true);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (1, 2, false);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (2, 1, true);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (2, 2, true);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (3, 2, false);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (3, 1, false);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (2, 3, true);

insert into attendance_submission(student_id, attendance_id, is_attended)
values (3, 3, true);

--! material data

-- Course 1
insert into material(material_name, material_description, course_id)
values ('material1_course1', 'material_description1_course1', 1);

insert into material(material_name, material_description, course_id)
values ('material2_course1', 'material_description2_course1', 1);

-- Course 2
insert into material(material_name, material_description, course_id)
values ('material1_course2', 'material_description1_course2', 2);

insert into material(material_name, material_description, course_id)
values ('material2_course2', 'material_description2_course2', 2);

-- Course 3
insert into material(material_name, material_description, course_id)
values ('material1_course3', 'material_description1_course3', 3);

insert into material(material_name, material_description, course_id)
values ('material2_course3', 'material_description2_course3', 3);

-- Course 4
insert into material(material_name, material_description, course_id)
values ('material1_course4', 'material_description1_course4', 4);

insert into material(material_name, material_description, course_id)
values ('material2_course4', 'material_description2_course4', 4);

-- Course 5
insert into material(material_name, material_description, course_id)
values ('material1_course5', 'material_description1_course5', 5);

insert into material(material_name, material_description, course_id)
values ('material2_course5', 'material_description2_course5', 5);

-- Course 6
insert into material(material_name, material_description, course_id)
values ('material1_course6', 'material_description1_course6', 6);

insert into material(material_name, material_description, course_id)
values ('material2_course6', 'material_description2_course6', 6);



--! post data

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post1', 'post_content1', 1, 'STUDENT', '2023-11-01', 1, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post2', 'post_content2', 2, 'STUDENT', '2023-11-12', 1, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post3', 'post_content3', 3, 'STUDENT', '2023-11-21', 2, 'QUESTION');

-- Course 1
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post4', 'post_content4', 20001, 'TEACHER', '2023-11-05', 1, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post5', 'post_content5', 20001, 'TEACHER', '2023-11-18', 1, 'ANNOUNCEMENT');

-- Course 2
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post6', 'post_content6', 1, 'STUDENT', '2023-11-08', 2, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post7', 'post_content7', 2, 'STUDENT', '2023-11-15', 2, 'QUESTION');

-- Course 3
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post8', 'post_content8', 20001, 'TEACHER', '2023-11-10', 3, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post9', 'post_content9', 20002, 'TEACHER', '2023-11-22', 3, 'ANNOUNCEMENT');

-- Course 4
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post10', 'post_content10', 20002, 'TEACHER', '2023-11-14', 4, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post11', 'post_content11', 20003, 'TEACHER', '2023-11-19', 4, 'ANNOUNCEMENT');

-- Course 5
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post12', 'post_content12', 6, 'STUDENT', '2023-11-07', 5, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post13', 'post_content13', 20002, 'TEACHER', '2023-11-20', 5, 'ANNOUNCEMENT');

-- Course 6
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post14', 'post_content14', 5, 'STUDENT', '2023-11-12', 6, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post15', 'post_content15', 20003, 'TEACHER', '2023-11-23', 6, 'ANNOUNCEMENT');




-- select *
-- from assignment_submission
--          left join assignment a on a.id = assignment_submission.assignment_id
-- where student_id = 1
--   and assignment_id = 1;
--
--
-- select *
-- from project_submission
--          left join project p on p.id = project_submission.project_id
-- where student_id = 1
--   and project_id = 1;
--
-- select *
-- from attendance_submission
--          left join attendance a on a.id = attendance_submission.attendance_id
-- where student_id = 1
--   and attendance_id = 1;
