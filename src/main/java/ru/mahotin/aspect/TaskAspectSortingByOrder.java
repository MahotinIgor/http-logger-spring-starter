package ru.mahotin.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.mahotin.entity.Task;


@Aspect
public class TaskAspectSortingByOrder {

    static final Logger log =
            LoggerFactory.getLogger(TaskAspectSortingByOrder.class);

    @Before("@annotation(ru.mahotin.aspect.annotation.LogTaskMapper) && args(task)")
    public void loggingBeforeMapping(final Object task) {
        log.info("Hello from aspect! Order = 1. Before Mapping args = " + task);


    }
}
