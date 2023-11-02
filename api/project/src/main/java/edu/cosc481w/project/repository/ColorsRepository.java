package edu.cosc481w.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.cosc481w.project.model.Colors;

public interface ColorsRepository extends JpaRepository<Colors, Long>{

   @Query("SELECT a FROM Colors a WHERE a.colorName =: colName")
   public Colors findByColorName(@Param("colName") String colorname);
   
}
