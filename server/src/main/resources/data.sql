INSERT INTO USER_TABLE (UID, ACC_ACTIVATED, ACC_EXPIRED, ACC_LOCKED, PASSWORD, NAME) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', 'true', 'false', 'false', '$2a$10$67OBfdBphsEdLr7hlD2XDOA1USyt5jCqJMmMUiByemWtRrbKObFfi', 'admin');
INSERT INTO AUTHORITY (AID, NAME) VALUES ('0', 'USER_AUTHORITY');
INSERT INTO AUTHORITY (AID, NAME) VALUES ('1', 'ADMIN_AUTHORITY');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '0');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '1');
