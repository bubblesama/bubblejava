package com.bbtools.horos.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bbtools.horos.extractor.Horoscope.Sign;

public class DailyPrediction {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Date date;
	public Map<Sign,Horoscope> horoscopes; 
	
	public DailyPrediction(){
		date = new Date();
		horoscopes = new HashMap<Sign,Horoscope>();
	}
	
	public String toJson(){
		StringBuffer result = new StringBuffer();
		result.append("{\"date\":\""+dateFormat.format(date)+"\", ");
		for (Sign sign: horoscopes.keySet()){
			String lowerCaseSign = sign.toString().toLowerCase();
			String prediction = horoscopes.get(sign).prediction;
			result.append("\""+lowerCaseSign+"\":\""+prediction+"\", ");
		}
		result.deleteCharAt(result.length()-2);
		result.append("}");
		return result.toString();
	}
	
	public void save(String folderPath){
		SimpleDateFormat compactDateFormat = new SimpleDateFormat("yyyyMMdd");
		String datePath = compactDateFormat.format(date);
		String jsonFilePath =   folderPath+"json."+datePath+".txt";
		File jsonFile = new File(jsonFilePath);
		if (!jsonFile.exists()){
			Charset charset = Charset.forName("UTF-8");
			Writer writer = null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(jsonFile), charset);
				writer.append(this.toJson());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
