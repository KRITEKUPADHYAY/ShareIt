package com.assignment.searchit.model;

import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchImageResponse {

    @SerializedName("total")
    private String total;

    @SerializedName("totalHits")
    private String totalHits;

    @SerializedName("hits")
    private ArrayList<ImageDetails> hits;

    public ArrayList<ImageDetails> getHits() {
        return hits;
    }

    public void setHits(ArrayList<ImageDetails> hits) {
        this.hits = hits;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    public static class ImageDetails{

        @SerializedName("user")
        private String user;

        @SerializedName("userImageURL")
        private String userImageURL;

        @SerializedName("pageURL")
        private String pageUrl;

        @SerializedName("largeImageURL")
        private String largeImageURL;

        @SerializedName("likes")
        private String likes;

        @SerializedName("views")
        private String views;

        @SerializedName("downloads")
        private String downloads;

        public String getDownloads() {
            return downloads;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getUserImageURL() {
            return userImageURL;
        }

        public void setUserImageURL(String userImageURL) {
            this.userImageURL = userImageURL;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public String getLargeImageURL() {
            return largeImageURL;
        }

        public void setLargeImageURL(String largeImageURL) {
            this.largeImageURL = largeImageURL;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }
    }

}
