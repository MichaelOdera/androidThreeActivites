package com.example.myfitnessapp.googlemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Id {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Id() {
    }

    /**
     * 
     * @param kind
     * @param videoId
     * @param channelId
     */
    public Id(String kind, String channelId, String videoId) {
        super();
        this.kind = kind;
        this.channelId = channelId;
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
