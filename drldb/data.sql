use drldb;
INSERT INTO faculties (id, name) VALUES
('1', 'Khoa Công Nghệ Thông Tin'),
('2', 'Khoa Y Dược'),
('3', 'Khoa Kinh Tế');

INSERT INTO users (id, email, password, first_name, last_name, avatar, point_1, point_2, point_3, point_4, role) VALUES
('1', 'admin@gmail.com', '$2a$10$k2KLa0DUlgUE65aPWoGnru/VMgClsU3n0QArq7JjPu8Lpj6Kppfqy', 'Admin', 'Pro', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743651318/n0i4pxhiwbmqztwrdyjs.jpg', '0', '0', '0', '0', 'ADMIN'),
('2', 'staff1@gmail.com', '$2a$10$k2KLa0DUlgUE65aPWoGnru/VMgClsU3n0QArq7JjPu8Lpj6Kppfqy', 'Staff', '1', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743651318/n0i4pxhiwbmqztwrdyjs.jpg', '0', '0', '0', '0', 'STAFF'),
('3', 'staff2@gmail.com', '$2a$10$k2KLa0DUlgUE65aPWoGnru/VMgClsU3n0QArq7JjPu8Lpj6Kppfqy', 'Staff', '2', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743651318/n0i4pxhiwbmqztwrdyjs.jpg', '0', '0', '0', '0', 'STAFF');

INSERT INTO admins (id, department) VALUES
('1', 'Quản lý đào tạo');

INSERT INTO staff (id, faculty_id) VALUES
('2', '2'),
('3', '3');

INSERT INTO activities (id, name, description, start_date, end_date, location, organizer_id, faculty_id, max_participants, current_participants, point_type, point_value, status, image, active) 
VALUES 
(1, 'Workshop Lập Trình', 'Một buổi workshop để nâng cao kỹ năng lập trình.', '2025-05-01 09:00:00', '2025-05-01 17:00:00', 'Phòng 101, Tòa nhà ABC', 1, 1, 50, 25, 'POINT_1', 10, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg', 1),
(2, 'Ngày Thể Thao', 'Một ngày thi đấu các môn thể thao.', '2025-06-10 08:00:00', '2025-06-10 18:00:00', 'Sân Thể Thao, Trường ABC', 2, 2, 100, 60, 'POINT_3', 20, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1),
(3, 'Chuyến Du Lịch Xây Dựng Đội Nhóm', 'Chuyến đi xây dựng tinh thần đội nhóm cho cán bộ và sinh viên.', '2025-07-15 10:00:00', '2025-07-17 15:00:00', 'Khu nghỉ dưỡng núi', 3, 3, 30, 15, 'POINT_2', 30, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg', 1),
(4, 'Hackathon', 'Cuộc thi lập trình để tìm kiếm giải pháp sáng tạo.', '2025-08-01 09:00:00', '2025-08-01 17:00:00', 'Phòng 202, Tòa nhà DEF', 2, 1, 60, 30, 'POINT_2', 15, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg', 1),
(5, 'Hội Thảo Sức Khỏe và Phúc Lợi', 'Hội thảo về duy trì lối sống lành mạnh.', '2025-09-05 10:00:00', '2025-09-05 12:00:00', 'Phòng 305, Tòa nhà GHI', 3, 2, 80, 50, 'POINT_1', 10, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1),
(6, 'Hội Nghị Khoa Học', 'Hội nghị chia sẻ các nghiên cứu khoa học mới.', '2025-10-10 08:00:00', '2025-10-10 17:00:00', 'Phòng Hội nghị, Tòa nhà XYZ', 1, 1, 100, 70, 'POINT_2', 25, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1),
(7, 'Ngày Tình Nguyện', 'Hoạt động tình nguyện hỗ trợ cộng đồng.', '2025-11-01 08:00:00', '2025-11-01 18:00:00', 'Trung tâm cộng đồng, Thành phố ABC', 1, 3, 200, 150, 'POINT_4', 20, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1),
(8, 'Lễ Hội Văn Hóa', 'Chương trình giao lưu văn hóa giữa các quốc gia.', '2025-12-05 09:00:00', '2025-12-05 16:00:00', 'Sảnh Tòa nhà ABC', 2, 2, 150, 100,'POINT_4', 30, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1),
(9, 'Chạy Marathon', 'Cuộc thi chạy marathon với sự tham gia của sinh viên và giảng viên.', '2026-01-15 06:00:00', '2026-01-15 12:00:00', 'Sân vận động ABC', 3, 1, 500, 350, 'POINT_3', 40, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644936/cld-sample-2.jpg', 1),
(10, 'Tập huấn Quản Lý Thời Gian', 'Khóa học giúp cải thiện kỹ năng quản lý thời gian cho sinh viên.', '2026-02-20 09:00:00', '2026-02-20 17:00:00', 'Phòng 404, Tòa nhà JKL', 1, 3, 60, 45, 'POINT_4', 15, 'UPCOMING', 'https://res.cloudinary.com/druxxfmia/image/upload/v1743644935/samples/coffee.jpg', 1);
