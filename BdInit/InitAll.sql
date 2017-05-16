SET search_path = public, pg_catalog;

DROP SEQUENCE IF EXISTS public.users_id_seq CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

DROP TABLE IF EXISTS public.user_tag CASCADE;
DROP TABLE IF EXISTS public.user_role CASCADE;

DROP SEQUENCE IF EXISTS public.tags_id_seq CASCADE;
DROP TABLE IF EXISTS public.tags CASCADE;

DROP SEQUENCE IF EXISTS public.roles_id_seq CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;

DROP SEQUENCE IF EXISTS public.images_id_seq CASCADE;
DROP TABLE IF EXISTS public.images CASCADE;

DROP SEQUENCE IF EXISTS public.events_id_seq CASCADE;
DROP TABLE IF EXISTS public.events CASCADE;

DROP TABLE IF EXISTS public.event_tag CASCADE;
DROP TABLE IF EXISTS public.event_participant CASCADE;

DROP SEQUENCE IF EXISTS public.auth_tokens_auth_token_seq CASCADE;
DROP TABLE IF EXISTS public.auth_tokens CASCADE;

DROP SEQUENCE IF EXISTS public.bans_id_seq CASCADE;
DROP TABLE IF EXISTS public.bans CASCADE;

DROP EXTENSION IF EXISTS plpgsql CASCADE;
DROP SCHEMA IF EXISTS public CASCADE;



CREATE SCHEMA public;
CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


CREATE TABLE auth_tokens (
    auth_token character varying(256) NOT NULL,
    user_id bigint NOT NULL,
    create_time timestamp without time zone NOT NULL,
    expiring_time time without time zone
);

CREATE SEQUENCE auth_tokens_auth_token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE auth_tokens_auth_token_seq OWNED BY auth_tokens.auth_token;



CREATE TABLE roles (
    id integer NOT NULL,
    role character varying(20) NOT NULL
);


CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE roles_id_seq OWNED BY roles.id;



CREATE TABLE user_role (
    role_id integer NOT NULL,
    user_id integer NOT NULL
);



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
    birthdate date,
    phone character varying(15)
);



CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;

CREATE TABLE bans (
  id bigint,
  userId bigint,
  duration timestamp NOT NULL
);

CREATE SEQUENCE bans_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY bans
    ADD CONSTRAINT bans_pkey PRIMARY KEY (id);

ALTER SEQUENCE bans_id_seq OWNED BY bans.id;

ALTER TABLE ONLY bans ALTER COLUMN id SET DEFAULT nextval('bans_id_seq'::regclass);

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

SELECT pg_catalog.setval('auth_tokens_auth_token_seq', 1, false);

SELECT pg_catalog.setval('roles_id_seq', 2, true);

SELECT pg_catalog.setval('users_id_seq', 1, true);


ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT auth_tokens_pkey PRIMARY KEY (auth_token);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_id_pk PRIMARY KEY (id);

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_role_id_user_id_pk PRIMARY KEY (role_id, user_id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


CREATE UNIQUE INDEX roles_id_uindex ON roles USING btree (id);

CREATE UNIQUE INDEX roles_role_uindex ON roles USING btree (role);

CREATE INDEX user_role_user_id_index ON user_role USING btree (user_id);

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT auth_tokens_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT fkkhs4tpy3l5krnk87ykkmafeic FOREIGN KEY (user_id) REFERENCES users(id);



ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
	
	
	

	
CREATE TABLE events (
  id              bigint PRIMARY KEY,
  creator_id      bigint                 NOT NULL,
  event_name      character varying(256) NOT NULL,
  content         text,
  event_date_time TIMESTAMP              NOT NULL,
  placeName       character varying(256),
  longitude       double precision,
  latitude        double precision,
  avatar          character varying(256),
  allowed         boolean,
  create_date     timestamp without time zone DEFAULT now(),
	
	CONSTRAINT events_creator_id_fk FOREIGN KEY (creator_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON DELETE CASCADE
);

CREATE SEQUENCE events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE tags (
  id       bigint PRIMARY KEY,
  tag_name CHARACTER VARYING(256) NOT NULL
);

CREATE SEQUENCE tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


	
CREATE TABLE images (
    id bigint PRIMARY KEY,
	image_source character varying(256),
	event_id bigint,
	
	CONSTRAINT images_event_id_fk FOREIGN KEY (event_id)
      REFERENCES public.events (id) MATCH SIMPLE
      ON DELETE CASCADE
);

CREATE SEQUENCE images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
	

ALTER SEQUENCE events_id_seq OWNED BY events.id;
	
ALTER TABLE ONLY events ALTER COLUMN id SET DEFAULT nextval('events_id_seq'::regclass);

CREATE UNIQUE INDEX event_unique_parametr
  ON public.events (event_name, creator_id, event_date_time);

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;
	
ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);

CREATE UNIQUE INDEX tag_uindex
  ON tags USING BTREE (tag_name);

ALTER SEQUENCE images_id_seq OWNED BY images.id;
	
ALTER TABLE ONLY images ALTER COLUMN id SET DEFAULT nextval('images_id_seq'::regclass);

CREATE UNIQUE INDEX image_source_uindex ON images USING btree (image_source);

	
CREATE TABLE  event_tag(
    event_id bigint,
	tag_id bigint,
	
	CONSTRAINT event_tag_event_id_fk FOREIGN KEY (event_id)
      REFERENCES public.events (id) MATCH SIMPLE
      ON DELETE CASCADE,
	  
	CONSTRAINT event_tag_tag_id_fk FOREIGN KEY (tag_id)
      REFERENCES public.tags (id) MATCH SIMPLE
      ON DELETE CASCADE
);	


CREATE TABLE  event_participant(
    event_id bigint,
	participant_id bigint,
	
	CONSTRAINT event_participant_event_id_fk FOREIGN KEY (event_id)
      REFERENCES public.events (id) MATCH SIMPLE
      ON DELETE CASCADE,
	  
	CONSTRAINT event_participant_participant_id_fk FOREIGN KEY (participant_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON DELETE CASCADE
);

CREATE TABLE  user_tag(
    user_id bigint,
	tag_id bigint,
	
	CONSTRAINT user_tag_tag_id_fk FOREIGN KEY (tag_id)
      REFERENCES public.tags (id) MATCH SIMPLE
      ON DELETE CASCADE,
	  
	CONSTRAINT user_tag_participant_id_fk FOREIGN KEY (user_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON DELETE CASCADE
);

INSERT  INTO users VALUES
  (1, 'user', 'pass', 'test@test.ru', 'power', 'wolf', NULL, NULL, NULL, NULL, NULL),
(2, 'admin', 'admin', 'dosug@test.ru', 'sanctus', 'lupus', NULL, NULL, NULL, NULL, NULL);
