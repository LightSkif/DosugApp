DROP SEQUENCE IF EXISTS public.images_id_seq CASCADE;
DROP TABLE IF EXISTS public.images CASCADE;

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

ALTER SEQUENCE images_id_seq OWNED BY images.id;

ALTER TABLE ONLY images ALTER COLUMN id SET DEFAULT nextval('images_id_seq'::regclass);

CREATE UNIQUE INDEX image_source_uindex ON images USING btree (image_source);
