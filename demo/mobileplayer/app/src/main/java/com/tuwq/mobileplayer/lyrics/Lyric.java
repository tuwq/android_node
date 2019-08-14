package com.tuwq.mobileplayer.lyrics;

public class Lyric implements Comparable<Lyric>{

    int startPoint;
    String content;

    public Lyric(int startPoint, String content) {
        this.startPoint = startPoint;
        this.content = content;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "startPoint=" + startPoint +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int compareTo(Lyric o) {
        return startPoint - o.getStartPoint();
    }
}
