CREATE TABLE emp (
	empno	Number		NOT NULL,
	rankno	Number		NOT NULL,
	deptno	Number		NOT NULL,
	mgr	Number		NULL,
	ename	varchar2(20)		NULL,
	id_number	varchar2(20)		NULL,
	age	Number		NULL,
	tel	varchar2(13)		NULL,
	hiredate	Date		NULL,
	email	varchar2(50)		NULL,
	addr	varchar2(80)		NULL
);

CREATE TABLE dept (
	deptno	Number		NOT NULL,
	dname	varchar2(20)		NULL
);

CREATE TABLE erank (
	rankno	Number		NOT NULL,
	rname	varchar2(20)		NULL
);

CREATE TABLE holiday (
	holidayno	Number		NOT NULL,
	hname	varchar2(20)		NULL
);

CREATE TABLE rest_holiday (
	empno	Number		NOT NULL,
	restday	Number		NULL
);

CREATE TABLE estate (
	stateno	Number		NOT NULL,
	sinfo	varchar2(10)		NULL
);

CREATE TABLE eapply (
	applyno	Number		NOT NULL,
	empno	Number		NOT NULL,
	holidayno	Number		NOT NULL,
	stateno	Number		NOT NULL,
	start_date	Date		NULL,
	end_date	Date		NULL,
	reason	varchar2(500)		NULL
);

ALTER TABLE emp ADD CONSTRAINT "PK_EMP" PRIMARY KEY (
	empno
);

ALTER TABLE dept ADD CONSTRAINT "PK_DEPT" PRIMARY KEY (
	deptno
);

ALTER TABLE erank ADD CONSTRAINT "PK_RANK" PRIMARY KEY (
	rankno
);

ALTER TABLE holiday ADD CONSTRAINT "PK_HOLIDAY" PRIMARY KEY (
	holidayno
);

ALTER TABLE rest_holiday ADD CONSTRAINT "PK_REST_HOLIDAY" PRIMARY KEY (
	empno
);

ALTER TABLE estate ADD CONSTRAINT "PK_STATE" PRIMARY KEY (
	stateno
);

ALTER TABLE eapply ADD CONSTRAINT "PK_APPLY" PRIMARY KEY (
	applyno
);

ALTER TABLE emp ADD CONSTRAINT "FK_rank_TO_emp_1" FOREIGN KEY (
	rankno
)
REFERENCES erank (
	rankno
);

ALTER TABLE emp ADD CONSTRAINT "FK_dept_TO_emp_1" FOREIGN KEY (
	deptno
)
REFERENCES dept (
	deptno
);

ALTER TABLE rest_holiday ADD CONSTRAINT "FK_emp_TO_rest_holiday_1" FOREIGN KEY (
	empno
)
REFERENCES emp (
	empno
);

ALTER TABLE eapply ADD CONSTRAINT "FK_emp_TO_apply_1" FOREIGN KEY (
	empno
)
REFERENCES emp (
	empno
);

ALTER TABLE eapply ADD CONSTRAINT "FK_holiday_TO_apply_1" FOREIGN KEY (
	holidayno
)
REFERENCES holiday (
	holidayno
);

ALTER TABLE eapply ADD CONSTRAINT "FK_state_TO_apply_1" FOREIGN KEY (
	stateno
)
REFERENCES estate (
	stateno
);

select * from emp;
select * from eapply;
select * from holiday;
select * from estate;

CREATE SEQUENCE emp_seq increment by 1 start with 1;
CREATE SEQUENCE eapply_seq increment by 1 start with 1;

SELECT eapply_seq.currval from dual;
select emp_seq.currval from dual;

select * from eapply;
select * from eapply where sysdate - start_date > 0;
select sysdate-start_date from eapply where applyno = 1;
select end_date-start_date from eapply;

select a.applyno, a.empno, a.holidayno, a.stateno, a.start_date, a.end_date, a.reason 
from eapply a join emp e on (a.empno = e.empno)
where e.mgr = 1;

select * from eapply order by 1;
select * from emp where mgr = 1;

select * from rest_holiday;
rollback;
