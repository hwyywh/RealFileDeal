package com.deal;

public class Generator {

	
		public static  float[][] init(int m){
			float[][] a = new float[m][m];
			for(int i=0;i<m;i++) {
				for(int j=0;j<m;j++) {
					 a[i][j]=0;
				}
			}
			return a;
		}
		
		public static String[][] init(int m,String name){
			String[][] a = new String[m][m];
			
			for(int i=0;i<m;i++) {
				for(int j=0;j<m;j++){
					 a[i][j]=null;
				}
			}
			
			return a;
		}
		
		public int[][] init(int m,int n){
			int[][] a = new int[m][m];
			for(int i=0;i<m;i++) {
				for(int j=0;j<m;j++) {
					 a[i][j]=n;
				}
			}
			return a;
		} 
//		public void setLX(Object[][]k) {
//			for(int i=0;i<k.length;i++) {
//				for(int j=0;j<k.length;j++) {
//					 (Object)k[i][j];
//				}
//			}
//		}

}
