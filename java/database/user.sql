-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER sweeter_dozen_owner
WITH PASSWORD 'password';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO sweeter_dozen_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO sweeter_dozen_owner;

CREATE USER sweeter_dozen_appuser
WITH PASSWORD 'password';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO sweeter_dozen_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO sweeter_dozen_appuser;
