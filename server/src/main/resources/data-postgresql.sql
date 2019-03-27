-- Inserting authorities
INSERT INTO AUTHORITY (AID, NAME) VALUES ('0', 'USER_AUTHORITY') ON CONFLICT DO NOTHING;
INSERT INTO AUTHORITY (AID, NAME) VALUES ('1', 'ADMIN_AUTHORITY') ON CONFLICT DO NOTHING;

-- Inserting base users
--  User: admin
--  Password: password
INSERT INTO USER_TABLE (USER_ID, ACC_ACTIVATED, ACC_EXPIRED, ACC_LOCKED, PASSWORD, NAME) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', 'true', 'false', 'false', '$2a$10$67OBfdBphsEdLr7hlD2XDOA1USyt5jCqJMmMUiByemWtRrbKObFfi', 'admin') ON CONFLICT DO NOTHING;
INSERT INTO USER_PROFILE (ID, USER_REF, POINTS) VALUES ('056573882efa45f5b3cfebcdb16d1cd4', '054597382efa45f5b3cfebcdb16d6cd4', '0') ON CONFLICT DO NOTHING;
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '0') ON CONFLICT DO NOTHING;
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('054597382efa45f5b3cfebcdb16d6cd4', '1') ON CONFLICT DO NOTHING;

--  User: gogreenuser
--  Password: password
INSERT INTO USER_TABLE (USER_ID, ACC_ACTIVATED, ACC_EXPIRED, ACC_LOCKED, PASSWORD, NAME) VALUES ('674597382efa45f5b3cfebcdb16d6cd4', 'true', 'false', 'false', '$2a$10$67OBfdBphsEdLr7hlD2XDOA1USyt5jCqJMmMUiByemWtRrbKObFfi', 'gogreenuser') ON CONFLICT DO NOTHING;
INSERT INTO USER_PROFILE (ID, USER_REF, POINTS) VALUES ('676573882efa45f5b3cfebcdb16d1cd4', '674597382efa45f5b3cfebcdb16d6cd4', '0') ON CONFLICT DO NOTHING;
INSERT INTO USER_AUTHORITIES (USER_ID, AUTHORITY_ID) VALUES ('674597382efa45f5b3cfebcdb16d6cd4', '0') ON CONFLICT DO NOTHING;


-- Inserting categories
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES ('0', 'Food', 'Food items'), ('1', 'Transport', 'Transportation'), ('2', 'Energy', 'Energy items'), ('3', 'Misc', 'Other items') ON CONFLICT DO NOTHING;

-- Inserting activities
-- Category: Food (0)
--   Activity: Eating vegetarian meal (0)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (0, 'Vegetarian meal', 'Eat a vegetarian meal', 0) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'COMPLETED_FOOD_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (0, 'ATE_VEGETARIAN_MEAL') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (0, 'Amount of meals', 'FLOAT', 0) ON CONFLICT DO NOTHING;

--   Activity: Buy local produce (3)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (3, 'Local produce', 'Buy local produce', 0) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (3, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (3, 'COMPLETED_FOOD_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (3, 'BOUGHT_LOCAL_PRODUCE') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (3, 'Kilograms of local produce bought', 'FLOAT', 3) ON CONFLICT DO NOTHING;

-- Category: Transport (1)
--   Activity: Use your bike instead of the bus (1)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (1, 'Biking', 'Use your bike instead of the bus', 1) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'COMPLETED_TRANSPORT_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (1, 'USED_BIKE_INSTEAD_BUS') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (1, 'Kilometers biked', 'FLOAT', 1) ON CONFLICT DO NOTHING;

--   Activity: Use public transport instead of your car (4)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (4, 'Public transport', 'Use public transport instead of your car', 1) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (4, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (4, 'COMPLETED_TRANSPORT_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (4, 'USED_PT_INSTEAD_OF_CAR') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (4, 'Kilometers travelled', 'FLOAT', 4) ON CONFLICT DO NOTHING;

-- Category: Energy (2)
--   Activity: Lower the temperature of your home (5)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (5, 'Lower temperature', 'Lower the temperature of your home', 2) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (5, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (5, 'COMPLETED_ENERGY_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (5, 'LOWERED_TEMP') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (5, 'Degrees Celsius dropped', 'FLOAT', 5) ON CONFLICT DO NOTHING;

--   Activity: Install solar panels (6)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (6, 'Solar panels', 'Install solar panels', 2) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (6, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (6, 'COMPLETED_ENERGY_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (6, 'INSTALLED_SOLAR_PANELS') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (6, 'Solar panels installed', 'FLOAT', 6) ON CONFLICT DO NOTHING;

-- Category: Misc (3)
--   Activity: Plant a tree in your garden (2)
INSERT INTO ACTIVITY (ID, NAME, DESCRIPTION, CATEGORY) VALUES (2, 'Tree planting', 'Plant a tree in your garden', 3) ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'COMPLETED_MISC_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS (ID, TRIGGER) VALUES (2, 'PLANTED_TREE') ON CONFLICT DO NOTHING;
INSERT INTO ACTIVITY_OPTION (ID, DESCRIPTION, INPUT_TYPE, ACTIVITY) VALUES (2, 'Trees planted', 'FLOAT', 2) ON CONFLICT DO NOTHING;

-- Inserting badges
INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (0, 'Completed activity', 'You completed an activity!', 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS_COMPLETE (ID, TRIGGER) VALUES (0, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;

INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (1, 'Completed food activity', 'You completed a food activity!', 'COMPLETED_FOOD_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS_COMPLETE (ID, TRIGGER) VALUES (1, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;

INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (2, 'Completed transport activity', 'You completed a transport activity!', 'COMPLETED_TRANSPORT_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS_COMPLETE (ID, TRIGGER) VALUES (2, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;

INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (3, 'Completed energy activity', 'You completed a energy activity!', 'COMPLETED_ENERGY_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS_COMPLETE (ID, TRIGGER) VALUES (3, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;

INSERT INTO BADGE (ID, NAME, MESSAGE, TRIGGER) VALUES (4, 'Completed miscellaneous activity', 'You completed a miscellaneous activity!', 'COMPLETED_MISC_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO TRIGGERS_COMPLETE (ID, TRIGGER) VALUES (4, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;

-- Inserting achievements
INSERT INTO ACHIEVEMENT (ID, NAME, MESSAGE, DESCRIPTION, REQUIRED_TRIGGERS, TRIGGER) VALUES (0, 'Make the world a better place', 'WIP', 'Complete 3 activities', 3, 'SUBMITTED_ACTIVITY') ON CONFLICT DO NOTHING;
INSERT INTO ACHIEVEMENT (ID, NAME, MESSAGE, DESCRIPTION, REQUIRED_TRIGGERS, TRIGGER) VALUES (1, 'Badge collector', 'WIP', 'Earn 5 badges', 5, 'ACHIEVED_BADGE') ON CONFLICT DO NOTHING;