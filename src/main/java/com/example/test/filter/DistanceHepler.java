package com.example.test.filter;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Component;

@Component
public class DistanceHepler {
	public static double distance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
		GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
		GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
		return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
	}
	public static void main(String[] args) {
		System.out.println("经纬度距离计算结果：" + distance(120.384428, 36.105215, 120.421221,36.283843) + "米");
		//20135.095292998125
	}
}
