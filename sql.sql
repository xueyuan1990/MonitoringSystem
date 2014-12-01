
mysql -h192.168.19.216 -uroot -proot
use fdfs_monitor;
select free from storage where groupId=1 and serverId=1 and time>'2014-10-14 08:00:00' and time<'2014-10-14 10:00:00';
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
groupThreshold int
);

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

insert into tracker values(1,'129.0.0.1','ACTIVE');
insert into tracker values(2,'129.0.0.2','OFFLINE');
insert into tracker values(3,'129.0.0.3','ACTIVE');

update tracker set trackerState='OFFLINE' where trackerId=3;
update tracker set trackerIp='129.0.0.8' where trackerId=3;
update tracker set trackerState='ACTIVE',trackerIp='129.0.0.10' where trackerId=3;

insert into serverStorage(groupId,serverId) values(1,1);
insert into serverStorage(groupId,serverId) values(1,2);
insert into serverStorage(groupId,serverId) values(2,1);
insert into serverStorage(groupId,serverId) values(2,2);
insert into serverStorage(groupId,serverId,serverThreshold) values(1,3,100);
insert into serverStorage(groupId,serverId) values(3,2);
insert into serverStorage(groupId,serverId) values(3,1);
insert into serverStorage(groupId,serverId) values(3,3);
insert into serverStorage(groupId,serverId) values(4,2);
insert into serverStorage(groupId,serverId) values(4,1);
insert into serverStorage(groupId,serverId) values(4,3);

insert into groupStorage(groupId) values(1);
insert into groupStorage(groupId) values(2);




insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",1,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",1,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",1,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",2,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",2,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",3,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",3,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",3,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",4,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",4,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 00:00:00",4,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",1,1,'127.0.0.1 ACTIVE',100,60,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",1,2,'127.0.0.1 ACTIVE',200,70,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",1,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",2,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",2,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",3,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",3,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",3,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",4,1,'127.0.0.1 ACTIVE',100,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",4,2,'127.0.0.1 ACTIVE',200,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 01:00:00",4,3,'127.0.0.1 ACTIVE',300,50,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",1,1,'127.0.0.1 ACTIVE',100,80,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",1,2,'127.0.0.1 OFFLINE',200,90,50);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",1,3,'127.0.0.1 OFFLINE',300,250,100);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",2,1,'127.0.0.1 ACTIVE',100,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",2,2,'127.0.0.1 ACTIVE',200,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",3,1,'127.0.0.1 ACTIVE',100,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",3,2,'127.0.0.1 ACTIVE',200,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",3,3,'127.0.0.1 ACTIVE',300,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",4,1,'127.0.0.1 ACTIVE',100,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",4,2,'127.0.0.1 ACTIVE',200,50,0);
insert into storage(time,groupId,serverId,ip,total,free,threshold) values("2014-01-01 02:00:00",4,3,'127.0.0.1 ACTIVE',300,50,0);

insert into user values("admin","123","2014-01-01 02:00:00","admin");
insert into user(username,password,createTime) values("u1","p1","2014-01-01 02:00:00");
insert into user values("user2","123","2014-01-01 02:00:00");
insert into user values("user3","123","2014-01-01 02:00:00");
insert into user values("user4","123","2014-01-01 02:00:00");
insert into user values("user5","123","2014-01-01 02:00:00");
insert into user values("user6","123","2014-01-01 02:00:00");
insert into user values("user7","123","2014-01-01 02:00:00");
insert into user values("user8","123","2014-01-01 02:00:00");
insert into user values("user9","123","2014-01-01 02:00:00");
insert into user values("user10","123","2014-01-01 02:00:00");
insert into user values("user11","123","2014-01-01 02:00:00");
insert into user values("user12","123","2014-01-01 02:00:00");
insert into user values("user13","123","2014-01-01 02:00:00");
insert into user values("user14","123","2014-01-01 02:00:00");
insert into user values("user15","123","2014-01-01 02:00:00");
insert into user values("user16","123","2014-01-01 02:00:00");
insert into user values("user17","123","2014-01-01 02:00:00");
insert into user values("user18","123","2014-01-01 02:00:00");
insert into user values("user19","123","2014-01-01 02:00:00");
insert into user values("user20","123","2014-01-01 02:00:00");


insert into groupStorage values(1,50);
insert into groupStorage values(2,50);
insert into groupStorage values(3,50);
insert into groupStorage values(4,50);



