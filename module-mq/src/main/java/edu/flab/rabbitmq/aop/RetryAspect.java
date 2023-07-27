package edu.flab.rabbitmq.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import edu.flab.rabbitmq.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class RetryAspect {

	@Around("@annotation(retry)")
	public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
		int maxRetry = retry.value();
		Exception exceptionHolder = null;

		for (int tryCount = 0; tryCount <= maxRetry; tryCount++) {
			try {
				if (tryCount > 0) {
					log.info("{} 재시도, 재시도 횟수={}/{}", joinPoint.getSignature().getName(), tryCount, maxRetry);
				}
				return joinPoint.proceed();
			} catch (Exception e) {
				exceptionHolder = e;
			}
		}

		log.error("{} 실행에 실패했습니다 <Parameters:{}>", joinPoint.getSignature().getName(), joinPoint.getArgs(),
			exceptionHolder);

		throw exceptionHolder;
	}
}
