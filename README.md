# Blog-project
Blog게시판 프로젝트

## 시작하기전 SQL Developer에 테이블생성, 더미데이터 생성 후 시작하기

### 테이블,시퀀스 생성
``` sql
create table users(
    id number primary key,
    username varchar2(20),
    password varchar2(20),
    email varchar2(50),
    createdAt TIMESTAMP
);

CREATE SEQUENCE users_seq 
INCREMENT BY 1 
START WITH 1;

create table boards(
    id number primary key,
    title varchar2(150),
    content clob,
    usersId number,
    createdAt TIMESTAMP,
    CONSTRAINT fk_users_key FOREIGN KEY(usersId) REFERENCES users (id)
    );

CREATE SEQUENCE boards_seq 
INCREMENT BY 1 
START WITH 1;

commit;
```
### 더미데이터 추가
```
insert into users(id, username, password, email, createdAt) values(users_seq.nextval, 'ssar', '1234', 'ssar@nate.com', sysdate);
insert into users(id, username, password, email, createdAt) values(users_seq.nextval, 'cos', '1234', 'cos@nate.com', sysdate);
insert into users(id, username, password, email, createdAt) values(users_seq.nextval, 'hong', '1234', 'hong@nate.com', sysdate);
commit;
```
