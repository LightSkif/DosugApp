DROP SEQUENCE IF EXISTS public.bans_id_seq CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE bans (
  id       BIGINT,
  userId   BIGINT,
  duration TIMESTAMP NOT NULL
);

ALTER TABLE ONLY bans
  ADD CONSTRAINT bans_pkey PRIMARY KEY (id);

ALTER SEQUENCE bans_id_seq OWNED BY users.id;

ALTER TABLE ONLY bans
  ALTER COLUMN id SET DEFAULT nextval('bans_id_seq' :: REGCLASS);

