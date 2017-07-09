package io.soracom.inventory.agent.core.lwm2m;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

	int resourceId();

	Operation operation();

	boolean multiple() default false;
}
