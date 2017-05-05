DROP SEQUENCE IF EXISTS public.users_id_seq CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE users (
    id bigint NOT NULL,
    username character varying(256) NOT NULL,
    password character varying(256) NOT NULL,
    email character varying(256) NOT NULL,
    first_name character varying(60),
    last_name character varying(60),
    create_date timestamp without time zone DEFAULT now(),
	  avatar character varying(256),
    description character varying(1000),
    birthdate dateTime,
    phone character varying(15)
);



CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);

INSERT  INTO users VALUE
(1, "user", "pass", "dosug@test.ru", "power", "wolf", NULL, NULL, NULL, NULL, NULL);