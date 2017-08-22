package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class StatisticsPOI implements ReadFile{
	
	public static float[][] function(int zushu)	{
		Generator g=new Generator();
		float num[][]=g.init(zushu);
			//{{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		float num1[][]=g.init(zushu);
		float num2[][]=g.init(zushu);
		
		StatisticsPOI sp=new StatisticsPOI();
		//获取待处理数据的列表
		File file=new File("C:\\Users\\Administrator\\Desktop\\商场.txt");
		ArrayList<String[]> listshop=sp.readFile(file);
		
		File file1=new File("C:\\Users\\Administrator\\Desktop\\学校.txt");
		ArrayList<String[]> listschool=sp.readFile(file1);
		
		File file2=new File("C:\\Users\\Administrator\\Desktop\\医院.txt");
		ArrayList<String[]> listhospital=sp.readFile(file2);
		//接下来将POI分类分区统计
		float[][] res=sp.staticsinglefile(listshop, num);
	
		float[][] res1=sp.staticsinglefile(listschool, num1);
	
		float[][] res2=sp.staticsinglefile(listhospital, num2);
		
		float[][] ress=Calculatetfidf(res, res1, res2,zushu);
		
		
		
//		String rest=null;
//		for(int i=0;i<20;i++) {
//			rest="";
//			for(int j=0;j<20;j++) {
//				rest+=String.valueOf(ress[i][j])+" ";
//			}
//			System.out.println(rest);
//			System.out.println('\n');
//		}
		return ress;
	}
	
	public static void main(String[] args) {
		int zushu=4;
		Generator g=new Generator();
		float num[][]=g.init(zushu);
			//{{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		float num1[][]=g.init(zushu);
		float num2[][]=g.init(zushu);
		
		StatisticsPOI sp=new StatisticsPOI();
		//获取待处理数据的列表
		File file=new File("C:\\Users\\Administrator\\Desktop\\商场.txt");
		ArrayList<String[]> listshop=sp.readFile(file);
		
		File file1=new File("C:\\Users\\Administrator\\Desktop\\学校.txt");
		ArrayList<String[]> listschool=sp.readFile(file1);
		
		File file2=new File("C:\\Users\\Administrator\\Desktop\\医院.txt");
		ArrayList<String[]> listhospital=sp.readFile(file2);
		//接下来将POI分类分区统计
		float[][] res=sp.staticsinglefile(listshop, num);
	
		float[][] res1=sp.staticsinglefile(listschool, num1);
	
		float[][] res2=sp.staticsinglefile(listhospital, num2);
		
		float[][] ress=Calculatetfidf(res, res1, res2,zushu);
		
		
		
		String rest=null;
		for(int i=0;i<zushu;i++) {
			rest="";
			for(int j=0;j<zushu;j++) {
				rest+=String.valueOf(ress[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
	}

	public ArrayList<String[]> readFile(File file) {
		// TODO Auto-generated method stub
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
            
        	int num=1;  
        	try{
        		String line1=br1.readLine();
        	while ((line1 ) != null){        		         		 
                 String[] se1 = line1.split(",");                                          
                 System.out.println("当前在读取"+file.getName()+"第"+num+"行");
                 System.out.println("POI名称: "+se1[0]+" LONG: "+se1[1]+" LAT: "+se1[2]);//输出一行       
                 list.add(se1);
                 num++;  
                 line1=br1.readLine();
        	}
        	br1.close();//关闭流
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return list;
	}
	
	public float[][] staticsinglefile(ArrayList<String[]> list,float[][] num){
					
		for(int i=0;i<list.size();i++){
			float longtitude=Float.parseFloat(list.get(i)[1]);
			float latitude=Float.parseFloat(list.get(i)[2]);
			if(longtitude>=121.4632&&longtitude<=121.4748&&latitude>=31.2574&&latitude<=31.2668){
				int p=(int)(Math.ceil((Float.parseFloat(list.get(i)[1])-121.4632)/0.0029)-1.0);
				int q=(int)(Math.ceil((Float.parseFloat(list.get(i)[2])-31.2574)/0.00235)-1.0);
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
		return num;
	}

	public static float[][]  Calculatetfidf(float[][]shop,float[][]school,float[][]hospital,int zushu) {
		/*
		 *先计算一些需要的数据 
		 */
		float[][]res=new Generator().init(zushu);
		float[][]shop1=shop;float[][]school1=school;float[][]hospital1=hospital;
		float shopnum=CalculateithCandidateSite(shop,zushu);
		float schoolnum=CalculateithCandidateSite(school,zushu);
		float hospitalnum=CalculateithCandidateSite(hospital,zushu);
		/*
		 * 首先计算商场tfidf
		 */
		for(int i=0;i<zushu;i++) {
			for(int j=0;j<zushu;j++) {
				float SitePOItotal=shop[i][j]+school[i][j]+hospital[i][j];//计算该区域POI总数
				if(SitePOItotal==0) {
					shop1[i][j]=0;
					continue;
				}
				shop1[i][j]=(shop[i][j]/SitePOItotal)*400/shopnum;//计算TF
				
			}
		}
		/*
		 * 计算学校tfidf
		 */
		for(int i=0;i<zushu;i++) {
			for(int j=0;j<zushu;j++) {
				float SitePOItotal=shop[i][j]+school[i][j]+hospital[i][j];//计算该区域POI总数
				if(SitePOItotal==0) {
					school1[i][j]=0;
					continue;
				}
				school1[i][j]=(school[i][j]/SitePOItotal)*400/schoolnum;//计算TF
				
			}
		}
		/*
		 * 计算医院tfidf
		 */
		for(int i=0;i<zushu;i++) {
			for(int j=0;j<zushu;j++) {
				float SitePOItotal=shop[i][j]+school[i][j]+hospital[i][j];//计算该区域POI总数
				if(SitePOItotal==0) {
					hospital1[i][j]=0;
					continue;
				}
				hospital1[i][j]=(hospital[i][j]/SitePOItotal)*400/hospitalnum;//计算TF
				
			}
		}
		
		for(int i=0;i<zushu;i++){
			for(int j=0;j<zushu;j++) {
				res[i][j]=shop1[i][j]+school1[i][j]+hospital1[i][j];
			}
		}
		return res;
	}
	
	public static float CalculateithCandidateSite(float[][] p,int zushu){//计算属于第i中种类的RSU候选区域 的数量
		float count=0;
		for(int i=0;i<zushu;i++) {
			for(int j=0;j<zushu;j++) {
				if(p[i][j]>0) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int[][] staticsinglefile(Object v, Object k) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

	

}
class POI{
	private int school;
	private int hospital;
	private int wall;
	private String[] poiname;
	public POI() {
		school=0;
		hospital=0;
		wall=0;
	}
}
