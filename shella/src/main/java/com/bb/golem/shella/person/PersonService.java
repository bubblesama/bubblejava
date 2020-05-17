package com.bb.golem.shella.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository personRepository;
	
	public String getInfosFromLogin(String login) {
		String result = null;
		PersonEntity person = personRepository.findByLogin(login);
		if (person != null) {
			result = person.getInfo();
		}
		return result;
	}

}
