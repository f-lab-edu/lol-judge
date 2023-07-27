package edu.flab.rabbitmq.annotation;

public @interface Retry {
	int value() default 3;
}
