package unswstudyclub.model;

import java.util.UUID;

public class Comment {

    private UUID id;
    private String content;
    private String uploader;

    public Comment(UUID id, String content, String uploader) {
        this.id = id;
        this.content = content;
        this.uploader = uploader;
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
}
