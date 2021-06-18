package com.automationanywhere.botcommand.samples.trigger;

import com.automationanywhere.bot.service.Trigger2ListenerContext;
import com.automationanywhere.bot.service.TriggerException;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.core.security.SecureString;
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

public class DBStatusTrigger implements Trigger2 {
  private static final Logger logger = LogManager.getLogger(DBStatusTrigger.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  private static DBStatusTrigger thisInstance = new DBStatusTrigger();

  private final DBStatus command = new DBStatus();

  private DBStatusTrigger() {
    super();
  }

  public static DBStatusTrigger getInstance() {
    return thisInstance;
  }

  public Object clone() {
    return thisInstance;
  }

  public void startListen(Trigger2ListenerContext triggerListenerContext,
      Map<String, Value> parameters) throws TriggerException {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null);
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("driverClassName") && parameters.get("driverClassName") != null && parameters.get("driverClassName").get() != null) {
      convertedParameters.put("driverClassName", parameters.get("driverClassName").get());
      if(convertedParameters.get("driverClassName") !=null && !(convertedParameters.get("driverClassName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","driverClassName", "String", parameters.get("driverClassName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("driverClassName") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","driverClassName"));
    }

    if(parameters.containsKey("jdbcUrl") && parameters.get("jdbcUrl") != null && parameters.get("jdbcUrl").get() != null) {
      convertedParameters.put("jdbcUrl", parameters.get("jdbcUrl").get());
      if(convertedParameters.get("jdbcUrl") !=null && !(convertedParameters.get("jdbcUrl") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","jdbcUrl", "String", parameters.get("jdbcUrl").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("jdbcUrl") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","jdbcUrl"));
    }

    if(parameters.containsKey("userName") && parameters.get("userName") != null && parameters.get("userName").get() != null) {
      convertedParameters.put("userName", parameters.get("userName").get());
      if(convertedParameters.get("userName") !=null && !(convertedParameters.get("userName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","userName", "String", parameters.get("userName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("userName") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","userName"));
    }

    if(parameters.containsKey("password") && parameters.get("password") != null && parameters.get("password").get() != null) {
      convertedParameters.put("password", parameters.get("password").get());
      if(convertedParameters.get("password") !=null && !(convertedParameters.get("password") instanceof SecureString)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","password", "SecureString", parameters.get("password").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("password") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","password"));
    }

    if(parameters.containsKey("sqlQuery") && parameters.get("sqlQuery") != null && parameters.get("sqlQuery").get() != null) {
      convertedParameters.put("sqlQuery", parameters.get("sqlQuery").get());
      if(convertedParameters.get("sqlQuery") !=null && !(convertedParameters.get("sqlQuery") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sqlQuery", "String", parameters.get("sqlQuery").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("sqlQuery") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sqlQuery"));
    }

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
      command.startTrigger((String)convertedParameters.get("driverClassName"),(String)convertedParameters.get("jdbcUrl"),(String)convertedParameters.get("userName"),(SecureString)convertedParameters.get("password"),(String)convertedParameters.get("sqlQuery"),(Double)convertedParameters.get("interval"));}
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
