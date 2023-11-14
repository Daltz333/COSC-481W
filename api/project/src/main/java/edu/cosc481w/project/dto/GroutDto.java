package edu.cosc481w.project.dto;

public class GroutDto {
    /** URL to the product image */
    public final String productImageUrl;

    /** Hex color that this grout represents */
    public final String hexColor;

    /** Friendly name for the grout, may not be used */
    public final String friendlyName;

    /** Purchase URL for the Grout Color */
    public final String productPurchaseUrl;

    /** Represents a grout object
     * Serialized object should look like
     * {
     *  "hexColor": "FF121212",
     *  "friendlyName": "Insane Blue",
     *  "productPurchaseUrl": "https://amazon.com/",
     *  "productImageUrl": "https://pic.amazon.com/"
     * }
     */
    public GroutDto(String hexColor, String friendlyName, String productPurchaseUrl, String productImageUrl) {
        this.hexColor = hexColor;
        this.friendlyName = friendlyName;
        this.productPurchaseUrl = productPurchaseUrl;
        this.productImageUrl = productImageUrl;
    }
}
