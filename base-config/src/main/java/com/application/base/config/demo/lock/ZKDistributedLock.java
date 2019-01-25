package com.application.base.config.demo.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: 孤狼
 * @desc:
 */
public class ZKDistributedLock implements Lock {
	private ZkClient zkClient;
	private String parentPath = "";
	private ThreadLocal<String> currentPath = new ThreadLocal<String>();
	private ThreadLocal<String> beforePath = new ThreadLocal<String>();
	CountDownLatch cdl = new CountDownLatch(1);
	
	public ZKDistributedLock(String parentPath) {
		this.parentPath = parentPath;
		//建立链接
		zkClient = new ZkClient("localhost:2181");
		//序列话接口
		zkClient.setZkSerializer(new ZKObjSerializer());
		
		if (!zkClient.exists(this.parentPath)) {
			zkClient.createPersistent(this.parentPath);
		}
		
	}
	
	@Override
	public void lock() {
		if (!tryLock()) {//未获取到监听
			waitForLock(); //注册监听，阻塞自己
			lock();//递归调用自身
		}
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
	
	}
	
	//注册监听 监听成功就阻塞自己
	public void waitForLock() {
		IZkDataListener listener = new IZkDataListener() {
			@Override
			public void handleDataChange(String s, Object o) throws Exception {
				System.out.println("节点 " + s + " 的数据发生了变化" + o);
			}
			
			@Override
			public void handleDataDeleted(String s) throws Exception {
				System.out.println("节点 " + s + " 被删除");
				cdl.countDown();
			}
		};
		zkClient.subscribeDataChanges(beforePath.get(), listener);
		if (zkClient.exists(beforePath.get())) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		zkClient.unsubscribeDataChanges(beforePath.get(), listener);
	}
	
	/**
	 * 临时顺序节点有一下两个特性：
	 * 1）断开链接时候，节点删除
	 * 2）它是有序递增的
	 * 我们为每个线程建立一个节点，建立节点成功就表示拿到了锁lock，并用TheadLocal接收,同时返回当前 节点的名称
	 * 父节点下的所有子节点可以排序，确保最小的节点为当前节点即可。如果当前节点不是最小节点，我们只关注当前节点的前一个节点，
	 * 对它加监听，当前 节点 删除时唤醒线程 ;
	 * 前节点依然存在的时候阻塞线程。
	 * 释放锁的过程就是删除前一节点
	 * 有几种情况：
	 * 1）子节点列表为空，表示第一个拿到锁，同时创建节点，这个时候，当前 节点等同于前节点。
	 * 2）子节点列表不为空，但是当前节点为空，表示当前 线程并没有创建节点，有其他的线程创建过节点，这个时候创建本线程的节点，递归 继续
	 * 3）子节点列表不为空且长度等于1，当前节点也不为空.这个时候就说明了只有当前线程创建了节点。所以这个 时候也是拿到了锁。这个时候前节点等同于当前节点
	 * 4）子节点列表不为空且长度也大于1，当前节点不为空，并且当前节点不再列表的首位，需要继续监听，阻塞,这个时候前节点不等同于当前节点
	 * 5）子节点列表不为空且长度大于1，当前节点不为空，并且 当前节点再列表的首位，说明拿到了锁。这个时候前节点等同于当前节点
	 *
	 * @return
	 */
	@Override
	public boolean tryLock() {
		boolean isback = false;
		List<String> children = zkClient.getChildren(parentPath);
		if (null == children && children.size() == 0) { //当前未创建节点 ,创建节点，于是获得了锁
			currentPath.set(zkClient.createEphemeralSequential(this.parentPath + "/", "aa"));
			beforePath.set(currentPath.get()); //这一种情况只有一个
			isback = true;
		} else {
			
			if (null == currentPath.get()) { //本线程创建一个节点
				currentPath.set(zkClient.createEphemeralSequential(this.parentPath + "/", "aa"));
				return tryLock();
			} else { //该线程已经创建过节点了。
				if (children.size() == 1) { //当前线程创建了一个节点，并且该父节点下的子节点数是1，也就是说只有当前线程创建了一个节点，所以它也拿到了锁
					isback = true;
					beforePath.set(currentPath.get());//这一种情况只有一个
				} else {
					Collections.sort(children);
					String curent = currentPath.get().substring(this.parentPath.length() + 1);
					int currentIndex = children.indexOf(curent);
					if (currentIndex > 0) {
						beforePath.set(this.parentPath + "/" + children.get(currentIndex - 1));
						isback = false;
					} else {
						isback = true;
						beforePath.set(currentPath.get());//这一种情况只有一个
					}
				}
				
			}
		}
		return isback;
	}
	
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
	
	@Override
	public void unlock() {
		zkClient.delete(beforePath.get());
	}
	
	@Override
	public Condition newCondition() {
		return null;
	}
}