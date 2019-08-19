package com.tuwq.mobileplayer.lyrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class LyricsParser {

    /**
     * 从歌词文件里解析出完整的歌词列表
     * @param file
     * @return
     */
    public static ArrayList<Lyric> parseFile(File file){
        ArrayList<Lyric> arrayList = new ArrayList<>();
        // 健壮性检查
        if (file ==null||!file.exists()){
            arrayList.add(new Lyric(0,"无法加载歌词文件"));
            return arrayList;
        }
        // 读取文件，按行解析
        try {
            InputStreamReader bgkIn = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader br = new BufferedReader(bgkIn);
            String line;
            while ((line=br.readLine())!=null){
                ArrayList<Lyric> lineList =  parseLine(line);
                arrayList.addAll(lineList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将歌词排序
        Collections.sort(arrayList);
        return arrayList;
    }

    // 解析一行歌词 [01:33.67][02:46.87]伤心的泪儿谁来擦
    private static ArrayList<Lyric> parseLine(String line) {
        ArrayList<Lyric> lineList = new ArrayList<>();
        String[] splitArr = line.split("]");
        // [01:33.67 [02:46.87 伤心的泪儿谁来擦
        String content = splitArr[splitArr.length - 1];
        // [01:33.67 [02:46.87
        for (int i = 0; i < splitArr.length - 1; i++) {
            int startPoint = parseStartPoint(splitArr[i]);
            lineList.add(new Lyric(startPoint,content));
        }
        return lineList;
    }

    // 解析歌词的起始时间 [02:46.87
    private static int parseStartPoint(String time) {
        String[] splitArr = time.split(":");
        // [02 46.87
        String minStr = splitArr[0].substring(1, 3);
        // 46.87
        String[] split = splitArr[1].split("\\.");
        // 46 87
        String secStr = split[0];
        String msecStr = split[1];
        // 将字符串转换为 int 值
        int min =  Integer.valueOf(minStr);
        int sec =  Integer.valueOf(secStr);
        int msec =  Integer.valueOf(msecStr);
        // 计算时间戳
        int startPoint = min * 60 * 1000
                + sec * 1000
                + msec * 10 ;
        return startPoint;
    }
}
