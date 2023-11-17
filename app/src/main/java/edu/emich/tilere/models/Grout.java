package edu.emich.tilere.models;

public class Grout {
    /**
     * URL to the product image
     */
    public final String productImageUrl;

    /**
     * Hex color that this grout represents
     */
    public final String hexColor;

    /**
     * Friendly name for the grout, may not be used
     */
    public final String friendlyName;

    /**
     * Purchase URL for the Grout Color
     */
    public final String productPurchaseUrl;

    /**
     * Brand name for the grout
     */
    public final String brandName;

    /**
     * Represents a grout object
     * Serialized object should look like
     * {
     * "hexColor": "FF121212",
     * "friendlyName": "Insane Blue",
     * "productPurchaseUrl": "https://amazon.com/",
     * "productImageUrl": "https://pic.amazon.com/"
     * }
     */
    public Grout(String hexColor, String friendlyName, String productPurchaseUrl, String productImageUrl, String brandName) {
        this.hexColor = hexColor;
        this.friendlyName = friendlyName;
        this.productPurchaseUrl = productPurchaseUrl;
        this.productImageUrl = productImageUrl;
        this.brandName = brandName;
    }
}
