DROP SEQUENCE IF EXISTS public.bans_id_seq CASCADE;
DROP TABLE IF EXISTS public.bans CASCADE;

CREATE TABLE bans (
  id       BIGINT,
  userId   BIGINT,
  duration TIMESTAMP NOT NULL
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

ALTER TABLE ONLY bans
  ALTER COLUMN id SET DEFAULT nextval('bans_id_seq' :: REGCLASS);

