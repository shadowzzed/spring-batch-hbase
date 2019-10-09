package com.zed.springbatchdemo.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Zeluo
 * @date 2019/9/24 13:53
 */
@Slf4j
public class FileUtils {
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        log.info("[获取文件列表-开始] 获取文件列表开始***************************************");
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                files.add(tempList[i].toString());
            }
//            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
//            }
        }
        log.info("[获取文件列表-结束] 获取文件列表结束***************************************");
        return files;
    }
}
