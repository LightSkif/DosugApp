DROP SEQUENCE IF EXISTS public.roles_id_seq CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;

CREATE TABLE roles (
  id   INTEGER               NOT NULL,
  role CHARACTER VARYING(20) NOT NULL
);

ALTER TABLE ONLY roles
  ADD CONSTRAINT roles_id_pk PRIMARY KEY (id);

CREATE SEQUENCE roles_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;

ALTER TABLE ONLY roles
  ALTER COLUMN id SET DEFAULT nextval('roles_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX roles_id_uindex
  ON roles USING BTREE (id);

CREATE UNIQUE INDEX roles_role_uindex
  ON roles USING BTREE (role);
DROP SEQUENCE IF EXISTS public.users_id_seq CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE users (
  id          BIGINT                 NOT NULL,
  username    CHARACTER VARYING(256) NOT NULL,
  password    CHARACTER VARYING(256) NOT NULL,
  email       CHARACTER VARYING(256) NOT NULL,
  first_name  CHARACTER VARYING(60),
  last_name   CHARACTER VARYING(60),
  create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  avatar      CHARACTER VARYING(256),
  description CHARACTER VARYING(1000),
  birthdate   DATE,
  phone       CHARACTER VARYING(15),
  is_banned   BOOLEAN
);


CREATE SEQUENCE users_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY users
  ALTER COLUMN id SET DEFAULT nextval('users_id_seq' :: REGCLASS);

ALTER TABLE ONLY users
  ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE UNIQUE INDEX users_email_uindex
  ON users USING BTREE (email);

CREATE UNIQUE INDEX users_username_uindex
  ON users USING BTREE (username);


INSERT INTO users VALUES
  (1, 'user', 'pass', 'test@test.ru', 'power', 'wolf', NULL, NULL, NULL, NULL, NULL),
  (2, 'admin', 'admin', 'dosug@test.ru', 'sanctus', 'lupus', NULL, NULL, NULL, NULL, NULL);
DROP SEQUENCE IF EXISTS public.events_id_seq CASCADE;
DROP TABLE IF EXISTS public.events CASCADE;

CREATE TABLE events (
  id                    BIGINT PRIMARY KEY,
  creator_id            BIGINT                 NOT NULL,
  event_name            CHARACTER VARYING(256) NOT NULL,
  content               TEXT,
  event_start_date_time TIMESTAMP              NOT NULL,
  event_end_date_time   TIMESTAMP              NOT NULL,
  place_name            CHARACTER VARYING(256),
  longitude             DOUBLE PRECISION,
  latitude              DOUBLE PRECISION,
  avatar                CHARACTER VARYING(256),
  like_count            INT,
  allowed               BOOLEAN,
  create_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),

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

ALTER SEQUENCE events_id_seq OWNED BY events.id;

ALTER TABLE ONLY events
  ALTER COLUMN id SET DEFAULT nextval('events_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX event_unique_parametr
  ON public.events (event_name, creator_id, event_start_date_time);
DROP SEQUENCE IF EXISTS public.tags_id_seq CASCADE;
DROP TABLE IF EXISTS public.tags CASCADE;

CREATE TABLE tags (
  id       BIGINT PRIMARY KEY,
  tag_name CHARACTER VARYING(256) NOT NULL
);

CREATE SEQUENCE tags_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;

ALTER TABLE ONLY tags
  ALTER COLUMN id SET DEFAULT nextval('tags_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX tag_uindex
  ON tags USING BTREE (tag_name);
DROP SEQUENCE IF EXISTS public.auth_tokens_auth_token_seq CASCADE;
DROP TABLE IF EXISTS public.auth_tokens CASCADE;

CREATE TABLE auth_tokens (
  auth_token    CHARACTER VARYING(256)      NOT NULL,
  user_id       BIGINT                      NOT NULL,
  create_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  expiring_time TIME WITHOUT TIME ZONE
);

CREATE SEQUENCE auth_tokens_auth_token_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER TABLE ONLY auth_tokens
  ADD CONSTRAINT auth_tokens_pkey PRIMARY KEY (auth_token);

ALTER SEQUENCE auth_tokens_auth_token_seq OWNED BY auth_tokens.auth_token;

ALTER TABLE ONLY auth_tokens
  ADD CONSTRAINT auth_tokens_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY auth_tokens
  ADD CONSTRAINT fkkhs4tpy3l5krnk87ykkmafeic FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO auth_tokens VALUES
  (1, 1, '2017-05-23 17:25:50.536', NULL);
DROP SEQUENCE IF EXISTS public.event_participant_id_seq CASCADE;
DROP TABLE IF EXISTS public.event_participant CASCADE;

CREATE TABLE event_participant (
  id             BIGINT PRIMARY KEY,
  event_id       BIGINT,
  participant_id BIGINT,
  liked          BOOLEAN,

  CONSTRAINT event_participant_event_id_fk FOREIGN KEY (event_id)
  REFERENCES public.events (id) MATCH SIMPLE
  ON DELETE CASCADE,

  CONSTRAINT event_participant_participant_id_fk FOREIGN KEY (participant_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON DELETE CASCADE
);

CREATE SEQUENCE event_participant_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE event_participant_id_seq OWNED BY event_participant.id;

ALTER TABLE ONLY event_participant
  ALTER COLUMN id SET DEFAULT nextval('event_participant_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX event_participant_unique_parametr
  ON public.event_participant (event_id, participant_id);
DROP SEQUENCE IF EXISTS public.images_id_seq CASCADE;
DROP TABLE IF EXISTS public.images CASCADE;

CREATE TABLE images (
  id           BIGINT PRIMARY KEY,
  image_source CHARACTER VARYING(256),
  event_id     BIGINT,

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

ALTER SEQUENCE images_id_seq OWNED BY images.id;

ALTER TABLE ONLY images
  ALTER COLUMN id SET DEFAULT nextval('images_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX image_source_uindex
  ON images USING BTREE (image_source);
DROP TABLE IF EXISTS public.user_role CASCADE;
DROP TABLE IF EXISTS public.event_tag CASCADE;

CREATE TABLE user_role (
  role_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL
);

ALTER TABLE ONLY user_role
  ADD CONSTRAINT user_role_role_id_user_id_pk PRIMARY KEY (role_id, user_id);

ALTER TABLE ONLY user_role
  ADD CONSTRAINT user_role_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE;

ALTER TABLE ONLY user_role
  ADD CONSTRAINT user_role_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

CREATE INDEX user_role_user_id_index
  ON user_role USING BTREE (user_id);


CREATE TABLE event_tag (
  event_id BIGINT,
  tag_id   BIGINT,

  CONSTRAINT event_tag_event_id_fk FOREIGN KEY (event_id)
  REFERENCES public.events (id)
  ON DELETE CASCADE,

  CONSTRAINT event_tag_tag_id_fk FOREIGN KEY (tag_id)
  REFERENCES public.tags (id)
  ON DELETE CASCADE
);
DROP SEQUENCE IF EXISTS public.user_tag_id_seq CASCADE;
DROP TABLE IF EXISTS public.user_tag CASCADE;

CREATE TABLE user_tag (
  id      BIGINT PRIMARY KEY,
  user_id BIGINT,
  tag_id  BIGINT,
  --	like_count int,

  CONSTRAINT user_tag_tag_id_fk FOREIGN KEY (tag_id)
  REFERENCES public.tags (id)
  ON DELETE CASCADE,

  CONSTRAINT user_tag_user_id_fk FOREIGN KEY (user_id)
  REFERENCES public.users (id)
  ON DELETE CASCADE
);

CREATE SEQUENCE user_tag_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE user_tag_id_seq OWNED BY user_tag.id;

ALTER TABLE ONLY user_tag
  ALTER COLUMN id SET DEFAULT nextval('user_tag_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX user_tag_unique_parametr
  ON user_tag (user_id, tag_id);
DROP SEQUENCE IF EXISTS public.user_like_id_seq CASCADE;
DROP TABLE IF EXISTS public.user_like CASCADE;

CREATE TABLE user_like (
  id                BIGINT PRIMARY KEY,
  evaluate_user_id  BIGINT,
  rated_user_tag_id BIGINT,
  event_id          BIGINT,

  CONSTRAINT user_like_evaluate_user_id_fk FOREIGN KEY (evaluate_user_id)
  REFERENCES public.users (id)
  ON DELETE CASCADE,

  CONSTRAINT user_like_rated_user_tag_id_fk FOREIGN KEY (rated_user_tag_id)
  REFERENCES public.user_tag (id)
  ON DELETE CASCADE,

  CONSTRAINT user_like_event_id_fk FOREIGN KEY (event_id)
  REFERENCES public.events (id)
  ON DELETE CASCADE
);

CREATE SEQUENCE user_like_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE user_like_id_seq OWNED BY user_like.id;

ALTER TABLE ONLY user_like
  ALTER COLUMN id SET DEFAULT nextval('user_like_id_seq' :: REGCLASS);

CREATE UNIQUE INDEX user_like_unique_parametr
  ON user_like (evaluate_user_id,
                rated_user_tag_id,
                event_id);
