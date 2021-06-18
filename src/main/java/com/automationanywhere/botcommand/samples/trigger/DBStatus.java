/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

package com.automationanywhere.botcommand.samples.trigger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.StartListen;
import com.automationanywhere.commandsdk.annotations.StopAllTriggers;
import com.automationanywhere.commandsdk.annotations.StopListen;
import com.automationanywhere.commandsdk.annotations.TriggerId;
import com.automationanywhere.commandsdk.annotations.TriggerRunnable;
import com.automationanywhere.commandsdk.annotations.rules.GreaterThan;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.annotations.rules.NumberInteger;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.core.security.SecureString;

/**
 * This example is an extension of the timer based trigger {@link TriggerDemo} and demonstrates the
 * pull mechanism.
 * <p>
 * Trigger will check the DB at regular interval and if the records returned by
 * user provided SQL is more than 0 then it will get triggered.
 * <p>
 * NOTE 1: This class is for illustrative purpose only not safe for use in production.<br>
 * NOTE 2: Please add the valid DB driver in build gradle to run this example.
 * 
 * 
 * @author Raj Singh Sisodia
 */
@BotCommand(commandType = BotCommand.CommandType.Trigger)
@CommandPkg(label = "JDBC Query Trigger", description = "JDBC Query Trigger", icon = "jdbc.svg", name = "jdbcQueryTrigger")
public class DBStatus {
	
	private static Logger logger = LogManager.getLogger(DBStatus.class);
	
	
	// Map storing multiple tasks
	private static final Map<String, TimerTask> taskMap = new ConcurrentHashMap<>();
	private static final Timer TIMER = new Timer(true);

	@TriggerId
	private String triggerUid;
	@TriggerRunnable
	private Runnable runnable;

	/*
	 * Starts the trigger.
	 */
	@StartListen
	public void startTrigger(
			@Idx(index="1", type = AttributeType.TEXT)
			@Pkg(label = "Please provide the database driver class")
			@NotEmpty
			String driverClassName,
			
			@Idx(index="2", type = AttributeType.TEXT)
			@Pkg(label = "Please provide the Jdbc connection string")
			@NotEmpty
			String jdbcUrl,
			
			@Idx(index="3", type = AttributeType.TEXT)
			@Pkg(label = "Please provide the user Name")
			@NotEmpty
			String userName,
			
			@Idx(index="4", type = AttributeType.CREDENTIAL)
			@Pkg(label = "Please provide the password")
			@NotEmpty
			SecureString password,
			
			@Idx(index="5", type = AttributeType.TEXT)
			@Pkg(label = "Please provide the SQL to check the records")
			@NotEmpty
			String sqlQuery,
			
			@Idx(index = "6", type = AttributeType.NUMBER)
			@Pkg(label = "Please provide the interval to query in seconds", default_value = "300", default_value_type = DataType.NUMBER)
			@GreaterThan("0")
			@NumberInteger
			@NotEmpty
			Double interval) {
		
		DataSource dataSource = getDataSource(driverClassName, jdbcUrl, userName, password);
		
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				logger.debug("checking DB");
				try {
					if(checkRecordsExist(dataSource.getConnection(), sqlQuery)){
						runnable.run();
						return;
					}
				} catch (SQLException e) {
					logger.warn(e.getMessage(),e);
					logger.warn("Trigger is still running.");
				}
				logger.debug("no recorrds found");

			}
		};

		taskMap.put(this.triggerUid, timerTask);
		TIMER.schedule(timerTask, interval.longValue(), interval.longValue());
	}

	/*
	 * Cancel all the task and clear the map.
	 */
	@StopAllTriggers
	public void stopAllTriggers() {
		taskMap.forEach((k, v) -> {
			if (v.cancel()) {
				taskMap.remove(k);
			}
		});
	}

	/*
	 * Cancel the task and remove from map
	 *
	 * @param triggerUid
	 */
	@StopListen
	public void stopListen(String triggerUid) {
		if (taskMap.get(triggerUid).cancel()) {
			taskMap.remove(triggerUid);
		}
	}

    public static DataSource getDataSource(String driverClassName, String url, String userName,SecureString password) {
    	BasicDataSource ds = new BasicDataSource();
    	ds.setDriverClassName(driverClassName);
    	ds.setUrl(url);
    	ds.setUsername(userName);
    	ds.setPassword(password.getInsecureString());
        return ds;
    }
	
    public static boolean checkRecordsExist(Connection con, String query)
    	    throws SQLException {
			
    	    Statement stmt = null;
    	    try {
    	        stmt = con.createStatement();
    	        ResultSet rs = stmt.executeQuery(query);
    	        rs.last();
    	        if(rs.getRow() > 0)
    	        	return true;
    	    } catch (SQLException e ) {
    	        throw new BotCommandException("Problem running statemnt", e);
    	    } finally {
    	        if (stmt != null) { stmt.close(); }
    	    }
    	    
    	    return false;
    	}
    
	public String getTriggerUid() {
		return triggerUid;
	}

	public void setTriggerUid(String triggerUid) {
		this.triggerUid = triggerUid;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
