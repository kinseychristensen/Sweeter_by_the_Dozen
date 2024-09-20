-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'sweeter_by_dozen';

DROP DATABASE sweeter_by_dozen;

DROP USER sweeter_dozen_owner;
DROP USER sweeter_dozen_appuser;
