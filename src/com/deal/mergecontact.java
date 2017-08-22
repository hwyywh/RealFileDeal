package com.deal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class mergecontact {
	public static void main(String[] args) {
		ArrayList<ArrayList<String[]>> list=getEveryPairTaxiContact(new File("D:\\2km范围的接触情况统计.txt"));
//		for(int i=0;i<list.size();i++){
//			System.out.println("正在输出");
//			for(int j=0;j<list.get(i).size();j++){
//				for(String s:list.get(i).get(j))
//			System.out.println(s);
//			}
//			
//		}
		System.out.println(list.size());
		System.out.println(list.get(0).get(0)[0]);
		System.out.println(list.get(0).get(0)[1]);
		System.out.println(list.get(22).get(0)[0]);
		System.out.println(list.get(22).get(0)[1]);
	}
	
	public static ArrayList<ArrayList<String[]>> getEveryPairTaxiContact(File file){//将每对车的联系分别存储起来
		
		ArrayList<ArrayList<String[]>> list=new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> al=v2vcontact.readFile(file);
		String firsttaxi=null;//记录两辆车的编号		
		String secondtaxi=null;
		ArrayList<String[]> tmp=new ArrayList<String[]>();//作为一种像滑动窗口一样的机制，或者理解为缓存池，将同一对车的contact记录在一个表中
		int location=0;//标记当前list中哨兵位置
		for(int i=0;i<al.size();i++){
			String tmpfirsttaxi=al.get(i)[0];
			String tmpsecondtaxi=al.get(i)[1];
			if((tmpfirsttaxi.equals(firsttaxi))==false||(tmpsecondtaxi.equals(secondtaxi))==false){//将同一对pair
//				if(tmp.isEmpty()==false){
//				list.add(tmp);//先将上组缓存池数据存入列表再刷新缓存池
//				}
				tmp.clear();//如果两组数据的ID对不上说明是两对车，那么就创建一个arraylist
				String[] stmp=al.get(i);
				tmp.add(stmp);
				firsttaxi=tmpfirsttaxi;
				secondtaxi=tmpsecondtaxi;//刷新当前的比较位，这里可以理解为哨兵
				list.add(tmp);
				
								
			}else if(tmpfirsttaxi.equals(firsttaxi)&&tmpsecondtaxi.equals(secondtaxi)){
				String[] stmp=al.get(i);
				list.get(list.size()-1).add(stmp);				
			}
			
			
		}
		return list;
		
	}
	
	public static void comparepair(String line1,String line2){
		String[] linex=line1.split(",");
		String[] liney=line2.split(",");
		//计算剩余的时间
		int remaintime=100*Integer.parseInt(linex[2])/Integer.parseInt(linex[4])-Integer.parseInt(linex[2]);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		java.util.Date now = df.parse(linex[3]);
        java.util.Date date=df.parse(liney[3]);
        long l=now.getTime()-date.getTime();
    //    System.out.println("读取源文件的第"+(hang-1)+"行时间"+"DateTime "+(String) source.get("DateTime"));
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        long min=((l/(60*1000))-day*24*60-hour*60);
        long s=(l/1000-day*24*60*60-hour*60*60-min*60);  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

}
