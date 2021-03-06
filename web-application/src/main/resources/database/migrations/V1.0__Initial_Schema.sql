-- Web Application Reference v1.0.0

-- Initialization

-- DROP DATABASE IF EXISTS "web-application-reference";
-- CREATE DATABASE "web-application-reference" WITH ENCODING 'UTF-8';

-- Extensions

-- UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA "public";

-- application_configurations

DROP TABLE IF EXISTS application_configurations CASCADE;

CREATE TABLE application_configurations
(
	id                 BIGINT                   NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,

	uid                UUID                     NOT NULL UNIQUE DEFAULT UUID_GENERATE_V4(),

	property           TEXT                     NOT NULL,
	value              TEXT                     NOT NULL,

	creation_timestamp TIMESTAMP WITH TIME ZONE NOT NULL        DEFAULT CLOCK_TIMESTAMP(),
	update_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL        DEFAULT CLOCK_TIMESTAMP(),

	version            INTEGER                  NOT NULL        DEFAULT 0
);

