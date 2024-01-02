-- create database OOAD_project;

--! drop table

drop table if exists course_project cascade;
drop table if exists project cascade;
drop table if exists assignment cascade;
drop table if exists team_project cascade;
drop table if exists team cascade;
drop table if exists assignment_submission cascade;
drop table if exists project_submission cascade;
drop table if exists attendance_submission cascade;
drop table if exists attendance cascade;
drop table if exists reply cascade;
drop table if exists material cascade;
drop table if exists post cascade;
drop table if exists course_sa cascade;
drop table if exists course_student cascade;
drop table if exists course_teacher cascade;
drop table if exists course cascade;
drop table if exists admin cascade;
drop table if exists teacher cascade;
drop table if exists student cascade;
drop table if exists grade_book cascade;
drop table if exists team_student cascade;
drop table if exists request_join_team cascade;
drop table if exists invite_join_team cascade;
drop table if exists file cascade;
drop table if exists team_peer_revision cascade;


--! create table student

create table student
(
    id                serial primary key,
    name              varchar(255)          not null,
    account           varchar(255)          not null unique,
    password          varchar(255)          not null,
    email             varchar(255) unique,
    self_Introduction text,
    technology_stack  varchar(255),
    programming_skill varchar(255),
    Intended_teammate varchar(255),
    phone_number      varchar(255) unique,
    sex               bool,
    major             varchar(255),
    level             varchar(255),
    isSA              boolean default false not null,
    department        varchar(255)
);


--! create table teacher

create table teacher
(
    id                serial primary key,
    name              varchar(255)       not null,
    account           varchar(255)       not null unique,
    password          varchar(255)       not null,
    email             varchar(255) unique,
    self_Introduction text,
    phone_number      varchar(255) unique,
    sex               bool default false not null,
    department        varchar(255)       not null,
    tenure            varchar(255)
);

--! create table admin

create table admin
(
    id           serial primary key,
    name         varchar(255) not null,
    account      varchar(255) not null unique,
    password     varchar(255) not null,
    email        varchar(255) not null unique,
    phone_number varchar(255) unique,
    sex          bool
);

--! create table course

create table course
(
    course_id          serial primary key,
    course_code        varchar(255) not null unique,
    course_name        varchar(255) not null,
    course_description varchar(255)
);

--! create table course_teacher

create table course_teacher
(
    course_teacher_id serial primary key,
    teacher_id        integer not null,
    course_id         integer not null
);

--! create table course_student

create table course_student
(
    course_student_id serial primary key,
    student_id        integer not null,
    course_id         integer not null
);

--! create table course_sa

create table course_sa
(
    course_sa_id serial primary key,
    student_id   integer not null,
    course_id    integer not null
);


--! create table project

create table project
(
    id                  serial primary key,
    project_title       varchar(255)     not null,
    project_description varchar(255)     not null,
    releaser            integer          not null,
    releaser_type       varchar(255)     not null,
    course_id           integer          not null,
    project_status      varchar(255)     not null,
    project_start_date  date,
    project_deadline    date             not null,
    max_people_in_team  integer          not null,
    max_score           integer          not null,
    proportion          double precision not null,
    file_id             integer,
    team_deadline       date
);

--! create table project_submission

create table project_submission
(
    project_submission_id serial primary key,
    project_id            integer              not null,
    student_id            integer              not null,
    submission_date       date,
    content               text,
    grade                 integer,
    grade_description     varchar(255),
    file_id               integer,
    fresh                 boolean default true not null
);

-- --! create table course_project
--
-- create table course_project
-- (
--     course_project_id serial primary key,
--     course_id         integer not null,
--     project_id        integer not null
-- );

--! create table team

create table team
(
    team_id                 serial primary key,
    team_name               varchar(255)          not null,
    leader                  integer,
    teacher_id              integer,
    presentation_date       date,
    team_description        varchar(255),
    recruitment_information varchar(255),
    team_size               integer               not null,
    project_id              integer               not null,
    privacy_team            boolean default false not null
);

--! create team_peer_revision

create table team_peer_revision
(
    team_peer_revision_id serial primary key,
    team1                 integer not null, --! 评价的小组
    team2                 integer not null, --! 被评价的小组
    grade                 integer not null,
    comment               varchar(255),
    project_id            integer not null
);

