package ru.mahotin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import ru.mahotin.util.LogUtil;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@Aspect
public class AspectLogHttpRequestParam {

    private final Logger log =
            LoggerFactory.getLogger(AspectLogHttpRequestParam.class);

    private final LogUtil logUtil;
    private  final LogLevel logLevel;

    public AspectLogHttpRequestParam(final LogUtil logUtil, final LogLevel logLevel) {
        this.logUtil = logUtil;
        this.logLevel = logLevel;
    }
    @Around(value = "@annotation(ru.mahotin.aspect.annotation.LogHttpRequestParam)")
    public Object getAccountOperationInfo(final ProceedingJoinPoint joinPoint) {

        Object res = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        logUtil.logger(logLevel, log, "full method description: " +  signature.getMethod());
        logUtil.logger(logLevel, log, "method name: " + signature.getMethod().getName());
        logUtil.logger(logLevel, log, "declaring type: " + signature.getDeclaringType());

        logUtil.logger(logLevel, log, "Method args types:");
        Arrays.stream(signature.getParameterTypes())
                .forEach(s -> logUtil.logger(logLevel, log, "arg type: " + s));

        logUtil.logger(logLevel, log, "Method args values:");
        Arrays.stream(joinPoint.getArgs())
                .forEach(o -> logUtil.logger(logLevel, log, "arg value: " + o.toString()));

        logUtil.logger(logLevel, log, "returning type: " + signature.getReturnType());
        logUtil.logger(logLevel, log, "method modifier: " + Modifier.toString(signature.getModifiers()));
        Arrays.stream(signature.getExceptionTypes())
                .forEach(aClass -> logUtil.logger(logLevel, log, "exception type: " + aClass));

        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        logUtil.logger(logLevel, log, "Result = " + res);
        return res;
    }
}