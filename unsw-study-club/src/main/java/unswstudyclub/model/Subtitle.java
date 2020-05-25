package unswstudyclub.model;

import java.util.ArrayList;
import java.util.UUID;

public class Subtitle {

    private UUID id;
    private UUID caseTitle;
    private String part;
    private String content;
    private ArrayList<Comment> comments;

    public Subtitle(UUID id,
                    UUID caseTitle,
                    String part,
                    String content) {
        this.id = id;
        this.caseTitle = caseTitle;
        this.part = part;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(UUID caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
