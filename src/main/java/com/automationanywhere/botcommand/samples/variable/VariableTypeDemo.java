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
package com.automationanywhere.botcommand.samples.variable;

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.VariableExecute;
import com.automationanywhere.commandsdk.model.DataType;

/**
 * This example shows how to create an Variable.
 * <p>
 * Here we are returning a static value of one, however more complicated logics to return a computed value can be used.
 * 
 * @author Raj Singh Sisodia
 *
 */
@BotCommand(commandType = BotCommand.CommandType.Variable)
@CommandPkg(description = "Returns '1' as a String", name = "variableTypeDemo", label = "", variable_return_type = DataType.STRING)
public class VariableTypeDemo {
	
    @VariableExecute
    public StringValue get() {
    	String computedValue = "1";
        return new StringValue(computedValue);
    }

}
