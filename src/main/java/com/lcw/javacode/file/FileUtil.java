package com.lcw.javacode.file;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lcw.javacode.base.BaseUtil;
import org.apache.log4j.Logger;

/**
 * java文件操作工具类
 * 
 */
public class FileUtil {
	
	private static final int BUFF_SIZE = 1024;
	private static Logger log = Logger.getLogger(FileUtil.class);

	/**
	 * 检查文件是否是指定格式的文件
	 * @param file 待检查文件
	 * @param fileSuffix 文件后缀
	 */
	public static boolean checkFile(File file, String fileSuffix) {
		String filePath = file.getAbsolutePath();
		int begin = filePath.lastIndexOf(".");
		int end = filePath.length();
		if (fileSuffix.equals(filePath.substring(begin, end))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 复制文件
	 * @param src 要复制的文件
	 * @param target 目标文件
	 */
	public static void copyFile(File src, File target) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			out = new BufferedOutputStream(new FileOutputStream(target));
			byte[] buffer = new byte[BUFF_SIZE];
			int len;
			while ((len = in.read(buffer, 0, BUFF_SIZE)) > 0) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in !=null) {
					in.close();
				}
			} catch (Exception e) {
			}
			try {
				if(out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 复制文件
	 * @param src 要复制的文件
	 * @param target 目标文件
	 */
	public static void copyFile(String src, String target) {
		copyFile(new File(src), new File(target));
	}

	/**
	 * 用文件的默认打开方式打开文件
	 * 
	 * @param fileName
	 */
	public static void openFile(String fileName) {
		try {
			if (new File(fileName).exists()) {
				Desktop desktop = Desktop.getDesktop();
				desktop.open(new File(fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件中读取字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] readBytesFromFile(File file) {
		byte[] bytes = null;
		try {
			InputStream is = new FileInputStream(file);
			bytes = BaseUtil.transIsToBytes(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 从文件中读取字节数组
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static byte[] readBytesFromFile(String fileName) {
		return readBytesFromFile(new File(fileName));
	}

	/**
	 * 将字节数组写到文件中
	 * 
	 * @param bytes
	 *            字节数组
	 * @param outputFile
	 *            文件名称
	 * @return
	 */
	public static void writeBytesToFile(byte[] bytes, String outputFile) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(outputFile);
			os.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os != null) {
					os.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	
	/**
	 * 像文件里面追加文本内容
	 * 
	 * @param outputFile
	 *            文件绝对路径
	 * @param bytes
	 *            追加的内容
	 */
	public static void appendBytesToFile(byte[] bytes, String outputFile) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile, true);
			out.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	/**
	 * 根据folderPath创建相应的文件夹
	 * @param folderPath
	 * @return
	 * @throws IOException
	 */
	public static File mkdir(String folderPath) {
		File file = new File(folderPath);
		if(!file.exists() || !file.isDirectory()){
			boolean success = false;
			do {
				success = file.mkdirs();
			} while (success);
			
		}
		return file;
	}
	
	/**
	 * 删除文件夹
	 * @param folderPath 文件夹完整绝对路径
	 */
	public static void deleteFolder(String folderPath) throws Exception {
		deleteAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		myFilePath.delete();
	}

	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean deleteAllFile(String path) throws Exception {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}

			if (temp.isFile()) {
				temp.delete();
			}

			if (temp.isDirectory()) {
				deleteAllFile(path + "/" + tempList[i]);
				deleteFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}
	

	/**
	 * 删除文件，可以删除单个文件或文件夹
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
 		File file = new File(fileName);
		if (!file.exists()) {
			log.info(fileName + " 文件不存在!");
			return true;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 
	 * 删除单个文件
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除单个文件 " + fileName + " 成功!");
				return true;
			} else {
				log.info("删除单个文件 " + fileName + " 失败!");
				return false;
			}
		} else {
			log.info(fileName + " 文件不存在!");
			return true;
		}
	}

	/**
	 * 
	 * 删除目录及目录下的文件
	 * 
	 * @param dirName 被删除的目录所在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.info(dirNames + " 目录不存在!");
			return true;
		}
		boolean flag = true;
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				// 如果删除文件失败，则退出循环
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i]
						.getAbsolutePath());
				// 如果删除子目录失败，则退出循环
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.info("删除目录失败!");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			log.info("删除目录 " + dirName + " 成功!");
			return true;
		} else {
			log.info("删除目录 " + dirName + " 失败!");
			return false;
		}

	}

	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			log.info("文件 " + descFileName + " 已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			log.info(descFileName + " 为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				log.info("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				log.info(descFileName + " 文件创建成功!");
				return true;
			} else {
				log.info(descFileName + " 文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(descFileName + " 文件创建失败!");
			return false;
		}
	}

	/**
	 * 创建目录
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			log.info("目录 " + descDirNames + " 已存在!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			log.info("目录 " + descDirNames + " 创建成功!");
			return true;
		} else {
			log.info("目录 " + descDirNames + " 创建失败!");
			return false;
		}

	}

    /**
     * 判断文件是否存在
     * @param path 文件路径
     * @return
     */
	public static boolean exists(String path) {
		return new File(path).exists();
	}

}
