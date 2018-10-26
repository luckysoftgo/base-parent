package com.application.base.cache.codis.architecture;

import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc 测试
 * @author 孤狼
 */
public class CodisTest {

	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(0);
		Jedis jedis = new Jedis("101.201.177.32", 16339);
		jedis.auth("02681330-d47f-4e34-a0fc-4a2f8931c523");
		
		Set<String> sets = jedis.keys("*.lock.cache.key");
		for (String key : sets) {
			if (Integer.valueOf(jedis.get(key)).intValue() >= 5) {
				count.addAndGet(1);
			}
		}
		System.out.println(count.get());
	}
}
