package com.example.administrator.layout;

import android.os.Parcel;
import android.os.Parcelable;

public class Product_list implements Parcelable {
    private int id;
    private String engWord;
    private String engPro;
    private String korMean;
    private String korMean2;

    private String exKor;
    private String exEng;
    private String exEngHidden;

    public Product_list(int id, String engWord, String engPro, String korMean, String korMean2, String exKor, String exEng, String exEngHidden) {
        this.id = id;
        this.engWord = engWord;
        this.engPro = engPro;
        this.korMean = korMean;
        this.korMean2 = korMean2;
        this.exKor = exKor;
        this.exEngHidden = exEngHidden;
    }

    public Product_list(Parcel in) {
        id = in.readInt();
        engWord = in.readString();
        engPro = in.readString();
        korMean = in.readString();
        korMean2 = in.readString();
        exKor = in.readString();
        exEngHidden = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(engWord);
        dest.writeString(engPro);
        dest.writeString(korMean);
        dest.writeString(korMean2);
        dest.writeString(exKor);
        dest.writeString(exEngHidden);

    }

    public static final Creator<Product_list> CREATOR = new Creator<Product_list>() {
        @Override
        public Product_list createFromParcel(Parcel in) {
            return new Product_list(in);
        }

        @Override
        public Product_list[] newArray(int size) {
            return new Product_list[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getEngPro() {
        return engPro;
    }

    public void setEngPro(String engPro) {
        this.engPro = engPro;
    }

    public String getKorMean() {
        return korMean;
    }

    public void setKorMean(String korMean) {
        this.korMean = korMean;
    }

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
}
