package com.deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CalculateDistance {

	private final static double EARTH_RADIUS = 6378.137;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
         Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    
    public static double distCnvter( double lat1,double longt1, double lat2,double longt2) {
        final double PI = 3.14159265358979323; // 圆周率
        final double R = 6378137; // 地球的半径       
            double x, y, distance;
            x = (longt2 - longt1) * PI * R
                    * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
            y = (lat2 - lat1) * PI * R / 180;
            distance = Math.hypot(x, y);
            return distance;
        
    }
    
    public static void main(String[] args) {
		double s=distCnvter(31.00000, 121.4623780, 31.00001, 121.4648740);
    //	double s=distCnvter(121.4623780, 31.2534920, 121.4648740, 31.2478930);
		System.out.println(s);
		
	}
   
}
