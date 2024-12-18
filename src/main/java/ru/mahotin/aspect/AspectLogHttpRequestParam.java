package ru.mahotin.aspect;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import ru.mahotin.config.CustomProperties;
import ru.mahotin.config.LoggingLevel;
import ru.mahotin.util.LogUtil;

import java.lang.reflect.Modifier;
import java.util.Arrays;

@Aspect
public class AspectLogHttpRequestParam {

    static final Logger log =
            LoggerFactory.getLogger(AspectLogHttpRequestParam.class);

    private final LogUtil logUtil;

    @Autowired
    private CustomProperties customProperties;

    public AspectLogHttpRequestParam(final LogUtil logUtil) {
        this.logUtil = logUtil;
    }

    @PostConstruct
    public void init() {
        logUtil.changeLoggingLevel(
                customProperties
                        .getLevels()
                        .getOrDefault("aspect1", new LoggingLevel(LogLevel.INFO))
                        .logLevel(),
                AspectLogHttpRequestParam.class, log);
        log.info(" Setting logging level for aspect1");
        log.info(" If properties not found, setting default level = INFO ");
    }

    @Around(value = "@annotation(ru.mahotin.aspect.annotation.LogHttpRequestParam)")
    public Object getAccountOperationInfo(final ProceedingJoinPoint joinPoint) {

        Object res = null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        log.info("full method description: " + signature.getMethod());
        log.info("method name: " + signature.getMethod().getName());
        log.info("declaring type: " + signature.getDeclaringType());

        log.info("Method args types:");
        Arrays.stream(signature.getParameterTypes())
                .forEach(s -> log.info("arg type: " + s));

        log.info("Method args values:");
        Arrays.stream(joinPoint.getArgs())
                .forEach(o -> System.out.println("arg value: " + o.toString()));

        log.info("returning type: " + signature.getReturnType());
        log.info("method modifier: " + Modifier.toString(signature.getModifiers()));
        Arrays.stream(signature.getExceptionTypes())
                .forEach(aClass -> log.info("exception type: " + aClass));

        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        log.info("Result = " + res);
        log.error("Result = " + res);
        return res;
    }
}
