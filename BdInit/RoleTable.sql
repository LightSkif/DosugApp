DROP SEQUENCE IF EXISTS public.roles_id_seq CASCADE;
DROP TABLE IF EXISTS public.roles CASCADE;

CREATE TABLE roles (
    id integer NOT NULL,
    role character varying(20) NOT NULL
);


CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE roles_id_seq OWNED BY roles.id;

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX roles_id_uindex ON roles USING btree (id);

CREATE UNIQUE INDEX roles_role_uindex ON roles USING btree (role);