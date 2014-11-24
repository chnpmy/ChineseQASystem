package com.chnpmy;

import java.io.*;

/**
 * Created by chnpmy on 2014/11/23.
 */
public class SegmentThread implements Runnable{
    private final int docsCount;
    public SegmentThread(int count){
        this.docsCount = count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= docsCount; i++){
            TextSegment(i);
        }
    }

    private synchronized void TextSegment(int id){
        try{
            Process proc = Runtime.getRuntime().exec("genSen.cmd " + id, null, new File("."));
            System.out.println(id);
            Thread.sleep(10000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}