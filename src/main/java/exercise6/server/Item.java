package exercise6.server;

public class Item {

    private final String name;
    private final int price;
    private final String category;

    public Item(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
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
