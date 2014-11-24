package com.chnpmy;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by chnpmy on 2014/11/24.
 */
public class WordCount {
    private final int docsCount;
    public WordCount(int d){
        this.docsCount = d;
    }
    public void work(){
        try {
            for (int i = 1; i <= docsCount; i++) {
                File sourceFile = new File("temp\\sample\\" + i + ".out");
                BufferedReader br = new BufferedReader(new FileReader(sourceFile));
                String s = null;
                int count = 0;
                Vector<String> ft = new Vector<String>();
                while ((s = br.readLine()) != null) {
                    count++;
                    StringTokenizer st = new StringTokenizer(s);
                    ft.add(st.countTokens() + " " + s);
                }
                br.close();

                File doc = new File("temp\\sample\\" + i + ".count.out");
                BufferedWriter bw = new BufferedWriter(new FileWriter(doc));
                bw.write(count + "\r\n");
                for (String s1 : ft) {
                    bw.write(s1 + "\r\n");
                }

                bw.flush();
                bw.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
