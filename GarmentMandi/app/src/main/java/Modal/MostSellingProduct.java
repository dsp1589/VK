package Modal;

/**
 * Created by firemoonpc_11 on 04-05-2016.
 */
public class MostSellingProduct {

    String imageUrl;
    String deal_title;
    String deal_price;
    String store_name;
    String  category_name;


  public   MostSellingProduct(String deal_title,String deal_price,String store_name,String  category_name ,String imageUrl)
    {
        this.deal_title=deal_title;
        this.category_name=category_name;
        this.store_name=store_name;
        this.deal_price=deal_price;
        this.imageUrl=imageUrl;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getDeal_price() {
        return deal_price;
    }

    public String getDeal_title() {
        return deal_title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStore_name() {
        return store_name;
    }

}
