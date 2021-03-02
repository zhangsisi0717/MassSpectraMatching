package readingDbFromFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class MoNADatabase {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//	}
	
	private String name = "Mona";
	private String negDirectory,posDirectory;
	private int numCompound;
	private ArrayList<MoNACompounds> compoundList;
	private HashMap<String, MoNACompounds> compoundMap;
	private ArrayList<MoNASpectrum> positiveSpectra;
	private ArrayList<MoNASpectrum> negativeSpectra;
	private HashMap<String, Map<Integer,ArrayList<MoNASpectrum>>> cmpSpectra; //key:compound inchikey, value: map{1: list of MS1; 2:list of MS2; 3: all spectra}
	
	public MoNADatabase(String name, String negDirectory, String posDirectory) {
		this.name = name;
		this.negDirectory = negDirectory;
		this.posDirectory = posDirectory;
		this.numCompound = 0;
		this.compoundList = new ArrayList<MoNACompounds>();
		this.compoundMap = new HashMap<String, MoNACompounds>();
		this.positiveSpectra = new ArrayList<MoNASpectrum>();
		this.negativeSpectra = new ArrayList<MoNASpectrum>();
		this.cmpSpectra = new HashMap<String, Map<Integer,ArrayList<MoNASpectrum>>>();
		
		
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
			int readingIdx = 0;
			for(Object key: allSpecInfo.keySet()) {
				readingIdx +=1;
				System.out.println("\n\n");
				System.out.println("Reading index: " + readingIdx);
				JSONObject JsonSpec = (JSONObject) allSpecInfo.get(key);
				String name = (String)JsonSpec.get("name");
				System.out.println("name: " + name);
				
				String SpectrumID = (String)JsonSpec.get("SpectrumID");
				System.out.println("SpectrumID: " + SpectrumID);
				
				String inchikey = (String)JsonSpec.get("inchikey");
				System.out.println("inchikey: " + inchikey);
				
				String molecularFormula = (String) JsonSpec.get("molecularFormula");
				System.out.println("molecularFormula: " + molecularFormula);
				
				double totalExactMass = (double)JsonSpec.get("totalExactMass");
				System.out.println("totalExactMass: " + totalExactMass);
				
					
				
//				String thisMode = (String)JsonSpec.get("mode");
				String msLevel = (String) JsonSpec.get("msLevel");
				System.out.println("msLevel: " + SpectrumID);
				
				String precursorType = (String) JsonSpec.get("precursorType");
				System.out.println("precursorType: " + precursorType);
				
				ArrayList<Double> intensities = (ArrayList<Double>) JsonSpec.get("intensities"); 
				System.out.println("molecularFormula: " + molecularFormula);
				
				ArrayList<Double> mzs = (ArrayList<Double>) JsonSpec.get("mzs"); 
				ArrayList<ArrayList<Double>> spectrumList = new ArrayList<ArrayList<Double>>();
				
				
				MoNASpectrum newSpec = new MoNASpectrum(name=name, SpectrumID=SpectrumID,inchikey=inchikey,molecularFormula=molecularFormula,totalExactMass=totalExactMass, 
				mode=mode, msLevel=msLevel, precursorType=precursorType,intensities=intensities, mzs=mzs,spectrumList=spectrumList); 
				
				this.addSpectra(mode, newSpec); //add newSpec to MoNADatabase object
				
				HashMap<Integer,ArrayList<MoNASpectrum>> tempMap = (HashMap<Integer,ArrayList<MoNASpectrum>>)cmpSpectra.get(inchikey);
				if(tempMap==null) { //reading spectra and add spectra one by one to this.cmpSpectra
					tempMap = new HashMap<Integer,ArrayList<MoNASpectrum>>();
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
				HashMap<Integer,ArrayList<MoNASpectrum>> thisSpcMap = (HashMap<Integer,ArrayList<MoNASpectrum>>)cmpSpectra.get(inchi);
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
			this.numCompound = compoundID;

		
	}

	public String getName() {
		return name;
	}

	public String getNegDirectory() {
		return negDirectory;
	}

	public String getPosDirectory() {
		return posDirectory;
	}

	public int getNumCompound() {
		return numCompound;
	}

	public ArrayList<MoNACompounds> getCompoundList() {
		return compoundList;
	}

	public HashMap<String, MoNACompounds> getCompoundMap() {
		return compoundMap;
	}

	public ArrayList<MoNASpectrum> getPositiveSpectra() {
		return positiveSpectra;
	}

	public ArrayList<MoNASpectrum> getNegativeSpectra() {
		return negativeSpectra;
	}

	
	
	
	
}
