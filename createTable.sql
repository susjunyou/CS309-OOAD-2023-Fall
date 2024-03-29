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
    email             varchar(255)          not null unique,
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
    email             varchar(255)       not null unique,
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
    team_name               varchar(255) not null,
    leader                  integer,
    teacher_id              integer,
    presentation_date       date,
    team_description        varchar(255),
    recruitment_information varchar(255),
    team_size               integer      not null,
    project_id              integer      not null,
    privacy_team             boolean default false not null
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
