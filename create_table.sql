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
    active BOOLEAN DEFAULT TRUE
);

-- 4. Tạo bảng admins
CREATE TABLE admins (
    id INT PRIMARY KEY,
    department VARCHAR(255),
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
    staff_id INT,
    status ENUM('PRESENT', 'ABSENT', 'LATE'),
    timestamp DATETIME,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES staff(id) ON DELETE CASCADE
);

-- 11. Tạo bảng reports
CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    generated_at DATETIME
);

-- 12. Tạo bảng report_details
CREATE TABLE report_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT,
    training_point_id INT,
    FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE,
    FOREIGN KEY (training_point_id) REFERENCES training_points(id) ON DELETE CASCADE
);

-- 13. Tạo bảng news_feed
CREATE TABLE news_feed (
    id INT AUTO_INCREMENT PRIMARY KEY
);

-- 14. Tạo bảng comments
CREATE TABLE comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    activity_id INT,
    content TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

-- 15. Tạo bảng likes
CREATE TABLE likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    activity_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

-- 16. Tạo bảng chat_service
CREATE TABLE chat_service (
    id INT AUTO_INCREMENT PRIMARY KEY
);

-- 17. Tạo bảng messages
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chat_service_id INT,
    sender_id INT,
    content TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chat_service_id) REFERENCES chat_service(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE
);
