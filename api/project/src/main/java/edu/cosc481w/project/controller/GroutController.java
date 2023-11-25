package edu.cosc481w.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cosc481w.project.constants.DbConstants;
import edu.cosc481w.project.model.Grout;
import edu.cosc481w.project.repository.GroutRepository;

@RestController
@RequestMapping("/api/" + DbConstants.kApiVersion + "/grout/") 
public class GroutController {
   
   private final GroutRepository groutRepository;

   public GroutController(GroutRepository repository) {
      this.groutRepository = repository;
   }

   @GetMapping("/all")
   public ResponseEntity<List<Grout>> getAll() {
      var allGrout = groutRepository.findAll();
      return ResponseEntity.ok(allGrout);
   }
}
