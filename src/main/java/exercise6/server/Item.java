package exercise6.server;

public class Item {

    private String name;
    private int price;
    private String category;

    public Item() {
        //
    }

    public Item(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Item: " + name + "\n" +
                "<br>" +
                "Price: " + price + "\n" +
                "<br>" +
                "Category: " + category + "\n";
    }
}
