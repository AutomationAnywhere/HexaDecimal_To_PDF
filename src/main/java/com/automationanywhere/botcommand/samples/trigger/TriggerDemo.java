/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

/**
 * 
 */
package com.automationanywhere.botcommand.samples.trigger;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

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

/**
 * There will be a singleton instance of this class. Whenever a new trigger is
 * created it comes with a triggerId. In this sample we create multiple tasks
 * and store them in a map with these triggerId as the key. Once the condition
 * of trigger matches we call the run method of the runnable to signal the
 * trigger.
 * <p>
 * In the following example we will create a trigger which triggers are user
 * specified intervals.
 * 
 * @author Raj Singh Sisodia
 *
 */
@BotCommand(commandType = BotCommand.CommandType.Trigger)
@CommandPkg(label = "Demo Trigger", description = "Demo Trigger", icon = "email.svg", name = "demoTrigger")
public class TriggerDemo {

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
	public void startTrigger(@Idx(index = "1", type = AttributeType.NUMBER)
	@Pkg(label = "Please provide the interval to trigger in seconds", default_value = "120", default_value_type = DataType.NUMBER)
	@GreaterThan("0")
	@NumberInteger
	@NotEmpty
	Double interval) {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				runnable.run();

			}
		};

		taskMap.put(this.triggerUid, timerTask);
		TIMER.schedule(timerTask, interval.longValue());
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
