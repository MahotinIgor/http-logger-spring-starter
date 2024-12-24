package ru.mahotin.aspect;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import ru.mahotin.config.CustomProperties;
import ru.mahotin.config.LoggingLevel;
import ru.mahotin.exception.TaskNotFoundException;
import ru.mahotin.util.LogUtil;


@Aspect
public class TaskAspect {

    private final LogUtil logUtil;
    @Autowired
    private CustomProperties customProperties;

        static Logger log = (Logger)
            LoggerFactory.getLogger(TaskAspect.class);

    public TaskAspect(LogUtil logUtil) {
        this.logUtil = logUtil;
    }

    @PostConstruct
    public void init() {
        logUtil.changeLoggingLevel(
                customProperties
                        .getLevels()
                        .getOrDefault("aspect2", new LoggingLevel(LogLevel.INFO))
                        .logLevel(),
                AspectLogHttpRequestParam.class, log);
        log.info(" Setting logging level for aspect2");
        log.info(" If properties not found, setting default level = INFO ");
    }


    @Around("@annotation(ru.mahotin.aspect.annotation.LogCreateEntity)")
    public Object logCreateNewEntityFromDto(final ProceedingJoinPoint joinPoint) {

        log.info("Hello from aspect! Before create Task");

        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Hello from aspect! After create Task");
        log.info("Task = " + res);
        return res;
    }

    @Before("@annotation(ru.mahotin.aspect.annotation.LogTaskMapper) && args(task)")
    public void loggingBeforeMapping(final Object task) {
        log.info("Hello from aspect! Order = 2. Before Mapping args = " + task);
    }
    @After("@annotation(ru.mahotin.aspect.annotation.LogDeleteTask) && args(id)")
    public void loggingDeleteTask(final Long id) {
        log.info("Delete Task with id = " + id);
    }

    @AfterReturning(
            value = "execution(* ru.mahotin.*.impl.TaskServiceImpl.update(..))",
            returning = "result"
    )
    public void logReceivedDtoToUpdate(final Object result) {
        log.info("Before update method dto = " + result);
    }

    @AfterThrowing(
            value = "@annotation(ru.mahotin.aspect.annotation.LogErrorGetTaskById) && args(id)",
            throwing = "ex")
    public void logExceptionNotFoundUser(final TaskNotFoundException ex,
                                         final Long id) {

        log.error(ex.getMessage() + " with id = " + id);
    }
}
