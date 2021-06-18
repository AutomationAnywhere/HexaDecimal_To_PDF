package com.automationanywhere.botcommand.samples.trigger;

import com.automationanywhere.bot.service.Trigger2ListenerContext;
import com.automationanywhere.bot.service.TriggerException;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.toolchain.runtime.Trigger2;
import java.lang.ClassCastException;
import java.lang.Double;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerDemoTrigger implements Trigger2 {
  private static final Logger logger = LogManager.getLogger(TriggerDemoTrigger.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  private static TriggerDemoTrigger thisInstance = new TriggerDemoTrigger();

  private final TriggerDemo command = new TriggerDemo();

  private TriggerDemoTrigger() {
    super();
  }

  public static TriggerDemoTrigger getInstance() {
    return thisInstance;
  }

  public Object clone() {
    return thisInstance;
  }

  public void startListen(Trigger2ListenerContext triggerListenerContext,
      Map<String, Value> parameters) throws TriggerException {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null);
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("interval") && parameters.get("interval") != null && parameters.get("interval").get() != null) {
      convertedParameters.put("interval", parameters.get("interval").get());
      if(convertedParameters.get("interval") !=null && !(convertedParameters.get("interval") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","interval", "Double", parameters.get("interval").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("interval") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","interval"));
    }
    if(convertedParameters.containsKey("interval")) {
      try {
        if(convertedParameters.get("interval") != null && !((double)convertedParameters.get("interval") > 0)) {
          throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.GreaterThan","interval", "0"));
        }
      }
      catch(ClassCastException e) {
        throw new TriggerException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","interval", "Number", convertedParameters.get("interval").getClass().getSimpleName()));
      }
      catch(NullPointerException e) {
        throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","interval"));
      }
      if(convertedParameters.get("interval")!=null && !(convertedParameters.get("interval") instanceof Number)) {
        throw new TriggerException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","interval", "Number", convertedParameters.get("interval").getClass().getSimpleName()));
      }

    }
    command.setRunnable(triggerListenerContext.getEventCallback());
    command.setTriggerUid(triggerListenerContext.getTriggerUid());
    try {
      command.startTrigger((Double)convertedParameters.get("interval"));}
    catch (ClassCastException e) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.IllegalParameters","startTrigger"));
    }
    catch (TriggerException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  public void stopListen(String triggerUid) throws TriggerException {
    try {
      command.stopListen(triggerUid);}
    catch (TriggerException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  public void stopAllTriggers() throws TriggerException {
    try {
      command.stopAllTriggers();}
    catch (TriggerException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
