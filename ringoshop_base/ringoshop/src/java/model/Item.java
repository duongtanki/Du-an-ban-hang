package model;


public class Item {
    private Shoes shoes;
    private int quantity;

    public Item() {}

    public Item(Shoes shoes, int quantity) {
        this.shoes = shoes;
        this.quantity = quantity;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return shoes.getPrice() * quantity;
    }
}

