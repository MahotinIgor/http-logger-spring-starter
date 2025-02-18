package ru.mahotin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mahotin.exception.TaskNotFoundException;

@Aspect
public class TaskAspect {
    private final Logger log = LoggerFactory.getLogger(TaskAspect.class);

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
