package com.hongri.androidaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhongyao
 * @date 2018/6/5
 */

public class Book implements Parcelable{

    private String name;
    private String pages;

    protected Book(Parcel in) {
        name = in.readString();
        pages = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    public Book() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pages);
    }
}
