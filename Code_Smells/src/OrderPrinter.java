public class OrderPrinter {
    public void printOrder(Order order) {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Details:\n");
        for (Item item : order.getItems()) {
            orderDetails.append(item.getName()).append(" - ").append(item.getPrice()).append("\n");
        }
        System.out.println(orderDetails.toString());
    }
}