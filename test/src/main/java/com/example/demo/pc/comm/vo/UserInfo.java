package com.example.demo.pc.comm.vo;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo {
	
	private String USER_IDXX;
	private String SCRT_NUMB;
	private String USDN_CODE;
	private String EMPL_NUMB;
	private String USER_NAME;
	private String DEPT_CODE;
	private String CUST_CODE;
	private String USEX_YSNO;
	private String REMK_100X;
	private String INST_USID;
	private Date INST_DATE;
	private String UPDT_USID;
	private Timestamp UPDT_DATE;
	
	
}
