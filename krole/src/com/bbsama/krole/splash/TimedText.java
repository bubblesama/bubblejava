package com.bbsama.krole.splash;

import com.bubblebob.tool.font.SlickFont;
import com.bubblebob.tool.font.SlickText;

public class TimedText extends SlickText{
	
	private long duration;
	
	public TimedText(String text, SlickFont slickFont, int x, int y, int width, int height, int interline, long duration) {
		super(text, slickFont, x, y, width, height, interline);
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
	
}