--! create table team_project

-- create table team_project
-- (
--     team_project_id serial primary key,
--     team_id         integer not null,
--     project_id      integer not null
-- );

--! create table team_student

create table team_student
(
    team_student_id serial primary key,
    student_id      integer not null,
    team_id         integer not null,
    project_id      integer not null
);

--! create table grade_book

-- create table grade_book
-- (
--     grade_book_id serial primary key,
--     student_id    integer not null,
--     course_id     integer not null
-- );

--！ create table assignment

create table assignment
(
    id                     serial primary key,
    assignment_title       varchar(255)     not null,
    assignment_description varchar(255),
    assignment_deadline    date             not null,
    assignment_status      varchar(255)     not null,
    max_score              integer          not null,
    proportion             double precision not null,
    releaser               integer          not null,
    releaser_type          varchar(255)     not null,
    course_id              integer          not null,
    file_id                integer
);

--! create table assignment_submission

create table assignment_submission
(
    assignment_submission_id serial primary key,
    assignment_id            integer              not null,
    student_id               integer              not null,
    submission_date          date,
    content                  text,
    grade                    integer,
    grade_description        varchar(255),
    file_id                  integer,
    fresh                    boolean default true not null
);

--! create table material

create table material
(
    id          serial primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    course_id   integer      not null,
    file_id     integer
);


--! create table attendance

create table attendance
(
    id                    serial primary key,
    attendance_date       date             not null,
    attendance_start_time time             not null,
    attendance_deadline   time             not null,
    attendance_status     varchar(255)     not null,
    max_score             integer          not null,
    proportion            double precision not null,
    course_id             integer          not null
);

--! create table attendance_submission

create table attendance_submission
(
    attendance_submission_id serial primary key,
    student_id               integer not null,
    attendance_id            integer not null,
    is_attended              bool
);

--! create table post

create table post
(
    post_id      serial primary key,
    post_title   varchar(255) not null,
    post_content text         not null,
    post_author  integer      not null,
    post_type    varchar(255) not null,
    author_type  varchar(255) not null,
    time         date         not null,
    course_id    integer      not null
);


--! create table reply

create table reply
(
    comment_id    serial primary key,
    reply_content text         not null,
    author_type   varchar(255) not null,
    reply_author  integer      not null,
    reply_time    timestamp    not null,
    post_id       integer      not null
);

--! create table request_join_team

create table request_join_team
(
    id         serial primary key,
    student_id integer not null,
    team_id    integer not null,
    project_id integer not null
);

--! create table invite_join_team

create table invite_join_team
(
    id         serial primary key,
    student_id integer not null,
    team_id    integer not null,
    project_id integer not null
);


--! create table file

create table file
(
    id        serial primary key,
    file_name varchar(255) not null,
    file_type varchar(255) not null,
    file_data BYTEA
);

--! add foreign key to reply

-- alter table reply
--     add foreign key (post_id) references post (post_id);


--! add foreign key to post

-- alter table post
--     add foreign key (course_id) references course (course_id);


--! add foreign key to material

-- alter table material
--     add foreign key (course_id) references course (course_id);


--! add foreign key to assignment_grade_book

-- alter table assignment_submission
--     add foreign key (assignment_id) references assignment (assignment_id);
-- alter table assignment_submission
--     add foreign key (student_id) references student (id);


--! add foreign key to assignment

-- alter table assignment
--     add foreign key (teacher_id) references teacher (id);
-- alter table assignment
--     add foreign key (course_id) references course (course_id);


--! add foreign key to project_grade_book

-- alter table project_submission
--     add foreign key (project_id) references project (project_id);
-- alter table project_submission
--     add foreign key (student_id) references student (id);


--! add foreign key to project

-- alter table project
--     add foreign key (teacher_id) references teacher (id);
-- alter table project
--     add foreign key (course_id) references course (course_id);


--! add foreign key to team_student

-- alter table team_student
--     add foreign key (student_id) references student (id);
-- alter table team_student
--     add foreign key (team_id) references team (team_id);


--! add foreign key to team

-- alter table team
--     add foreign key (project_id) references project (project_id);


--! add foreign key to attendance_grade_book

