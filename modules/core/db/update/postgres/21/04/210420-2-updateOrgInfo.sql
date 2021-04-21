alter table ORGINFO_ORG_INFO rename column org_head_email to org_head_email__u84608 ;
alter table ORGINFO_ORG_INFO alter column org_head_email__u84608 drop not null ;
