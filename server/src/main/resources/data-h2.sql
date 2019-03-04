INSERT INTO USER_TABLE (USER_ID, ACC_ACTIVATED, ACC_EXPIRED, ACC_LOCKED, PASSWORD, NAME) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', 'true', 'false', 'false', '$2a$10$67OBfdBphsEdLr7hlD2XDOA1USyt5jCqJMmMUiByemWtRrbKObFfi', 'admin');
INSERT INTO USER_PROFILE (PROFILE_ID, USER_ID) VALUES ('056573882efa45f5b3cfebcdb16d1cd4', '054597382efa45f5b3cfebcdb16d6cd4');
INSERT INTO AUTHORITY (AID, NAME) VALUES ('0', 'USER_AUTHORITY');
INSERT INTO AUTHORITY (AID, NAME) VALUES ('1', 'ADMIN_AUTHORITY');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '0');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '1');
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES ('1', 'Food', 'Food items'),('2', 'Transport', 'Transportation'),('3', 'Energy', 'Energy items'),('4', 'Misc', 'Other items');
INSERT INTO ACTIVITY (ID,NAME,DESCRIPTION, CATEGORY) VALUES (1,'Eating a veg meal', 'desc', '1'),(2,'Using bike instead of car','desc', '2'),(3,'Reducing heating','desc', '3'),(4,'Planting a garden','desc',  '4');