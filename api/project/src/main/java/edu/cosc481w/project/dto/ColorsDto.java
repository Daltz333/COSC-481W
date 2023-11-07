package edu.cosc481w.project.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.cosc481w.project.model.Colors;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColorsDto implements Serializable{
   private Long id;
   private String brandName;
   private String brandCode;
   private String colorName;
   private String colorHex;
   private String productLink;
   
   public ColorsDto() { }

   public ColorsDto(Colors color) {
      this.id = color.getId();
      this.brandName = color.getBrandName();
      this.brandCode = color.getBrandCode();
      this.colorName = color.getColorName();
      this.colorHex = color.getColorHex();
      this.productLink = color.getProductLink();
   }
}
