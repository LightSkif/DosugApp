DROP SEQUENCE IF EXISTS public.events_id_seq CASCADE;
DROP TABLE IF EXISTS public.events CASCADE;

CREATE TABLE events (
  id bigint PRIMARY KEY,
	creator_id bigint NOT NULL,
	event_name character varying(256) NOT NULL,
	content text,
	event_start_date_time timestamp   NOT NULL,
  event_end_date_time   timestamp   NOT NULL,
	place_name character varying(256),
	longitude double precision,
	latitude double precision,
	avatar character varying(256),
	allowed boolean,
  create_date timestamp without time zone DEFAULT now(),

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

ALTER TABLE ONLY events ALTER COLUMN id SET DEFAULT nextval('events_id_seq'::regclass);

CREATE UNIQUE INDEX event_unique_parametr ON public.events (event_name, creator_id, event_date_time);