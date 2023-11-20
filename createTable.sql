-- create database OOAD_project;

--! drop table

-- drop table if exists course_project cascade ;
-- drop table if exists project cascade ;
-- drop table if exists assignment cascade ;
-- drop table if exists team_project cascade ;
-- drop table if exists team cascade ;
-- drop table if exists assignment_grade_book cascade ;
-- drop table if exists project_grade_book cascade ;
-- drop table if exists attendance_grade_book cascade ;
-- drop table if exists attendance cascade ;
-- drop table if exists reply cascade ;
-- drop table if exists material cascade ;
-- drop table if exists post cascade ;
-- drop table if exists course_sa cascade ;
-- drop table if exists course_student cascade ;
-- drop table if exists course_teacher cascade ;
-- drop table if exists course cascade ;
-- drop table if exists admin cascade ;
-- drop table if exists teacher cascade ;
-- drop table if exists student cascade ;
-- drop table if exists grade_book cascade ;
-- drop table if exists team_student cascade ;


--! create table student

create table student
(
    student_id        serial primary key,
    id                integer               not null,
    name              varchar(255)          not null,
    account           varchar(255)          not null,
    password          varchar(255)          not null,
    email             varchar(255)          not null,
    self_Introduction text,
    phone_number      varchar(255)          not null,
    sex               bool                  not null,
    major             varchar(255)          not null,
    level             varchar(255)          not null,
    isSA              boolean default false not null,
    department        varchar(255)          not null
);


--! create table teacher

create table teacher
(
    teacher_id        serial primary key,
    id                integer      not null,
    name              varchar(255) not null,
    account           varchar(255) not null,
    password          varchar(255) not null,
    email             varchar(255) not null,
    self_Introduction text,
    phone_number      varchar(255) not null,
    sex               bool         not null,
    department        varchar(255) not null,
    tenure            varchar(255) not null
);

--! create table admin

create table admin
(
    admin_id     serial primary key,
    id           integer      not null,
    name         varchar(255) not null,
    account      varchar(255) not null,
    password     varchar(255) not null,
    email        varchar(255) not null,
    phone_number varchar(255) not null,
    sex          bool         not null
);

--! create table course

create table course
(
    course_id          serial primary key,
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
    project_id            serial primary key,
    project_title         varchar(255)     not null,
    project_description   varchar(255)     not null,
    teacher_id            integer          not null,
    course_id             integer          not null,
    assignment_start_time date             not null,
    project_status        varchar(255)     not null,
    project_start_date    date             not null,
    project_deadline      date             not null,
    max_people_in_team    integer          not null,
    max_grade             integer          not null,
    proportion            double precision not null
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
    team_id          serial primary key,
    team_name        varchar(255) not null,
    leader           integer      not null,
    team_description varchar(255) not null,
    team_size        integer      not null,
    project_id       integer      not null
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
    teacher_id             integer          not null,
    course_id              integer          not null
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
    material_name        varchar(255) not null,
    material_description varchar(255) not null,
    course_id            integer      not null
);


--! create table attendance

create table attendance
(
    attendance_id serial primary key,
    releaser      integer          not null,
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
    post_id      serial primary key,
    post_title   varchar(255) not null,
    post_content text         not null,
    post_author  integer      not null,
    time         date         not null,
    course_id    integer      not null
);


--! create table reply

create table reply
(
    comment_id serial primary key,
    reply_content    text      not null,
    reply_author     integer   not null,
    reply_time       timestamp not null,
    post_id    integer   not null
);

--! add foreign key to reply

alter table reply
    add foreign key (post_id) references post (post_id);


--! add foreign key to post

alter table post
    add foreign key (course_id) references course (course_id);


--! add foreign key to material

alter table material
    add foreign key (course_id) references course (course_id);


--! add foreign key to assignment_grade_book
alter table assignment_grade_book
    add foreign key (assignment_id) references assignment (assignment_id);
alter table assignment_grade_book
    add foreign key (grade_book_id) references grade_book (grade_book_id);


--! add foreign key to assignment

alter table assignment
    add foreign key (teacher_id) references teacher (teacher_id);
alter table assignment
    add foreign key (course_id) references course (course_id);


--! add foreign key to project_grade_book

alter table project_grade_book
    add foreign key (project_id) references project (project_id);
alter table project_grade_book
    add foreign key (grade_book_id) references grade_book (grade_book_id);


--! add foreign key to project

alter table project
    add foreign key (teacher_id) references teacher (teacher_id);
alter table project
    add foreign key (course_id) references course (course_id);


--! add foreign key to team_student

alter table team_student
    add foreign key (student_id) references student (student_id);
alter table team_student
    add foreign key (team_id) references team (team_id);


--! add foreign key to team

alter table team
    add foreign key (project_id) references project (project_id);


--! add foreign key to attendance_grade_book

alter table attendance_grade_book
    add foreign key (attendance_id) references attendance (attendance_id);
alter table attendance_grade_book
    add foreign key (grade_book_id) references grade_book (grade_book_id);


--! add foreign key to material

alter table material
    add foreign key (course_id) references course (course_id);


--! add foreign key to course_sa

alter table course_sa
    add foreign key (course_id) references course (course_id);
alter table course_sa
    add foreign key (student_id) references student (student_id);

--! add foreign key to course_teacher

alter table course_teacher
    add foreign key (course_id) references course (course_id);
alter table course_teacher
    add foreign key (teacher_id) references teacher (teacher_id);

--! add foreign key to course_student

alter table course_student
    add foreign key (course_id) references course (course_id);
alter table course_student
    add foreign key (student_id) references student (student_id);


--! add foreign key to grade_book

alter table grade_book
    add foreign key (student_id) references student (student_id);
alter table grade_book
    add foreign key (course_id) references course (course_id);

