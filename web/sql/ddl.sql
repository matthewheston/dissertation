CREATE DATABASE dissertation;

USE dissertation;

CREATE TABLE `Message` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `message_text` TEXT, `message_from` TEXT, `handled` INTEGER NOT NULL, `received_at` INTEGER, `responded_at` INTEGER, `participant_id` TEXT);
CREATE INDEX `index_Message_uid` ON `Message` (`uid`);

CREATE TABLE `SurveyResult` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `availability` INTEGER NOT NULL, `urgency` INTEGER NOT NULL, `message_id` INTEGER NOT NULL, `participant_id` TEXT, FOREIGN KEY(`message_id`) REFERENCES `Message`(`uid`) ON UPDATE NO ACTION ON DELETE NO ACTION );

CREATE TABLE `Participant` (`uid` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, `participant_id` TEXT);
