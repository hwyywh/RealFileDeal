package com.deal;

public class test{
	public static void main(String[] args) {
		int num=6;
		float[][] beichu=Generator.init(num);
		float[][] res=Generator.init(num);
		for(int i=0;i<num;i++) {
			for(int j=0;j<num;j++) {
			beichu[i][j]=(int)(Math.random()*10);
			}
		}
		
//		for(int i=0;i<num;i++) {
//			for(int j=0;j<num;j++) {
//			res[i][j]=chushu[i][j]/beichu[i][j];
//			}
//		}
		
		
		String rest=null;
		for(int i=0;i<num;i++) {
			rest="";
			for(int j=0;j<num;j++) {
				rest+=String.valueOf(beichu[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}
	}

}