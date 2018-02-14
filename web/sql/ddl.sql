CREATE DATABASE dissertation;

USE dissertation;

CREATE TABLE `Message` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `message_text` TEXT, `message_from` TEXT, `message_from_name` TEXT, `in_response_to` TEXT, `handled` INTEGER NOT NULL, `received_at`BIGINT, `responded_at` BIGINT, `participant_id` TEXT, `puid` INT);
CREATE INDEX `index_Message_uid` ON `Message` (`uid`);

CREATE TABLE `SurveyResult` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `availability` INTEGER NOT NULL, `urgency` INTEGER NOT NULL, `message_id` INTEGER NOT NULL, `participant_id` TEXT, `puid` INT);

CREATE TABLE `Participant` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `participant_id` TEXT);

CREATE TABLE `AllMessage` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `body` TEXT, `thread_id` INTEGER NOT NULL, `type` INTEGER NOT NULL, `received_at` BIGINT, `participant_id` TEXT);

CREATE TABLE `Thread` (`uid` INTEGER NOT NULL, `contact_name` TEXT, `address` TEXT, `participant_id` TEXT, PRIMARY KEY(`uid`));

CREATE TABLE `RelationalSurvey` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `intimacy1` INTEGER NOT NULL, `intimacy2` INTEGER NOT NULL, `intimacy3` INTEGER NOT NULL, `power1` INTEGER NOT NULL, `power2` INTEGER NOT NULL, `power3` INTEGER NOT NULL,`contact_name` TEXT, `participant_id` TEXT);
