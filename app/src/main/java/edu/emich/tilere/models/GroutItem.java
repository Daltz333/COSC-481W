package edu.emich.tilere.models;

public class GroutItem {
    public Long id;
    public String brandName;
    public String brandCode;
    public String groutName;
    public int colorHex;
    public String productLink;

    public GroutItem(Long id, String brandName, String brandCode, String groutName, int colorHex, String productLink) {
        this.id = id;
        this.brandName = brandName;
        this.brandCode = brandCode;
        this.groutName = groutName;
        this.colorHex = colorHex;
        this.productLink = productLink;
    }

    public Long getId() {
        return id;
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
        return id + ", " + brandName + ", " + groutName + ", " + colorHex + ", " + productLink;
    }
}
