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
package com.automationanywhere.botcommand.samples.iterator;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.HasNext;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Inject;
import com.automationanywhere.commandsdk.annotations.Next;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.BotCommand.CommandType;
import com.automationanywhere.commandsdk.annotations.rules.GreaterThanEqualTo;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.annotations.rules.NumberInteger;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

/**
 * This example shows how to create an Iterator.
 * <p>
 * Here we are creating a simple loop which would run a specified number of times.
 * 
 * @author Raj Singh Sisodia
 *
 */
@BotCommand(commandType=CommandType.Iterator)
@CommandPkg(return_label = "Return the value in variable", node_label = ": {{times}} times", 
label = "Iterator demo", description = "Iterate number of times", name = "iteratorTypeDemo", return_type = DataType.NUMBER)
public class IteratorTypeDemo {
    @Idx(index = "1", type = AttributeType.NUMBER)
    @Pkg(label = "times", default_value = "10", default_value_type = DataType.NUMBER)
    @GreaterThanEqualTo("0")
    @NumberInteger
    @NotEmpty
    @Inject
	private Double times = 10d;

	private Double counter = 0d;

    @HasNext
    public boolean hasNext() {
		return counter < times;
    }

    @Next
    public Value<Double> next() throws Exception{
		if (counter >= times)
			throw new Exception("Counter '"+ counter +"' is exceed the times limit '"+times+"'");

		counter++;
        NumberValue result = new NumberValue();
        result.set(counter);
        return result;
    }

    public void setTimes(Double times) {
        this.times = times;
    }
}
