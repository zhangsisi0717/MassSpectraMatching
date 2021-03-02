package readingDbFromFiles;

import java.util.ArrayList;

public class MoNASpectrum extends MSSpectrum {

	//	public static void main(String[] args) {
	//		// TODO Auto-generated method stub
	//
	//	}
	private String name, SpectrumID,inchikey,molecularFormula;
	Number totalExactMass;

	public MoNASpectrum(String name, String SpectrumID, String inchikey,String molecularFormula, Number totalExactMass, String mode, String msLevel, String precursorType,ArrayList<Number> intensities, ArrayList<Number> mzs,ArrayList<ArrayList<Number>> spectrumList) {
		super(mode,msLevel,precursorType, intensities, mzs,spectrumList);
		this.name = name;
		this.SpectrumID = SpectrumID;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
	}

	public String getName() {
		return name;
	}

	public String getSpectrumID() {
		return SpectrumID;
	}

	public String getInchikey() {
		return inchikey;
	}

	public String getMolecularFormula() {
		return molecularFormula;
	}

	public Number getTotalExactMass() {
		return totalExactMass;
	}

	
}
