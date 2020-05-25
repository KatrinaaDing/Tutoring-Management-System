package unswstudyclub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Case {

    private UUID id;
    private String subject;
    private String title;
    private String description;
    private Timestamp date;

    public Case(@JsonProperty("id") UUID id,
                @JsonProperty("subject") String subject,
                @JsonProperty("title") String title,
                @JsonProperty("description") String description,
                @JsonProperty("date") Timestamp date) {
        this.id = id;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
