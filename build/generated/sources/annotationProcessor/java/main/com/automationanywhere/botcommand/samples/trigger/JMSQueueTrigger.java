package com.automationanywhere.botcommand.samples.trigger;

import com.automationanywhere.bot.service.Trigger2ListenerContext;
import com.automationanywhere.bot.service.TriggerException;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.toolchain.runtime.Trigger2;
import java.lang.ClassCastException;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JMSQueueTrigger implements Trigger2 {
  private static final Logger logger = LogManager.getLogger(JMSQueueTrigger.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  private static JMSQueueTrigger thisInstance = new JMSQueueTrigger();

  private final JMSQueue command = new JMSQueue();

  private JMSQueueTrigger() {
    super();
  }

  public static JMSQueueTrigger getInstance() {
    return thisInstance;
  }

  public Object clone() {
    return thisInstance;
  }

  public void startListen(Trigger2ListenerContext triggerListenerContext,
      Map<String, Value> parameters) throws TriggerException {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null);
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("brokerURL") && parameters.get("brokerURL") != null && parameters.get("brokerURL").get() != null) {
      convertedParameters.put("brokerURL", parameters.get("brokerURL").get());
      if(convertedParameters.get("brokerURL") !=null && !(convertedParameters.get("brokerURL") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","brokerURL", "String", parameters.get("brokerURL").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("brokerURL") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","brokerURL"));
    }

    if(parameters.containsKey("queueName") && parameters.get("queueName") != null && parameters.get("queueName").get() != null) {
      convertedParameters.put("queueName", parameters.get("queueName").get());
      if(convertedParameters.get("queueName") !=null && !(convertedParameters.get("queueName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","queueName", "String", parameters.get("queueName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("queueName") == null) {
      throw new TriggerException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","queueName"));
    }

    command.setRunnable(triggerListenerContext.getEventCallback());
    command.setTriggerUid(triggerListenerContext.getTriggerUid());
    try {
      command.startTrigger((String)convertedParameters.get("brokerURL"),(String)convertedParameters.get("queueName"));}
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
