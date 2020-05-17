package com.bb.golem.shella.webtouch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class WebTouchManager {
	
	@Autowired
	WebTouchService webtouchService;
	
	@ShellMethod(value = "touch url", key = "webtouch")
	public String touch(String host, @ShellOption(defaultValue="/")String path){
		return webtouchService.getSimpleStatusFromUrl(host, path);
	}
	

}
