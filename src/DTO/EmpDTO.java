package DTO;

import java.util.Date;

import lombok.Data;

@Data
public class EmpDTO {
	private int empno;
	private int rankno;
	private int deptno;
	private int mgr;
	private String ename;
	private String id_number;
	private int age;
	private String tel;
	private Date hiredate;
	private String email;
	private String addr;
}
