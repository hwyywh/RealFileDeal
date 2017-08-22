package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CalculateVehicleSiteDensity {
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
	
	public static void staticsinglefile(ArrayList<String[]> list,float[][] num){
		
	
		
		for(int i=0;i<list.size();i++){
			float longtitude=Float.parseFloat(list.get(i)[2]);
			float latitude=Float.parseFloat(list.get(i)[3]);
			if(longtitude>=121.4632&&longtitude<=121.4838&&latitude>=31.2503&&latitude<=31.2668){
				int p=(int)(Math.ceil((Float.parseFloat(list.get(i)[2])-121.4632)/0.00515)-1.0);
				int q=(int)(Math.ceil((Float.parseFloat(list.get(i)[3])-31.2503)/0.004125)-1.0);
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
		//return num;
	}
	
	public static float[][] function(int zushu){
		Generator g=new Generator();
		float num[][]=g.init(zushu);
		ArrayList<String []> list=new ArrayList<>();
		for(int i=0;i<24;i++) {
			list=readFile(new File("D:\\2km每个小时分时段\\a"+i+(i+1)+".txt"));
			staticsinglefile(list, num);	
		}
		
		
//		String rest=null;
//		for(int i=0;i<20;i++) {
//			rest="";
//			for(int j=0;j<20;j++) {
//				rest+=String.valueOf(num[i][j])+" ";
//			}
//			System.out.println(rest);
//			System.out.println('\n');
//		}
		
		return num;
	}
	
	public static void main(String[] args) {
		Generator g=new Generator();
		float num[][]=g.init(4);
		ArrayList<String []> list=new ArrayList<>();
		for(int i=0;i<24;i++) {
			list=readFile(new File("D:\\2km每个小时分时段\\a"+i+(i+1)+".txt"));
			staticsinglefile(list, num);	
		}
		
		
		String rest=null;
		for(int i=0;i<4;i++) {
			rest="";
			for(int j=0;j<4;j++) {
				rest+=String.valueOf(num[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
		
	
	}

}
