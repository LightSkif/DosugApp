DROP TABLE IF EXISTS public.user_tag CASCADE;
DROP TABLE IF EXISTS public.user_role CASCADE;
DROP TABLE IF EXISTS public.event_tag CASCADE;

CREATE TABLE user_role (
    role_id integer NOT NULL,
    user_id integer NOT NULL
);

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_role_id_user_id_pk PRIMARY KEY (role_id, user_id);

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

CREATE INDEX user_role_user_id_index ON user_role USING btree (user_id);


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