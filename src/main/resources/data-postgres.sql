INSERT INTO camp_group(id, name) VALUES (1, 'prva grupa');
INSERT INTO camp_group(id, name) VALUES (2, 'druga grupa');
INSERT INTO camp_group(id, name) VALUES (3, 'treca grupa');

INSERT INTO person (nickname, accepted, surname, name, password, email)
VALUES  ('pcvitanovic', true, 'cvitnaovic', 'petar', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('ccvile', true, 'cvile', 'cic', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('dcipela', true, 'cipela', 'doktor', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('pcicula', true, 'cicula', 'p', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('lmercep', true, 'mercep', 'luka', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('tlovren', true, 'lovren', 'tomi', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('jstrac', true, 'strac', 'jakov', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('mjilicic', true, 'jilic', 'mura', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('kmango', true, 'mango', 'kniwi', '21232f297a57a5a743894a0e4a801fc3',  'mail'),
        ('otompa', true, 'tompa', 'opet', '21232f297a57a5a743894a0e4a801fc3',  'mail');

INSERT INTO animator(nickname) VALUES ('pcvitanovic'),
                                      ('ccvile'),
                                      ('dcipela');

INSERT INTO participant(contact, nickname) VALUES ('0994421421', 'pcicula'),
                                                  ('0994421421', 'lmercep'),
                                                  ('0994421421', 'tlovren'),
                                                  ('0994421421', 'jstrac'),
                                                  ('0994421421', 'mjilicic'),
                                                  ('0994421421', 'kmango'),
                                                  ('0994421421', 'otompa');

INSERT INTO activity(name, type, activity_description, duration_in_minutes) VALUES ('Odbojka', 1, 'spana se odbojka', 60),
                                                                                   ('Nogomet', 2, 'spana se fuca', 60),
                                                                                   ('Kosarka', 3, 'spana se sica', 60);

INSERT INTO activity_in_time(name, activity_id) VALUES ('Odbojka', 1);
INSERT INTO activity_in_time(name, activity_id) VALUES ('Kosarka', 3);
INSERT INTO activity_in_time(name, activity_id) VALUES ('Nogomet', 2);

INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (1, 1);
INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (1, 2);
INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (2, 2);
INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (2, 3);
INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (3, 1);
INSERT INTO activity_group(group_id, activity_in_time_id) VALUES (3, 3);

INSERT INTO activity_animator(animator_id, activity_in_time_id) VALUES ('pcvitanovic', 1);
INSERT INTO activity_animator(animator_id, activity_in_time_id) VALUES ('pcvitanovic', 2);
INSERT INTO activity_animator(animator_id, activity_in_time_id) VALUES ('ccvile', 3);
INSERT INTO activity_animator(animator_id, activity_in_time_id) VALUES ('ccvile', 1);
INSERT INTO activity_animator(animator_id, activity_in_time_id) VALUES ('dcipela', 3);