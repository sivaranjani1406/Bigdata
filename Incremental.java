🔴 Incremental CLOUDERA
====================

hadoop dfsadmin -safemode leave

mysql -uroot -pcloudera
create database if not exists zeyodb;
use zeyodb;
drop table if exists zeyotab;
create table zeyotab (id int,name varchar(100),city varchar(100),mode varchar(100));
insert into zeyotab values(1,'zeyo','chennai','cash');
insert into zeyotab values(2,'hema','hyderabad','credit');
insert into zeyotab values(3,'raji','chennai','cash');
insert into zeyotab values(4,'viru','delhi','credit');
select * from zeyotab;

==================
🔴 quit from sql
==================

quit

==================
🔴 Sqoop Import
==================


sqoop import --connect jdbc:mysql://localhost/zeyodb --username root --password cloudera --table zeyotab --m 1  --delete-target-dir --target-dir /user/cloudera/indir


==================
🔴 First Import data
==================


hadoop fs -ls /user/cloudera/indir

hadoop fs -cat /user/cloudera/indir/part-m-00000


==================
🔴 Add 2 more records in SQL
==================


mysql -uroot -pcloudera
use zeyodb;

insert into zeyotab values(3,'rani','chennai','cash');
insert into zeyotab values(5,'rani','chennai','cash');
insert into zeyotab values(6,'rita','delhi','credit');
select * from zeyotab;
quit

========================
🔴 SQOOP Incremental Import
========================

sqoop import --connect jdbc:mysql://localhost/zeyodb --username root --password cloudera --table zeyotab --m 1 --target-dir /user/cloudera/indir --incremental append --check-column id --last-value 4


==============================
🔴 NEW File with only 2 records
==============================


hadoop fs -ls /user/cloudera/indir

hadoop fs -cat /user/cloudera/indir/part-m-00001
