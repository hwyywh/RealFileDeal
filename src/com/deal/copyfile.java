package com.deal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;

public class copyfile {
	
	public static void main(String[] args) {
		int[] list={10109,
				11913,
				40048,
				54287,
				76644,
				10192,
				87502,
				87054,
				84902,
				84011,
				87224,
				26352,
				80347,
				81564,
				83869,
				86762,
				88925,
				89286,
				85385,
				87814,
				88930,
				2199,
				54251,
				76677,
				70215,
				56382,
				152,
				70206,
				82461,
				89303,
				84233,
				87529,
				19571,
				37814,
				84052,
				20126,
				33507,
				84151,
				20135,
				20156,
				2176,
				2181,
				5314,
				81534,
				2206,
				81440,
				87127,
				3202,
				3225,
				484,
				3238,
				36237,
				3257,
				76617,
				82646,
				37396,
				5164,
				88767,
				87323,
				89469,
				84520,
				82534,
				70133,
				70160,
				70211,
				76412,
				84697,
				87509,
				86345,
				82540,
				83839,
				83967,
				85294,
				88964,
				88915
};
		for(int i=0;i<list.length;i++){
			File file1=new File("D:\\newtaxirang=2km\\Taxi_"+list[i]);
			File file2=new File("D:\\2km车辆GPS数据区域分布情况modularity=7\\Taxi_"+list[i] );
			fileChannelCopy(file1, file2);
		}
		
	}
	
	
	
    public static ArrayList<File> multiProcessData(String path){//多对多处理，扫描文件夹，对每个文件执行一对多处理
    	int fileNum = 0, folderNum = 0;
    	ArrayList<File> list=null;
        File file = new File(path);
        if (file.exists()) {
             list = new ArrayList<File>();//先将所有文件遍历存入list中
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());                                                      
                    
                    folderNum++;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                }
            }
            
//            for(File stfile:list){
//            	System.out.println("haha");
//            	            	            	
//            }

        } else {
            System.out.println("文件不存在!");
        }
        
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return list;
    
    }
    
    
//	public static void main(String[] args) {
//		ArrayList<File> list= multiProcessData("D:\\2km车辆GPS数据区域分布情况");
//		for(int i=0;i<list.size();i++){
//			File file=new File("D:\\2km\\"+list.get(i).getName()+".csv");
//			fileChannelCopy(list.get(i), file);
//			
//		}
//	}

    /**

    * 使用文件通道的方式复制文件

    * 

    * @param s

    *            源文件

    * @param t

    *            复制到的新文件

    */

    public static void fileChannelCopy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();//得到对应的文件通道

            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
}
