
public class TaxableItem extends Item {
    public static final double DEFAULT_TAX_RATE = 7.0;
    private double taxRate;
    
    public TaxableItem(String name, double price, int quantity, DiscountType discountType, double discountAmount){
        super(name, price, quantity, discountType, discountAmount);
        taxRate = DEFAULT_TAX_RATE;
    }

    public double getTaxRate(){
        return taxRate;
    }

    public void setTaxRate(double rate) {
        if (rate>=0){
            taxRate = rate;
        }
    }
}
