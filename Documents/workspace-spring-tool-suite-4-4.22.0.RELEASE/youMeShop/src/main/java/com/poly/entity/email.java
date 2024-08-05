package com.poly.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class email {
	
	String form="XuanTruong";
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	List<File> file= new ArrayList<>();
	
	public email(String to, String subject,String body)
	{
		this.form="FPT Polytechnic <poly@fpt.edu.vn>";
		this.to=to;
		this.subject=subject;
		this.body=body;
	}
}
