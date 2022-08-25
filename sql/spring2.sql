-- (관리자계정) spring2 일반계정 생성
alter session set "_oracle_script" = true;

create user spring2
IDENTIFIED by spring2
default tablespace users;

alter user spring2 quota unlimited on users;

grant connect, resource to spring2;

-- (spring 계정) 
-- dev 테이블 생성
create table dev(
    no number,
    name varchar2(100) not null,
    career number not null,
    email varchar2(200) not null,
    gender char(1),
    lang varchar2(100) not null,
    created_at date default sysdate,
    constraint pk_dev_no primary key(no),
    constraint ck_dev_gender check(gender in ('M', 'F'))
);

create sequence seq_dev_no;

select * from dev order by no;
delete from dev where no in (2,3,6,7,8,9,22,23,24,25);
commit;

-- 회원테이블 생성
create table member(
    member_id varchar2(50),
    password varchar2(300) not null,
    name varchar2(256) not null,
    gender char(1),
    birthday date,
    email varchar2(256),
    phone char(11) not null,
    address varchar2(512),
    hobby varchar2(256),
    created_at date default sysdate,
    updated_at date,
    enabled number default 1, -- 1, 0
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in ('M', 'F')),
    constraint ck_member_enabled check(enabled in (1, 0))
);

insert into spring2.member values ('abcde','1234','아무개','M',to_date('88-01-25','rr-mm-dd'),'abcde@naver.com','01012345678','서울시 강남구','운동,등산,독서',default, null, default);
insert into spring2.member values ('qwerty','1234','김말년','F',to_date('78-02-25','rr-mm-dd'),'qwerty@naver.com','01098765432','서울시 관악구','운동,등산',default, null, default);
insert into spring2.member values ('admin','1234','관리자','F',to_date('90-12-25','rr-mm-dd'),'admin@naver.com','01012345678','서울시 강남구','독서',default, null, default);
commit;

select * from member;
delete from member where member_id = 'honggd';
commit;

update member set password = '$2a$12$6B.P2BndwvYrNwX0N6L96O6zfMq5yGpDzY6wK9Hl/.6R.0Ibl05P6' where member_id = 'abcde';
update member set password = '$2a$12$iI7usZkZemHKmK7akM8CE.eDjFumf7sDm5KCw8lpu14gD9GuilxwK' where member_id = 'qwerty';
update member set password = '$2a$12$nKwgZQiaHGfwfrMEcZydSuPFymIOw0OjeDA9L0Jc5RVOBOCTpi6sG' where member_id = 'admin';
commit;

-- todo 테이블 생성
create table todo(
    no number,
    todo varchar2(2000) not null,
    created_at date default sysdate,
    completed_at date,
    constraint pk_todo_no primary key(no)
);

create sequence seq_todo_no;

insert into todo values(seq_todo_no.nextval, '우산 청소하기', default, null);
insert into todo values(seq_todo_no.nextval, '형광등 교체', default, null);
insert into todo values(seq_todo_no.nextval, '장 보기', default, null);
insert into todo values(seq_todo_no.nextval, '차에 물 퍼내기', default, null);

select * from todo;

update todo set completed_at = sysdate where no = 4;
update todo set completed_at = sysdate where no = 2;
commit;

select * from todo;

select * from todo order by decode(completed_at, null, 1), completed_at desc;

select
    *
from
    todo
order by
    (case when completed_at is null then 1 end), completed_at desc;
    
select * from (select * from todo where completed_at is null order by no)
union all
select * from (select * from todo where completed_at is not null order by completed_at desc);

select * from todo order by completed_at desc nulls first, no;