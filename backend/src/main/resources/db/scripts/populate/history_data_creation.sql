
-- Using next script, you can update record to its original state.

/*
UPDATE request
SET title = 'sapien in sapien',
  description = 'suspendisse ornare consequat lectus in est risus auctor',
  priority_status_id = 1,
  progress_status_id = 5,
  reporter_id = 101,
  assignee_id = null,
  estimate_time_in_days = 2,
  date_of_creation = '2017-02-08 21:04:00',
  last_changer_id = 13
WHERE id = 113;
*/

-- Using next scripts, you can update record.

UPDATE request
SET title = 'New changed title',
  description = 'Very-very-very-very-very-very looooooooooooooooooooooooong description, ' ||
                'very-very-very-very-very-very looooooooooooooooooooooooong description.',
  priority_status_id = 2,
  last_changer_id = 13
WHERE id = 113;

UPDATE request
SET progress_status_id = 6,
  assignee_id = 35,
  estimate_time_in_days = 3,
  last_changer_id = 25,
  parent_id = 114
WHERE id = 113;