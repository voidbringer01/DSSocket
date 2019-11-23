package com.company;

import java.math.BigInteger;
import java.util.*;

public class Cypher {

    private void permute(int[] key){
        ArrayList<Integer> list = new ArrayList();

        for(int i = 0; i<key.length ; i++)
            list.add(key[i]);

        Collections.shuffle(list);


        for(int i = 0; i < key.length ;i++)
            key[i] = list.get(i);
    }

    public String transpose(String plainText) {
        int length = plainText.length();
        char[] cypherText = new char[length];

        int[] key= new int[length];
        for(int i = 0;i<length;i++)
            key[i] = i;
        permute(key);

        for(int i = 0; i<length ;i++)
            cypherText[key[i]] = plainText.charAt(i);

        return String.valueOf(cypherText);
    }


    // Roman Cypher
    public String substitute(String plainText) {
        int length = plainText.length();
        char[] cypherText = new char[length];
        Random rand = new Random();
        int key = rand.nextInt(25);

        for(int i = 0;i<length;i++) {
            int value;
            if(Character.isLowerCase(plainText.charAt(i)))
                value = (((int)plainText.charAt(i)) - 97 + key) % 26 + 97;
            else
                value = (((int)plainText.charAt(i)) - 65 + key) % 26 + 65;
            cypherText[i]=(char)value;
        }
        return String.valueOf(cypherText);
    }

    String RSA(String plainText) {
        BigInteger p, q, n, e, d;
        Random rand = new Random();
        p = BigInteger.probablePrime(512, rand);
        q = BigInteger.probablePrime(512, rand);
        n = p.multiply(q);
        BigInteger ojlerovBroj = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(512, rand);
        while (ojlerovBroj.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(ojlerovBroj) < 0)
            e.add(BigInteger.ONE);
        d = e.modInverse(ojlerovBroj);

        byte[] plainTextInBytes = plainText.getBytes();
        byte[] cypherTextInBytes = new BigInteger(plainTextInBytes).modPow(e,n).toByteArray();
        String cypherText = new String(cypherTextInBytes);
        return cypherText;
    }
}
