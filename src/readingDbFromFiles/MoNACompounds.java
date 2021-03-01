package readingDbFromFiles;

import java.util.ArrayList;

public class MoNACompounds {
	int cmpID;
	String name,inchikey,molecularFormula;
	double totalExactMass;
	ArrayList<MoNASpectrum> spectraMS1;
	ArrayList<MoNASpectrum> spectraMS2;
	ArrayList<MoNASpectrum> allSpectra;
	
	public MoNACompounds(int cmpID, String name, String inchikey, String molecularFormula, double totalExactMass,
			ArrayList<MoNASpectrum> spectraMS1, ArrayList<MoNASpectrum> spectraMS2,ArrayList<MoNASpectrum> allSpectra) {
		this.cmpID = cmpID;
		this.name = name;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
		this.totalExactMass = totalExactMass;
		this.spectraMS1 = spectraMS1;
		this.spectraMS2 = spectraMS2;
		this.allSpectra = allSpectra;
	}
	
	public void addSpectraMS1(MoNASpectrum spectrumMS1) {
		this.spectraMS1.add(spectrumMS1);
	}
	
	public void addSpectraMS2(MoNASpectrum spectrumMS2) {
		this.spectraMS2.add(spectrumMS2);
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

	public ArrayList<MoNASpectrum> getSpectraMS1() {
		return spectraMS1;
	}

	public ArrayList<MoNASpectrum> getSpectraMS2() {

		return spectraMS2;
	}
	

	public ArrayList<MoNASpectrum> getAllSpectra() {
		return allSpectra;
	}
	
	
}


