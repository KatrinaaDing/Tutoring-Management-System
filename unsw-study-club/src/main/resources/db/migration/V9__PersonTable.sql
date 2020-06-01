-- change timestamp format
create or replace function toTimeStamp() returns trigger
as $$
begin
	NEW.start_date := TO_TIMESTAMP(NEW.start_date::TEXT, 'YYYY-MM-DD HH24:MI:SS');
	return NEW;
end;
$$ language 'plpgsql';