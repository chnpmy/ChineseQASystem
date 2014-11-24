package com.chnpmy;

import java.io.*;


public class DeletePunc {
    public void main() {
        String filein = "textDeletePunc.txt";
        String fileout = "textNoPunc.txt";
        String fileout1 = "textNum.txt";
        int count=0,countWord=0;

        try{
            File fin = new File(filein);
            File fout = new File(fileout);
            BufferedReader in = new BufferedReader(new FileReader(fin));
            PrintWriter out = new PrintWriter(new FileWriter(fout));
            String s1 = in.readLine();
            while(s1 != null){
                count++;
                String s2 = s1.replaceAll("[pP\\p{Punct}]", "");
                s2 = s2.replaceAll("   ", " ");
                s2 = s2.replaceAll("  ", " ");
                countWord=s2.split(" ").length;
                if(countWord==1){
                    count--;
                    s1 = in.readLine();
                }
                else{
                    out.println(countWord+" "+s2);
                    s1 = in.readLine();
                }
            }
            in.close();
            out.close();
        }
        catch(FileNotFoundException e1){
            System.out.println("File not found!");
        }
        catch(IOException e2){
            e2.printStackTrace();
        }
        try{
            File fin1 = new File(fileout);
            File fout1 = new File(fileout1);
            BufferedReader in1 = new BufferedReader(new FileReader(fin1));
            PrintWriter out1 = new PrintWriter(new FileWriter(fout1));
            out1.println(count);
            String s11 = in1.readLine();
            while(s11 != null){
                out1.println(s11);
                s11 = in1.readLine();
            }
            in1.close();
            out1.close();
        }
        catch(FileNotFoundException e1){
            System.out.println("File not found!");
        }
        catch(IOException e2){
            e2.printStackTrace();
        }
    }
}

