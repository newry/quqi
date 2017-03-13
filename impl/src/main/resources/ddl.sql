
    alter table USER_TRACKING 
        drop constraint if exists FK_n7bvqsvtxx1pf4p3fa700dqlg;

    drop table USERS cascade;

    drop table USER_AUTH_CODE cascade;

    drop table USER_SMS_HISTORY cascade;

    drop table USER_TRACKING cascade;

    create table USERS (
        ID  bigserial not null,
        CREATOR varchar(255) not null,
        DATE_ADDED timestamp not null,
        DATE_LAST_MODIFIED timestamp not null,
        STATUS varchar(32) not null,
        UPDATED_BY varchar(255) not null,
        mac_address varchar(255) not null,
        phone_number varchar(64),
        primary key (ID)
    );

    create table USER_AUTH_CODE (
        ID  bigserial not null,
        CREATOR varchar(255) not null,
        DATE_ADDED timestamp not null,
        DATE_LAST_MODIFIED timestamp not null,
        STATUS varchar(32) not null,
        UPDATED_BY varchar(255) not null,
        auth_code varchar(64),
        mac_address varchar(255) not null,
        phone_number varchar(64),
        primary key (ID)
    );

    create table USER_SMS_HISTORY (
        ID  bigserial not null,
        CREATOR varchar(255) not null,
        DATE_ADDED timestamp not null,
        DATE_LAST_MODIFIED timestamp not null,
        STATUS varchar(32) not null,
        UPDATED_BY varchar(255) not null,
        phone_number varchar(64) not null,
        primary key (ID)
    );

    create table USER_TRACKING (
        ID  bigserial not null,
        CREATOR varchar(255) not null,
        DATE_ADDED timestamp not null,
        DATE_LAST_MODIFIED timestamp not null,
        STATUS varchar(32) not null,
        UPDATED_BY varchar(255) not null,
        END_TIME timestamp,
        START_TIME timestamp not null,
        store varchar(255),
        USER_ID int8 not null,
        primary key (ID)
    );

    alter table USERS 
        add constraint UK_6pykqfs5r52qsubl2uptep04c unique (mac_address);

    alter table USER_TRACKING 
        add constraint FK_n7bvqsvtxx1pf4p3fa700dqlg 
        foreign key (USER_ID) 
        references USERS;
