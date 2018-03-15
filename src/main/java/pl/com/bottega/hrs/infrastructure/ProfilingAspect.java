package pl.com.bottega.hrs.infrastructure;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {

    @Around("execution(* pl.com.bottega.hrs.application.Handler.handle(..))")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        long ts = System.currentTimeMillis();
        try {
            Object ret = joinPoint.proceed();
            logTime(joinPoint, ts);
            return ret;
        } catch (Throwable ex) {
            logTime(joinPoint, ts);
            throw ex;
        }
    }

    private void logTime(ProceedingJoinPoint joinPoint, long ts) {
        long te = System.currentTimeMillis();
        long t = te - ts;
        String msg = String.format("Execution %s took %s ms", joinPoint.getSignature(), t);
        Logger.getLogger(ProfilingAspect.class).info(msg);
    }

}