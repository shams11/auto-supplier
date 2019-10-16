/*
 * Copyright (c) 2019 Afreen, Inc.
 * All rights reserved. Patents pending.
 */
package com.auto.supplier.commons.aspects;

import com.auto.supplier.commons.utils.LoggingLevel;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingInterceptorAspect {
  private static final String FORMATTER_ARRAY_EMPTY = "[]";
  private static final String FORMATTER_ARRAY_SINGLE = "[%s]";
  private static final String FORMATTER_ARRAY = "[%s,...,%s]";

  @Pointcut("within(@com.auto.supplier.commons.utils.LoggingProfiler *)")
  public void beanAnnotatedWithLoggingProfiler() {
  }

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {
  }

  @Pointcut("publicMethod() && beanAnnotatedWithLoggingProfiler()")
  public void publicMethodInsideAClassMarkedWithAtMonitor() {
  }

  @Around("publicMethodInsideAClassMarkedWithAtMonitor()")
  Object aroundTxnMethod(ProceedingJoinPoint point) throws Throwable {
    return loggingMethod(point);
  }

  private Object loggingMethod(ProceedingJoinPoint point) throws Throwable {
    Object result = null;
    Stopwatch stopWatch = Stopwatch.createStarted();

    MethodSignature signature = (MethodSignature) point.getSignature();
    LoggingLevel level = getLoggingLevel(point, signature);

    log(level, "Entering method [{}.{}({})]",
        signature.getDeclaringTypeName(),
        signature.getName(),
        getMethodArguments(point));
    try {
      result = point.proceed();
      return result;
    } finally {
      stopWatch.stop();
      log(level, "Leaving method [{}.{}(): {}]; {}",
          signature.getDeclaringTypeName(),
          signature.getName(),
          formatResultToBeLogged(result),
          stopWatch.elapsed(TimeUnit.MILLISECONDS));
    }
  }

  private LoggingLevel getLoggingLevel(ProceedingJoinPoint joinPoint, MethodSignature signature)
      throws Throwable {
    // First looking at the class level
    LoggingProfiler loggingClassAnnotation;
    if (signature.getDeclaringType().isInterface()) {
      loggingClassAnnotation = joinPoint.getTarget().getClass().getAnnotation(LoggingProfiler
          .class);
    } else {
      loggingClassAnnotation = (LoggingProfiler) signature.getDeclaringType()
          .getAnnotation(LoggingProfiler.class);
    }


    // Search if there is a method level override
    Method method = signature.getMethod();
    if (signature.getDeclaringType().isInterface()) {
      method = joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(), method
          .getParameterTypes());
    }
    LoggingProfiler loggingMethodAnnotation = method.getAnnotation(LoggingProfiler.class);

    //When Both Class and method level annotations are null return DEBUG
    if (loggingMethodAnnotation == null && loggingClassAnnotation == null) {
      // Default to DEBUG level
      return LoggingLevel.DEBUG;
    }

    //First preference for method level annotation
    if (loggingMethodAnnotation != null) {
      return loggingMethodAnnotation.value();
    }

    return loggingClassAnnotation.value();
  }

  private String getMethodArguments(ProceedingJoinPoint point) {
    return Joiner.on(",").skipNulls().join(point.getArgs());
  }

  private void log(LoggingLevel level, String format, Object... arguments) {
    switch (level) {
      case DEBUG:
        log.debug(format, arguments);
        break;
      case INFO:
        log.info(format, arguments);
        break;
      default:
        log.debug(format, arguments);
        break;
    }
  }

  private Object formatResultToBeLogged(Object resultObj) {
    if (resultObj instanceof byte[]) {
      // Do not log the full byte array as it will pollute the log file
      return formatByteArray((byte[]) resultObj);
    }
    return resultObj;
  }

  private String formatByteArray(byte[] array) {
    if (array.length == 0) {
      return FORMATTER_ARRAY_EMPTY;
    } else if (array.length == 1) {
      return String.format(FORMATTER_ARRAY_SINGLE, array[0]);
    } else {
      return String.format(FORMATTER_ARRAY, array[0], array[array.length - 1]);
    }
  }
}
