DROP SEQUENCE IF EXISTS public.auth_tokens_auth_token_seq CASCADE;
DROP TABLE IF EXISTS public.auth_tokens CASCADE;

CREATE TABLE auth_tokens (
    auth_token character varying(256) NOT NULL,
    user_id bigint NOT NULL,
    create_time timestamp without time zone NOT NULL,
    expiring_time time without time zone
);

CREATE SEQUENCE auth_tokens_auth_token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE auth_tokens_auth_token_seq OWNED BY auth_tokens.auth_token;

ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT auth_tokens_pkey PRIMARY KEY (auth_token);

ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT auth_tokens_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY auth_tokens
    ADD CONSTRAINT fkkhs4tpy3l5krnk87ykkmafeic FOREIGN KEY (user_id) REFERENCES users(id);

