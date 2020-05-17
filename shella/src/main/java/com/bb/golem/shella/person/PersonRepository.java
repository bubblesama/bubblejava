package com.bb.golem.shella.person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long>{

	PersonEntity findByLogin(String login);
	
}
