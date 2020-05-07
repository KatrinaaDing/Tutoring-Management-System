package unswstudyclub.model;

import java.util.UUID;

public class Course {

    private final UUID id;
    private String code;
    private String name;
    private String handbook;

    public Course(UUID id, String code, String name, String handbook) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.handbook = handbook;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandbook() {
        return handbook;
    }

    public void setHandbook(String handbook) {
        this.handbook = handbook;
    }
}
