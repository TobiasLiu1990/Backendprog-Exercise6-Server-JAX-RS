package exercise6.server;

public class Item {

    private String itemName;
    private int price;
    private String category;

    public Item() {
        //
    }

    public Item(String itemName, int price, String category) {
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item: " + itemName + "\n" +
                "Price: " + price + "\n" +
                "Category: " + category + "\n";
    }
}
