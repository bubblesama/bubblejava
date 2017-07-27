package com.bubblebob.tool.worldgen;

import java.util.Random;

public class ComposedPerlinNoise {
	
	public int harmos;
	public double[][] distorsions;
	public double[] powers;
	
	

	
	public double[][] generateRandomBitmap(int w, int h){
		
		double[][] result = new double[w][h];
		double min = 1;
		double max = 0;
		double[] offsets = new double[harmos+1];
		//  ponderation sum
		double pondSum = 0;
		for (int k=0;k<harmos;k++){
			pondSum += powers[k];
		}
		//offsets
		for (int n=0;n<harmos+1;n++){
			offsets[n] = new Random().nextDouble()*((w+h)*10);
		}
		// bitmap
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				double value = 0;
				for (int k=0;k<harmos;k++){
					value+= powers[k]*(PerlinNoise.noise((offsets[k]+i)/distorsions[k][0], (offsets[k]+j)/distorsions[k][1], offsets[harmos])+1)/2.0;
				}
				result[i][j] = (i/(double)w)*(j/(double)h)*value/pondSum;
				if (result[i][j]> max){max = result[i][j];}
				if (result[i][j]< min){min = result[i][j];}
			}
		}
		System.out.println("ComposedPerlinNoise#generateRandomBitmap min="+min+" max="+max);
		return result;
		
	}
	
	public double[][] generateAttenuatedBitmap(int w, int h){
		double[][] randomBitmap = generateRandomBitmap(w, h);
		double min = 1;
		double max = 0;
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				randomBitmap[i][j] = attenuator(i/(double)w)*attenuator(j/(double)h)*randomBitmap[i][j];
				if (randomBitmap[i][j]> max){max = randomBitmap[i][j];}
				if (randomBitmap[i][j]< min){min = randomBitmap[i][j];}
			}
		}
		// dilatation 
		double dilatation = max-min;
		System.out.println("ComposedPerlinNoise#generateAttenuatedBitmap min="+min+" max="+max+" dilatation="+dilatation);
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				randomBitmap[i][j] = (randomBitmap[i][j])/dilatation;
			}
		}
		return randomBitmap;
	}
	
	
	public double[][] generateOldAttenuatedBitmap(int w, int h){
		double[][] result = new double[w][h];
		double min = 1;
		double max = 0;
		double[] offsets = new double[harmos+1];
		//  ponderation sum
		double pondSum = 0;
		for (int k=0;k<harmos;k++){
			pondSum += powers[k];
		}
		//offsets
		for (int n=0;n<harmos+1;n++){
			offsets[n] = new Random().nextDouble()*((w+h)*10);
		}
		// bitmap
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				double value = 0;
				for (int k=0;k<harmos;k++){
					value+= powers[k]*(PerlinNoise.noise((offsets[k]+i)/distorsions[k][0], (offsets[k]+j)/distorsions[k][1], offsets[harmos])+1)/2.0;
				}
				result[i][j] = attenuator(i/(double)w)*attenuator(j/(double)h)*value/pondSum;
				
				if (result[i][j]> max){max = result[i][j];}
				if (result[i][j]< min){min = result[i][j];}
			}
		}
		System.out.println("ComposedPerlinNoise#generateBitmap min="+min+" max="+max);
		return result;
	}
	
	static double attenuator(double x){return 4*x*(1-x);}
	
}
