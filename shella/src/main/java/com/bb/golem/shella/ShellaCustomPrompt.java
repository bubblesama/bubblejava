package com.bb.golem.shella;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShellaCustomPrompt implements PromptProvider{

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("shella> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
	}
	
}


