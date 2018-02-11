CREATE DATABASE dissertation;

USE dissertation;

CREATE TABLE `Message` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `message_text` TEXT, `message_from` TEXT, `message_from_name` TEXT, `in_response_to` TEXT, `handled` INTEGER NOT NULL, `received_at`BIGINT, `responded_at` BIGINT, `participant_id` TEXT, `puid` INT);
CREATE INDEX `index_Message_uid` ON `Message` (`uid`);

CREATE TABLE `SurveyResult` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `availability` INTEGER NOT NULL, `urgency` INTEGER NOT NULL, `message_id` INTEGER NOT NULL, `participant_id` TEXT, `puid` INT);

CREATE TABLE `Participant` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `participant_id` TEXT);

CREATE TABLE `AllMessage` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `body` TEXT, `thread_id` INTEGER NOT NULL, `type` INTEGER NOT NULL, `received_at` BIGINT, `participant_id` TEXT);

CREATE TABLE `Thread` (`uid` INTEGER NOT NULL, `contact_name` TEXT, `address` TEXT, `participant_id` TEXT, PRIMARY KEY(`uid`));
