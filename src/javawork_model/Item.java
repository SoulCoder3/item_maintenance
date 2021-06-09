package javawork_model;

import java.sql.Date;

/**
 * Item model class
 */
public class Item {
    private int item_id;
    private String upc_code;
    private String description;
    private int category_id;
    private float unit_price;
    private float unit_size;
    private int monthly_sales;
    private int company_id;

    private Date update_date;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getUpc_code() {
        return upc_code;
    }

    public void setUpc_code(String upc_code) {
        this.upc_code = upc_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public float getUnit_size() {
        return unit_size;
    }

    public void setUnit_size(float unit_size) {
        this.unit_size = unit_size;
    }

    public int getMonthly_sales() {
        return monthly_sales;
    }

    public void setMonthly_sales(int monthly_sales) {
        this.monthly_sales = monthly_sales;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
