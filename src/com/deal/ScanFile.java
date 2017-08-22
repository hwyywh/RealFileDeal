package com.deal;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class ScanFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScanFile.traverseFolder1("C:\\Users\\Administrator\\Desktop\\社区分区");

	}
	
	public static LinkedList<File> traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list = new LinkedList<File>();
        if (file.exists()) {
            
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                	System.out.println("文件:" + file2.getAbsolutePath());
                    folderNum++;
                } else {                   
                    System.out.println("文件jia:" + file2.getAbsolutePath());                                                      
                    list.add(file2);
                    fileNum++;
                }
            }
            
            
//            File temp_file;
//            while (!list.isEmpty()) {
//                temp_file = list.removeFirst();
//                files = temp_file.listFiles();
//                for (File file2 : files) {
//                    if (file2.isDirectory()) {
//                        System.out.println("duchu文件夹:" + file2.getAbsolutePath());
//                        list.add(file2);
//                        fileNum++;
//                    } else {
//                        System.out.println("duchu文件:" + file2.getAbsolutePath());
//                        folderNum++;
//                    }
//                }
//            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        System.out.println(list.size());
        return list;

    }

}
