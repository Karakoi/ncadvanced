DROP TRIGGER IF EXISTS tr__history_adding_for_request ON request;

DROP FUNCTION IF EXISTS add_history_record_for_request();

CREATE FUNCTION add_history_record_for_request() RETURNS TRIGGER AS $emp_stamp$
BEGIN

  IF ((OLD.title <> NEW.title)
      OR (OLD.title IS NULL AND NEW.title IS NOT NULL)
      OR (OLD.title IS NOT NULL AND NEW.title IS NULL))
    then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
    VALUES ('title',
            OLD.title,
            NEW.title,
            localtimestamp,
              (SELECT last_changer_id
               FROM request r
               WHERE r.id = OLD.id),
            OLD.id);
  END IF;

  IF ((OLD.description <> NEW.description)
      OR (OLD.description IS NULL AND NEW.description IS NOT NULL)
      OR (OLD.description IS NOT NULL AND NEW.description IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('description',
          OLD.description,
          NEW.description,
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  IF ((OLD.priority_status_id <> NEW.priority_status_id)
      OR (OLD.priority_status_id IS NULL AND NEW.priority_status_id IS NOT NULL)
      OR (OLD.priority_status_id IS NOT NULL AND NEW.priority_status_id IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('priority_status_id',
          OLD.priority_status_id,
          /*(SELECT name
           FROM priority_status s
           WHERE s.id = OLD.id),*/
          NEW.priority_status_id,
          /*(SELECT name
           FROM priority_status s
           WHERE s.id = OLD.id),*/
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  IF ((OLD.progress_status_id <> NEW.progress_status_id)
      OR (OLD.progress_status_id IS NULL AND NEW.progress_status_id IS NOT NULL)
      OR (OLD.progress_status_id IS NOT NULL AND NEW.progress_status_id IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('progress_status_id',
          OLD.progress_status_id,
          NEW.progress_status_id,
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  IF ((OLD.assignee_id <> NEW.assignee_id)
      OR (OLD.assignee_id IS NULL AND NEW.assignee_id IS NOT NULL)
      OR (OLD.assignee_id IS NOT NULL AND NEW.assignee_id IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('assignee_id',
          OLD.assignee_id,
          NEW.assignee_id,
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  IF ((OLD.estimate_time_in_days <> NEW.estimate_time_in_days)
      OR (OLD.estimate_time_in_days IS NULL AND NEW.estimate_time_in_days IS NOT NULL)
      OR (OLD.estimate_time_in_days IS NOT NULL AND NEW.estimate_time_in_days IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('estimate_time_in_days',
          OLD.estimate_time_in_days,
          NEW.estimate_time_in_days,
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  IF ((OLD.parent_id <> NEW.parent_id)
      OR (OLD.parent_id IS NULL AND NEW.parent_id IS NOT NULL)
      OR (OLD.parent_id IS NOT NULL AND NEW.parent_id IS NULL))
  then INSERT INTO history (column_name, old_value, new_value, date_of_change, changer_id, record_id)
  VALUES ('parent_id',
          OLD.parent_id,
          NEW.parent_id,
          localtimestamp,
          (SELECT last_changer_id
           FROM request r
           WHERE r.id = OLD.id),
          OLD.id);
  END IF;

  RETURN OLD;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER tr__history_adding_for_request
AFTER UPDATE ON request FOR EACH ROW
EXECUTE PROCEDURE add_history_record_for_request();