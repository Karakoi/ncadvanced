DELETE FROM request
WHERE id = 999;

DELETE FROM request
WHERE id = 998;

DELETE FROM request
WHERE id = 997;

INSERT INTO request (id, title, description, parent_id, reporter_id, date_of_creation, last_changer_id)
VALUES (998, 'test', 'test', NULL , 34, '2015-02-07 13:48:30', 13);

INSERT INTO request (id, title, description, parent_id, reporter_id, date_of_creation, last_changer_id)
VALUES (997, 'test', 'test', NULL , 34, '2015-02-08 13:48:30', 13);

INSERT INTO request (id, title, description, priority_status_id, progress_status_id, reporter_id, assignee_id,
                     estimate_time_in_days, date_of_creation, last_changer_id, parent_id)
VALUES (999, 'title 1', 'desc 1', 1, 5, 101, 34 , 2, '2017-02-09 21:04:00', 13, 997);

UPDATE request
SET title = 'title 2',
  description = 'desc 2',
  priority_status_id = 2,
  progress_status_id = 9,
  assignee_id = 35,
  estimate_time_in_days = 3,
  parent_id = 998
WHERE id = 999;