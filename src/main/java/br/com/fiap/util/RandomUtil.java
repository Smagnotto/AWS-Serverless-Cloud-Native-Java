package br.com.fiap.util;

import java.util.Random;

public class RandomUtil {
    private static Random rnd = new Random();
    
    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for(int i=0; i < digCount; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }
}