DELETE FROM request
WHERE id = 999;

INSERT INTO request (id, title, description, parent_id,reporter_id,date_of_creation, last_changer_id)
VALUES (999, 'title 1', 'desc 1', 113,34,'2015-02-07 13:48:30', 13);

DROP TRIGGER IF EXISTS tr__history_adding_for_request ON request;

DROP FUNCTION IF EXISTS add_history_record_for_request();

CREATE FUNCTION add_history_record_for_request() RETURNS TRIGGER AS $emp_stamp$
BEGIN

  IF (OLD.title <> NEW.title)
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

  IF (OLD.description <> NEW.description)
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

  RETURN OLD;
END;
$emp_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER tr__history_adding_for_request
AFTER UPDATE ON request FOR EACH ROW
EXECUTE PROCEDURE add_history_record_for_request();

UPDATE request
SET title = 'title 2', description = 'desc 2'
WHERE id = 999;