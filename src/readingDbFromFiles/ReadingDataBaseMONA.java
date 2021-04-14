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
		
		String negPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Negative_Mode.json";
		String posPath = "/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Positive_Mode.json";
		MoNADatabase mona = new MoNADatabase("MoNA", negPath, posPath);
		mona.readFile("negative", negPath);
//		System.out.println();
		System.out.println("\n\n"+ mona);
		MoNACompounds cmp = mona.getCompoundList().get(100);
//		System.out.println("\n\n"+ cmp);
		MoNASpectrum s1 = cmp.getAllSpectra().get(0);
		ArrayList<CompoundMatchingResults> result = s1.findMatchMonaDB(mona, 0.8);
		for(int index=0; index<10; index++) {
		System.out.println("\n");
		System.out.println(result.get(index));}
		
	}

	public static JSONObject readingJSONMoNA(String path) throws IOException, ParseException{
		JSONParser jsonParser = new JSONParser();

		FileReader reader = new FileReader(path);

		Object obj = jsonParser.parse(reader); //jsonParser returned a JAVAObject

		JSONArray specArrays = (JSONArray)obj; //cast java object to JSONArray     {}:JSONObject  []:JSONArray

		JSONObject allSpectraInfo = new JSONObject();
		for(int idx=0; idx<specArrays.size();++idx) {
			
			if (idx%1000==0 || idx == specArrays.size()-1) {
			System.out.print("\r"+ "reading spectrum: " + (idx+1) + "/" + specArrays.size()+ "    ");
			}
			JSONObject singleSpectraInfo = new JSONObject();

			JSONObject specInfo = (JSONObject)specArrays.get(idx);  //specInfo = mona[idx]

			String specID = (String) specInfo.get("id");        // specID = mona[idx].get("id")
			singleSpectraInfo.put("SpectrumID",specID);			//add specID

			String specString = (String) specInfo.get("spectrum"); //spectrum can be null  specString = mona[idx].get("spectrum")

			if(specString == null) {
				continue;
			}
		
			JSONArray metaData1 = (JSONArray) specInfo.get("metaData"); //metaData1 = mona[idx].get("metaData")  (Array)
			JSONArray comInfoArray = (JSONArray)specInfo.get("compound");  //comInfoArray = mona[idx].get('compound') (Array)

			JSONObject comInfoObj = (JSONObject)comInfoArray.get(0);  //comInfoObj = mona[idx].get('compound')[0]
			String inchikey = (String)comInfoObj.get("inchiKey");  //inchikey = mona_raw[idx]['compound'][0].get('inchiKey')
			if(inchikey==null) {
				continue;
			}
			singleSpectraInfo.put("inchikey",inchikey); //add inchikey
			JSONArray nameArray = (JSONArray) comInfoObj.get("names"); //nameArray = mona_raw[idx]['compound'][0].get('names')
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

			for(int jd=tempIndex+1; jd<specString.length();++jd) {        //parse the spectra string and convert into double
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

	
			JSONArray metaData2 = (JSONArray)comInfoObj.get("metaData"); //metaData2 = mona_raw[idx]['compound'].get(0).get('metaData')

			for(int j=0;j<metaData2.size();++j) { //iterate in metaData2
				JSONObject temp = (JSONObject) metaData2.get(j);
				String name = (String) temp.get("name");
				if(name.equals("molecular formula")) {
					String formula = (String) temp.get("value");
					singleSpectraInfo.put("molecularFormula",formula); //add to JSONObject
				
				}
				else if(name.equals("total exact mass")) {
					double totalExactMass = (double)temp.get("value");
					singleSpectraInfo.put("totalExactMass", totalExactMass);

					
				}

			}
			if(singleSpectraInfo.get("molecularFormula") ==null) {
				singleSpectraInfo.put("molecularFormula", "null");///to confirm if those fields are null!
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
