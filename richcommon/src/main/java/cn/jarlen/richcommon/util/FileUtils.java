/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.jarlen.richcommon.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * DESCRIBE: File tools
 * Created by jarlen on 2016/6/22.
 */
public class FileUtils {

    private static final int IO_BUFFER_SIZE = 16384;

    /**
     * delete file whether it is a file or directory
     *
     * @param file file/directory
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean deleteFile(File file) {
        if (file == null) {
            return true;
        }

        if (file.isFile()) {
            return file.delete();
        }

        boolean result = true;
        File files[] = file.listFiles();
        for (int index = 0; index < files.length; index++) {
            result |= deleteFile(files[index]);
        }
        result |= file.delete();

        return result;
    }


    /**
     * delete file(which path is $filePath)
     *
     * @param filePath <br>file/directory path
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return deleteFile(file);
    }

    /**
     * create file
     *
     * @param file file
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean createFile(File file) {

        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * create file(which path is $filePath)
     *
     * @param filePath file/directory path
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean createFile(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }

        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * create directory(which path is $dirPath)
     *
     * @param dirPath
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean createDirectory(String dirPath) {

        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }

        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return true;
    }

    /**
     * move file(which path is $srcPath) to file(which path is dstPath)
     *
     * @param srcPath
     * @param dstPath
     * @param isForce <br>true: delete dstPath forcely or else
     * @return <br>true : success
     * <br>false: fail
     */
    public static boolean moveFile(String srcPath, String dstPath, boolean isForce) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return false;
        }

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return false;
        }

        File dstFile = new File(dstPath);
        if (dstFile.exists()) {
            if (isForce) {
                deleteFile(dstFile);
            } else {
                return false;
            }
        }
        return srcFile.renameTo(dstFile);
    }

    /**
     * check whether file(which path is srcFilePath)is gif
     *
     * @param srcPath
     * @return
     */
    public static boolean isGifImage(File srcPath) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcPath);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }

        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];

            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return true;
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                return false;
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I'
                    && b9 == (byte) 'F') {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * get size of file or directory
     *
     * @param file
     * @return
     */
    public static long getsize(File file) {

        if (file == null || !file.exists()) {
            return 0;
        }

        long length = 0;
        if (file.isFile()) {
            length = file.length();
            return length;
        }

        File files[] = file.listFiles();
        if (files == null || files.length == 0) {
            return length;
        }

        int size = files.length;
        for (int index = 0; index < size; index++) {
            File child = files[index];
            length += getsize(child);
        }
        return length;
    }

    /**
     * get SHA1 value of file(which path is path)
     *
     * @param path
     * @return
     */
    public static String getFileSHA1(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        String fileSHA1 = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int length = 0;
            while ((length = fis.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, length);
            }
            fis.close();
            fileSHA1 = SecurityUtils.bytes2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(fileSHA1)) {
            fileSHA1 = fileSHA1.trim();
        }
        return fileSHA1;
    }

    /**
     * get MD5 value of file(which path is path)
     * @param path
     * @return
     */
    public static String getFileMD5(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        String fileMD5 = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int length = 0;
            while ((length = fis.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, length);
            }
            fis.close();
            fileMD5 = SecurityUtils.bytes2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(fileMD5)) {
            fileMD5 = fileMD5.trim();
        }
        return fileMD5;
    }

    /**
     * get size of file
     * <br>format: ##0.0
     * @param length
     * @return
     */
    public static String getFileSize(long length) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        if (length < 1024)
            return length + "B";
        double t = length / 1024.0;
        if (t > 1024) {
            double t1 = t / 1024;
            if (t1 > 1024) {
                double t2 = t1 / 1024;
                return decimalFormat.format(t2) + "GB";
            } else {
                return decimalFormat.format(t1) + "MB";
            }
        } else {
            return decimalFormat.format(t) + "KB";
        }
    }


}
