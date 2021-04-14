package readingDbFromFiles;

import java.util.ArrayList;

public class MoNASpectrum extends MSSpectrum {

	private String name, SpectrumID,inchikey,molecularFormula;
	private Number totalExactMass;

	public MoNASpectrum(String name, String SpectrumID, String inchikey,String molecularFormula, Number totalExactMass, String mode, String msLevel, String precursorType,ArrayList<Number> intensities, ArrayList<Number> mzs,ArrayList<ArrayList<Number>> spectrumList) {
		super(mode,msLevel,precursorType, intensities, mzs,spectrumList);
		this.name = name;
		this.SpectrumID = SpectrumID;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
	}
	
	

	@Override
	public String toString() {
		return "MoNASpectrum [name=" + name + ", mode=" + this.getMode() + ", spectrumList=" + this.getSpectrumList() + "]";
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
	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((SpectrumID == null) ? 0 : SpectrumID.hashCode());
		result = prime * result + ((totalExactMass == null) ? 0 : totalExactMass.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoNASpectrum other = (MoNASpectrum) obj;
		if (SpectrumID == null) {
			if (other.SpectrumID != null)
				return false;
		} else if (!SpectrumID.equals(other.SpectrumID))
			return false;
		if (totalExactMass == null) {
			if (other.totalExactMass != null)
				return false;
		} else if (!totalExactMass.equals(other.totalExactMass))
			return false;
		return true;
	}


	

}

