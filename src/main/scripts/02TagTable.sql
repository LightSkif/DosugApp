DROP SEQUENCE IF EXISTS public.tags_id_seq CASCADE;
DROP TABLE IF EXISTS public.tags CASCADE;

CREATE TABLE tags (
    id bigint PRIMARY KEY,
	tag_name character varying(256) NOT NULL
);

CREATE SEQUENCE tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;

ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);

CREATE UNIQUE INDEX tag_uindex ON tags USING btree (tag_name);