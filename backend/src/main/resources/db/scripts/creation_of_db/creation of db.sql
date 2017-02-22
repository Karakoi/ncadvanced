-- Start --------------------------------------------------------------------------

CREATE SEQUENCE main_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE main_id_seq OWNER TO tkacgwphjmxbcr;

-- --------------------------------------------------------------------------

CREATE TYPE role AS ENUM ('employee', 'office manager', 'admin');

-- --------------------------------------------------------------------------

CREATE TABLE forum (
    id integer NOT NULL,
    title character varying(45) NOT NULL
);

ALTER TABLE forum OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY forum.id;

-- --------------------------------------------------------------------------

CREATE TABLE history (
    id integer NOT NULL,
    column_name character varying(45) NOT NULL,
    current_value character varying(45) NOT NULL,
    date_of_last_change date NOT NULL,
    changer_id integer NOT NULL,
    main_row_id integer NOT NULL
);

ALTER TABLE history OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY history.id;

-- --------------------------------------------------------------------------

CREATE TABLE joined_request (
    id integer NOT NULL,
    title character varying(45) NOT NULL,
    description character varying(45) NOT NULL,
    assignee_id integer NOT NULL,
    progress_status_id integer NOT NULL,
    priority_status_id integer NOT NULL
);

ALTER TABLE joined_request OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY joined_request.id;

-- --------------------------------------------------------------------------

CREATE TABLE message (
    id integer NOT NULL,
    sender_id integer NOT NULL,
    recipient_id integer,
    topic_id integer,
    text character varying(500) NOT NULL,
    date_and_time date NOT NULL
);

ALTER TABLE message OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY message.id;

-- --------------------------------------------------------------------------

CREATE TABLE priority_status (
    id integer NOT NULL,
    name character varying(45) NOT NULL
);

ALTER TABLE priority_status OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY priority_status.id;

-- --------------------------------------------------------------------------

CREATE TABLE progress_status (
    id integer NOT NULL,
    name character varying(45) NOT NULL
);

ALTER TABLE progress_status OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY progress_status.id;

-- --------------------------------------------------------------------------

CREATE TABLE request (
    id integer NOT NULL,
    title character varying(45) NOT NULL,
    description character varying(200) NOT NULL,
    priority_status_id integer NOT NULL,
    progress_status_id integer NOT NULL,
    joined_request_id integer,
    reporter_id integer NOT NULL,
    assignee_id integer,
    estimate_time_in_days integer,
    date_of_creation date NOT NULL
);

ALTER TABLE request OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY request.id;

-- --------------------------------------------------------------------------

CREATE TABLE sub_request (
    id integer NOT NULL,
    title character varying(45) NOT NULL,
    description character varying(200) NOT NULL,
    request_id integer NOT NULL
);

ALTER TABLE sub_request OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY sub_request.id;

-- --------------------------------------------------------------------------

CREATE TABLE topic (
    id integer NOT NULL,
    title character varying(45) NOT NULL,
    forum_id integer NOT NULL
);

ALTER TABLE topic OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY topic.id;

-- --------------------------------------------------------------------------

CREATE TABLE "user" (
    id integer NOT NULL,
    first_name character varying(45) NOT NULL,
    last_name character varying(45) NOT NULL,
    second_name character varying(45),
    password character varying(200) NOT NULL,
    email character varying(45) NOT NULL,
    date_of_birth date,
    phone_number character varying(45),
    role role NOT NULL
);

ALTER TABLE "user" OWNER TO tkacgwphjmxbcr;

ALTER SEQUENCE main_id_seq OWNED BY "user".id;

-- --------------------------------------------------------------------------

ALTER TABLE ONLY forum ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY history ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY joined_request ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY message ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY priority_status ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY progress_status ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY request ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY sub_request ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY topic ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('main_id_seq'::regclass);

-- --------------------------------------------------------------------------

ALTER TABLE ONLY forum ADD CONSTRAINT forum_pkey PRIMARY KEY (id);

ALTER TABLE ONLY history ADD CONSTRAINT history_pkey PRIMARY KEY (id);

ALTER TABLE ONLY joined_request ADD CONSTRAINT joined_request_pkey PRIMARY KEY (id);

ALTER TABLE ONLY message ADD CONSTRAINT message_pkey PRIMARY KEY (id);

ALTER TABLE ONLY priority_status ADD CONSTRAINT priority_status_name_key UNIQUE (name);

ALTER TABLE ONLY priority_status ADD CONSTRAINT priority_status_pkey PRIMARY KEY (id);

ALTER TABLE ONLY progress_status ADD CONSTRAINT progress_status_name_key UNIQUE (name);

