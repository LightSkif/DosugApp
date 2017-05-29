DROP SEQUENCE IF EXISTS public.user_tag_id_seq CASCADE;
DROP TABLE IF EXISTS public.user_tag CASCADE;

CREATE TABLE  user_tag(
  id bigint PRIMARY KEY,
  user_id bigint,
	tag_id bigint,
--	like_count int,

	CONSTRAINT user_tag_tag_id_fk FOREIGN KEY (tag_id)
      REFERENCES public.tags (id)
      ON DELETE CASCADE,

	CONSTRAINT user_tag_participant_id_fk FOREIGN KEY (user_id)
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

ALTER TABLE ONLY user_tag ALTER COLUMN id SET DEFAULT nextval('user_tag_id_seq' :: regclass);

CREATE UNIQUE INDEX user_tag_unique_parametr
  ON user_tag (user_id, tag_id);
