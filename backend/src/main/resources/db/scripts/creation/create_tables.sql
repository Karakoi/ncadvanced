-- ----------------------------
-- Sequence structure for main_id_seq
-- ----------------------------
--DROP SEQUENCE IF EXISTS "public"."main_id_seq";
CREATE SEQUENCE "public"."main_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 10;
SELECT setval('"public"."main_id_seq"', 1, true);

-- ----------------------------
-- Table structure for history
-- ----------------------------
--DROP TABLE IF EXISTS "public"."history";
CREATE TABLE "public"."history" (
 "id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
 "column_name" varchar(45) COLLATE "default" NOT NULL,
 "old_value" varchar(200) COLLATE "default",
 "new_value" varchar(200) COLLATE "default",
 "demonstration_of_old_value" varchar(200),
 "demonstration_of_new_value" varchar(200),
 "changer_id" int4 NOT NULL,
 "record_id" int4 NOT NULL,
 "date_of_change" TIMESTAMP NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for message
-- ----------------------------
--DROP TABLE IF EXISTS "public"."message";
CREATE TABLE "public"."message" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"sender_id" int4 NOT NULL,
"recipient_id" int4,
"topic_id" int4,
"text" varchar(500) COLLATE "default" NOT NULL,
"date_and_time" TIMESTAMP NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for priority_status
-- ----------------------------
--DROP TABLE IF EXISTS "public"."priority_status";
CREATE TABLE "public"."priority_status" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"name" varchar(45) COLLATE "default" NOT NULL,
"value" int4 NOT NULL NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for progress_status
-- ----------------------------
--DROP TABLE IF EXISTS "public"."progress_status";
CREATE TABLE "public"."progress_status" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"name" varchar(45) COLLATE "default" NOT NULL,
"value" int4 NOT NULL NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for request
-- ----------------------------
--DROP TABLE IF EXISTS "public"."request";
CREATE TABLE "public"."request" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"title" varchar(45) COLLATE "default" NOT NULL,
"description" varchar(200) COLLATE "default" NOT NULL,
"priority_status_id" int4,
"progress_status_id" int4,
"reporter_id" int4 NOT NULL,
"assignee_id" int4,
"estimate_time_in_days" int4,
"date_of_creation" TIMESTAMP NOT NULL,
"parent_id" int4,
 "last_changer_id" int4 NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for role
-- ----------------------------
--DROP TABLE IF EXISTS "public"."role";
CREATE TABLE "public"."role" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"name" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for topic
-- ----------------------------
--DROP TABLE IF EXISTS "public"."topic";
CREATE TABLE "public"."topic" (
 "id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
 "title" varchar(45) COLLATE "default" NOT NULL,
 "description" varchar(500) NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for topic_to_role
-- ----------------------------
--DROP TABLE IF EXISTS "public"."topic_to_role";
CREATE TABLE "public"."topic_to_role" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"role_id" int4 NOT NULL,
"topic_id" int4 NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for user
-- ----------------------------
--DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"first_name" varchar(45) COLLATE "default" NOT NULL,
"last_name" varchar(45) COLLATE "default" NOT NULL,
"second_name" varchar(45) COLLATE "default",
"password" varchar(200) COLLATE "default" NOT NULL,
"email" varchar(45) COLLATE "default" NOT NULL,
"date_of_birth" date,
"phone_number" varchar(45) COLLATE "default",
"role" int4,
"is_deactivated" boolean NOT NULL DEFAULT false,
"date_of_deactivation" TIMESTAMP
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
--DROP TABLE IF EXISTS "public"."comment";
CREATE TABLE "public"."comment" (
"id" int4 DEFAULT nextval('main_id_seq'::regclass) NOT NULL,
"sender_id" int4 NOT NULL,
"request_id" int4,
"text" varchar(500) COLLATE "default" NOT NULL,
"date_and_time" TIMESTAMP NOT NULL
)
WITH (OIDS=FALSE);

-- ----------------------------
-- Indexes structure for table request
-- ----------------------------
CREATE UNIQUE INDEX request_reporter_date_uindex ON "public"."request" (reporter_id, date_of_creation);

-- ----------------------------
-- Indexes structure for table role
-- ----------------------------
CREATE UNIQUE INDEX role_name_uindex ON "public"."role" (name);

-- ----------------------------
-- Indexes structure for table priority status
-- ----------------------------
CREATE UNIQUE INDEX priority_status_name_uindex ON "public"."priority_status" (name);

-- ----------------------------
-- Indexes structure for table progress status
-- ----------------------------
CREATE UNIQUE INDEX progress_status_name_uindex ON "public"."progress_status" (name);

-- ----------------------------
-- Indexes structure for table topic
-- ----------------------------
CREATE UNIQUE INDEX topic_title_uindex ON "public"."topic" (title);

-- ----------------------------
-- Indexes structure for table comment
-- ----------------------------
CREATE UNIQUE INDEX comment_sender_date_uindex ON "public"."comment" (sender_id, date_and_time);

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "public"."main_id_seq" OWNED BY "role"."id";

-- ----------------------------
-- Indexes structure for table history
-- ----------------------------
CREATE INDEX "custom_main_row_id_idx" ON "public"."history" USING btree ("record_id");
CREATE INDEX "history_fk_history_detail_ver2_user1_idx" ON "public"."history" USING btree ("changer_id");

-- ----------------------------
-- Primary Key structure for table history
-- ----------------------------
ALTER TABLE "public"."history" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table message
-- ----------------------------
CREATE INDEX "message_fk_message_topic2_idx" ON "public"."message" USING btree ("topic_id");
CREATE INDEX "message_fk_message_user1_idx" ON "public"."message" USING btree ("recipient_id");
CREATE INDEX "message_fk_message_user3_idx" ON "public"."message" USING btree ("sender_id");

-- ----------------------------
-- Indexes structure for table comment
-- ----------------------------
CREATE INDEX "comment_fk_comment_topic2_idx" ON "public"."comment" USING btree ("request_id");
CREATE INDEX "comment_fk_comment_user3_idx" ON "public"."comment" USING btree ("sender_id");

-- ----------------------------
-- Primary Key structure for table message
-- ----------------------------
ALTER TABLE "public"."message" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table comment
-- ----------------------------
ALTER TABLE "public"."comment" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table priority_status
-- ----------------------------
ALTER TABLE "public"."priority_status" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table priority_status
-- ----------------------------
ALTER TABLE "public"."priority_status" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table progress_status
-- ----------------------------
ALTER TABLE "public"."progress_status" ADD UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table progress_status
-- ----------------------------
ALTER TABLE "public"."progress_status" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table request
-- ----------------------------
CREATE INDEX "request_fk_request_priority_status1_idx" ON "public"."request" USING btree ("priority_status_id");
CREATE INDEX "request_fk_request_progress_status1_idx" ON "public"."request" USING btree ("progress_status_id");
CREATE INDEX "request_fk_request_user2_idx" ON "public"."request" USING btree ("assignee_id");
CREATE INDEX "request_fk_request_user3_idx" ON "public"."request" USING btree ("reporter_id");

-- ----------------------------
-- Primary Key structure for table request
-- ----------------------------
ALTER TABLE "public"."request" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table topic
-- ----------------------------
ALTER TABLE "public"."topic" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table topic_to_role
-- ----------------------------
CREATE UNIQUE INDEX "topic_to_role_id_uindex" ON "public"."topic_to_role" USING btree ("id");

-- ----------------------------
-- Primary Key structure for table topic_to_role
-- ----------------------------
ALTER TABLE "public"."topic_to_role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD UNIQUE ("email");

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Key structure for table "public"."history"
-- ----------------------------
ALTER TABLE "public"."history" ADD FOREIGN KEY ("changer_id") REFERENCES "public"."user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."message"
-- ----------------------------
ALTER TABLE "public"."message" ADD FOREIGN KEY ("recipient_id") REFERENCES "public"."user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD FOREIGN KEY ("topic_id") REFERENCES "public"."topic" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD FOREIGN KEY ("sender_id") REFERENCES "public"."user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."comment"
-- ----------------------------
ALTER TABLE "public"."comment" ADD FOREIGN KEY ("request_id") REFERENCES "public"."request" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."comment" ADD FOREIGN KEY ("sender_id") REFERENCES "public"."user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."request"
-- ----------------------------
ALTER TABLE "public"."request" ADD FOREIGN KEY ("assignee_id") REFERENCES "public"."user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."request" ADD FOREIGN KEY ("parent_id") REFERENCES "public"."request" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."request" ADD FOREIGN KEY ("progress_status_id") REFERENCES "public"."progress_status" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."request" ADD FOREIGN KEY ("priority_status_id") REFERENCES "public"."priority_status" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."request" ADD FOREIGN KEY ("reporter_id") REFERENCES "public"."user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."topic_to_role"
-- ----------------------------
ALTER TABLE "public"."topic_to_role" ADD FOREIGN KEY ("role_id") REFERENCES "public"."role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."topic_to_role" ADD FOREIGN KEY ("topic_id") REFERENCES "public"."topic" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."user"
-- ----------------------------
ALTER TABLE "public"."user" ADD FOREIGN KEY ("role") REFERENCES "public"."role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;