-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table package;
truncate table image;

SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

INSERT INTO package (tracking_no) VALUES (1);
INSERT INTO package (tracking_no) VALUES (2);
INSERT INTO package (tracking_no) VALUES (3);
INSERT INTO package (tracking_no) VALUES (4);
INSERT INTO package (tracking_no) VALUES (5);

INSERT INTO image (filename, type, package_id) VALUES ('파일 이름1', 'JPG', 1);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름2', 'JPG', 1);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름3', 'JPG', 1);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름4', 'JPG', 1);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름5', 'JPG', 1);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름1', 'JPG', 2);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름2', 'JPG', 2);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름3', 'JPG', 2);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름1', 'JPG', 3);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름2', 'JPG', 3);
INSERT INTO image (filename, type, package_id) VALUES ('파일 이름3', 'JPG', 3);

