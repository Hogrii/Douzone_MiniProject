package DTO;

import java.util.Date;

import lombok.Data;

@Data
public class EapplyDTO {
	private int applyno;
	private int empno;
	private int holidayno;
	private int stateno;
	private Date start_date;
	private Date end_date;
	private String reason;	
}
