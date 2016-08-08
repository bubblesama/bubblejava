package com.bubblesama.tetrahis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Highscore {

	private static final String HIGHSCORE_PATH = "tetrahis_highscore.txt";
	private static final int SIZE = 10;

	public List<Score> scores;

	public Highscore(){
		scores = new ArrayList<Score>();
	}

	public static Highscore load(){
		Highscore result = new Highscore();
		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(HIGHSCORE_PATH), "UTF-8");
			try {
				while (scanner.hasNextLine()){
					String line = scanner.nextLine();
//					System.out.println("Highscore#load line="+line);
					String[] rawScore = line.split(" ");
					result.addScore(Integer.parseInt(rawScore[1]), rawScore[0]);
				}
			}
			finally{
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public void save(){
		Collections.sort(scores);
		Collections.reverse(scores);
		this.scores = this.scores.subList(0, Math.min(scores.size(), SIZE));
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(HIGHSCORE_PATH), "UTF-8");
			for (Score score: scores){
//				System.out.println("Highscore#save score.name="+score.name+" score.value="+score.value);
				out.write(score.name+" "+score.value+"\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addScore(int value, String name){
		this.scores.add(new Score(value, name));
		Collections.sort(scores);
		Collections.reverse(scores);
		this.scores = this.scores.subList(0, Math.min(scores.size(), SIZE));
	}

	public class Score implements Comparable<Score>{
		public int value;
		public String name;
		public int compareTo(Score other) {
			return Integer.valueOf(value).compareTo(Integer.valueOf((other).value));
		}
		public Score(int _value, String _name){
			name = _name;
			value = _value;
		}
	}

	public static void main(String[] args) {
		Highscore score = Highscore.load();
		score.addScore(123, "John");
		score.addScore((int)(System.currentTimeMillis()-(System.currentTimeMillis()/1000000)*1000000), "Jonh");
		score.save();
		score = Highscore.load();
	}
}
