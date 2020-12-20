package AhmedMElhalaby_University.com.thingstodo.Medules;

public class Category {
    private String id ;
    private String name ;
    private String color ;
    private String userId;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Category(String id, String name, String color, String userId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    public Category() {
    }

    public Category(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
