CREATE TABLE `message`
(
    `created_at`       datetime(6)  NOT NULL,
    `message_sequence` bigint(20)   NOT NULL AUTO_INCREMENT,
    `updated_at`       datetime(6)  NOT NULL,
    `content`          varchar(255) NOT NULL,
    `user_name`        varchar(255) NOT NULL,
    PRIMARY KEY (`message_sequence`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_uca1400_ai_ci;

CREATE TABLE `message_user`
(
    `connection_count`       int(11)      NOT NULL,
    `created_at`             datetime(6)  NOT NULL,
    `updated_at`             datetime(6)  NOT NULL,
    `user_id`                bigint(20)   NOT NULL AUTO_INCREMENT,
    `connection_invite_code` varchar(255) NOT NULL,
    `password`               varchar(255) NOT NULL,
    `username`               varchar(255) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_uca1400_ai_ci;

CREATE TABLE `user_connection`
(
    `created_at`        datetime(6)                                                  NOT NULL,
    `inviter_user_id`   bigint(20)                                                   NOT NULL,
    `partner_user_a_id` bigint(20)                                                   NOT NULL,
    `partner_user_b_id` bigint(20)                                                   NOT NULL,
    `updated_at`        datetime(6)                                                  NOT NULL,
    `status`            enum ('ACCEPTED','DISCONNECTED','NONE','PENDING','REJECTED') NOT NULL,
    PRIMARY KEY (`partner_user_a_id`, `partner_user_b_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_uca1400_ai_ci;

CREATE TABLE `channel`
(
    `channel_id`        bigint(20)   NOT NULL AUTO_INCREMENT,
    `title` varchar(30) not null ,
    `channel_invite_code`varchar(32) NOT NULL,
    `head_count` int(11) NOT NULL,
    `created_at`       datetime(6)  NOT NULL,
    `updated_at`       datetime(6)  NOT NULL,
    PRIMARY KEY (`channel_id`),
    constraint channel_invite_code unique (channel_invite_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_uca1400_ai_ci;

CREATE TABLE `user_channel`
(
    `user_id`  bigint(20)   NOT NULL AUTO_INCREMENT,
    `channel_id`        bigint(20)   NOT NULL AUTO_INCREMENT,
    `last_read_msg_seq` bigint   NOT NULL,
    `created_at`       datetime(6)  NOT NULL,
    `updated_at`       datetime(6)  NOT NULL,
    PRIMARY KEY (`user_id`,`channel_id`),
    index `channel_id` (`channel_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_uca1400_ai_ci;

