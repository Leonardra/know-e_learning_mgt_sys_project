 set foreign_key_checks = 0;
 truncate table learning_party;
 truncate table authority;
 truncate table instructor;
 truncate table course;

--  insert into learning_party (`id`,`email`,`password`,`enabled`)
--  values (123, 'toni@gmail.com', 'password123',false);

set foreign_key_checks = 1;
