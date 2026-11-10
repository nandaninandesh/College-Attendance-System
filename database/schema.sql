create database college_attendance;
USE college_attendance;

CREATE TABLE classes (
    class_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    section VARCHAR(10) NOT NULL
);
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    roll_no VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    class_id INT,

    FOREIGN KEY (class_id)
    REFERENCES classes(class_id)
);
CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15)
);
CREATE TABLE subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    class_id INT NOT NULL,
    teacher_id INT,

    FOREIGN KEY (class_id)
    REFERENCES classes(class_id),

    FOREIGN KEY (teacher_id)
    REFERENCES teachers(teacher_id)
);
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
    student_id INT UNIQUE,
    teacher_id INT UNIQUE,

    FOREIGN KEY (student_id)
    REFERENCES students(student_id),

    FOREIGN KEY (teacher_id)
    REFERENCES teachers(teacher_id)
);
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT','ABSENT') NOT NULL,

    FOREIGN KEY (student_id)
    REFERENCES students(student_id),

    FOREIGN KEY (subject_id)
    REFERENCES subjects(subject_id),

    FOREIGN KEY (teacher_id)
    REFERENCES teachers(teacher_id),

    UNIQUE (student_id, subject_id, attendance_date)
);
