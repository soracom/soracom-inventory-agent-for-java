package io.soracom.inventory.agent.core.lwm2m;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LWM2MObject {
	int objectId();
	String name();
	boolean multiple() default false;
}
