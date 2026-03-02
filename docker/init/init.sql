-- Grant connect on database
GRANT CONNECT ON DATABASE pomodoro TO my_app_user;

-- Create music schema owned by flyway_user (this runs as postgres, so it succeeds)
CREATE SCHEMA IF NOT EXISTS music AUTHORIZATION my_app_user;

-- Grant my_app_user the ability to use the schema and access objects
GRANT USAGE ON SCHEMA music TO my_app_user;

GRANT ALL ON ALL TABLES IN SCHEMA music TO my_app_user;

