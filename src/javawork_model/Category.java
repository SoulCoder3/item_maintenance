package javawork_model;

/**
 * Category model class
 */
public class Category {
    private int id;
    private String category_name;
    private int aisle_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getAisle_number() {
        return aisle_number;
    }

    public void setAisle_number(int aisle_number) {
        aisle_number = aisle_number;
    }
}
