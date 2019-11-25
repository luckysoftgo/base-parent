package com.application.base.sync.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @desc 加载外部的jar文件到jvm内存中去,方便在程序中去调用使用.
 * @author admin
 */
@Slf4j
public class ClassLoadUtils {
	
	/**
	 * 加载jar文件.
	 */
    public static void loadJdbcJar() {
        String extensionJarPath = System.getProperty("user.dir") + "/lib";
        loadExtensionJar(extensionJarPath);
    }
    
	/**
	 * 加载jar文件.
	 */
    public static void loadHttpHeadersFactoryJar() {
        String extensionJarPath = System.getProperty("user.dir") + "/ext";
        loadExtensionJar(extensionJarPath);
    }
	
	/**
	 * 加载外部扩展jar
	 * @param extensionJarPath
	 */
	public static void loadExtensionJar(String extensionJarPath) {
        log.info("加载外部扩展jar");
        log.info("Extension jar path is: " + extensionJarPath);
        File filePath = new File(extensionJarPath);
        if (filePath.exists() && filePath.isDirectory()) {
            Method method = null;
            try {
                method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            } catch (NoSuchMethodException | SecurityException e) {
                log.error("Failed to load extension jar", e);
                return;
            }
            // 获取方法的访问权限以便写回
            boolean accessible = method.isAccessible();
            try {
                method.setAccessible(true);
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) ClassLoadUtils.class.getClassLoader();
                for (File file : filePath.listFiles()) {
                    URL url = file.toURI().toURL();
                    method.invoke(classLoader, url);
                }
            } catch (Exception e) {
                log.error("Failed to load extension jar", e);
            } finally {
                method.setAccessible(accessible);
            }
        }
    }
}
