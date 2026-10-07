-- Execute once while connected to the maintenance database "postgres".
-- PostgreSQL does not support CREATE DATABASE inside a transaction.
CREATE DATABASE cinema_db WITH ENCODING = 'UTF8' TEMPLATE = template0;