-- alter table attendance_submission
--     add foreign key (attendance_id) references attendance (attendance_id);
-- alter table attendance_submission
--     add foreign key (student_id) references student (id);


--! add foreign key to material

-- alter table material
--     add foreign key (course_id) references course (course_id);


--! add foreign key to course_sa

-- alter table course_sa
--     add foreign key (course_id) references course (course_id);
-- alter table course_sa
--     add foreign key (student_id) references student (id);

--! add foreign key to course_teacher

-- alter table course_teacher
--     add foreign key (course_id) references course (course_id);
-- alter table course_teacher
--     add foreign key (teacher_id) references teacher (id);

--! add foreign key to course_student

-- alter table course_student
--     add foreign key (course_id) references course (course_id);
-- alter table course_student
--     add foreign key (student_id) references student (id);


--! add foreign key to grade_book

-- alter table grade_book
--     add foreign key (student_id) references student (id);
-- alter table grade_book
--     add foreign key (course_id) references course (course_id);

--! add foreign key to team

-- alter table team
--     add foreign key (project_id) references project (project_id);
--
-- alter table team
--     add foreign key (leader) references student (id);

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
insert into admin (name, account, password, email, phone_number, sex)
values ('admin', '30001', '123456', '30001@qq.com', '18140000001', true);

insert into admin (name, account, password, email, phone_number, sex)
values ('admin2', '30002', '123456', '30002@qq.com', '18140000002', true);

insert into admin (name, account, password, email, phone_number, sex)
values ('admin3', '30003', '123456', '30003@qq.com', '18140000003', true);


--! student data
insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Li Hua', '10001', '123456', '10001@qq.com', 'I am LiHua, I am C++ master', '18130000001', true, 'cs', '1',
        true,
        'COMPUTER_SCIENCE', 'vue', 'C++', 'Zhang San');

insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Zhang San', '10002', '123456', '10002@qq.com', 'self_Introduction', '18130000002', true, 'cs', '1', false,
        'COMPUTER_SCIENCE', 'sprint boot', 'java', 'Li Hua');

insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Li Si', '10003', '123456', '10003@qq.com', 'self_Introduction', '18130000003', true, 'cs', '1', true,
        'COMPUTER_SCIENCE', 'evaluation algorithm', 'python', 'Ke Li');

insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Zhao Liu', '10004', '123456', '10004@qq.com', 'self_Introduction', '18130000004', false, 'ece', '2', false,
        'ELECTRICAL_ENGINEERING', 'embedded systems', 'C', 'Ke Li');

insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Ke Li', '10005', '123456', '10005@qq.com', 'self_Introduction', '18130000005', true, 'mech', '3', true,
        'MECHANICAL_ENGINEERING', 'CAD design', 'Python', 'Li Si');

insert into student (name, account, password, email, self_Introduction, phone_number, sex, major, level, isSA,
                     department, technology_stack, programming_skill, intended_teammate)
values ('Ke Qing', '10006', '123456', '10006@qq.com', 'self_Introduction', '18130000006', false, 'ee', '2', true,
        'ELECTRICAL_ENGINEERING', 'signal processing', 'Java', 'Ke Li');


