package com.example.util;


import com.example.pojo.CountStudentNum;
import com.example.pojo.Trail;


//坐标转换
public class GPSUtil {
    static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static void bd_decrypt(double bd_lat, double bd_lon, Trail trail) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);

        trail.setLongitude(String.valueOf(gg_lon));
        trail.setLatitude(String.valueOf(gg_lat));
    }
    public static void bd_decryptNum(double bd_lat, double bd_lon, CountStudentNum countStudentNum) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);

        countStudentNum.setLongitude(String.valueOf(gg_lon));
        countStudentNum.setLatitude(String.valueOf(gg_lat));
    }
}
