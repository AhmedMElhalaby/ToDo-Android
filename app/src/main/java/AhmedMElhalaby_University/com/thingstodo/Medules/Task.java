package AhmedMElhalaby_University.com.thingstodo.Medules;

public class Task {
    private long id ;
    private String title ;
    private String description ;
    private Category category ;
    private String createDate ;
    private boolean IsSelect = false ;
    private String userId ;

    public Task() {
    }

    public Task(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Task(long id, String title, String description, Category category, String createDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isSelect() {
        return IsSelect;
    }

    public void setSelect(boolean select) {
        IsSelect = select;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
