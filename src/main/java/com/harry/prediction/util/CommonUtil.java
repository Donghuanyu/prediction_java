package com.harry.prediction.util;

public class CommonUtil {

    /**
     * 获取指定区间的N个不重复的随机数
     * @param min   最小值
     * @param max   最大值
     * @param n     随机数个数
     * @return      随机数集合
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
