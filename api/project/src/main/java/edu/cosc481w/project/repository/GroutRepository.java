package edu.cosc481w.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import edu.cosc481w.project.model.Grout;

@Repository
public interface GroutRepository extends MongoRepository<Grout, String> {
}
