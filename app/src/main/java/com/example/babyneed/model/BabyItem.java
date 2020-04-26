package com.example.babyneed.model;

public class BabyItem {
    public BabyItem() {
    }

    public BabyItem(int id, int itemQuantity, int itemSize, String itemName, String itemColor, String dateItemAdded) {
        this.id = id;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }



    public BabyItem(int itemQuantity, int itemSize, String itemName, String itemColor, String dateItemAdded) {
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.dateItemAdded = dateItemAdded;
    }
    private int id;
    private int itemQuantity;
    private int itemSize;
    private String itemName;
    private String itemColor;
    private String dateItemAdded;
}
