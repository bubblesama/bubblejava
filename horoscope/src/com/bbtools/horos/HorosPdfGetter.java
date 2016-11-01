package com.bbtools.horos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import com.bbtools.horos.Horoscope.Sign;
import com.bubblebob.tool.web.CopyTools;

public class HorosPdfGetter {

	public HorosPdfGetter(Date date) {
		super();
		this.date = date;
		this.init();
	}

	public HorosPdfGetter() {
		super();
		this.date = new Date();
		this.init();
	}

	private static Map<String,Sign> translationMapping;
	private static String pdfFolder = "D:/tmp/horoscope/";
	private static String jsonFolder = "D:/tmp/horoscope/";
	private String datePath;
	private String yearPath;
	private Date date;

	static{
		translationMapping = new HashMap<String,Sign>();
		translationMapping.put("Bélier", Sign.Aries);
		translationMapping.put("Taureau", Sign.Taurus);
		translationMapping.put("Gémeaux", Sign.Gemini);
		translationMapping.put("Cancer", Sign.Cancer);
		translationMapping.put("Lion", Sign.Leo);
		translationMapping.put("Vierge", Sign.Virgo);
		translationMapping.put("Balance", Sign.Libra);
		translationMapping.put("Scorpion", Sign.Scorpius);
		translationMapping.put("Sagittaire", Sign.Sagittarius);
		translationMapping.put("Capricorne", Sign.Capricorn);
		translationMapping.put("Verseau", Sign.Aquarius);
		translationMapping.put("Poissons", Sign.Pisces);
	}

	private void init(){
		SimpleDateFormat compactDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		datePath = compactDateFormat.format(date);
		yearPath = yearFormat.format(date);
		// configuration
		Properties conf = new Properties();
		try {
			InputStream  input = new FileInputStream("horoscope.properties");
			// load a properties file
			conf.load(input);
			pdfFolder = conf.getProperty("folder_pdf");
			jsonFolder = conf.getProperty("folder_json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifie si le PDF existe, puis le telecharge eventuellement
	 */
	public boolean checkAndGetCurrentPdf(){
		boolean result = true;
		String pdfName = datePath+"_LIL.pdf";
		String pdfUrl = "http://pdf.20mn.fr/"+yearPath+"/quotidien/"+pdfName;
		String pdfPath = pdfFolder+pdfName;
		System.out.println("HorosPdfGetter#checkAndGetCurrentPdf pdf path: "+pdfPath);
		System.out.println("HorosPdfGetter#checkAndGetCurrentPdf pdf url: "+pdfUrl);
		File file = new File(pdfPath);
		if (!file.exists()){
			// recuperation du fichier du 20 minutes de lille
			result = !CopyTools.copyFile(pdfUrl, pdfFolder+pdfName);
		}
		return result;
	}

	/**
	 * Fournit une prediction journaliere en recuperant le PDF sur le reseau
	 * @return la prediction du jour du getter
	 */
	public DailyPrediction getPredictionFromPdf(){
		DailyPrediction result = null;
		InputStream inputStream = null;
		try {
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			String pdfName = datePath+"_LIL.pdf";
			inputStream = new FileInputStream(pdfFolder+pdfName);
			ContentHandler contentHandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			PDFParser pdfparser = new PDFParser();
			try{
				pdfparser.parse(inputStream, contentHandler, metadata, new ParseContext());
				result = new DailyPrediction();
				result.date = date;
				for (String frenchSign: translationMapping.keySet()){
					Sign sign = translationMapping.get(frenchSign);
					Pattern pattern = Pattern.compile(frenchSign+".+?\\n(.+?)\\n\\n(.+?)\\n(.+?)\\n",Pattern.MULTILINE);
					Matcher matcher = pattern.matcher(contentHandler.toString());
					String prediction = "";
					while(matcher.find()){
						for (int i=1;i<4;i++){
							prediction += matcher.group(i);
						}
					}
					Horoscope horoscope = new Horoscope(sign,prediction);
					result.horoscopes.put(sign, horoscope);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Sauvegarde dans un fichier JSON la prediction recuperee du PDF
	 */
	public void savePrediction(){
		String jsonFilePath = pdfFolder+"json."+datePath+".txt";
		File jsonFile = new File(jsonFilePath);
		if (!jsonFile.exists()){
			DailyPrediction prediction = getPredictionFromPdf();
			Charset charset = Charset.forName("UTF-8");
			Writer writer = null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(jsonFile), charset);
				writer.append(prediction.toJson());
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

	public static void main(String[] args) {
		HorosPdfGetter getter = new HorosPdfGetter();
		if (getter.checkAndGetCurrentPdf()){
			DailyPrediction prediction = getter.getPredictionFromPdf();
			if (prediction != null){
				System.out.println(prediction.toJson());
				getter.savePrediction();
			}
		}else{
			System.out.println("Erreur de recuperation des horoscopes");
		}
	}

	/**
	 * Recupere le PDF de la date fournie et cree le fichier JSON de prediction correspondant
	 * @param dateString
	 * @throws ParseException
	 */
	public static void synchroniseHoroscope(String dateString) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
		Date date = formater.parse(dateString);
		HorosPdfGetter getter = new HorosPdfGetter(date);
		if (getter.checkAndGetCurrentPdf()){
			DailyPrediction prediction = getter.getPredictionFromPdf();
			if (prediction != null){
				System.out.println(prediction.toJson());
				getter.savePrediction();
			}
		}else{
			System.out.println("Erreur de recuperation des horoscopes");
		}
	}

}
