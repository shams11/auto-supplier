-- Roles
INSERT INTO ROLE (ID, UNIQUE_NAME, DESCRIPTION, VERSION, CREATED_BY_USER, MODIFIED_BY_USER, TIME_CREATED, TIME_UPDATED) VALUES ('F1BD130A6F2F11E8ADC0FA7AE01BBEBC', 'ADMIN', 'aministrator', 0, NULL, NULL, SYSDATE, SYSDATE);

-- admin users
INSERT INTO USER (id, username, first_name, last_name, email, active, created_by_user, modified_by_user, time_created, time_updated, version)
VALUES ('77ECEB74CDE74B1AA04047B20E6FED77','shams', 'shams', 'tabrez', 'shams.tabrez11@gmail.com', 1, NULL, NULL, SYSDATE, SYSDATE, 0);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('77ECEB74CDE74B1AA04047B20E6FED77', 'F1BD130A6F2F11E8ADC0FA7AE01BBEBC');

INSERT INTO USER (id, username, first_name, last_name, email, active, created_by_user, modified_by_user, time_created, time_updated, version)
VALUES ('FBB4280F964945019DE711CD72C42DC2','afreen', 'afreen', 'inamdar', 'afreeninamdar20@gmail.com', 1, NULL, NULL, SYSDATE, SYSDATE, 0);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES ('FBB4280F964945019DE711CD72C42DC2', 'F1BD130A6F2F11E8ADC0FA7AE01BBEBC');
