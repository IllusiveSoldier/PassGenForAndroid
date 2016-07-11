package com.passgen.knack.passwordgeneratorandconnecttodatabase;


import java.util.Random;

public class GeneratePass
{
    public String Generate()
    {
        String resourceString = "~!@#$%^&*()_+ZXCVBNM<>?QWERTYUIOP{}ASDFGHJKL:\"|`1234567890-=zxc" +
                "vbnm,./qwertyuiop[]asdfghjkl;'\'";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();


        for (int i = 0; i <= 24; i++)
        {
            sb.append(resourceString.charAt(rand.nextInt(resourceString.length())));
        }

        return sb.toString();
    }
}
