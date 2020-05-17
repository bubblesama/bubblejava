package com.bb.golem.shella.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class PersonManager {
	
	@Autowired
	PersonService personService;

	@ShellMethod(value = "List all authorizations", key = "list")
	public List<String> listAllAuthorizations(){
		List<String> result = new ArrayList<>();
		return result;
	}
	
	@ShellMethod(value = "List all authorizations", key = "dump")
	public void dumpAllAuthorizations(){
		//todo
	}
	
	@ShellMethod(value = "Search authorization", key = "search")
	public String searchAuthorization(String login){
		return personService.getInfosFromLogin(login);
	}
	
	@ShellMethod(value = "Add authorization", key = "add")
	public void addAuthorization(){
		//todo
	}
	
	@ShellMethod(value = "Connection prompt", key = "connect")
	public void connect(){
		//todo
	}

	@ShellMethodAvailability({"add"})
	public Availability isUserLogged() {
		//TODO
        return Availability.available();
	}

}
