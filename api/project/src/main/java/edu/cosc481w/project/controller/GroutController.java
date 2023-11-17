package edu.cosc481w.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cosc481w.project.dto.GroutDto;
import edu.cosc481w.project.model.Properties;

@RestController
@RequestMapping("app/api/" + Properties.kApiVersion + "/grout/")
public class GroutController {
   @GetMapping("/")
   public GroutDto getAll() {
      var testDto = new GroutDto("555555", "test", "google.com", "google.com");
      return testDto;
   }
}
