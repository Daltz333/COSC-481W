package edu.cosc481w.project.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "colors")
@AttributeOverride(name = "id", column = @Column(name = "color_id", nullable = false, columnDefinition = "BIGINT UNASIGNED"))
@EqualsAndHashCode(callSuper = false)
public class Colors {
   
   @Column(name = "color_id")
   private Long id;


   @Column(name = "brand_name")
   private String brandName;
   
   @Column(name = "brand_code")
   private String brandCode;
   
   
   @Column(name = "color_name")
   private String colorName;
   
   
   @Column(name = "color_hex")
   private String colorHex;
   
   
   @Column(name = "product_link")
   private String productLink;


}
