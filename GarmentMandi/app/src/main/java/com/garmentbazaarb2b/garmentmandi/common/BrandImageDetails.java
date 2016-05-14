package com.garmentbazaarb2b.garmentmandi.common;

/**
 * Created by lokendra on 4/27/16.
 */
public class BrandImageDetails {

    private String imageTitle="";
    private String image_encoded_string="";
    private String brandName="";


    public BrandImageDetails() {
    }

    public BrandImageDetails(String imageTitle, String image_encoded_string, String brandName) {
        this.imageTitle = imageTitle;
        this.image_encoded_string = image_encoded_string;
        this.brandName = brandName;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImage_encoded_string() {
        return image_encoded_string;
    }

    public void setImage_encoded_string(String image_encoded_string) {
        this.image_encoded_string = image_encoded_string;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