--! teacher data
insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('马可波罗', '20001', '123456', '20001@qq.com', 'I will teach Artificial Intelligence this year :)',
        '18120000001', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('诸葛亮', '20002', '123456', '20002@qq.com', '我将给你下载元梦之星', '18120000002', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('赵云', '20003', '123456', '20003@qq.com', 'Hello World', '18120000003', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('菲尼克斯', '20006', '123456', '20006@qq.com', '闹够了吗该结束了！', '18120000006', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('蝰蛇', '20004', '123456', '20004@qq.com', '不要妨碍我！', '18120000004', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('贤者', '20005', '123456', '20005@qq.com', '你的使命还没结束！', '18120000005', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');


insert into teacher (name, account, password, email, self_Introduction, phone_number, sex, department, tenure)
values ('钢索', '20007', '123456', '20007@qq.com', '我的D盘我说了算！', '18120000007', true,
        'COMPUTER_SCIENCE',
        'PROFESSOR');

--! course data
insert into course(course_code, course_name, course_description)
values ('CS101', 'Introduction to Computer Science', 'This course introduces the basic concepts of computer science.');

insert into course_student(course_id, student_id)
values (1, 1);
insert into course_student(course_id, student_id)
values (1, 3);
insert into course_student(course_id, student_id)
values (1, 6);

insert into course_sa(student_id, course_id)
values (5, 1);

insert into course_teacher(course_id, teacher_id)
values (1, 1);
insert into course_teacher(course_id, teacher_id)
values (1, 2);

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
Values ('project1', 'project_description1', 1, 1, 'Finished', '2023-09-30', '2024-1-2', 1, 100, 15, 'TEACHER',
        '2023-12-15');

--! team data

insert into team(team_name, leader, teacher_id, presentation_date, team_description, recruitment_information, team_size,
                 project_id)
values ('team1', 1, 1, '2024-1-3', 'single dog', 'single dog', 1, 1);

insert into team(team_name, leader, teacher_id, presentation_date, team_description, recruitment_information, team_size,
                 project_id)
values ('team3', 3, 1, '2024-1-3', 'single dog', 'single dog', 1, 1);

insert into team(team_name, leader, teacher_id, presentation_date, team_description, recruitment_information, team_size,
                 project_id)
values ('team6', 6, 1, '2024-1-3', 'single dog', 'single dog', 1, 1);

insert into team_student(student_id, team_id, project_id)
values (1, 1, 1);
insert into team_student(student_id, team_id, project_id)
values (3, 2, 1);
insert into team_student(student_id, team_id, project_id)
values (6, 3, 1);


--! project submission data
insert
into project_submission(project_id, student_id, submission_date, content, grade,
                        grade_description, file_id)
VALUES (1, 1, '2023-12-31', 'aaa', 90, 'good job', NULL);

insert into project_submission(project_id, student_id, submission_date, content, grade,
                               grade_description, file_id)
VALUES (1, 3, '2023-12-31', 'ccc', 80, 'good job', NULL);

insert into project_submission(project_id, student_id, submission_date, content, grade,
                               grade_description, file_id)
VALUES (1, 6, '2023-12-31', 'bbb', 95, 'good job', NULL);



insert
into project(project_title, project_description, releaser, course_id, project_status,
             project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
             team_deadline)
Values ('project2', 'project_description2', 2, 1, 'Started', '2023-09-01', '2024-01-15', 3, 100, 15, 'TEACHER',
        '2024-1-1');

--! team data

insert into team(team_name, leader, teacher_id, presentation_date, team_description, recruitment_information, team_size,
                 project_id)
values ('team22', 1, 1, '2024-1-3', 'single dog', 'single dog', 1, 2);

insert into team_student(student_id, team_id, project_id)
values (1, 4, 2);

insert into team_student(student_id, team_id, project_id)
values (3, 4, 2);

insert into team(team_name, leader, teacher_id, presentation_date, team_description, recruitment_information, team_size,
                 project_id)
values ('team23', 3, 1, '2024-1-3', 'single dog', 'single dog', 1, 2);

insert into team_student(student_id, team_id, project_id)
values (6, 5, 2);



insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project3', 'project_description3', 1, 1, 'Started', '2023-10-01', '2024-1-15', 4, 80, 20, 'TEACHER',
        '2024-1-20');


-- Assignment for Course 1
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment1', 'assignment_description1', '2023-12-20', 'Finished', 100, 5, 1, 1, 'TEACHER');

-- submission data

insert into assignment_submission(assignment_id, student_id, submission_date, content, grade,
                                  grade_description, file_id)
values (1, 1, '2023-12-18', 'hello world', 80, '6666666', NULL);

insert into assignment_submission(assignment_id, student_id, submission_date, content, grade,
                                  grade_description, file_id)
values (1, 1, '2023-12-19', 'hello world1', 86, '77777', NULL);

insert into assignment_submission(assignment_id, student_id, submission_date, content, grade,
                                  grade_description, file_id)
values (1, 1, '2023-12-16', 'hello world2', 99, '8888888', NULL);

insert into assignment (assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                        proportion, releaser, course_id, releaser_type)
values ('assignment7', 'assignment_description7', '2024-01-10', 'Started', 95, 15, 5, 1, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment13', 'assignment_description13', '2024-02-10', 'Started', 80, 10, 1, 1, 'TEACHER');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS103', 'Programming in Python', 'This course focuses on programming in the Python language.');

insert into course_student(course_id, student_id)
values (2, 1);
insert into course_student(course_id, student_id)
values (2, 2);
insert into course_student(course_id, student_id)
values (2, 3);


insert into course_sa(student_id, course_id)
values (5, 2);
insert into course_sa(student_id, course_id)
values (6, 2);

insert into course_teacher(course_id, teacher_id)
values (2, 1);
insert into course_teacher(course_id, teacher_id)
values (2, 3);

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
Values ('project4', 'project_description4', 1, 2, 'Started', '2023-09-01', '2024-01-10', 3, 100, 15, 'TEACHER',
        '2023-12-15');

insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project5', 'project_description5', 3, 2, 'Started', '2024-1-1', '2024-1-30', 3, 90, 30, 'TEACHER',
        '2024-1-15');


-- Assignment for Course 2
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment8', 'assignment_description8', '2024-01-15', 'Started', 80, 10, 5, 2, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment2', 'assignment_description2', '2024-1-10', 'Started', 80, 10, 5, 2, 'SA');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment14', 'assignment_description14', '2024-02-15', 'Started', 120, 15, 6, 2, 'SA');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS102', 'Programming in Java', 'This course focuses on programming in the Java language.');


insert into course_student(course_id, student_id)
values (3, 1);
insert into course_student(course_id, student_id)
values (3, 4);
insert into course_student(course_id, student_id)
values (3, 5);
insert into course_student(course_id, student_id)
values (3, 6);

insert into course_sa(student_id, course_id)
values (3, 3);

insert into course_teacher(course_id, teacher_id)
values (3, 1);
insert into course_teacher(course_id, teacher_id)
values (3, 2);



insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project6', 'project_description6', 1, 3, 'Started', '2023-11-01', '2023-1-14', 5, 120, 15, 'TEACHER',
        '2023-12-15');


-- Assignment for Course 3
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment15', 'assignment_description15', '2024-02-20', 'Started', 100, 12, 1, 3, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment3', 'assignment_description3', '2023-12-15', 'Started', 120, 15, 1, 3, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment9', 'assignment_description9', '2024-01-20', 'Started', 100, 12, 2, 3, 'TEACHER');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS201', 'Data Structures and Algorithms', 'This course introduces the data structures and algorithms.');

