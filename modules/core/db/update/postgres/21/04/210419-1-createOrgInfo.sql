create table ORGINFO_ORG_INFO (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ORG_NAME varchar(50) not null,
    ORG_CODE varchar(50) not null,
    ORG_TYPE varchar(50) not null,
    ORG_HEAD_EMAIL varchar(50) not null,
    --
    primary key (ID)
);