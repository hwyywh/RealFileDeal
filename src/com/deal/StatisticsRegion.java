package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class StatisticsRegion {
	static int num[][]={{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
	
	
	public static ArrayList<String[]> readFile(File file){
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
            String line1;
        	int num=1;  
        	try{
        	while ((line1 = br1.readLine()) != null){        		         		 
                 String[] se1 = line1.split(",");                                          
                 System.out.println("当前在读取"+file.getName()+"第"+num+"行");
                 System.out.println("DateTime: "+se1[1]+" LONG: "+se1[2]+" LAT: "+se1[3]);//输出一行       
                 list.add(se1);
                 num++;           
        	}
        	br1.close();//关闭流
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return list;
	}
	
    public static ArrayList<File> multiProcessData(String path){//多对多处理，扫描文件夹，对每个文件执行一对多处理
    	int fileNum = 0, folderNum = 0;
        File file = new File(path);
        ArrayList<File> list=null;
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
//            	sysProcessData(stfile, list);            	            	
//            }

        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
       return list;
    
    }
	
	public static void statics(String path){
		ArrayList<File> al=multiProcessData(path);
		for(int i=0;i<al.size();i++){
			staticsinglefile(al.get(i));
		}
		
	}
	
	public static void staticsinglefile(File file){
		ArrayList<String[]> list=readFile(file);
		for(int i=0;i<list.size();i++){
			float longtitude=Float.parseFloat(list.get(i)[2]);
			float latitude=Float.parseFloat(list.get(i)[3]);
			if(longtitude>=121.4632&&longtitude<=121.4838&&latitude>=31.2503&&latitude<=31.2668){
				int p=(int)(Math.ceil((Float.parseFloat(list.get(i)[2])-121.4632)/0.00412)-1.0);
				int q=(int)(Math.ceil((Float.parseFloat(list.get(i)[3])-31.2503)/0.0033)-1.0);
				//System.out.printf("%d ,%d",p,q);
				//System.out.println("file:"+file.getName()+" 行数:"+i+" long:"+longtitude+" lat:"+latitude+" p:"+p+" q:"+q);				
				num[q][p]++;
//			int count=num[p][q];
//			count++;
//			num[p][q]=count;
//				
				}
		}
		//System.out.println(num);
	}
	
	public static void main(String[] args) {
		statics("D:\\2km车辆GPS数据区域分布情况modularity=7");
		for(int i=0;i<5;i++){
			
			for(int j=0;j<5;j++){
			System.out.println(num[i][j]);
			}
		}
	}

}
