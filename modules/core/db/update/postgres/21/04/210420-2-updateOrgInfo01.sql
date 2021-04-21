alter table ORGINFO_ORG_INFO add column ORG_HEAD_EMAIL varchar(50) ^
update ORGINFO_ORG_INFO set ORG_HEAD_EMAIL = '' where ORG_HEAD_EMAIL is null ;
alter table ORGINFO_ORG_INFO alter column ORG_HEAD_EMAIL set not null ;
