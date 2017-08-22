package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class testdeal {
	
	public static void main(String[] args) {
		File file1=new File("E:\\taxi\\Taxi_070220\\Taxi_105");
		File file2=new File("E:\\taxi\\Taxi_070220\\Taxi_106");
		processData(file1,file2 );
		
//	    try {
//			ArrayList<Map<String, Object>> list=readFile(file);
//			for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i));System.out.println(i);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public static ArrayList<Map<String, Object>> readFile(File file) throws IOException{
		ArrayList<Map<String, Object>> al=new ArrayList<Map<String,Object>>();
		Map<String, Object> dest = new HashMap<String, Object>(); 
        BufferedReader br1 = null;  //读取目标文件
        try  
        {  
            br1 = new BufferedReader(new FileReader(file));
        }catch(Exception e){
        	e.printStackTrace();
        }
        String[] columnName1 =  
            { "Id", "DateTime", "Long", "Lat", "Speed","Angle","Status" }; // 列名  
            int[] courseIndexs1 =  
            { 0,1, 2, 3,4,5,6}; // 数据对应的列  
            int i1, j1, index1;  
            String line1;                
            	int num=1;            	           
                while ((line1 = br1.readLine()) != null)  
                {                  	
                	System.out.println("当前在读取目标文件第"+num+"行");                	
                    index1 = 0;  
                    String[] se1 = line1.split(",");  
                   
                    for (i1 = 0; i1< se1.length; i1++)  
                    {  
                        if ("".equals(se1[i1]))  
                        {  
                            continue;  
                        }  
                        if (index1 >= columnName1.length)  
                        {  
                            continue;  
                        }  
                        dest.put(columnName1[index1], se1[i1]);  
                        index1++;  
                        
                    }  
                    al.add(dest);
                }
                return al;
        
	}
	
	
    public static void processData(File sourcefile,File destfile){//一对一
    	
    	
    	
    	ArrayList<Map<Integer, Map<String, Object>>> desthang=new ArrayList<Map<Integer,Map<String,Object>>>(); //这个量用来记录目标文件的所有行的Map映射
    	Map<Integer, Map<String, Object>> hangys=new HashMap<Integer, Map<String,Object>>();//这个量记录每一行的行序号和数据的映射
    	Map<String, Object> source = new HashMap<String, Object>();
    	int flag=1;//设置一个标志位，这个量会记录上一个文件比较行的下限值，这样下一行比较时就可以直接跳过上一个文件比较是不符合范围的值
       
    	//首先读取目标文件
    	BufferedReader br1 = null;  //读取目标文件
        try  
        {  
            br1 = new BufferedReader(new FileReader(destfile));  
        } catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
             
        }  
  
        String[] columnName1 =  
        { "Id", "DateTime", "Long", "Lat", "Speed","Angle","Status" }; // 列名  
        int[] courseIndexs1 =  
        { 0,1, 2, 3,4,5,6}; // 数据对应的列  
        int i1, j1, index1;  
        String line1;  
        
//这个模块是读取文件每一行             
        	Map<String, Object> dest = new HashMap<String, Object>();//这个量是为了临时解析每一行数据
        	int num=1;  
        	try{
        	while ((line1 = br1.readLine()) != null){
        		 index1 = 0;  
        		 //dest.clear();
                 String[] se1 = line1.split(",");                         
                 for (i1 = 0; i1< se1.length; i1++)  
                 {  
                     if ("".equals(se1[i1]))  
                     {  
                         continue;  
                     }  
                     if (index1 >= columnName1.length)  
                     {  
                         continue;  
                     }  
                     dest.put(columnName1[index1], se1[i1]);  
                     index1++;  
                     
                 }  
                 System.out.println("当前在读取目标文件第"+num+"行");
                 System.out.println("DateTime "+(String) dest.get("DateTime")+"LONG "+(String) dest.get("Long")+
             			"LAT "+(String) dest.get("Lat"));//输出一行
                 
                 hangys.put(Integer.valueOf(num-1), dest);//将每一行的行序号与该行的值的映射存入
                 num++;
               //  desthang.add(hangys); //将每一行的Map映射存入,对应的是每行的下标和对应的Map数据
        	}
        	br1.close();//关闭流
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
    	
    	
    	BufferedReader br = null;  
        try  
        {  
            br = new BufferedReader(new FileReader(sourcefile));  
        } catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
             
        }  
  
        String[] columnName =  
        { "Id", "DateTime", "Long", "Lat", "Speed","Angle","Status" }; // 列名  
        int[] courseIndexs =  
        { 0,1, 2, 3,4,5,6}; // 数据对应的列  
        int i, j, index;  
        String line;  
    //    List<Map<String, Object>> vehicledata = new ArrayList<Map<String, Object>>();  
       
        try  
        {  
        	int hang=1;
          //  br.readLine(); // 去掉第一行  
            while ((line = br.readLine()) != null)  
            {  
            	System.out.println("当前在读取源文件第"+hang+"行");
            	hang++;
                index = 0;  
                String[] se = line.split(",");  
                
                for (i = 0; i < se.length; i++)  
                {  
                    if ("".equals(se[i]))  
                    {  
                        continue;  
                    }  
                    if (index >= columnName.length)  
                    {  
                        continue;  
                    }  
                    source.put(columnName[index], se[i]);  
                    index++;  
                    
                }  
                 
           
                
                System.out.println("DateTime "+(String) source.get("DateTime")+"LONG "+(String) source.get("Long")+
                			"LAT "+(String) source.get("Lat"));//输出一行
                	
                
                
                	
 //这个模块开始比较               	
                    for(int count=flag-1;count<hangys.size();count++)  //循环比较ArrayList中的所有的行，超出上限的直接跳出循环，低于下限的记录它的位置，下一行再比较时直接从下限开始比较。
                    {  
                    	String destdatetime=(String)hangys.get(Integer.valueOf(count)).get("DateTime");
                    	String longtitude=(String)hangys.get(Integer.valueOf(count)).get("Long");
                    	String latitude=(String)hangys.get(Integer.valueOf(count)).get("Lat");
                    	
         
               SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               java.util.Date now = df.parse(destdatetime);
               java.util.Date date=df.parse((String)source.get("DateTime"));
               long l=now.getTime()-date.getTime();
               System.out.println("读取源文件的第"+(hang-1)+"行时间"+"DateTime "+(String) source.get("DateTime"));
               long day=l/(24*60*60*1000);
               long hour=(l/(60*60*1000)-day*24);
               long min=((l/(60*1000))-day*24*60-hour*60);
               long s=(l/1000-day*24*60*60-hour*60*60-min*60);
               
               //比较模块，论文中可修改的部分
               if(day==0&&hour==0&&min==0&&s>0&&s<=60){
            	   System.out.println("第"+(count+1)+"行时间窗口符合");
            	   double dis=CalculateDistance.GetDistance(Double.parseDouble((String)source.get("Lat"))  , Double.parseDouble((String)source.get("Long")) , Double.parseDouble(latitude) , Double.parseDouble(longtitude));
            	   if(dis>0&&dis<=100){
               System.out.println("第"+(count+1)+"行距离符合");
               System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
             //  ReadFromFile.writeFile("E:\\taxi\\105-109.txt", "当前是源文件的第"+(hang-1)+"行在比较目标文件第"+num+"行，结果是"+""+day+"天"+hour+"小时"+min+"分"+s+"秒,"+"距离是"+dis+'\n');
               ReadFromFile.writeFile("F:\\taxi\\"+sourcefile.getName()+"_"+destfile.getName()+".txt", (hang-1)+","+(count+1)+","+s+","+destdatetime+","+dis+'\n');
               System.out.println("写入成功"+'\n');
            	   }else{
            		   System.out.println("第"+(count+1)+"行距离bu符合"+"距离是"+dis);
            	   }
               }else if(s>60){//超过上限直接跳过本次循环
            	   System.out.println("第"+(count+1)+"行时间窗口bu符合,高过上限");
            	   double disa=CalculateDistance.GetDistance(Double.parseDouble((String)source.get("Lat")),Double.parseDouble((String)source.get("Long")) , Double.parseDouble(latitude) , Double.parseDouble(longtitude) );
                   System.out.println(disa);
                   System.out.println("直接跳过比较后面的行");
                   break;
               }else{
            	   System.out.println("第"+(count+1)+"行时间窗口bu符合,低于下限");
            	   flag=(count+2);//将下限下一行所在的行记录在标志位
            	   double disa=CalculateDistance.GetDistance(Double.parseDouble((String)source.get("Lat")),Double.parseDouble((String)source.get("Long")) , Double.parseDouble(latitude) , Double.parseDouble(longtitude) );
                   System.out.println(disa);
               }
               
           //    num++;
               
               
                    }
              //      br1.close();   
                
                
            }  
            
            br.close();  
           
                 
    }  catch(Exception e){
    	e.printStackTrace();
    }
    	    	
    }

}
