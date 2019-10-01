INSERT INTO authentication_types (id, name) VALUES (7, 'JSON Web Token');

INSERT INTO authentications (customer_id, user_id, type_id, identity, password, expiration_timestamp, status_id)
VALUES (1, 1, 6, '622e3fb2-16e5-485a-8061-98977ca7914e', '', '2020-12-31 18:30:00.000000', 1);

