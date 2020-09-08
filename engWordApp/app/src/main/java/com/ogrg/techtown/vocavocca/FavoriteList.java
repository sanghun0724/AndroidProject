package com.ogrg.techtown.vocavocca;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoriteList")
public class FavoriteList {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "prengWord")
    private String engWord;

    @ColumnInfo(name = "prkorMean")
    private String korMean;

    @ColumnInfo(name = "prkorMean2")
    private String korMean2;

    @ColumnInfo(name = "prexKor")
    private String exKor;

    @ColumnInfo(name = "prexEng")
    private String exEng;

    @ColumnInfo(name = "prexEngHidden")
    private String exEngHidden;

    public String getKorMean2() {
        return korMean2;
    }

    public void setKorMean2(String korMean2) {
        this.korMean2 = korMean2;
    }

    public String getExKor() {
        return exKor;
    }

    public void setExKor(String exKor) {
        this.exKor = exKor;
    }

    public String getExEng() {
        return exEng;
    }

    public void setExEng(String exEng) {
        this.exEng = exEng;
    }

    public String getExEngHidden() {
        return exEngHidden;
    }

    public void setExEngHidden(String exEngHidden) {
        this.exEngHidden = exEngHidden;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEngWord(){
        return engWord;
    }

    public void setEngWord(String engWord){
        this.engWord = engWord;
    }

    public String getKorMean(){
        return korMean;
    }

    public void setKorMean(String korMean){
        this.korMean = korMean;
    }

}
