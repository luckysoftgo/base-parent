package com.application.base.utils.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * @desc 压缩文件操作方式.
 * @author 孤狼
 */
public class ZipGzFileUtils {
	
	public static void main(String[] args) {
		try {
			gzDecompress("E:\\20180426\\HCDK_DHGL_DKKHMX_20180426.txt.gz","E:\\20180426\\","HCDK_DHGL_DKSJZW_20170506.txt");
			System.out.println(" finish ... ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*************************************************************.zip*************************************************************/
    /**
     * 压缩文件
     * @param zipFileName : 压缩的zip名字.
     * @param inputFile : 输入的文件名字.
     * @throws Exception
     */
    public static void zip(String zipFileName, String inputFile)
            throws Exception {
        File f = new File(inputFile);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, f, null);
        System.out.println("zip done");
        out.close();
    }
    
    /**
     * 文件压缩
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static void zip(String zipFileName, List<String> inputFile)
            throws Exception {
        Iterator<String> iterator = inputFile.iterator();
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        for (; iterator.hasNext();) {
            File f = new File(iterator.next());
            zip(out, f, f.getName());
            System.out.println("zip done");
        }
        out.close();
    }
    
    /**
     * 压缩主方法
     * @param out 输出流
     * @param f 文件
     * @param base 字符
     * @throws Exception
     */
    private static void zip(ZipOutputStream out, File f, String base)
            throws Exception {
        if (f.isDirectory()) {
            File[] fc = f.listFiles();
            if (base != null) {
                out.putNextEntry(new ZipEntry(base + "/"));
            }
            base = (base == null ? "" : base + "/");
            for (int i = 0; i < fc.length; i++) {
                zip(out, fc[i], base + fc[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }
    
    /**
     * 解压文件
     * @param zipFileName
     * @param outputDirectory
     * @throws Exception
     */
    public static void unzip(String zipFileName, String outputDirectory)
            throws Exception {
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry z;
        while ((z = in.getNextEntry()) != null) {
            String name = z.getName();
            if (z.isDirectory()) {
                name = name.substring(0, name.length() - 1);
                File f = new File(outputDirectory + File.separator + name);
                f.mkdir();
            } else {
                File f = new File(outputDirectory + File.separator + name);
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                out.close();
            }
        }
        in.close();
    }
    
    /**
     * 解压文件到指定文件夹
     *
     * @param zip      源文件
     * @param destPath 目标文件夹路径
     * @throws Exception 解压失败
     */
    public static void zipDecompress(String zip, String destPath) throws Exception {
        //参数检查
        if (StringUtils.isEmpty(zip) || StringUtils.isEmpty(destPath)) {
            throw new IllegalArgumentException("zip or destPath is illegal");
        }
        File zipFile = new File(zip);
        if (!zipFile.exists()) {
            throw new FileNotFoundException("zip file is not exists");
        }
        File destFolder = new File(destPath);
        if (!destFolder.exists()) {
            if (!destFolder.mkdirs()) {
                throw new FileNotFoundException("destPath mkdirs is failed");
            }
        }
        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip)));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                //得到解压文件在当前存储的绝对路径
                String filePath = destPath + File.separator + ze.getName();
                if (ze.isDirectory()) {
                    new File(filePath).mkdirs();
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    byte[] bytes = baos.toByteArray();
                    File entryFile = new File(filePath);
                    //创建父目录
                    if (!entryFile.getParentFile().exists()) {
                        if (!entryFile.getParentFile().mkdirs()) {
                            throw new FileNotFoundException("zip entry mkdirs is failed");
                        }
                    }
                    //写文件
                    bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                    bos.write(bytes);
                    bos.flush();
                }
            }
        } finally {
            zipCloseQuietly(zis);
            zipCloseQuietly(bos);
        }
    }

    /**
     * @param srcPath  源文件的绝对路径，可以为文件或文件夹
     * @param destPath 目标文件的绝对路径  /sdcard/.../file_name.zip
     * @throws Exception 解压失败
     */
    public static void zipCompress(String srcPath, String destPath) throws Exception {
        //参数检查
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(destPath)) {
            throw new IllegalArgumentException("srcPath or destPath is illegal");
        }
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("srcPath file is not exists");
        }
        File destFile = new File(destPath);
        if (destFile.exists()) {
            if (!destFile.delete()) {
                throw new IllegalArgumentException("destFile is exist and do not delete.");
            }
        }
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            // 对目标文件做CRC32校验，确保压缩后的zip包含CRC32值
            cos = new CheckedOutputStream(new FileOutputStream(destPath), new CRC32());
            //装饰一层ZipOutputStream，使用zos写入的数据就会被压缩啦
            zos = new ZipOutputStream(cos);
            //设置压缩级别 0-9,0表示不压缩，1表示压缩速度最快，9表示压缩后文件最小；默认为6，速率和空间上得到平衡。
            zos.setLevel(9);
            if (srcFile.isFile()) {
                zipCompressFile("", srcFile, zos);
            } else if (srcFile.isDirectory()) {
                zipCompressFolder("", srcFile, zos);
            }
        } finally {
            zipCloseQuietly(zos);
        }
    }
    
    /**
     * 整个文件夹压缩.
     * @param prefix
     * @param srcFolder
     * @param zos
     * @throws IOException
     */
    private static void zipCompressFolder(String prefix, File srcFolder, ZipOutputStream zos) throws IOException {
        String new_prefix = prefix + srcFolder.getName() + File.separator;
        File[] files = srcFolder.listFiles();
        //支持空文件夹
        if (files.length == 0) {
            zipCompressFile(prefix, srcFolder, zos);
        } else {
            for (File file : files) {
                if (file.isFile()) {
                    zipCompressFile(new_prefix, file, zos);
                } else if (file.isDirectory()) {
                    zipCompressFolder(new_prefix, file, zos);
                }
            }
        }
    }

    /**
     * 压缩文件和空目录
     *
     * @param prefix
     * @param src
     * @param zos
     * @throws IOException
     */
    private static void zipCompressFile(String prefix, File src, ZipOutputStream zos) throws IOException {
        //若是文件,那肯定是对单个文件压缩
        //ZipOutputStream在写入流之前，需要设置一个zipEntry
        //注意这里传入参数为文件在zip压缩包中的路径，所以只需要传入文件名即可
        String relativePath = prefix + src.getName();
        if (src.isDirectory()) {
            //处理空文件夹
            relativePath += File.separator;
        }
        ZipEntry entry = new ZipEntry(relativePath);
        //写到这个zipEntry中，可以理解为一个压缩文件
        zos.putNextEntry(entry);
        InputStream is = null;
        try {
            if (src.isFile()) {
                is = new FileInputStream(src);
                byte[] buffer = new byte[1024 << 3];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                }
            }
            //该文件写入结束
            zos.closeEntry();
        } finally {
            zipCloseQuietly(is);
        }
    }
    
    /**
     * 关闭操作
     * @param closeable
     */
    private static void zipCloseQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
	
    /*************************************************************.gz*************************************************************/
        
    /**
     * 解压文件到指定文件夹
     *
     * @param gzip     源文件
     * @param destPath 目标文件绝对路径
     * @throws Exception 解压失败
     */
    public static void gzDecompress(String gzip, String destPath,String fileName) throws Exception {
        //参数检查
        if (StringUtils.isEmpty(gzip) || StringUtils.isEmpty(destPath)) {
            throw new IllegalArgumentException("gzip or destPath is illegal");
        }
        File gzipFile = new File(gzip);
        if (!gzipFile.exists()) {
            throw new FileNotFoundException("gzip file is not exists");
        }
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        GZIPInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            zis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(gzipFile)));
            File finalFile = new File(destPath+File.separator+fileName);
            if (!finalFile.exists()) {
				finalFile.createNewFile();
			}
            bos = new BufferedOutputStream(new FileOutputStream(finalFile));
            byte[] buffer = new byte[1024 << 3];
            int len = 0;
            while ((len = zis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } finally {
        	gzCloseQuietly(zis);
        	gzCloseQuietly(bos);
        }
    }

    /**
     * 压缩文件.
     * @param srcPath
     * @param destPath
     * @throws Exception
     */
    public static void gzCompress(String srcPath, String destPath) throws Exception {
        //参数检查
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(destPath)) {
            throw new IllegalArgumentException("srcPath or destPath is illegal");
        }
        File srcFile = new File(srcPath);
        if (!srcFile.exists() || srcFile.isDirectory()) {
            throw new FileNotFoundException("srcPath file is not exists");
        }
        File destFile = new File(destPath);
        if (destFile.exists()) {
            if (!destFile.delete()) {
                throw new IllegalArgumentException("destFile is exist and do not delete.");
            }
        }
        GZIPOutputStream zos = null;
        InputStream is = null;
        try {
            zos = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(destPath)));
            is = new FileInputStream(srcFile);
            byte[] buffer = new byte[1024 << 3];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                zos.write(buffer, 0, len);
            }
            zos.flush();
        } finally {
        	gzCloseQuietly(is);
            gzCloseQuietly(zos);
        }
    }
    
    /**
     * 关闭对象
     * @param closeable
     */
    private static void gzCloseQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
	
}
