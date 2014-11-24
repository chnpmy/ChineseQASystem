package com.chnpmy;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Created by chnpmy on 2014/11/7.
 */

public class Main {
    public static void main(String[] args){
        //createDocs();
        //xmlDocsToText();
        //getSentences();
        wordCount();
    }
    static final int docsCount = 100;//sample.xml里面的问题数量

    private static void createDocs() {
        String format = "xml";
        File sourceFile = new File("resource\\Sample.xml");
        Document document = null;
        try{
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(sourceFile);
            Element root = document.getRootElement();
            for(Iterator i = root.elementIterator(); i.hasNext();){
                Element el = (Element)i.next();
                System.out.println(el.attribute("id").getText() + " " + el.elementText(new QName("q")));

                File doc = new File("temp\\sample\\" + el.attribute("id").getText() + "." + format);
                URL url = new URL("http://localhost:8983/solr/rss/select?q=" + URLEncoder.encode(el.elementText(new QName("q")), "utf-8")  + "&wt=" + format + "&indent=true");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String s = null;
                StringBuffer sb = new StringBuffer();
                while((s = br.readLine()) != null)
                {
                    sb.append(s+"\r\n");
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter(doc));
                bw.write(sb.toString());
                bw.flush();
                bw.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将原始的xml文档转化为只有维基正文内容的txt文件，方便窦芃处理
     * 这部分交给奚远完成
     */
    private static void xmlDocsToText(){
        for (int i = 1; i <= docsCount; i++){
            File oldDoc = new File("temp\\sample\\" + i + ".xml");
            File newDoc = new File("temp\\sample\\" + i + ".txt");
            Document doc = null;
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(newDoc));


                SAXReader saxReader = new SAXReader();
                doc = saxReader.read(oldDoc);
                Element root = doc.getRootElement();
                Element result = root.element("result");
                List<Element> docs = result.elements();
                for (Element d : docs){
                    for (Element str : (List<Element>)(d.elements())){
                        if (str.attribute("name").getText().equals("text")){
                            bw.write(str.getText() + "\r\n");
                        }
                    }
                }
                bw.flush();
                bw.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用分词工具对奚远生成的.txt文件进行分词，时间会比较长，而且会出各种问题
     * 之后考虑把NIU换成IK吧
     * （之前应该再加一步繁体转简体的工作）
     */
    private static void getSentences(){
        SegmentThread segmentThread = new SegmentThread(docsCount);
        segmentThread.run();
    }

    /**
     * 这部分是姚畅的工作吧
     * 将分词后的结果进行词数统计，方便窦芃处理
     */
    private static void wordCount(){
        new WordCount(docsCount).work();
    }

    /**
     * 将问题分割
     * 然后调用窦芃的程序得到
     */
    private static void getSentence(){
        return;
    }
}