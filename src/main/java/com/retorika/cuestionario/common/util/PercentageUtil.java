package com.retorika.cuestionario.common.util;

import java.math.BigDecimal;
import java.util.Map;

public class PercentageUtil {
    private PercentageUtil() {}

    public static double round(double value, int decimals) {
        return BigDecimal.valueOf(value).setScale(decimals, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /*
    * Ajuste para que la suma sea 100.0%
    * */
    public static Map<String,Double> normalizeTo100(Map<String,Double> input){
        double sum = input.values().stream().mapToDouble(Double::doubleValue).sum();
        if(Math.abs(sum-100)<0.001){
            return input;
        }
        //Ajuste al mayor
        String maxKey = input.entrySet().stream()
                .max((a,b) -> Double.compare(a.getValue(),b.getValue()))
                .orElseThrow().getKey();

        Double diff = round(100.0-sum,1);
        input.put(maxKey,round(input.get(maxKey)+diff,1));
        return input;
    }
}
