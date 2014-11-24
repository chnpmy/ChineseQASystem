package com.chnpmy;

import java.io.*;
import java.util.Vector;

public class ToLines {
    private final int docsCount;
    public ToLines(int d){
        this.docsCount = d;
    }

    public void main(){
        String[] key = {"。", "！", "？", "\n"};
        try{
            for (int i = 1; i <= docsCount; i++) {
                String filein = "temp\\sample\\" + i + ".out";
                String fileout = "temp\\sample\\" + i + ".tout";
                File fin = new File(filein);
                File fout = new File(fileout);
                BufferedReader in = new BufferedReader(new FileReader(fin));
                PrintWriter out = new PrintWriter(new FileWriter(fout));
                String s1 = in.readLine();
                System.out.println(s1);
                while (s1 != null) {
                    String s2 = getStrings(s1, key);
                    out.println(s2);
                    s1 = in.readLine();
                }
                in.close();
                out.close();
            }
        }
        catch(FileNotFoundException e1){
            System.out.println("File not found!");
        }
        catch(IOException e2){
            e2.printStackTrace();
        }

    }
    public String getStrings(String s, String[] c) {
        int begin = 0;
        int end = 0;
        int lineWidth = 0;
        int line = 0;
        Vector v = new Vector();

        String ends = s.substring(s.length() - 1);
        if (!ends.equals("" + c)) {
            s += c;
        }
        for (int i = 0; i < s.length(); i++) {
            boolean tkey = false;
            for (int m = 0; m < c.length-1; m++) {
                String tms = s.substring(i, i+1);
                if (tms.equals(c[m])) {
                    tkey = true;
                    break;
                }
            }

            if (tkey) {
                end = i+1;
                String newS = new String(s.substring(begin, end));
                newS = "\n" + newS;
                v.addElement(newS);
                begin = i;
                if (tkey) {
                    begin++;
                }
                line++;
                lineWidth = 0;
            }
        }

        String strs = "";
        for (int i = 0; i < v.size(); i++) {
            strs += (String) v.elementAt(i);
        }

        v.removeAllElements();
        v = null;
        return strs;
    }
}
