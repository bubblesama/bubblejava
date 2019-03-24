package com.bbtools.horos.extractor;

import java.io.File;
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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.bbtools.horos.extractor.Horoscope.Sign;
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
	private static String FOLDER_PDF = "D:/tmp/horoscope/";
	private static String FOLDER_JSON = "D:/tmp/horoscope/";
	private static String URL_PATTERN="https://pdf.20mn.fr/%s/quotidien/%s";
	private String datePath;
	private String yearPath;
	private String fullPdfPath;
	private String pdfUrl;
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
		System.out.println("HorosPdfGetter#init IN");
		// configuration
		Properties conf = new Properties();
		try {
			InputStream  input = getClass().getResourceAsStream("/horoscope.properties");
			// load a properties file
			conf.load(input);
			System.out.println("HorosPdfGetter#init loading des confs");
			FOLDER_PDF = conf.getProperty("folder.pdf");
			FOLDER_JSON = conf.getProperty("folder.json");
			URL_PATTERN = conf.getProperty("url.pattern");
			System.out.println("HorosPdfGetter#init confs: FOLDER_PDF="+FOLDER_PDF+" FORLDER_JSON="+FOLDER_JSON+" URL_PATTERN="+URL_PATTERN );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SimpleDateFormat compactDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		this.datePath = compactDateFormat.format(date);
		this.yearPath = yearFormat.format(date);
		String pdfName = datePath+"_LIL.pdf";
		this.pdfUrl = String.format(URL_PATTERN,yearPath,pdfName);
		this.fullPdfPath = FOLDER_PDF+pdfName;
	}

	/**
	 * Verifie si le PDF existe, puis le telecharge eventuellement
	 */
	public boolean checkAndGetCurrentPdf(){
		boolean result = true;
		System.out.println("HorosPdfGetter#checkAndGetCurrentPdf IN  fullPdfPath="+this.fullPdfPath+" pdfUrl="+this.pdfUrl);
		File file = new File(fullPdfPath);
		if (!file.exists()){
			result = !CopyTools.copyFile(pdfUrl, fullPdfPath);
			System.out.println("HorosPdfGetter#checkAndGetCurrentPdf file copied");
		}else {
			System.out.println("HorosPdfGetter#checkAndGetCurrentPdf file already available");
		}
		return result;
	}

	/**
	 * Fournit une prediction journaliere en recuperant le PDF sur le reseau
	 * @return la prediction du jour du getter
	 */
	public DailyPrediction getPredictionFromPdf(){
		System.out.println("HorosPdfGetter#getPredictionFromPdf IN parsing pdf: "+fullPdfPath);
		DailyPrediction result = null;
		try (PDDocument document = PDDocument.load(new File(fullPdfPath))) {
			document.getClass();
			if (!document.isEncrypted()) {
				System.out.println("HorosPdfGetter#getPredictionFromPdf file found, beginning parsing");
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				String pdfFileInText = tStripper.getText(document);
				String lines[] = pdfFileInText.split("\\r?\\n");
				/*
				for (String line : lines) {
					System.out.println(line);
				}
				*/
				result = new DailyPrediction();
				result.date = date;
				for (String frenchSign: translationMapping.keySet()){
					System.out.println("HorosPdfGetter#getPredictionFromPdf signe="+frenchSign);
					boolean found = false;
					int lineIndex = 0;
					String prediction = "";
					for (String line : lines) {
						if (line.startsWith(""+frenchSign)){
							System.out.println("HorosPdfGetter#getPredictionFromPdf signe:"+frenchSign+" detecte");
							found = true;
						}
						if (found && lineIndex<4) {
							if (lineIndex>0) {
								prediction += line;
							}
							lineIndex++;
						}
					}
					if (found) {
						Sign sign = translationMapping.get(frenchSign);
						Horoscope horoscope = new Horoscope(sign,prediction);
						result.horoscopes.put(sign, horoscope);
					}
					
					/*
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
					 */
				}
				System.out.println("HorosPdfGetter#getPredictionFromPdf parsing finished");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Sauvegarde dans un fichier JSON la prediction recuperee du PDF
	 */
	public void savePrediction(){
		String jsonFilePath = FOLDER_PDF+"json."+datePath+".txt";
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

	
	/**
	 * Argument: date following the format yyyy-MM-dd, optionnal (default: today
	 * @param args
	 */
	public static void main(String[] args) {
		// horoscope du jour
		HorosPdfGetter getter; 
		if (args.length > 0) {
			//forcage au 2018-03-22
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date chosenDate = dayFormat.parse(args[0]);
				getter = new HorosPdfGetter(chosenDate);
				System.out.println("HorosPdfGetter#main date provided "+args[0]);
			} catch (ParseException e) {
				System.out.println("HorosPdfGetter#main error on date, default date today taken into account");
				e.printStackTrace();
				getter = new HorosPdfGetter();
			}
		}else {
			getter = new HorosPdfGetter();
			System.out.println("HorosPdfGetter#main no date provided, default date on today");
		}
		if (getter.checkAndGetCurrentPdf()){
			DailyPrediction prediction = getter.getPredictionFromPdf();
			if (prediction != null){
				System.out.println(prediction.toJson());
				getter.savePrediction();
			}
		}else{
			System.out.println("HorosPdfGetter#main Erreur de recuperation des horoscopes");
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
