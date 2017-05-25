DROP SEQUENCE IF EXISTS public.user_like_id_seq CASCADE;
DROP TABLE IF EXISTS public.user_like CASCADE;

CREATE TABLE  user_like(
  id bigint PRIMARY KEY,
  evaluate_user_id bigint,
  rated_user_id bigint,
	tag_id bigint,
	event_id bigint,

  CONSTRAINT user_like_evaluate_user_id_fk FOREIGN KEY (evaluate_user_id)
      REFERENCES public.users (id)
      ON DELETE CASCADE,

  CONSTRAINT user_like_rated_user_id_fk FOREIGN KEY (rated_user_id)
      REFERENCES public.users (id)
      ON DELETE CASCADE,

	CONSTRAINT user_like_tag_id_fk FOREIGN KEY (tag_id)
      REFERENCES public.tags (id)
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

ALTER TABLE ONLY user_like ALTER COLUMN id SET DEFAULT nextval('user_like_id_seq' :: regclass);

CREATE UNIQUE INDEX user_like_unique_parametr
  ON user_like (evaluate_user_id,
                rated_user_id,
	              tag_id,
	              event_id);
