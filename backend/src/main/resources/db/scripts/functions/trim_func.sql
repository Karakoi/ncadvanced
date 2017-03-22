DROP FUNCTION IF EXISTS trim_text(s text);

CREATE OR REPLACE FUNCTION trim_text(s text, max_count_of_chars integer) RETURNS text AS $emp_stamp$
  DECLARE result text;
BEGIN
  result = substring(s from 1 for max_count_of_chars);
  IF(length(s) > max_count_of_chars) THEN
    result = result || '...';
  END IF;
  RETURN result;
END;
$emp_stamp$ LANGUAGE plpgsql;

--select trim_text('qweqweqwe1qweqweqwe2qweqweqwe3', 20) long, trim_text('qwe', 20) short;
