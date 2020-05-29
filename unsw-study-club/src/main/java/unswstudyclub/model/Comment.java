package unswstudyclub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Comment {

    private UUID id;
    private String subtitle;
    private String content;
    private String uploader;
    private Timestamp postDate;

    public Comment(@JsonProperty("id") UUID id,
                   @JsonProperty("subtitle") String subtitle,
                   @JsonProperty("content") String content,
                   @JsonProperty("uploader") String uploader,
                   @JsonProperty("post_date") Timestamp postDate) {
        this.id = id;
        this.subtitle = subtitle;
        this.content = content;
        this.uploader = uploader;
        this.postDate = postDate;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
