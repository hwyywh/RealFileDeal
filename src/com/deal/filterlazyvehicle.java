package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class filterlazyvehicle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		multiProcessData("F:\\taxi\\Taxi_070220");
	}

	
	public static void multiProcessData(String path){//多对多处理，扫描文件夹，对每个文件执行一对多处理
    	int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();//先将所有文件遍历存入list中
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
            
            for(File stfile:list){
            	readFile(stfile);
            	            	            	
            }

        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    
    }
	
	
	
	public static void readFile(File file){
		ArrayList<String[]>list=new ArrayList<String[]>();
		//首先读取目标文件
    	BufferedReader br1 = null;  //读取目标文件
        try  
        {  
            br1 = new BufferedReader(new FileReader(file));  
        } catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
             
        }  
  
        
//这个模块是读取目标文件每一行             
            String line1,firstline=null;
        	int num=0;  
        	try{
        	firstline=br1.readLine();	
        	while ((line1 = br1.readLine()) != null){        		         		 
                 String[] se1 = line1.split(",");                                          
              //   System.out.println("当前在读取"+file.getName()+"第"+num+"行");
             //    System.out.println("DateTime: "+se1[1]+" LONG: "+se1[2]+" LAT: "+se1[3]);//输出一行       
                 //满足GPS的范围的数据存入文档
//                 if(Double.parseDouble(se1[2])>=121.4620&&Double.parseDouble(se1[2])<=121.4744&&Double.parseDouble(se1[3])>=31.2478&&Double.parseDouble(se1[3])<=31.2588)
//                 ReadFromFile.writeFile("E:\\newtaxi\\"+file.getName(), line1+'\n');                       
                 if(se1[1].equals(firstline)&&num<10){          	 
                 num++;           
                 }else{
                	 System.out.println(file.getName()+"有问题");
                	 break;
                	 
                 }
        	}
        	br1.close();//关闭流
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
	}
}
