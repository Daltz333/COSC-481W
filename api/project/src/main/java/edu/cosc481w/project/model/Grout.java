package edu.cosc481w.project.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Grout {
    private String brand_name;
    private String brand_code;
    private String grout_name;
    private String color_hex;

    public String getBrandName() {
        return brand_name;
    }

    public String getBrandCode() {
        return brand_code;
    }

    public String getGroutName() {
        return grout_name;
    }

    public String getColorHex() {
        return color_hex;
    }

    public Grout(String brand_name, String brand_code, String grout_name, String color_hex) {
        this.brand_name = brand_name;
        this.brand_code = brand_code;
        this.grout_name = grout_name;
        this.color_hex = color_hex;
    }
}
