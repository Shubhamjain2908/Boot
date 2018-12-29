package org.sj.entity;

import lombok.Data;

@Data
public class Log 
{
	private long debug;
	private long info;
	private long warn;
	private long error;
	private long trace;
}
