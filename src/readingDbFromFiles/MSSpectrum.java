package readingDbFromFiles;

import java.util.ArrayList;

import matrixOperations.Matrix;


public class MSSpectrum {
	
	private String mode,msLevel,precursorType;
	private ArrayList<Number> intensities;
	private ArrayList<Number> mzs;
	private ArrayList<ArrayList<Number>> spectrumList;
	private ArrayList<Number> mzRange = new ArrayList<Number>();
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
		this.mzRange.add(this.mzs.get(0));
		this.mzRange.add(this.mzs.get(this.mzs.size()-1));

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
		diff = diff.divideMatrix(sigma,false).powerMatrix(2).multiplyMatrix(-1).exp();
		ArrayList<ArrayList<Number>> a = new ArrayList<ArrayList<Number>>();
		ArrayList<ArrayList<Number>> b = new ArrayList<ArrayList<Number>>();
		a.add(this.intensities);
		b.add(other.getIntensities());
		Matrix inta = new Matrix(a);
		Matrix intb = new Matrix(b);
		Matrix result = diff.matrixMultiplication(intb.matrixTranspose());
		result = inta.matrixMultiplication(result);
		return result.getThisMatrix().get(0).get(0).doubleValue();
		
	}
	
	
	public double cosineSimilarityScore(MSSpectrum other, double ppm) {
		double innerProduct = gaussianInnerProduct(other, ppm);
		double selfInner = gaussianInnerProduct(this, ppm);
		double otherInner = other.gaussianInnerProduct(other, ppm);
		return innerProduct/Math.sqrt(selfInner*otherInner);
	}
	
	
	public ArrayList<CompoundMatchingResults> findMatchMonaDB(MoNADatabase mona, double scoreThreshold){
		ArrayList<MoNACompounds> candidates = new ArrayList<MoNACompounds>();
		ArrayList<CompoundMatchingResults> candidatesFiltered = new ArrayList<CompoundMatchingResults>();
		for(MoNACompounds cmp: mona.getCompoundList()) {
			if(this.mzRange.get(1).doubleValue() >= cmp.getcmpMzRange().get(0).doubleValue() && this.mzRange.get(0).doubleValue()<=cmp.getcmpMzRange().get(1).doubleValue()){
				{candidates.add(cmp);
				}
			}
		}
		for(int i=0; i<candidates.size(); ++i) {
			System.out.print("\r"+"Matching compounds: " + i+1 + "/" + candidates.size()+"           ");
			CompoundMatchingResults result = findBestMatchedSpectrum(candidates.get(i));
			if(result.getBestMatchedScore().doubleValue()>=scoreThreshold) {
				candidatesFiltered.add(result);
			}
			}
		
		return sortMatchingResults(candidatesFiltered);
	}
	
	
	public CompoundMatchingResults findBestMatchedSpectrum(MoNACompounds cmp) {
		double maxScore = 0.0;
		MoNASpectrum spec = cmp.getAllSpectra().get(0);
		for(MoNASpectrum spectrum: cmp.getAllSpectra()) {
			double tempScore = this.cosineSimilarityScore(spectrum, 20);
			if (maxScore < tempScore) {
				maxScore = tempScore;
				spec = spectrum;
			}
		}
		return new CompoundMatchingResults(cmp, spec, maxScore);
	}
	
	
	public ArrayList<CompoundMatchingResults> sortMatchingResults(ArrayList<CompoundMatchingResults> allMatchedCompounds){
		if(allMatchedCompounds.size()==1) {
			return allMatchedCompounds;
		}
		ArrayList<CompoundMatchingResults> left = new ArrayList(allMatchedCompounds.subList(0, allMatchedCompounds.size()/2));
		ArrayList<CompoundMatchingResults> right = new ArrayList(allMatchedCompounds.subList(allMatchedCompounds.size()/2,allMatchedCompounds.size()));
		left = sortMatchingResults(left);
		right = sortMatchingResults(right);
		ArrayList<CompoundMatchingResults> newResult = new ArrayList<CompoundMatchingResults>();
		int i=0;
		int j=0;
		while(i<left.size() || j<right.size()) {
			if(i>= left.size()) {
				ArrayList<CompoundMatchingResults> newList = new ArrayList(right.subList(j, right.size()));
				newResult.addAll(newList);
				break;
			}
			if(j>= right.size()) {
				ArrayList<CompoundMatchingResults> newList2 = new ArrayList(left.subList(i, left.size()));
				newResult.addAll(newList2);
				break;
			}
			if(left.get(i).getBestMatchedScore().doubleValue()>right.get(j).getBestMatchedScore().doubleValue()) {
				newResult.add(left.get(i));
				i +=1;
			}
			else {
				newResult.add(right.get(j));
				j+=1;
			}
			
		}
		return newResult;
	}
		
	}
