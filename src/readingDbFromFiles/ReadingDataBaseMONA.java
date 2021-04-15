package readingDbFromFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import matrixOperations.Matrix;

public class ReadingDataBaseMONA {

	public static void main(String[] args) throws IOException, ParseException {
		
//		String negPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Negative_Mode.json";
//		String posPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Positive_Mode.json";
//		MoNADatabase mona = new MoNADatabase("MoNA", negPath, posPath);
//		mona.readFile("negative", negPath);
//		System.out.println("\n\n"+ mona);
//
//		Number[] mz = {199.0000,241.0100,259.0210};
//		Number[] ints = {0.1032,0.003643933,1.0};
//		ArrayList<Number> mz2 = new ArrayList<Number>();
//		ArrayList<Number> ints2 = new ArrayList<Number>();
//		Collections.addAll(mz2, mz);
//		Collections.addAll(ints2, ints);
//		MSSpectrum s = new MSSpectrum("negative", "", "",ints2,mz2,new ArrayList<ArrayList<Number>>());	
//		ArrayList<CompoundMatchingResults> result = s.findMatchMonaDB(mona, 0.01);
//		
//	
//		for(int index=0; index<10; index++) { ////print out the top 10 best match
//		System.out.println("\n");
//		System.out.println(result.get(index));}
		
	}

	public static JSONObject readingJSONMoNA(String path) throws IOException, ParseException{
		JSONParser jsonParser = new JSONParser();

		FileReader reader = new FileReader(path);

		Object obj = jsonParser.parse(reader); 

		JSONArray specArrays = (JSONArray)obj; 

		JSONObject allSpectraInfo = new JSONObject();
		for(int idx=0; idx<specArrays.size();++idx) {
			
			if (idx%1000==0 || idx == specArrays.size()-1) {
			System.out.print("\r"+ "reading spectrum: " + (idx+1) + "/" + specArrays.size()+ "    ");
			}
			JSONObject singleSpectraInfo = new JSONObject();

			JSONObject specInfo = (JSONObject)specArrays.get(idx);  

			String specID = (String) specInfo.get("id");       
			singleSpectraInfo.put("SpectrumID",specID);			

			String specString = (String) specInfo.get("spectrum"); 

			if(specString == null) {
				continue;
			}
		
			JSONArray metaData1 = (JSONArray) specInfo.get("metaData"); 
			JSONArray comInfoArray = (JSONArray)specInfo.get("compound"); 

			JSONObject comInfoObj = (JSONObject)comInfoArray.get(0);  
			String inchikey = (String)comInfoObj.get("inchiKey");  
			if(inchikey==null) {
				continue;
			}
			singleSpectraInfo.put("inchikey",inchikey); 
			JSONArray nameArray = (JSONArray) comInfoObj.get("names"); 
			if (nameArray.isEmpty()) {
				singleSpectraInfo.put("name","null");

			}
			else {
				JSONObject nameObj = (JSONObject) nameArray.get(0);
				String name = (String) nameObj.get("name");
				singleSpectraInfo.put("name",name);
			}

			int tempIndex = 0;
			List<Number> specMz = new ArrayList<Number>();
			List<Number> specInts = new ArrayList<Number>();

			for(int jd=tempIndex+1; jd<specString.length();++jd) {       
				if(specString.charAt(jd) == ':') {
					Double mz = Double.parseDouble(specString.substring(tempIndex, jd));
			
					specMz.add(mz);
					tempIndex = jd +1;
				}
				else if(specString.charAt(jd) == ' ') {
					Double intensity = Double.parseDouble(specString.substring(tempIndex, jd));
					specInts.add(intensity);
					tempIndex = jd +1;	
				}
				else if(jd == specString.length()-1) {
					Double intensity = Double.parseDouble(specString.substring(tempIndex, specString.length()));
					specInts.add(intensity);
				}
			}
			if(specMz.size()>100) {
				continue;
			}
			singleSpectraInfo.put("mzs", specMz);
			singleSpectraInfo.put("intensities", specInts);

	
			JSONArray metaData2 = (JSONArray)comInfoObj.get("metaData"); 

			for(int j=0;j<metaData2.size();++j) { 
				JSONObject temp = (JSONObject) metaData2.get(j);
				String name = (String) temp.get("name");
				if(name.equals("molecular formula")) {
					String formula = (String) temp.get("value");
					singleSpectraInfo.put("molecularFormula",formula); 
				
				}
				else if(name.equals("total exact mass")) {
					double totalExactMass = (double)temp.get("value");
					singleSpectraInfo.put("totalExactMass", totalExactMass);

					
				}

			}
			if(singleSpectraInfo.get("molecularFormula") ==null) {
				singleSpectraInfo.put("molecularFormula", "null");
			}
			if(singleSpectraInfo.get("totalExactMass")==null) {
				singleSpectraInfo.put("totalExactMass", 0.0);
			}

			for(int i=0; i<metaData1.size();++i){
				JSONObject temp = (JSONObject) metaData1.get(i);
				String name = (String) temp.get("name");
				if(name.equals("ms level")) {
					String msLevel = (String) temp.get("value");
					singleSpectraInfo.put("msLevel", msLevel);
					
				}
				else if(name.equals("precursor type")) {
					String preType = (String) temp.get("value");
					singleSpectraInfo.put("precursorType", preType);
					
				}
			}
			if(singleSpectraInfo.get("msLevel")==null) {
				singleSpectraInfo.put("msLevel","null");
			}
			if(singleSpectraInfo.get("precursorType")==null) {
				singleSpectraInfo.put("precursorType", "null");
			}

			allSpectraInfo.put(idx, singleSpectraInfo);
		}
		return allSpectraInfo;
	}
}
