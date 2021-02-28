package readingDbFromFiles;

import java.util.ArrayList;

public class MoNACompounds {
	int cmpID;
	String name,inchikey,molecularFormula;
	double totalExactMass;
	ArrayList<MoNASpectrum> spectraMS1;
	ArrayList<MoNASpectrum> spectraMS2;
	public MoNACompounds(int cmpID, String name, String inchikey, String molecularFormula, double totalExactMass,
			ArrayList<MoNASpectrum> spectraMS1, ArrayList<MoNASpectrum> spectraMS2) {
		this.cmpID = cmpID;
		this.name = name;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
		this.totalExactMass = totalExactMass;
		this.spectraMS1 = new ArrayList<MoNASpectrum>();
		this.spectraMS2 = new ArrayList<MoNASpectrum>();
	}
	
	public void addSpectraMS1(MoNASpectrum spectrumMS1) {
		this.spectraMS1.add(spectrumMS1);
	}
	
	public void addSpectraMS2(MoNASpectrum spectrumMS2) {
		this.spectraMS2.add(spectrumMS2);
	}
}

