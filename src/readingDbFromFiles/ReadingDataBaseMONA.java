package readingDbFromFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadingDataBaseMONA {

	public static void main(String[] args) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		
//		File jsonFile = new File("databasefiles/b.json");
//		System.out.println(jsonFile.exists());
		
		FileReader reader = new FileReader("/Users/sisizhang/Dropbox/Share_Yuchen/Projects/JAVA_MassSpectraMatching/MoNA-export-LC-MS-MS_Negative_Mode.json");
		
//		FileReader reader = new FileReader("..\\databasefiles\\b.json");
		
		Object obj = jsonParser.parse(reader); //jsonParser returned a JAVAObject
	
		JSONArray specArrays = (JSONArray)obj; //cast java object to JSONArray     {}:JSONObject  []:JSONArray
	
//		for(int i=0; i<arrays.size();++i) {
//			JSONObject cmp = (JSONObject) arrays;
//			
//		}
		int idx = 0;
		JSONObject specInfo = (JSONObject)specArrays.get(idx);
		JSONArray metaData1 = (JSONArray) specInfo.get("metaData");
		JSONArray comInfoArray = (JSONArray)specInfo.get("compound");
		
		JSONArray metaData2 = (JSONArray)specInfo.get("metaData");
		String specString = (String) specInfo.get("spectrum"); //spectrum can be null
		String specID = (String) specInfo.get("id");
		JSONObject comInfoObj = (JSONObject)comInfoArray.get(idx);
		String inchikey = (String)comInfoObj.get("inchiKey");
		
		System.out.println(inchikey);
		System.out.println(specString);
		
		for(int j=0;j<metaData2.size();++j) { //iterate in metaData1
			JSONObject temp = (JSONObject) metaData2.get(j);
			String name = (String) temp.get("name");
			if(name.equals("molecular formula")) {
				String formula = (String) temp.get("value");
				System.out.println("molecular formula: " + formula);
			}
			else if(name.equals("total exact mass")) {
				String totalMass = (String) temp.get("value");
				System.out.println("total exact mass: " + totalMass);
			}

			
		}
		for(int i=0; i<metaData1.size();++i){
			JSONObject temp = (JSONObject) metaData1.get(i);
			String name = (String) temp.get("name");
			if(name.equals("ms level")) {
				String msLevel = (String) temp.get("value");
				System.out.println("ms level: " + msLevel);
			}
			else if(name.equals("collision energy")) {
				String colliEnergy = (String) temp.get("value");
				System.out.println("collision energy: " + colliEnergy);
			}
			else if(name.equals("precursor m/z")) {
				double preMZ = (double)temp.get("value");
				System.out.println("precursor m/z: " + preMZ);
			}
			else if(name.equals("precursor type")) {
				String preType = (String) temp.get("value");
				System.out.println("precursor type: " + preType);
			}


		}
		
		
		
		
		// TODO Auto-generated method stub
		
	}

}