insert into course_student(course_id, student_id)
values (4, 2);
insert into course_student(course_id, student_id)
values (4, 4);
insert into course_student(course_id, student_id)
values (4, 5);


insert into course_sa(student_id, course_id)
values (3, 4);
insert into course_sa(student_id, course_id)
values (6, 4);


insert into course_teacher(course_id, teacher_id)
values (4, 2);
insert into course_teacher(course_id, teacher_id)
values (4, 3);



insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project7', 'project_description7', 2, 4, 'Started', '2023-11-15', '2023-12-31', 4, 100, 15, 'TEACHER',
        '2023-12-15');


-- Assignment for Course 4
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment10', 'assignment_description10', '2024-01-25', 'Started', 85, 8, 2, 4, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment4', 'assignment_description4', '2023-12-22', 'Started', 90, 8, 3, 4, 'TEACHER');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS202', 'Database Management Systems',
        'This course covers the principles and practices of database management.');

insert into course_student(course_id, student_id)
values (5, 4);
insert into course_student(course_id, student_id)
values (5, 5);


insert into course_sa(student_id, course_id)
values (3, 5);
insert into course_sa(student_id, course_id)
values (6, 5);


insert into course_teacher(course_id, teacher_id)
values (5, 2);



insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project8', 'project_description8', 2, 5, 'UNStart', '2023-12-21', '2024-01-15', 3, 80, 20, 'TEACHER',
        '2023-12-15');


-- Assignment for Course 5
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment5', 'assignment_description5', '2023-12-28', 'Started', 100, 12, 2, 5, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment11', 'assignment_description11', '2024-01-30', 'Started', 110, 10, 2, 5, 'TEACHER');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS301', 'Operating Systems', 'This course introduces operate system.');

