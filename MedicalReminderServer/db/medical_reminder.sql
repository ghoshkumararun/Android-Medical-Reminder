
/* create database */

create database medical_reminder;




/* create user table 
*/
create table `medical_reminder`.user (email VARCHAR(30) PRIMARY KEY, name VARCHAR(20), password VARCHAR(20));

/* create medicine table */

create table `medical_reminder`.medicine ( id INT AUTO_INCREMENT, name VARCHAR(20), start_date INT, end_date INT, repetition VARCHAR(10), 
instruction VARCHAR(15), type VARCHAR(20), user_email VARCHAR(30), FOREIGN KEY (user_email) references `medical_reminder`.user(email), PRIMARY KEY(id));


/* create time table */

create table `medical_reminder`.time (medicine_id  INT, take_time TIME, dose FLOAT
, PRIMARY KEY(medicine_id, take_time), FOREIGN KEY(medicine_id) references `medical_reminder`.medicine(id));
