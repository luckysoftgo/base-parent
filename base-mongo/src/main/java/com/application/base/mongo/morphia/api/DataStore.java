package com.application.base.mongo.morphia.api;

import java.lang.annotation.*;

/**
 * 
 * 用来标注多个数据源的.
 * 
 * @author admin
 *
 */
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataStore {

	String tagValue();
	
	String mongoDBName() default "";
	
}
