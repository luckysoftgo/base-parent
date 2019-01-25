package com.application.base.config.demo.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: 孤狼
 * @desc:
 */
public class LockTest {
	public static void main(String[] args) throws Exception {
		ZkClient zkClient = new ZkClient("118.24.157.96:2181", 3000);
		SimpleDistributedLock simple = new SimpleDistributedLock(zkClient, "/locker");
		for (int i = 0; i < 10; i++) {
			try {
				simple.acquire();
				System.out.println("正在进行运算操作：" + System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				simple.release();
				System.out.println("=================\r\n");
			}
		}
	}
}
