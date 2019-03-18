-- Inserting authorities
INSERT INTO AUTHORITY (AID, NAME) VALUES ('0', 'USER_AUTHORITY');
INSERT INTO AUTHORITY (AID, NAME) VALUES ('1', 'ADMIN_AUTHORITY');

-- Inserting base user
INSERT INTO USER_TABLE (USER_ID, ACC_ACTIVATED, ACC_EXPIRED, ACC_LOCKED, PASSWORD, NAME) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', 'true', 'false', 'false', '$2a$10$67OBfdBphsEdLr7hlD2XDOA1USyt5jCqJMmMUiByemWtRrbKObFfi', 'admin');
INSERT INTO USER_PROFILE (PROFILE_ID, USER_ID, POINTS) VALUES ('056573882efa45f5b3cfebcdb16d1cd4', '054597382efa45f5b3cfebcdb16d6cd4', '0');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '0');
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '1');

-- Inserting categories
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES ('0', 'Food', 'Food items'), ('1', 'Transport', 'Transportation'), ('2', 'Energy', 'Energy items'), ('3', 'Misc', 'Other items');

-- Inserting activities
-- Category: Food (0)
--   Activity: Eating vegetarian meal
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (0, 'Vegetarian meal', 'Eat a vegetarian meal', 0);
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'SUBMITTED_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'COMPLETED_FOOD_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'ATE_VEGETARIAN_MEAL');
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (0, 'Amount of meals', 'FLOAT', 0);

-- Category: Transport (1)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (1, 'Biking', 'Use your bike instead of the bus', 1);
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'SUBMITTED_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'COMPLETED_TRANSPORT_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'USED_BIKE_INSTEAD_BUS');
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (1, 'Kilometers biked', 'FLOAT', 1);

-- Category: Energy (2)

-- Category: Misc (3)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (2, 'Tree planting', 'Plant a tree in your garder', 3);
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'SUBMITTED_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'COMPLETED_MISC_ACTIVITY');
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'PLANTED_TREE');
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (2, 'Trees planted', 'FLOAT', 2);

--INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (1, 'Eating a veg meal', 'description', '1'), (2, 'Using bike instead of car', 'description', '2'), (3, 'Reducing heating', 'description', '3'), (4, 'Planting a garden', 'description',  '4');

-- Inserting badges
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (0, 'Completed activity', 'You completed an activity!', 'SUBMITTED_ACTIVITY');
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (1, 'Completed food activity', 'You completed a food activity!', 'COMPLETED_FOOD_ACTIVITY');
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (2, 'Completed transport activity', 'You completed a food activity!', 'COMPLETED_TRANSPORT_ACTIVITY');
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (3, 'Completed energy activity', 'You completed a food activity!', 'COMPLETED_ENERGY_ACTIVITY');
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (4, 'Completed miscellaneous activity', 'You completed a miscellaneous activity!', 'COMPLETED_MISC_ACTIVITY');