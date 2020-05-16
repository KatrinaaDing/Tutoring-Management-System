package unswstudyclub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Video {

    private final UUID id;
    private String link;
    private String title;
    private String description;
    private String courseCode;
    private UUID uploader;
    private Timestamp uploadDate;

    public Video(@JsonProperty("id") UUID id,
                 @JsonProperty("link") String link,
                 @JsonProperty("title") String title,
                 @JsonProperty("description") String description,
                 @JsonProperty("course") String courseCode,
                 @JsonProperty("uploader") UUID uploader,
                 @JsonProperty("uploadDate") Timestamp uploadDate) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.courseCode = courseCode;
        this.uploader = uploader;
        this.uploadDate = uploadDate;
    }

    public UUID getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse() {
        return courseCode;
    }

    public void setCourse(String courseCode) {
        this.courseCode = courseCode;
    }

    public UUID getUploader() {
        return uploader;
    }

    public void setUploader(UUID uploader) {
        this.uploader = uploader;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

}
