package org.example.c9laboration2.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class LoggingInterceptor {

  Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  @AroundInvoke
  public Object logMethodEntry(InvocationContext ctx) throws Exception {
    logger.info("Entering method: " + ctx.getMethod().getName());
    try {
      return ctx.proceed();
    } finally {
      logger.info("Exiting method: " + ctx.getMethod().getName());
    }
  }
}
