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
	private HashMap<String, ArrayList<MoNASpectrum>> cmpSpectra; //key:compound inchikey, value: map{1: list of MS1; 2:list of MS2; 3: all spectra}

	public MoNADatabase(String name, String negDirectory, String posDirectory) {
		this.name = name;
		this.negDirectory = negDirectory;
		this.posDirectory = posDirectory;
		this.numCompound = 0;
		this.compoundList = new ArrayList<MoNACompounds>();
		this.compoundMap = new HashMap<String, MoNACompounds>();
		this.positiveSpectra = new ArrayList<MoNASpectrum>();
		this.negativeSpectra = new ArrayList<MoNASpectrum>();
		this.cmpSpectra = new HashMap<String, ArrayList<MoNASpectrum>>();

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
		for(Object key: allSpecInfo.keySet()) {  //iterate through returned allSpecInfo: key:1,2,3... value:mzs,ints,name...
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
			System.out.println("msLevel: " + msLevel);

			String precursorType = (String) JsonSpec.get("precursorType");
			System.out.println("precursorType: " + precursorType);

			ArrayList<Double> intensities = (ArrayList<Double>) JsonSpec.get("intensities"); 

			ArrayList<Double> mzs = (ArrayList<Double>) JsonSpec.get("mzs"); 
			ArrayList<ArrayList<Double>> spectrumList = new ArrayList<ArrayList<Double>>();


			MoNASpectrum newSpec = new MoNASpectrum(name=name, SpectrumID=SpectrumID,inchikey=inchikey,molecularFormula=molecularFormula,totalExactMass=totalExactMass, 
					mode=mode, msLevel=msLevel, precursorType=precursorType,intensities=intensities, mzs=mzs,spectrumList=spectrumList); 

			this.addSpectra(mode, newSpec); //add newSpec to MoNADatabase object


			if(this.cmpSpectra.get(inchikey)==null) { //reading spectra and add spectra one by one to this.cmpSpectra
				ArrayList<MoNASpectrum> tempSpecList = new ArrayList<MoNASpectrum>();
				tempSpecList.add(newSpec);
				this.cmpSpectra.put(inchikey, tempSpecList);						

			}
			else {
				this.cmpSpectra.get(inchikey).add(newSpec);

			}

		}
		int compoundID = 0; //append new MoNAcompound to this.compoundList and this.compoundMap
		for(Object inchi: cmpSpectra.keySet()) {
			String thisinchi = (String) inchi;
			String thisCmpName = cmpSpectra.get(thisinchi).get(0).getName();
			String thisMolecularFormular = cmpSpectra.get(thisinchi).get(0).getMolecularFormula();
			double thisTotalMass = cmpSpectra.get(thisinchi).get(0).getTotalExactMass();

			ArrayList<MoNASpectrum> thisAllSpec = (ArrayList<MoNASpectrum>)cmpSpectra.get(thisinchi);

			MoNACompounds thiscmp = new MoNACompounds(compoundID, thisCmpName, thisinchi, thisMolecularFormular, thisTotalMass,thisAllSpec);
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
