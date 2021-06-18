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
package com.automationanywhere.botcommand.samples.conditional;

import static com.automationanywhere.commandsdk.annotations.BotCommand.CommandType.Condition;
import static com.automationanywhere.commandsdk.model.DataType.BOOLEAN;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.ConditionTest;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.annotations.rules.VariableType;
import com.automationanywhere.commandsdk.model.AttributeType;

/**
 * 
 * This example shows how to create an Condition.
 * <p>
 * Here we are checking of the provided boolean value is false.
 * 
 * 
 * @author Raj Singh Sisodia
 *
 */
@BotCommand(commandType = Condition)
@CommandPkg(label = "False condition demo", name = "conditionalTypeDemo",
        description = "Checks the boolean variable is false.")
public class ConditionalTypeDemo {
	
	 @ConditionTest
	    public Boolean validate(
	            @Idx(index = "1", type = AttributeType.BOOLEAN)
	            @VariableType(BOOLEAN)
	            @Pkg(label = "Boolean variable", default_value_type = BOOLEAN) @NotEmpty Boolean variable
	    ) {
	        return variable == null ? false : !variable ;
	    }

}
