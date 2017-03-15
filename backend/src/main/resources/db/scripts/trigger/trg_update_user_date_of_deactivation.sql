DROP TRIGGER IF EXISTS trg_update_user_date_of_deactivation ON "user";
CREATE OR REPLACE FUNCTION add_date_of_deactivation_for_user() RETURNS TRIGGER AS
$BODY$
BEGIN
    IF OLD.is_deactivated = false AND NEW.is_deactivated = true THEN
        NEW.date_of_deactivation := now();
    ELSIF OLD.is_deactivated = true AND NEW.is_deactivated = false THEN
        NEW.date_of_deactivation := NULL;
    END IF;
    RETURN NEW;
END;
$BODY$
LANGUAGE 'plpgsql';
CREATE TRIGGER trg_update_user_date_of_deactivation BEFORE UPDATE ON "user" FOR EACH ROW EXECUTE PROCEDURE add_date_of_deactivation_for_user();