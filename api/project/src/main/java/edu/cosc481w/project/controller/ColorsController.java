package edu.cosc481w.project.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cosc481w.project.dto.ColorsDto;
import edu.cosc481w.project.service.ColorsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("application/colors")
@Slf4j
public class ColorsController {
   
   @Autowired
   private ColorsService colorsService;

   @GetMapping("/")
   public ResponseEntity<List<ColorsDto>> getAll() {
      try {
         return new ResponseEntity<List<ColorsDto>>(colorsService.getAll(), HttpStatus.OK);
      } catch (NoSuchElementException e) {
         log.error("Unable to find " + e.getMessage());
         return new ResponseEntity<List<ColorsDto>>(HttpStatus.NOT_FOUND);
      }
   }
}