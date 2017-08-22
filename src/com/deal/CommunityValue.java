package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class CommunityValue {
	
	
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
		int zushu=8;
		//Generator g=new Generator();
		float[][] res=Generator.init(zushu);//生成一个4*4的方阵
		LinkedList<File> list=ScanFile.traverseFolder1("C:\\Users\\Administrator\\Desktop\\社区分区");//获取文件列表
		/*
		 * 读取文件中的车辆ID并提取文件对方阵区域进行赋值
		 */
		for(int i=0;i<list.size();i++) {
			readvehicleID(list.get(i),res);//执行读取对应社区的车辆数据并赋值
		}
		
		calcCVdividedbyCost(res,zushu);
		System.out.println("原矩阵");
		String rest=null;
		for(int i=0;i<zushu;i++) {
			rest="";
			for(int j=0;j<zushu;j++) {
				rest+=String.valueOf(res[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
		sort(res);
		

	}
	
	/*
	 * 给输出元素排序，在每个数组后面加一个括号，里面是这个数的大小排名
	 */
	public static void sort(float[][] sorted) {
		ArrayList<Float>list=new ArrayList<>();		
		//String[][] st=Generator.init(sorted.length, "String");
		String[][] st = new String[sorted.length][sorted.length];
		
		for(int i=0;i<sorted.length;i++) {
			for(int j=0;j<sorted.length;j++){
				 st[i][j]=null;
			}
		}
		
		for(int i=0;i<sorted.length;i++) {
			for(int j=0;j<sorted.length;j++) {
				list.add(sorted[i][j]);				
			}
		}
		list.sort(new Comparator<Float>() {

			@Override
			public int compare(Float o1, Float o2) {
				// TODO Auto-generated method stub
				if(o1>o2) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		int end=list.size();
		while(end>0) {
		for(int i=0;i<sorted.length;i++) {
			for(int j=0;j<sorted.length;j++) {
				if(sorted[i][j]==list.get(end-1)) {
					st[i][j]=""+sorted[i][j]+"("+end+")";
					end--;					
				}
				
			}
		}
		}
//		for(int i=0;i<list.size();i++) {
//			System.out.println(list.get(i));
//		}
		String rest=null;
		for(int i=0;i<st.length;i++) {
			rest="";
			for(int j=0;j<st.length;j++) {
				rest+=st[i][j]+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
	}
	public static void calcCVdividedbyCost(float[][] chushu,int num){
		float[][] res=Generator.init(num);
		float[][] beichu=Generator.init(num);
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
			beichu[i][j]=(int)(Math.random()*10+1);
			}
		}
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
			res[i][j]=chushu[i][j]/beichu[i][j];
			}
		}
		System.out.println("随机数的矩阵");
		String rest=null;
		for(int i=0;i<num;i++) {
			rest="";
			for(int j=0;j<num;j++) {
				rest+=String.valueOf(beichu[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
		System.out.println("比值结果的矩阵");
//		String rest1=null;
//		for(int i=0;i<num;i++) {
//			rest1="";
//			for(int j=0;j<num;j++) {
//				rest1+=String.valueOf(res[i][j])+" ";
//			}
//			System.out.println(rest1);
//			System.out.println('\n');
//		}
		sort(res);
	}

	public static void readvehicleID(File file,float[][] res) {
		String[] communityvalue=file.getName().split("-|\\.");//将社区权值分离出来community[2]就是
//		System.out.println(communityvalue[0]+"和"+communityvalue[1]);
		ArrayList<String>list= readfilereturnID(file);//读取文件中的车辆ID并返回
		for(int i=0;i<list.size();i++) {
			File tmp=new File("D:\\newtaxirang=2km\\Taxi_"+list.get(i));
			ArrayList<String[]>al=readFilereturndata(tmp);
			staticsinglefile(al, res,Integer.parseInt(communityvalue[1]));
		}
	}
	
	public static float[][] staticsinglefile(ArrayList<String[]> list,float[][] num,Integer value){
		
		for(int i=0;i<list.size();i++){
			float longtitude=Float.parseFloat(list.get(i)[2]);
			float latitude=Float.parseFloat(list.get(i)[3]);
			if(longtitude>=121.4632&&longtitude<=121.4864&&latitude>=31.2480&&latitude<=31.2668){
				int p=(int)(Math.ceil((Float.parseFloat(list.get(i)[2])-121.4632)/0.0029)-1.0);
				int q=(int)(Math.ceil((Float.parseFloat(list.get(i)[3])-31.2480)/0.00235)-1.1);
				//System.out.printf("%d ,%d",p,q);
				//System.out.println("file:"+file.getName()+" 行数:"+i+" long:"+longtitude+" lat:"+latitude+" p:"+p+" q:"+q);				
				num[q][p]+=value;
//			int count=num[p][q];
//			count++;
//			num[p][q]=count;
//				
				}
		}
		//System.out.println(num);
		return num;
	}
	
	public static ArrayList<String> readfilereturnID(File file) {//读取社区文件，返回车辆ID列表
		ArrayList<String>list=new ArrayList<>();
		//首先读取目标文件
    	BufferedReader br1 = null;  //读取目标文件
        try  
        {  
            br1 = new BufferedReader(new FileReader(file));  
        } catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
             
        } 
        try {
        String line=br1.readLine();
        while(line!=null) {
        	list.add(line);
        	line=br1.readLine();
        }
        br1.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        System.out.println(file.getName()+list.size());
        return list;
	}
	
	public static ArrayList<String[]> readFilereturndata(File file) {
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

	

}
class Community{
	int communityweight;
	
}
//class POI{
//	private int school;
//	private int hospital;
//	private int wall;
//	private String[] poiname;
//	public POI() {
//		school=0;
//		hospital=0;
//		wall=0;
//	}
//}
