package com.imagedetail.detail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * input bundle for show detail image
 */
public class DetailInput implements Parcelable {
    /**
     * key for send bundle input
     */
    public static final String INPUT_KEY = "input";
    private ArrayList<String> listImageUrl;
    private String time;
    private String title;
    private String status;
    private int likeCount;
    private int commentCount;

    private DetailInput(Builder builder) {
        listImageUrl = builder.listImageUrl;
        time = builder.time;
        title = builder.title;
        status = builder.status;
        likeCount = builder.likeCount;
        commentCount = builder.commentCount;
    }

    public ArrayList<String> getListImageUrl() {
        return listImageUrl;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public static final class Builder {
        private ArrayList<String> listImageUrl;
        private String time;
        private String title;
        private String status;
        private int likeCount;
        private int commentCount;

        public Builder() {
        }

        public Builder listImageUrl(ArrayList<String> val) {
            listImageUrl = val;
            return this;
        }

        public Builder time(String val) {
            time = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder likeCount(int val) {
            likeCount = val;
            return this;
        }

        public Builder commentCount(int val) {
            commentCount = val;
            return this;
        }

        public DetailInput build() {
            return new DetailInput(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.listImageUrl);
        dest.writeString(this.time);
        dest.writeString(this.title);
        dest.writeString(this.status);
        dest.writeInt(this.likeCount);
        dest.writeInt(this.commentCount);
    }

    protected DetailInput(Parcel in) {
        this.listImageUrl = in.createStringArrayList();
        this.time = in.readString();
        this.title = in.readString();
        this.status = in.readString();
        this.likeCount = in.readInt();
        this.commentCount = in.readInt();
    }

    public static final Parcelable.Creator<DetailInput> CREATOR = new Parcelable.Creator<DetailInput>() {
        @Override
        public DetailInput createFromParcel(Parcel source) {
            return new DetailInput(source);
        }

        @Override
        public DetailInput[] newArray(int size) {
            return new DetailInput[size];
        }
    };
}
