package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class v2RSU {
/*			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<File> list=multiProcessData("D:\\newtaxirang=2km\\");//
		 for(int i=0;i<list.size();i++){
			 for(int j=i+1;j<list.size();j++){
				 processData(list.get(i), list.get(j));//只比较一次，不再出现互相比较而contact对不上的问题
			 }
		 }
		 for(int i=0;i<calculatecommunication.length;i++){
			 for(int j=0;j<calculatecommunication[i].length;j++){
			 //System.out.println(calculatecommunication[i][j]);
			 ReadFromFile.writeFile("d:\\v2rsucount2.txt",Integer.toString(calculatecommunication[i][j])+' ');
			 }
			 ReadFromFile.writeFile("d:\\v2rsucount2.txt",""+'\n');
		 }

		//multiProcessData("D:\\newtaxirang=2km\\");//计算潜在通信次数
		//System.out.println(rsu[1][1]);
	
	}
*/
	static long value=0;
	public static void main(String[] args) {
		multiProcessData("D:\\r=200-44\\88\\");
	}
	static int[][] calculatecommunication={{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0}};
	static double rsu1[][]= {{31.251525,121.46465}};
	static double rsu7[][]= {{31.253965,121.46465},{31.253965,121.47625},{31.253965,121.47335},{31.263305,121.47915},{31.253965,121.47915},{31.258645,121.46465},{31.258645,121.47915}};
	static double rsu3[][]= {{31.2606125,121.476075},{31.2606125,121.465775},{31.2564875,121.470925}};
	static double rsu6[][]={{31.2606125,121.465775},{31.2564875,121.470925},{31.262675,121.47865},{31.2606125,121.4706125},{31.2606125,121.481225},{31.2523625,121.481225}};
	static double rsu10[][]= {{31.260925,121.46465},{31.260625,121.47045},{31.260625,121.46755},{31.260625,121.47335},{31.260625,121.46465},{31.258575,121.47335},{31.263575,121.47045},{31.258575,121.46755},{31.263275,121.47335},{31.260925,121.46755}};
	static double rsu16[][]={{31.253965,121.46465},{31.2253965,121.47625},{31.253965,121.47335},{31.263305,121.47915},{31.253965,121.47915},{31.258645,121.46465},{31.258645,121.47915},{31.258645,121.47335},{31.263305,121.47045},{31.253965,121.47045},{31.256315,121.47915},{31.260975,121.47915},{31.263305,121.46755},
			{31.256315,121.47335},{31.253965,121.46755},{31.263305,121.46465}};
	

	
	
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
	
	
    public static void processData(File sourcefile,File destfile) {//一对一
   	
    	//ArrayList<Map<Integer, Map<String, Object>>> desthang=new ArrayList<Map<Integer,Map<String,Object>>>(); //这个量用来记录目标文件的所有行的Map映射
    	//Map<Integer, Map<String, Object>> hangys=new HashMap<Integer, Map<String,Object>>();//这个量记录每一行的行序号和数据的映射
    	
    	ArrayList<String[]> sourcelist=new ArrayList<String[]>();//存储源文件每一行数据，数据是数组形式
    	ArrayList<String[]> destlist=new ArrayList<String[]>();//存储目标文件每一行数据，数据是数组形式
    	
    	Map<String, Object> source = new HashMap<String, Object>();
    	int flag=1;//设置一个标志位，这个量会记录上一个文件比较行的下限值，这样下一行比较时就可以直接跳过上一个文件比较是不符合范围的值        	
    	destlist=readFile(destfile);//读取目标文件返回对应的ArrayList
    	sourcelist=readFile(sourcefile);//读取源文件返回对应的ArrayList
       // long value=0;            	
           //这个模块开始比较       
    	
               for(int count=0;count<sourcelist.size();count++)  //循环比较ArrayList中的所有的行，超出上限的直接跳出循环，低于下限的记录它的位置，下一行再比较时直接从下限开始比较。这是外层循环，源文件
               {                     	
            	for(int i=0;i<destlist.size();i++){
            		
               SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               try{
               java.util.Date now = df.parse(destlist.get(i)[1]);
               java.util.Date date=df.parse(sourcelist.get(count)[1]);
               long l=now.getTime()-date.getTime();
           //    System.out.println("读取源文件的第"+(hang-1)+"行时间"+"DateTime "+(String) source.get("DateTime"));
               long day=l/(24*60*60*1000);
               long hour=(l/(60*60*1000)-day*24);
               long min=((l/(60*1000))-day*24*60-hour*60);
               long s=(l/1000-day*24*60*60-hour*60*60-min*60);
               
               //比较模块，论文中可修改的部分
               if(day==0&&hour==0&&min>0&&min<10){//检测时间窗口
            	  System.out.println("第"+(count+1)+"行时间窗口符合");
            	  System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
            	  
            	  int hang;
            	  for(hang=0;hang<rsu1.length;hang++){
            		  for(int hanga=0;hanga<rsu1.length;hanga++){
            	  double sourcedis=CalculateDistance.distCnvter(Double.parseDouble(sourcelist.get(count)[2]), Double.parseDouble(sourcelist.get(count)[3]), rsu1[hang][1], rsu1[hang][0]);
            	  double destdis=CalculateDistance.distCnvter(Double.parseDouble(destlist.get(i)[2]), Double.parseDouble(destlist.get(i)[3]), rsu1[hanga][1], rsu1[hanga][0]);
            	  if(sourcedis<=200&&destdis<=200){
                      System.out.println("第"+(count+1)+"行距离符合");
                      //calculatecommunication[hang][hanga]++;
                      //calculatecommunication[hanga][hang]++;
                      value++;
                      System.out.println("此时value值"+value);
                     // Thread.sleep(5000);
                      
                   //   System.out.println("写入成功"+'\n');
                   	   }else{
                   		   System.out.println("第"+(count+1)+"行距离bu符合"+"距离是"+sourcedis+" "+destdis);
                   	   }
            		  }
            	 
            	  }
               }
               
            	}catch(Exception e){
            		e.printStackTrace();
            	}
               
               
                    }
             
            }  
               //ReadFromFile.writeFile("d:\\v2rsucount2.txt",sourcefile.getName().substring(5)+","+destfile.getName().substring(5)+","+value+","+'\n');
                System.out.println("value="+value); 
    }  
    
//    public static void multiProcessData(String path){//多对多处理，扫描文件夹，对每个文件执行一对多处理
//    	int fileNum = 0, folderNum = 0;
//        File file = new File(path);
//        if (file.exists()) {
//            LinkedList<File> list = new LinkedList<File>();//先将所有文件遍历存入list中
//            File[] files = file.listFiles();
//            for (File file2 : files) {
//                if (file2.isDirectory()) {
//                    System.out.println("文件夹:" + file2.getAbsolutePath());                                                      
//                    
//                    folderNum++;
//                } else {
//                    System.out.println("文件:" + file2.getAbsolutePath());
//                    list.add(file2);
//                    fileNum++;
//                }
//            }
//            
//            for(File stfile:list){
//            	System.out.println("haha");
//            	sysProcessData(stfile, list);            	            	
//            }
//
//        } else {
//            System.out.println("文件不存在!");
//        }
//        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
//    
//    }
    
    public static LinkedList<File> multiProcessData(String path){//多对多处理，扫描文件夹，对每个文件执行一对多处理
    	int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list=null;
        if (file.exists()) {
             list = new LinkedList<File>();//先将所有文件遍历存入list中
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
            	System.out.println("haha");
            	sysProcessData(stfile, list);            	            	
            }

        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
       return list;
    
    }
    
    public static void sysProcessData(File sourcefile,LinkedList<File>list){//循环处理数据,一对多的类
        
        
        for(File filef:list){
        	if(filef.isDirectory()){
        		System.out.println("这是一个文件夹"+filef.getAbsolutePath());
        	}else{
        		if(sourcefile.getName().equalsIgnoreCase(filef.getName())){
        			continue;//比较源文件与目标文件是否是同一个文件,是就跳过此次处理，否则就处理
        		}else{
        			processData(sourcefile, filef);
        		}
        		
        	}
        }
                 
}
    	    	
    }

