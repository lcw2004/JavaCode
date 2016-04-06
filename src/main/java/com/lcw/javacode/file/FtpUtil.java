package com.lcw.javacode.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.log4j.Logger;

public class FtpUtil {
    private Logger log = Logger.getLogger(this.getClass());
    private static final String FILE_SEP = "/";
    private FTPClient ftpClient;

    private String ip;
    private int port;
    private String userName;
    private String password;

    public FtpUtil(String ip, int port, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void initFtpClient() {
        try {
            int reply;
            ftpClient = new FTPClient();
            ftpClient.connect(ip, port);// 连接FTP服务器
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_NT);
            config.setServerLanguageCode("zh");

            ftpClient.login(userName, password);// 登录
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("连接ftp服务器出错，请联系管理员处理！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFtpsClient() {
        FTPSClient ftpsClient = null;
        try {
            FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_NT);
            config.setServerLanguageCode("zh");

            ftpsClient = new FTPSClient(false);
            ftpsClient.connect(ip, port);
            ftpsClient.setControlEncoding("GBK");
            ftpsClient.setSoTimeout(900000);
            ftpsClient.getReplyCode();
            ftpsClient.execPBSZ(0);
            ftpsClient.execPROT("P");
            ftpsClient.login(userName, password);
            int reply = ftpsClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpsClient.disconnect();
                throw new Exception("连接ftp服务器出错，请联系管理员处理！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ftpClient = ftpsClient;
    }

    /**
     * @return ftp连接
     * @throws Exception
     */
    public FTPClient getFtpClient() {
        return ftpClient;
    }

    /**
     * @param targetPath 原文件
     * @param backRemote 重命名之后的文件
     * @return
     * @throws IOException
     */
    public boolean rename(String targetPath, String backRemote) throws IOException {
        return ftpClient.rename(targetPath, backRemote);
    }

    /**
     * @param pathname ftp服务器上的文件
     * @throws IOException
     */
    public void delete(String pathname) throws IOException {
        ftpClient.deleteFile(pathname);
    }

    /**
     * 是否已经存在路径文件
     *
     * @param pathname ftp上的路径文件
     * @return 是否存在
     * @throws IOException
     */
    public boolean isExistFile(String pathname) throws Exception {
        return ftpClient.getStatus(pathname) == null ? false : true;
    }

    /**
     * 删除ftp上的文件
     *
     * @param pathname 路径文件
     * @return
     * @throws IOException
     */
    public boolean delFile(String pathname) throws IOException {
        return ftpClient.deleteFile(pathname);
    }

    /**
     * @param targetFile 上传的目标文件路径跟文件名
     * @param localeFile 要上传的文件路径
     * @return true：上传成功！false：上传失败！
     */
    public boolean upload(String localeFile, String targetFile) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(localeFile));
        } catch (FileNotFoundException e1) {
            log.error("没有找到本地文件，请重新选择已存在的文件上传");
            e1.printStackTrace();
        }
        return upload(fis, targetFile);
    }

    /**
     * @param targetFile 上传的目标文件路径跟文件名
     * @param fis        要上传的文件输入流
     * @return true：上传成功！false：上传失败！
     */
    public boolean upload(InputStream fis, String targetFile) {
        targetFile = "/" + targetFile;
        String targetPath = targetFile.substring(0, targetFile.lastIndexOf("/"));
        String fileName = targetFile.substring(targetFile.lastIndexOf("/") + 1);
        boolean b = false;
        try {
            mkDir(targetPath);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            b = ftpClient.storeFile(fileName, fis);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传出错，请重新上传");
        }
        return b;
    }

    public InputStream download(String remote) throws Exception {
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftpClient.retrieveFileStream(remote);
    }

    /**
     * @param remote 远程文件地址
     * @param local  本地文件地址
     * @return
     * @throws Exception
     */
    public boolean download(String remote, String local) throws Exception {
        OutputStream output = new FileOutputStream(local);
        try {
            boolean b1 = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean b2 = ftpClient.retrieveFile(remote, output);
            return b1 && b2;
        } finally {
            if (null != output) {
                output.close();
            }
        }
    }

    /**
     * @param filePath 要创建的文件路径
     * @throws IOException
     */
    private synchronized void mkDir(String filePath) throws IOException {
        String[] fileName = filePath.split(FILE_SEP);
        for (int i = 0; i < fileName.length; i++) {
            String targetPath = fileName[i];
            if (targetPath != null && !"".equals(targetPath)) {
                boolean temp = ftpClient.changeWorkingDirectory(targetPath);
                if (!temp) {
                    ftpClient.makeDirectory(targetPath);
                    ftpClient.changeWorkingDirectory(targetPath);
                }
            }
        }
    }

    /**
     * 关闭ftp连接
     */
    public void disConFtp() {
        try {
            if (null != ftpClient) {
                ftpClient.logout();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("退出ftp异常，检查网络！");
        }
    }
}
