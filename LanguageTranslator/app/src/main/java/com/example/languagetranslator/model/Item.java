package com.example.languagetranslator.model;

public class Item {
    private int id;
    private String srcWord;
    private String resWord;
    private String date;


    public Item(int id, String srcWord, String resWord, String date) {
        this.id = id;
        this.srcWord = srcWord;
        this.resWord = resWord;
        this.date = date;
    }

    public Item(String srcWord, String resWord, String date) {
        this.srcWord = srcWord;
        this.resWord = resWord;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrcWord() {
        return srcWord;
    }

    public void setSrcWord(String srcWord) {
        this.srcWord = srcWord;
    }

    public String getResWord() {
        return resWord;
    }

    public void setResWord(String resWord) {
        this.resWord = resWord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
