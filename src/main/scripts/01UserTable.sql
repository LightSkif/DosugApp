DROP SEQUENCE IF EXISTS public.users_id_seq CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE users (
  id          BIGINT                 NOT NULL,
  username    CHARACTER VARYING(256) NOT NULL,
  password    CHARACTER VARYING(256) NOT NULL,
  email       CHARACTER VARYING(256) NOT NULL,
  first_name  CHARACTER VARYING(128),
  last_name   CHARACTER VARYING(128),
  create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  avatar      CHARACTER VARYING(256),
  description CHARACTER VARYING(1000),
  birthdate   DATE,
  phone       CHARACTER VARYING(15),
  is_banned   BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);



INSERT  INTO users VALUES
(1, 'user', 'pass', 'test@test.ru', 'power', 'wolf', NULL, NULL, NULL, NULL, NULL),
(2, 'admin', 'admin', 'dosug@test.ru', 'sanctus', 'lupus', NULL, NULL, NULL, NULL, NULL);
