package readingDbFromFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class MoNADatabase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	String name = "Mona";
	String negDirectory,posDirectory;
	int numCompound;
	ArrayList<MoNACompounds> compoundList;
	Map<String, MoNACompounds> compoundMap;
	ArrayList<MoNASpectrum> positiveSpectra;
	ArrayList<MoNASpectrum> negativeSpectra;
	Map<String, Map<Integer,ArrayList<MoNASpectrum>>> cmpSpectra; //key:compound inchikey, value: map{1: list of MS1; 2:list of MS2; 3: all spectra}
	
	public MoNADatabase(String name, String negDirectory, String posDirectory) {
		this.name = name;
		this.negDirectory = negDirectory;
		this.posDirectory = posDirectory;
	}
	
	private void addSpectra(String mode, MoNASpectrum spec) {
		if(mode.equals("negative")) {
			this.negativeSpectra.add(spec);
		}
		else if (mode.equals("positive")) {
			this.positiveSpectra.add(spec);
		}
		else {
			throw new java.lang.Error("must be positive or negative");
		}
	}
	
	public void readFile(String mode, String filePath) throws IOException, ParseException {
			if (filePath ==null) {
				throw new java.lang.Error("file path is empty!");
			}
			ReadingDataBaseMONA readingDB = new ReadingDataBaseMONA();
			JSONObject allSpecInfo =readingDB.readingJSONMoNA(filePath);
//			System.out.print(allSpecInfo.keySet());
			for(Object key: allSpecInfo.keySet()) {
				JSONObject JsonSpec = (JSONObject) allSpecInfo.get(key);
				String name = (String)JsonSpec.get("name");
				String SpectrumID = (String)JsonSpec.get("SpectrumID");
				String inchikey = (String)JsonSpec.get("inchikey");
				String molecularFormula = (String) JsonSpec.get("molecularFormula");
				double totalExactMass = (double)JsonSpec.get("totalExactMass");
				String thisMode = (String)JsonSpec.get("mode");
				String msLevel = (String) JsonSpec.get("msLevel");
				String precursorType = (String) JsonSpec.get("precursorType");
				ArrayList<Double> intensities = (ArrayList<Double>) JsonSpec.get("intensities"); 
				ArrayList<Double> mzs = (ArrayList<Double>) JsonSpec.get("mzs"); 
				ArrayList<ArrayList<Double>> spectrumList = new ArrayList<ArrayList<Double>>();
				
				MoNASpectrum newSpec = new MoNASpectrum(name=name, SpectrumID=SpectrumID,inchikey=inchikey,molecularFormula=molecularFormula, totalExactMass=totalExactMass, 
				mode=mode, msLevel=msLevel, precursorType=precursorType,intensities=intensities, mzs=mzs,spectrumList=spectrumList); 
				
				this.addSpectra(mode, newSpec); //add newSpec to MoNADatabase object
				
				Map<Integer,ArrayList<MoNASpectrum>> tempMap = (Map<Integer,ArrayList<MoNASpectrum>>)cmpSpectra.get(inchikey);
				if(tempMap.isEmpty()) { //reading spectra and add spectra one by one to this.cmpSpectra
					if(msLevel == "MS2") {
						ArrayList<MoNASpectrum> ms2ArrayList = new ArrayList<MoNASpectrum>();
						ms2ArrayList.add(newSpec);
						tempMap.put(2, ms2ArrayList);
						tempMap.put(3, ms2ArrayList);
						
					}
					else {
						ArrayList<MoNASpectrum> ms1ArrayList = new ArrayList<MoNASpectrum>();
						ms1ArrayList.add(newSpec);
						tempMap.put(1, ms1ArrayList);
						tempMap.put(3, ms1ArrayList);
					}
				}
				else {
					if(msLevel =="MS2") {
						ArrayList<MoNASpectrum> ms2List = tempMap.get(2);
						ms2List.add(newSpec);
						ArrayList<MoNASpectrum> allList = tempMap.get(3);
						allList.add(newSpec);

					}
					else {
						ArrayList<MoNASpectrum> ms1List = tempMap.get(1);
						ms1List.add(newSpec);
						ArrayList<MoNASpectrum> allList = tempMap.get(3);
						allList.add(newSpec);
					}
				}
			}
			int compoundID = 0; //append new MoNAcompound to this.compoundList and this.compoundMap
			for(Object inchi: cmpSpectra.keySet()) {
				String thisinchi = (String) inchi;
				int thiscmpID = compoundID;
				Map<Integer,ArrayList<MoNASpectrum>> thisSpcMap = (Map<Integer,ArrayList<MoNASpectrum>>)cmpSpectra.get(inchi);
				ArrayList<MoNASpectrum> thisAllSpecList = (ArrayList<MoNASpectrum>) thisSpcMap.get(3);
				MoNASpectrum thisSpec = thisAllSpecList.get(0);
				String thisCmpName = (String) thisSpec.getName();
				String thisMolecularFormular = (String) thisSpec.getMolecularFormula();
				double thisTotalMass = (double) thisSpec.getTotalExactMass();
				ArrayList<MoNASpectrum> thisMS1Spec = (ArrayList<MoNASpectrum>) thisSpcMap.get(1);
				ArrayList<MoNASpectrum> thisMS2Spec = (ArrayList<MoNASpectrum>) thisSpcMap.get(2);
				ArrayList<MoNASpectrum> thisAllSpec = (ArrayList<MoNASpectrum>) thisSpcMap.get(3);

				MoNACompounds thiscmp = new MoNACompounds(compoundID,thisCmpName,thisinchi, thisMolecularFormular,thisTotalMass,thisMS1Spec, thisMS2Spec,thisAllSpec);
				this.compoundList.add(thiscmp);
				this.compoundMap.put(thisinchi, thiscmp);
				compoundID +=1;
			}

		
	}
	
	
	
}
