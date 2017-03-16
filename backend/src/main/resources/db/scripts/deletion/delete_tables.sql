-- ----------------------------
-- Delete Sequence for main_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."main_id_seq";

-- ----------------------------
-- Delete Table for history
-- ----------------------------
DROP TABLE IF EXISTS "public"."history";

-- ----------------------------
-- Delete Table for message
-- ----------------------------
DROP TABLE IF EXISTS "public"."message";

-- ----------------------------
-- Delete Table for topic_to_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."topic_to_role";

-- ----------------------------
-- Delete Table for topic
-- ----------------------------
DROP TABLE IF EXISTS "public"."topic";

-- ----------------------------
-- Delete Table for request
-- ----------------------------
DROP TABLE IF EXISTS "public"."request";

-- ----------------------------
-- Delete Table for priority_status
-- ----------------------------
DROP TABLE IF EXISTS "public"."priority_status";

-- ----------------------------
-- Delete Table for progress_status
-- ----------------------------
DROP TABLE IF EXISTS "public"."progress_status";

-- ----------------------------
-- Delete Table for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";

-- ----------------------------
-- Delete Table for role
-- ----------------------------
DROP TABLE IF EXISTS "public"."role";
