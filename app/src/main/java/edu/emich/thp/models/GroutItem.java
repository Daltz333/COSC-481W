package edu.emich.thp.models;

public class GroutItem {
    public String brandName;
    public String brandCode;
    public String groutName;
    public int colorHex;
    public String productLink;

    public GroutItem(String brandName, String brandCode, String groutName, int colorHex) {
        this.brandName = brandName;
        this.brandCode = brandCode;
        this.groutName = groutName;
        this.colorHex = colorHex;
        this.productLink = "https://google.com/search?q=grout%20" + brandName + "%20" + brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public String getGroutName() {
        return groutName;
    }

    public int getColorHex() {
        return colorHex;
    }

    public String getProductLink() {
        return productLink;
    }

    // This will be default to string.
    public String toString() {
        return brandName + ", " + groutName + ", " + colorHex + ", " + productLink;
    }
}
