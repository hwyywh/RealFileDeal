package com.deal;

public class CalculateHeatValue {
	public static void main(String[] args) {
		int zushu=4;
		float [][]num1=CalculateVehicleSiteDensity.function(zushu);
		float [][]num2=StatisticsPOI.function(zushu);
		float [][]numres=new Generator().init(zushu);
		for(int i=0;i<zushu;i++) {
			for(int j=0;j<zushu;j++) {
				numres[i][j]=num1[i][j]*num2[i][j];
			}
					
	}
		String rest=null;
		for(int i=0;i<zushu;i++) {
			rest="";
			for(int j=0;j<zushu;j++) {
				rest+=String.valueOf(numres[i][j])+" ";
			}
			System.out.println(rest);
			System.out.println('\n');
		}

}
}
