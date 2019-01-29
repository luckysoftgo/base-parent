package com.application.base.utils.common.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 孤狼
 * @desc: 接口限流的 java 实现操作.
    1 java信号量是随着消费而减少，直到无法再消费时等待，消费完需要释放信号量
	1.2 guava的RateLimiter是固定时间产生固定的信号量，如果当前时间比产生一个信号量的时间要早，则无法消费，需要等待到下一次产生信号量的时候。
	1.3 guava的RateLimiter不需要去释放信号量，所以假如一个请求执行了很久也不会影响到下一个请求的执行时间，这点有利也有弊，对于有限资源来说，会引起并发的一些问题
	其次，与java信号量类似，有可能需要等待的acquire的方法，也有立即返回的tryAcquire的方式，tryAcquire也有允许等待多时时间的重载方法.
    如果感觉不够优雅:参见 https://blog.csdn.net/fanrenxiang/article/details/80683378
 */
public class GuavaRateLimiterUtil implements Serializable {
	
	public static void main(String[] args) {
		// 这里的0.1表示每秒允许处理的量为0.1个
		RateLimiter rateLimiter = getLimiter(0.1);
		List<Runnable> tasks = new ArrayList<Runnable>();
		for (int i = 0; i < 10; i++) {
			tasks.add(new UserRequest(i));
		}
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (Runnable runnable : tasks) {
			System.out.println("等待时间：" + rateLimiter.acquire());
			threadPool.execute(runnable);
		}
	}
	
	private static class UserRequest implements Runnable {
		private int id;
		public UserRequest(int id) {
			this.id = id;
		}
		@Override
		public void run() {
			System.out.println(id);
		}
	}
	
	/**
	 * 创建限流对象: 这里的 permitsPerSecond 表示每秒允许处理的量为 permitsPerSecond 个,permitsPerSecond 越小,则等待的时间越长.
	 * @param permitsPerSecond:过滤的时间率比.
	 * @param warmupPeriod:时间超长.
	 * @param unit:时间单位
	 * @return
	 */
	public static RateLimiter getLimiter(double permitsPerSecond, long warmupPeriod, TimeUnit unit){
		return RateLimiter.create(permitsPerSecond,warmupPeriod,unit);
	}
	
	/**
	 * 创建限流对象: 这里的 permitsPerSecond 表示每秒允许处理的量为 permitsPerSecond 个,permitsPerSecond 越小,则等待的时间越长.
	 * @param permitsPerSecond:过滤的时间率比
	 * @return
	 */
	public static RateLimiter getLimiter(double permitsPerSecond){
		return RateLimiter.create(permitsPerSecond);
	}
	
}
