# proposal of Project B(Project Helper)

## 1. Project Title: Project Helper

## 2. Executive Abstract		

"Project Helper" is a digital platform tailored for students and educators, aiming to streamline the processes of team formation, grade inquiry and course management. In today's educational landscape, efficient teamwork and information management are paramount. Our platform offers students a centralized hub where they can effortlessly **find teammates, update personal skills, and view grades.** Concurrently, educators and administrators can efficiently **post project notifications,  release assignment, manage groups, and upload grades.** By integrating modern technologies, "Project Helper" seeks to deliver an intuitive, efficient, and user-friendly interface, thereby enhancing the educational experience.

## 3. Team

Our team has five members, they are:

- Junyou Su(苏军又) responsible for back-end
- Pengru Chen(陈鹏如) responsible for back-end
- Zhaoshan Zhang(张兆杉) responsible for front-end
- Bangjing jin(金邦景) responsible for front-end
- Jingchen zhong(钟靖宸) responsible for front-end

## 4. Description

### 4.1 Motivation

#### 4.1.1 What is the problem?

- Project management and forming group is inefficient (has no platform can do this, students need from a group in social media such as WeChat or QQ)
- Students can't ask to teachers and SAs effectively, often need to use Tencent file to question and answer(Q&A).
- Teachers cannot easily arrange for presentations, also need to use Tencent file to do this, which seems to be informal.
- Students need to go through a cumbersome official campus leave system, which can be quite inconvenient when they have urgent matters or fall ill and cannot promptly inform their teachers about their leave. At the same time, it also facilitates teachers in keeping track of attendance scores.
- The Grade Book is confused, cannot understand easily.

#### 4.1.2 What is our vision for solving the problem?

- Unified Platform: We envision a comprehensive digital ecosystem that not only streamlines team formation, project management, and grade inquiries but also fosters a sense of community within the educational institution. By centralizing these functionalities, we aim to reduce the fragmentation of information and tools that students and educators currently face.
- Enhanced Communication: In today's fast-paced study environment, timely and clear communication is paramount. Our platform will serve as a conduit for seamless interactions between students, educators, and administrators. By integrating advanced communication tools and collaborative features, we aim to break down the barriers that often hinder effective communication in educational settings.
- Empowering Users: Beyond mere functionality, our vision is to empower every user. For students, this means having easy access to resources, grades, and team members. For educators, it's about having a powerful tool-set at their fingertips to manage courses, projects, and student progress efficiently.

#### 4.1.3 What are our solutions?

- Tailored Digital Platform: At the heart of our solution is "Project Helper", a platform meticulously designed with the end-user in mind. Recognizing the diverse needs of students and educators, we've incorporated features that cater specifically to their unique requirements. From profile management, skill showcasing for team formation, to project submission and feedback mechanisms, every feature is crafted to enhance the user experience. 
- Intuitive User Interface (UI): A platform's functionality is only as good as its usability. With that in mind, we'll employ a clean, intuitive UI design, ensuring that even the most non-tech-savvy users can navigate and utilize the platform with ease.

 By combining our vision with these concrete solutions and the right technology stack, we aim to revolutionize the way educational institutions manage projects, teams, and communication.

### 4.2 Feature Description

#### 4.2.1 User "stories"

- As a student, I want to view and update my profile to showcase my skills and find potential teammates.

- As an educator, I wish to post project notifications, manage groups, and upload grades efficiently.


#### 4.2.2 UML Use Cases

<img src="C:\Users\74035\Documents\WeChat Files\wxid_hti7z0dbpjyn22\FileStorage\Temp\3783da29fc87566e0a8e260271108d7.png" alt="3783da29fc87566e0a8e260271108d7" style="zoom: 40%;" />

#### 4.2.3 Mockup

<img src="C:\Users\74035\Documents\WeChat Files\wxid_hti7z0dbpjyn22\FileStorage\Temp\3716646c4207d786f126dadd702e26c.png" alt="3716646c4207d786f126dadd702e26c" style="zoom:47%;" />

### 4.3 Requirements

- User registration and authentication.

- Profile management for students.

- Group creation, modification, and deletion by educators.

- Real-time notifications for project updates.

- Grade uploading and inquiry system.

- Integrated chat system for real-time communication between teachers and students (serve as efficient QA).

- Arrangement for project present


### 4.4 Design Document

#### 4.4.1 Architecture

<img src="C:\Users\74035\Documents\WeChat Files\wxid_hti7z0dbpjyn22\FileStorage\Temp\7e5f1ce4efb644140c5bd284e7a2a0d.jpg" alt="7e5f1ce4efb644140c5bd284e7a2a0d" style="zoom: 20%;" />

#### 4.4.2 TimeLine

<img src="C:\Users\74035\Documents\WeChat Files\wxid_hti7z0dbpjyn22\FileStorage\Temp\4757e01b90950314c981d9411b6b049.png" alt="4757e01b90950314c981d9411b6b049" style="zoom:67%;" />

#### 4.4.3 APIs, Services

- Git: Utilize Git for version control and collaborative development.
- eolinker: Assist in automatic testing and validation of API functions to ensure API stability and reliability.
- element-ui: the UI framework provides Vue with a rich component library that makes interface development easier.
- Navicat: database management tools, can be more convenient to manage the database.


### 4.5 Feasibility

#### **Potential Risks**:

- Lack of familiarity with APIs: Team members might need training or resources.

- Cost: Potential expenses for hosting or third-party services.

- Feature Overload: Prioritizing essential features to avoid overwhelming users.

