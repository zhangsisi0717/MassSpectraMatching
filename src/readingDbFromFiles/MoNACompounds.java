package readingDbFromFiles;

import java.util.ArrayList;

public class MoNACompounds {
	int cmpID;
	String name,inchikey,molecularFormula;
	double totalExactMass;
//	ArrayList<MoNASpectrum> spectraMS1;
//	ArrayList<MoNASpectrum> spectraMS2;
	ArrayList<MoNASpectrum> allSpectra;
	
	public MoNACompounds(int cmpID, String name, String inchikey, String molecularFormula, double totalExactMass,ArrayList<MoNASpectrum> allSpectra) {
		this.cmpID = cmpID;
		this.name = name;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
		this.totalExactMass = totalExactMass;
//		this.spectraMS1 = spectraMS1;
//		this.spectraMS2 = spectraMS2;
		this.allSpectra = allSpectra;
	}
	

	public int getCmpID() {
		return cmpID;
	}

	public String getName() {
		return name;
	}

	public String getInchikey() {
		return inchikey;
	}

	public String getMolecularFormula() {
		return molecularFormula;
	}

	public double getTotalExactMass() {
		return totalExactMass;
	}


	public ArrayList<MoNASpectrum> getAllSpectra() {
		return allSpectra;
	}
	
	
}