insert into course_student(course_id, student_id)
values (6, 2);
insert into course_student(course_id, student_id)
values (6, 3);
insert into course_student(course_id, student_id)
values (6, 6);


insert into course_sa(student_id, course_id)
values (5, 6);

insert into course_teacher(course_id, teacher_id)
values (6, 3);


insert into project(project_title, project_description, releaser, course_id, project_status,
                    project_start_date, project_deadline, max_people_in_team, max_score, proportion, releaser_type,
                    team_deadline)
values ('project9', 'project_description9', 3, 6, 'Finished', '2023-01-01', '2023-02-15', 5, 90, 30, 'TEACHER',
        '2023-12-15');


-- Assignment for Course 6
insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment12', 'assignment_description12', '2024-02-05', 'Started', 95, 15, 3, 6, 'TEACHER');

insert into assignment(assignment_title, assignment_description, assignment_deadline, assignment_status, max_score,
                       proportion, releaser, course_id, releaser_type)
values ('assignment6', 'assignment_description6', '2024-01-05', 'Started', 110, 10, 3, 6, 'TEACHER');

----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS203', 'Artificial Intelligence',
        'This course introduces the principles and practices of artificial intelligence.');


----------------------------------------------------------------------------------------------------------
insert into course(course_code, course_name, course_description)
values ('CS302', 'Software Engineering', 'This course explores principles and practices of software development.');


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
insert into material(name, description, course_id)
values ('material1_course1', 'material_description1_course1', 1);

insert into material(name, description, course_id)
values ('material2_course1', 'material_description2_course1', 1);

-- Course 2
insert into material(name, description, course_id)
values ('material1_course2', 'material_description1_course2', 2);

insert into material(name, description, course_id)
values ('material2_course2', 'material_description2_course2', 2);

-- Course 3
insert into material(name, description, course_id)
values ('material1_course3', 'material_description1_course3', 3);

insert into material(name, description, course_id)
values ('material2_course3', 'material_description2_course3', 3);

-- Course 4
insert into material(name, description, course_id)
values ('material1_course4', 'material_description1_course4', 4);

insert into material(name, description, course_id)
values ('material2_course4', 'material_description2_course4', 4);

-- Course 5
insert into material(name, description, course_id)
values ('material1_course5', 'material_description1_course5', 5);

insert into material(name, description, course_id)
values ('material2_course5', 'material_description2_course5', 5);

-- Course 6
insert into material(name, description, course_id)
values ('material1_course6', 'material_description1_course6', 6);

insert into material(name, description, course_id)
values ('material2_course6', 'material_description2_course6', 6);


--! post data


-- Course 1
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post4', 'post_content4', 1, 'TEACHER', '2023-11-05', 1, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post5', 'post_content5', 1, 'TEACHER', '2023-11-18', 1, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post1', 'post_content1', 1, 'STUDENT', '2023-11-01', 1, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post2', 'post_content2', 2, 'STUDENT', '2023-11-12', 1, 'QUESTION');

-- Course 2
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post6', 'post_content6', 1, 'STUDENT', '2023-11-08', 2, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post7', 'post_content7', 2, 'STUDENT', '2023-11-15', 2, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post3', 'post_content3', 3, 'STUDENT', '2023-11-21', 2, 'QUESTION');

-- Course 3
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post8', 'post_content8', 1, 'TEACHER', '2023-11-10', 3, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post9', 'post_content9', 2, 'TEACHER', '2023-11-22', 3, 'ANNOUNCEMENT');

-- Course 4
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post10', 'post_content10', 2, 'TEACHER', '2023-11-14', 4, 'ANNOUNCEMENT');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post11', 'post_content11', 3, 'TEACHER', '2023-11-19', 4, 'ANNOUNCEMENT');

-- Course 5
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post12', 'post_content12', 6, 'STUDENT', '2023-11-07', 5, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post13', 'post_content13', 2, 'TEACHER', '2023-11-20', 5, 'ANNOUNCEMENT');

-- Course 6
insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post14', 'post_content14', 5, 'STUDENT', '2023-11-12', 6, 'QUESTION');

insert into post(post_title, post_content, post_author, author_type, time, course_id, post_type)
values ('post15', 'post_content15', 3, 'TEACHER', '2023-11-23', 6, 'ANNOUNCEMENT');