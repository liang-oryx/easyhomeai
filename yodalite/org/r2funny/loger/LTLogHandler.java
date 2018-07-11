package org.r2funny.loger;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LTLogHander extends Formatter { 
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    @Override 
    public String format(LogRecord record) { 
    	return df.format(System.currentTimeMillis()) + " " + record.getLevel() + ":" + record.getMessage()+"\n"; 
    } 
}
