package com.automationanywhere.botcommand.samples.iterator;

import com.automationanywhere.botcommand.CommandIterator;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Double;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class IteratorTypeDemoIterator implements CommandIterator {
  private static final Logger logger = LogManager.getLogger(IteratorTypeDemoIterator.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  private final IteratorTypeDemo command = new IteratorTypeDemo();

  public boolean hasNext(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null);
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("times") && parameters.get("times") != null && parameters.get("times").get() != null) {
      convertedParameters.put("times", parameters.get("times").get());
      if(convertedParameters.get("times") !=null && !(convertedParameters.get("times") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Double", parameters.get("times").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("times") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","times"));
    }
    if(convertedParameters.containsKey("times")) {
      try {
        if(convertedParameters.get("times") != null && !((double)convertedParameters.get("times") >= 0)) {
          throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.GreaterThanEqualTo","times", "0"));
        }
      }
      catch(ClassCastException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Number", convertedParameters.get("times").getClass().getSimpleName()));
      }
      catch(NullPointerException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","times"));
      }
      if(convertedParameters.get("times")!=null && !(convertedParameters.get("times") instanceof Number)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Number", convertedParameters.get("times").getClass().getSimpleName()));
      }
      try {
        command.setTimes( (Double)convertedParameters.get("times"));
      }
      catch (ClassCastException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.ClassCastException","times", "Double", parameters.get("times") != null ? (parameters.get("times").get() != null ? parameters.get("times").get().getClass().toString() : "null") : "null"),e);
      }

    }
    try {
      boolean result = command.hasNext();
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","hasNext"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  @Deprecated
  public Optional<Map<String, Value>> next(Map<String, Value> parameters,
      Map<String, Object> sessionMap) {
    throw new BotCommandException(MESSAGES_GENERIC.getString("generic.DepricatedNextMethod"));
  }

  public Optional<Value> nextOne(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null);
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("times") && parameters.get("times") != null && parameters.get("times").get() != null) {
      convertedParameters.put("times", parameters.get("times").get());
      if(convertedParameters.get("times") !=null && !(convertedParameters.get("times") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Double", parameters.get("times").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("times") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","times"));
    }
    if(convertedParameters.containsKey("times")) {
      try {
        if(convertedParameters.get("times") != null && !((double)convertedParameters.get("times") >= 0)) {
          throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.GreaterThanEqualTo","times", "0"));
        }
      }
      catch(ClassCastException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Number", convertedParameters.get("times").getClass().getSimpleName()));
      }
      catch(NullPointerException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","times"));
      }
      if(convertedParameters.get("times")!=null && !(convertedParameters.get("times") instanceof Number)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","times", "Number", convertedParameters.get("times").getClass().getSimpleName()));
      }
      try {
        command.setTimes( (Double)convertedParameters.get("times"));
      }
      catch (ClassCastException e) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.ClassCastException","times", "Double", parameters.get("times") != null ? (parameters.get("times").get() != null ? parameters.get("times").get().getClass().toString() : "null") : "null"),e);
      }

    }
    try {
      Optional<Value> result =  Optional.ofNullable(command.next());
      logger.traceExit(result);
      return result;
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","next"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
