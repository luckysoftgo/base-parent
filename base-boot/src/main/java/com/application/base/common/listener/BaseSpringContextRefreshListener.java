package com.application.base.common.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @desc Spring容器刷新监听器，当Spring容器加载完毕后执行该事件监听器，
 * 如果还需其他在功能在spring容器加载后完成，请直接在方法onApplicationEvent中添加相应功能
 * 还可以通过实现ContextClosedEvent、ContextStartedEvent、ContextStoppedEvent、RequestHandleEvent等事件实现相关功能，详情请参照相关API 
 * @ClassName:  BaseSpringContextRefreshListener   
 * @author 孤狼
 */
public abstract class BaseSpringContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 如果为父容器才加载结果消息容器，使用SpringMVC会启动两个spring容器，MVC容器为主容器的子容器，启动MVC容器不需要加载消息容器
		if (event.getApplicationContext().getParent() == null) {
			doListen(event);
		}
	}

	/**
	 * 处理容器监听时间方法，需要在子类实现不同的功能
	 * @param event
	 * void
	 *
	 */
	protected abstract void doListen(ContextRefreshedEvent event);

}
