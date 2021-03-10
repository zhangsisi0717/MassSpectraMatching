package readingDbFromFiles;

import java.util.ArrayList;

public class MoNASpectrum extends MSSpectrum {

	//	public static void main(String[] args) {
	//		// TODO Auto-generated method stub
	//
	//	}
	private String name, SpectrumID,inchikey,molecularFormula;
	private Number totalExactMass;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((SpectrumID == null) ? 0 : SpectrumID.hashCode());
		result = prime * result + ((inchikey == null) ? 0 : inchikey.hashCode());
		result = prime * result + ((molecularFormula == null) ? 0 : molecularFormula.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (inchikey == null) {
			if (other.inchikey != null)
				return false;
		} else if (!inchikey.equals(other.inchikey))
			return false;
		if (molecularFormula == null) {
			if (other.molecularFormula != null)
				return false;
		} else if (!molecularFormula.equals(other.molecularFormula))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (totalExactMass == null) {
			if (other.totalExactMass != null)
				return false;
		} else if (this.totalExactMass.doubleValue() != other.totalExactMass.doubleValue()) 
			return false;

		if (msLevel == null) {
			if (other.msLevel != null)
				return false;
		} else if (!msLevel.equals(other.msLevel))
			return false;

		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (msLevel == null) {
			if (other.msLevel != null)
				return false;
		} else if (!msLevel.equals(other.msLevel))
			return false;
		if (mzs == null) {
			if (other.mzs != null)
				return false;
		}else if(!mzs.equals(other.mzs))
			return false;
		if (intensities == null) {
			if (other.intensities != null)
				return false;
		} else if (!intensities.equals(other.intensities))
			return false;
		
		if (spectrumList == null) {
			if (other.spectrumList != null)
			return false;
		} else if (!spectrumList.equals(other.spectrumList)) {
			return false;}		
	
		return true;
	}	

	

}

