-- create users and schema expected by application-dev.yml
-- Note: my_app_user is already created by PostgreSQL via POSTGRES_USER environment variable
-- Only create flyway_user if it doesn't exist
DO $$ BEGIN
    CREATE ROLE flyway_user LOGIN PASSWORD 'flyway';
EXCEPTION WHEN DUPLICATE_OBJECT THEN
    -- Role already exists, do nothing
END $$;

-- Grant connect on database to both users
GRANT CONNECT ON DATABASE music TO flyway_user, my_app_user;

-- Grant CREATE privilege on database so flyway_user can create schemas during migrations
GRANT CREATE ON DATABASE music TO flyway_user;

-- Create pomodoro schema owned by flyway_user (this runs as postgres, so it succeeds)
CREATE SCHEMA IF NOT EXISTS pomodoro AUTHORIZATION flyway_user;

-- Grant flyway_user full control to manage the schema and its objects
GRANT ALL ON SCHEMA pomodoro TO flyway_user;

-- Grant my_app_user the ability to use the schema and access objects
GRANT USAGE ON SCHEMA pomodoro TO my_app_user;

-- Grant my_app_user permissions on all current and future tables in pomodoro schema
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA pomodoro TO my_app_user;
ALTER DEFAULT PRIVILEGES FOR ROLE flyway_user IN SCHEMA pomodoro
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO my_app_user;

-- Grant sequence permissions for auto-increment IDs
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA pomodoro TO my_app_user;
ALTER DEFAULT PRIVILEGES FOR ROLE flyway_user IN SCHEMA pomodoro
    GRANT USAGE, SELECT ON SEQUENCES TO my_app_user;