ALTER TABLE ONLY progress_status ADD CONSTRAINT progress_status_pkey PRIMARY KEY (id);

ALTER TABLE ONLY request ADD CONSTRAINT request_pkey PRIMARY KEY (id);

ALTER TABLE ONLY sub_request ADD CONSTRAINT sub_request_pkey PRIMARY KEY (id);

ALTER TABLE ONLY topic ADD CONSTRAINT topic_pkey PRIMARY KEY (id);

ALTER TABLE ONLY "user" ADD CONSTRAINT user_email_key UNIQUE (email);

ALTER TABLE ONLY "user" ADD CONSTRAINT user_pkey PRIMARY KEY (id);

-- --------------------------------------------------------------------------
	
-- CUSTOM INDEX	
CREATE INDEX custom_main_row_id_idx ON history USING btree (main_row_id);

CREATE INDEX history_fk_history_detail_ver2_user1_idx ON history USING btree (changer_id);

CREATE INDEX joined_request_fk_joined_request_priority_status1_idx ON joined_request USING btree (priority_status_id);

CREATE INDEX joined_request_fk_joined_request_progress_status1_idx ON joined_request USING btree (progress_status_id);

CREATE INDEX joined_request_fk_joined_request_user2_idx ON joined_request USING btree (assignee_id);

CREATE INDEX message_fk_message_topic2_idx ON message USING btree (topic_id);

CREATE INDEX message_fk_message_user1_idx ON message USING btree (recipient_id);

CREATE INDEX message_fk_message_user3_idx ON message USING btree (sender_id);

CREATE INDEX request_fk_request_joined_request1_idx ON request USING btree (joined_request_id);

CREATE INDEX request_fk_request_priority_status1_idx ON request USING btree (priority_status_id);

CREATE INDEX request_fk_request_progress_status1_idx ON request USING btree (progress_status_id);

CREATE INDEX request_fk_request_user2_idx ON request USING btree (assignee_id);

CREATE INDEX request_fk_request_user3_idx ON request USING btree (reporter_id);

CREATE INDEX sub_request_fk_sub_request_request1_idx ON sub_request USING btree (request_id);

CREATE INDEX topic_fk_topic_forum1_idx ON topic USING btree (forum_id);

-- --------------------------------------------------------------------------

ALTER TABLE ONLY history ADD CONSTRAINT fk_history_detail_ver2_user1 FOREIGN KEY (changer_id) REFERENCES "user"(id);

ALTER TABLE ONLY joined_request ADD CONSTRAINT fk_joined_request_priority_status1 FOREIGN KEY (priority_status_id) REFERENCES priority_status(id);

ALTER TABLE ONLY joined_request ADD CONSTRAINT fk_joined_request_progress_status1 FOREIGN KEY (progress_status_id) REFERENCES progress_status(id);

ALTER TABLE ONLY joined_request ADD CONSTRAINT fk_joined_request_user2 FOREIGN KEY (assignee_id) REFERENCES "user"(id);

ALTER TABLE ONLY message ADD CONSTRAINT fk_message_topic2 FOREIGN KEY (topic_id) REFERENCES topic(id);

ALTER TABLE ONLY message ADD CONSTRAINT fk_message_user1 FOREIGN KEY (recipient_id) REFERENCES "user"(id);

ALTER TABLE ONLY message ADD CONSTRAINT fk_message_user3 FOREIGN KEY (sender_id) REFERENCES "user"(id);

ALTER TABLE ONLY request ADD CONSTRAINT fk_request_joined_request1 FOREIGN KEY (joined_request_id) REFERENCES joined_request(id);

ALTER TABLE ONLY request ADD CONSTRAINT fk_request_priority_status1 FOREIGN KEY (priority_status_id) REFERENCES priority_status(id);

ALTER TABLE ONLY request ADD CONSTRAINT fk_request_progress_status1 FOREIGN KEY (progress_status_id) REFERENCES progress_status(id);

ALTER TABLE ONLY request ADD CONSTRAINT fk_request_user2 FOREIGN KEY (assignee_id) REFERENCES "user"(id);

ALTER TABLE ONLY request ADD CONSTRAINT fk_request_user3 FOREIGN KEY (reporter_id) REFERENCES "user"(id);

ALTER TABLE ONLY sub_request ADD CONSTRAINT fk_sub_request_request1 FOREIGN KEY (request_id) REFERENCES request(id);

ALTER TABLE ONLY topic ADD CONSTRAINT fk_topic_forum1 FOREIGN KEY (forum_id) REFERENCES forum(id);

-- END --------------------------------------------------------------------------