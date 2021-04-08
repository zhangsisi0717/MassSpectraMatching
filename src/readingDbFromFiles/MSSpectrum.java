package readingDbFromFiles;

import java.util.ArrayList;

import matrixOperations.Matrix;


public class MSSpectrum {
	
	protected String mode,msLevel,precursorType;
	protected ArrayList<Number> intensities;
	protected ArrayList<Number> mzs;
	protected ArrayList<ArrayList<Number>> spectrumList;
	public MSSpectrum(String mode, String msLevel, String precursorType,ArrayList<Number> intensities, ArrayList<Number> mzs,ArrayList<ArrayList<Number>> spectrumList) {
		this.mode = mode;
		this.msLevel = msLevel;
		this.precursorType = precursorType;
		this.intensities = intensities;
		this.mzs = mzs;
		this.spectrumList = new ArrayList<ArrayList<Number>>();
		if((! this.intensities.isEmpty()) && (! this.mzs.isEmpty())){
			if(this.intensities.size() == this.mzs.size()) {
			 for(int i=0;i <this.intensities.size();++i) {
				 ArrayList<Number> temArray = new ArrayList<Number>();
				 temArray.add(this.mzs.get(i));
				 temArray.add(this.intensities.get(i));
				 this.spectrumList.add(temArray);
				 
			 }
			}
			else {
				throw new java.lang.Error("size of mzs and intensities should be equal");
			}
			
		}
		else {
			throw new java.lang.Error("mzs and intensities should not be empty!");
		}

	}
	
	
	@Override
	public String toString() {
		return "MSSpectrum [mode=" + mode + ", msLevel=" + msLevel + ", spectrumList=" + spectrumList + "]";
	}


	public String getMode() {
		return mode;
	}
	public String getMsLevel() {
		return msLevel;
	}
	public String getPrecursorType() {
		return precursorType;
	}
	public ArrayList<Number> getIntensities() {
		return intensities;
	}
	public ArrayList<Number> getMzs() {
		return mzs;
	}
	public ArrayList<ArrayList<Number>> getSpectrumList() {
		return spectrumList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intensities == null) ? 0 : intensities.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((msLevel == null) ? 0 : msLevel.hashCode());
		result = prime * result + ((mzs == null) ? 0 : mzs.hashCode());
		result = prime * result + ((precursorType == null) ? 0 : precursorType.hashCode());
		result = prime * result + ((spectrumList == null) ? 0 : spectrumList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MSSpectrum other = (MSSpectrum) obj;
		if (intensities == null) {
			if (other.intensities != null)
				return false;
		} else if (!intensities.equals(other.intensities))
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
		} else if (!mzs.equals(other.mzs))
			return false;
		if (precursorType == null) {
			if (other.precursorType != null)
				return false;
		} else if (!precursorType.equals(other.precursorType))
			return false;
		if (spectrumList == null) {
			if (other.spectrumList != null)
				return false;
		} else if (!spectrumList.equals(other.spectrumList))
			return false;
		return true;
	}
	
	public double gaussianInnerProduct(MSSpectrum other, double ppm) {
		ArrayList<Number> mza_log = Matrix.vectorlog10(this.mzs);
		ArrayList<Number> mzb_log = Matrix.vectorlog10(other.getMzs());
		double sigma  = 2*Math.log10(1+ ppm * Math.pow(1, -6));
		Matrix diff = Matrix.VecorSubtractOuter(mza_log, mzb_log);
		diff = diff.divideMatrix(sigma,true).powerMatrix(2).multiplyMatrix(-1).logE();
		ArrayList<ArrayList<Number>> a = new ArrayList<ArrayList<Number>>();
		ArrayList<ArrayList<Number>> b = new ArrayList<ArrayList<Number>>();
		a.add(this.intensities);
		b.add(other.getIntensities());
		Matrix inta = new Matrix(a);
		Matrix intb = new Matrix(b);
		Matrix result = diff.matrixMultiplication(intb);
		result = inta.matrixMultiplication(result);
		return result.getThisMatrix().get(0).get(0).doubleValue();
		
	}
	
	public double cosineSimilarityScore(MSSpectrum other, double ppm) {
		double innerProduct = gaussianInnerProduct(other, ppm);
		double selfInner = gaussianInnerProduct(this, ppm);
		double otherInner = other.gaussianInnerProduct(other, ppm);
		return innerProduct/Math.sqrt(selfInner*otherInner);
	}
	
	

}
