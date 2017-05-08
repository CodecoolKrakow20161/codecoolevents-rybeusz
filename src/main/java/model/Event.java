package model;

/**
 * Created by rybeusz on 08.05.17.
 */
public class Event {
    private Integer id;
    private String name;
    private String description;
    private String date;
    private String category;

    public Event(String name, String description, String date, String category) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
