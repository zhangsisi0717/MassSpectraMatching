package readingDbFromFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.parser.ParseException;

public class RunSpectrumMatching {

	public static void main(String[] args) throws IOException, ParseException {
		//path of the downloaded MoNA-export-LC-MS-MS_Negative_Mode.json/MoNA-export-LC-MS-MS_Positive_Mode.json files
		String negPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Negative_Mode.json";
		String posPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Positive_Mode.json";
		
		MoNADatabase mona = new MoNADatabase("MoNA", negPath, posPath);  
		mona.readFile("negative", negPath);  //reading MoNA database
		System.out.println("\n\n"+ mona);
		
		//input the mzs list of spectrum that needed to be matched
		Number[] mz = {78.959,96.969,138.979,168.9900,191.299,199.0000,241.0100,259.0210}; 
		
		//input the intensity list of spectrum that needed to be matched
		Number[] ints = {0.0202833,0.3542572,0.026045,0.0178521,0.0001062,0.1032,0.003643933,1.0}; 
		
		ArrayList<Number> mz2 = new ArrayList<Number>();
		ArrayList<Number> ints2 = new ArrayList<Number>();
		Collections.addAll(mz2, mz);
		Collections.addAll(ints2, ints);
		MSSpectrum s = new MSSpectrum("negative", "", "",ints2,mz2,new ArrayList<ArrayList<Number>>());	
		
		
		/**
		 * output: a list of matched compound results
		 * @params: 1. database: default mona
		 * 			2. threshold matching score, the larger the better match (0.0-1.0), 
		 * 			results larger than the score will be included into the final results
		 */
		ArrayList<CompoundMatchingResults> result = s.findMatchMonaDB(mona, 0.3);
		
		
		for(int index=0; index<Math.max(result.size(), 10); index++) { //print out the top 10 best match
		System.out.println("\n");
		System.out.println(result.get(index));}
		
	}

}
