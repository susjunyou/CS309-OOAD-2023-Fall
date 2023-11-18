-- create database OOAD_project;

--! create table student
create table student
(
    student_id        serial primary key,
    SID               integer               not null,
    student_name      varchar(255)          not null,
    student_account   varchar(255)          not null,
    student_password  varchar(255)          not null,
    student_email     varchar(255)          not null,
    self_Introduction text,
    phone_number      varchar(255)          not null,
    sex               bool                  not null,
    major             varchar(255)          not null,
    student_level     varchar(255)          not null,
    isSA              boolean default false not null,
    department        varchar(255)          not null
);


--! create table teacher

create table teacher
(
    teacher_id        serial primary key,
    TID               integer      not null,
    teacher_name      varchar(255) not null,
    teacher_account   varchar(255) not null,
    teacher_password  varchar(255) not null,
    teacher_email     varchar(255) not null,
    self_Introduction text,
    phone_number      varchar(255) not null,
    sex               bool         not null,
    department        varchar(255) not null,
    tenure            varchar(255) not null
);

--! create table admin

create table admin
(
    admin_id       serial primary key,
    AID            integer      not null,
    admin_name     varchar(255) not null,
    admin_account  varchar(255) not null,
    admin_password varchar(255) not null,
    admin_email    varchar(255) not null,
    phone_number   varchar(255) not null,
    sex            bool         not null
);

--! create table course

create table course
(
    course_id          serial primary key,
    CID                integer      not null,
    course_name        varchar(255) not null,
    course_description varchar(255) not null
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
    course_teacher_id serial primary key,
    student_id        integer not null,
    course_id         integer not null
);


--! create table project

create table project
(
    project_id          serial primary key,
    project_title       varchar(255)     not null,
    project_description varchar(255)     not null,
    teacher_id          integer          not null,
    project_status      varchar(255)     not null,
    project_start_date  date             not null,
    project_deadline    date             not null,
    max_people_in_team  integer          not null,
    max_grade           integer          not null,
    proportion          double precision not null
);

--! create table project_grade_book

create table project_grade_book
(
    project_grade_book_id serial primary key,
    project_id            integer      not null,
    grade_book_id         integer      not null,
    grade                 integer      not null,
    grade_description     varchar(255) not null
);

--! create table course_project

create table course_project
(
    course_project_id serial primary key,
    course_id         integer not null,
    project_id        integer not null
);

--! create table team

create table team
(
    team_id          serial primary key,
    team_name        varchar(255) not null,
    team_leader      integer      not null,
    team_description varchar(255) not null,
    team_size        integer      not null
);

--! create table team_project

create table team_project
(
    team_project_id serial primary key,
    team_id         integer not null,
    project_id      integer not null
);

--! create table team_student

create table team_student
(
    team_student_id serial primary key,
    student_id      integer not null,
    team_id         integer not null
);

--! create table grade_book

create table grade_book
(
    grade_book_id serial primary key,
    student_id    integer not null,
    course_id     integer not null
);

--！ create table assignment

create table assignment
(
    assignment_id          serial primary key,
    assignment_title       varchar(255)     not null,
    assignment_description varchar(255)     not null,
    assignment_deadline    date             not null,
    assignment_status      varchar(255)     not null,
    max_grade              integer          not null,
    proportion             double precision not null,
    teacher_id             integer          not null
);

--! create table assignment_grade_book

create table assignment_grade_book
(
    assignment_grade_book_id serial primary key,
    assignment_id            integer      not null,
    grade_book_id            integer      not null,
    grade                    integer      not null,
    grade_description        varchar(255) not null
);

--! create table material

create table material
(
    material_id          serial primary key,
    material_title       varchar(255) not null,
    material_description varchar(255) not null,
    course_id            integer      not null
);


--! create table attendance

create table attendance
(
    attendance_id serial primary key,
    max_grade     integer          not null,
    proportion    double precision not null
);

--! create table attendance_grade_book

create table attendance_grade_book
(
    attendance_grade_book_id serial primary key,
    grade_book_id            integer not null,
    attendance_id            integer not null,
    is_attended              bool    not null
);

--! create table post

create table post
(
    post_id   serial primary key,
    title     varchar(255) not null,
    content   text         not null,
    author    integer      not null,
    time      timestamp    not null,
    course_id integer      not null
);


--! create table reply

create table reply
(
    comment_id serial primary key,
    content    text      not null,
    author     integer   not null,
    time       timestamp not null,
    post_id    integer   not null
);




