package edu.cosc481w.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cosc481w.project.model.Properties;

@RestController
@RequestMapping("app/api/" + Properties.kApiVersion + "/colors/")
public class ColorsController {
   @GetMapping("/")
   public String getAll() {
      return "hello";
   }
}
