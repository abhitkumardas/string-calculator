package com.adtech.stringcalculator.service;

import com.adtech.stringcalculator.model.constants.CalculatorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CalculatorService {

    @Autowired
    private CalculatorConstants calculatorConstants;

    public int add(String numbers) throws Exception {
        if(numbers.trim().isEmpty()){
            return 0;
        }else{
            if (containsItemFromArray(numbers, calculatorConstants.DELIMITERS)){
                StringBuilder cleanedNums=new StringBuilder();
                for (String del:calculatorConstants.DELIMITERS) {
                    if (numbers.contains(del)){
                        numbers=numbers.replace(del,";").trim();
                    }
                }

                numbers=numbers.replaceAll(";{2,}", ";")
                        .replaceAll("(^;)","")
                        .replaceAll("(;$)","").trim();

                //todo:remove after result conf
                String [] finalNumStr=numbers.split(";");
                int[] intArr=Arrays.asList(finalNumStr).stream().mapToInt(Integer::parseInt).toArray();

                int[] negatives = Arrays.stream(intArr).filter(i -> i < 0).toArray();
                if (negatives.length>0){
                    throw new Exception("Negative Nums Not Allowed: "+ Arrays.toString(negatives));
                }

                //Nums greater than 1000 will be ignored
                int[] sumArr = Arrays.stream(intArr).filter(i -> i < 1001).toArray();
                int sum = Arrays.stream(sumArr).sum();
                return sum;
            }else {
                return toInt(numbers);
            }
        }
    }

    public static boolean containsItemFromArray(String inputString, String[] items) {
        return Arrays.stream(items).anyMatch(inputString::contains);
    }

    private int toInt(String str){
        return Integer.parseInt(str);
    }
}
