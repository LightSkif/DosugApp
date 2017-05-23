DROP TABLE IF EXISTS public.event_participant CASCADE;

CREATE TABLE  event_participant(
  id bigint,
  event_id bigint,
	participant_id bigint,
	liked boolean,

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

ALTER TABLE ONLY event_participant ALTER COLUMN id SET DEFAULT nextval('event_participant_id_seq'::regclass);
