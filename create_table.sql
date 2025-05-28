-- 1. Tạo database
CREATE DATABASE IF NOT EXISTS drldb;
USE drldb;

-- 2. Tạo bảng faculties
CREATE TABLE faculties (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Tạo bảng class
CREATE TABLE class_room (
	id INT AUTO_INCREMENT primary key,
    name varchar(50) NOT NULL,
    faculty_id int,
    foreign key (faculty_id) references faculties(id) ON DELETE SET NULL
);

-- tạo bảng department
CREATE TABLE departments (
	id INT auto_increment primary key,
    name varchar(250) not null,
    description text
);

-- 18. Tạo bảng emails
CREATE TABLE emails (
    email VARCHAR(50) PRIMARY KEY
);

-- 3. Tạo bảng users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    point_1 INT,
    point_2 INT,
    point_3 INT,
    point_4 INT,
    FOREIGN KEY (email) references emails(email) ON DELETE CASCADE,
    role ENUM('ADMIN', 'STAFF', 'STUDENT') NOT NULL,
    is_OAuth boolean default false,
    active BOOLEAN DEFAULT TRUE
);

-- 4. Tạo bảng admins
CREATE TABLE admins (
    id INT PRIMARY KEY,
    department_id int,
    foreign key (department_id) references departments(id)  on delete set null,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. Tạo bảng staff
CREATE TABLE staff (
    id INT PRIMARY KEY,
    faculty_id INT,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE SET NULL
);

-- 6. Tạo bảng students
CREATE TABLE students (
    id INT PRIMARY KEY,
    student_id VARCHAR(50) UNIQUE NOT NULL,
    class_room_id int,
    faculty_id INT,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE SET NULL,
    foreign key (class_room_id) references class_room(id) ON DELETE SET NULL
);

-- 7. Tạo bảng activities
CREATE TABLE activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATETIME,
    end_date DATETIME,
    location VARCHAR(255),
    organizer_id INT,
    faculty_id INT,
    max_participants INT,
    current_participants INT DEFAULT 0,
    point_type ENUM('POINT_1', 'POINT_2', 'POINT_3', 'POINT_4') NOT NULL,
    point_value INT,
    status ENUM('UPCOMING', 'ONGOING', 'COMPLETED', 'CANCELLED'),
    image VARCHAR(255),
	active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE SET NULL
);

-- Bảng phụ khi sinh viên đăng ký hoạt động
CREATE TABLE activity_registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_id INT,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    confirm boolean null,
    is_send_mail boolean null,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

-- 8. Tạo bảng training_points
CREATE TABLE training_points (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_id INT,
    point INT,
    date_awarded DATETIME,
    confirmed_by INT,
    status ENUM('PENDING', 'CONFIRMED', 'REJECTED'),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
    FOREIGN KEY (confirmed_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Tạo bảng lưu danh sách sinh viên báo thiếu
CREATE TABLE missing_reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_id INT,
    training_point_id INT,
    point INT,
    date_report DATETIME,
    image VARCHAR(255),
    status ENUM('PENDING','CONFIRMED','REJECTED'),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
    FOREIGN KEY (training_point_id) REFERENCES training_points(id) ON DELETE CASCADE
);

-- 9. Tạo bảng evidences
CREATE TABLE evidences (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_registration_id INT,
    training_point_id INT,
    file_path VARCHAR(255),
    upload_date DATETIME,
    verify_status ENUM('PENDING', 'APPROVED', 'REJECTED'),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_registration_id) REFERENCES activity_registrations(id) ON DELETE CASCADE,
    FOREIGN KEY (training_point_id) REFERENCES training_points(id) ON DELETE CASCADE
);

-- 10. Tạo bảng attendances
CREATE TABLE attendances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    user_id INT,
    status ENUM('PRESENT', 'ABSENT', 'LATE'),
    timestamp DATETIME,
    is_register boolean,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 14. Tạo bảng comments
CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_id INT,
    content TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

-- 15. Tạo bảng likes
CREATE TABLE likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
	id INT auto_increment primary key,
    user_id int,
    content text,
    is_read boolean,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    foreign key (user_id) references users(id) on delete cascade
);

