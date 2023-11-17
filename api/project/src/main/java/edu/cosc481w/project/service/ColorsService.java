package edu.cosc481w.project.service;

import java.util.List; 
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cosc481w.project.dto.ColorsDto;
// import edu.cosc481w.project.model.Colors;
import edu.cosc481w.project.repository.ColorsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service(value = "colorsService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ColorsService {
   
   // @Autowired
   private ColorsRepository colorsRepository;

   public List<ColorsDto> getAll() {
      List<ColorsDto> listColors = colorsRepository.findAll()
         .stream().map(ColorsDto::new).collect(Collectors.toList());

      if (listColors == null) 
         log.error("No Colors Available");

      return listColors;
   }  
 
}
