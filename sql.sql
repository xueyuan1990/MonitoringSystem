
mysql -h172.16.3.14 -uroot -proot
mysql -h192.168.19.216 -uroot -proot
use fdfs_monitor;

索引：
create index time_groupId_serverId on storage (time,groupId,serverId);
ALTER TABLE storage ADD INDEX time_groupId_serverId (time,groupId,serverId);

drop table user;
create table user(
username varchar(16) primary key,
password varchar(16) not null,
createTime varchar(32),
userRights varchar(32) default 'normal user'
);

drop table groupStorage;
create table groupStorage(
groupId int primary key,
groupName varchar(32),
groupThreshold int
);
alter table groupStorage add groupName varchar(32);
update groupStorage set groupname="group 1" where groupId=1;
update groupStorage set groupname="group 2" where groupId=2;


drop table serverStorage;
create table serverStorage(
id bigint AUTO_INCREMENT primary key,
groupId int not null,
serverId int not null,
serverThreshold int
);

drop table storage;
create table storage(
id bigint AUTO_INCREMENT primary key,
time varchar(32) not null,
groupId int not null,
serverId int not null,
ip_addr varchar(256),
total_storage int,
free_storage int,
success_upload_count int,
success_download_count int,
last_heart_beat_time  varchar(32)
);


drop table tracker;
create table tracker(
trackerId int primary key,
trackerIp varchar(256) not null,
trackerState varchar(32) not null
);

