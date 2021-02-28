package readingDbFromFiles;

import java.util.ArrayList;

public class MSSpectrum {
	
	private String mode,msLevel,precursorType;
	private ArrayList<Double> intensities;
	private ArrayList<Double> mzs;
	private ArrayList<ArrayList<Double>> spectrumList;
	public MSSpectrum(String mode, String msLevel, String precursorType,ArrayList<Double> intensities, ArrayList<Double> mzs,ArrayList<ArrayList<Double>> spectrumList) {

		this.mode = mode;
		this.msLevel = msLevel;
		this.precursorType = precursorType;
		this.intensities = intensities;
		this.mzs = mzs;
		this.spectrumList = new ArrayList<ArrayList<Double>>();
		if((! this.intensities.isEmpty()) && (! this.mzs.isEmpty())){
			if(this.intensities.size() == this.mzs.size()) {
			 for(int i=0;i <this.intensities.size();++i) {
				 ArrayList<Double> temArray = new ArrayList<Double>();
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
	public String getMode() {
		return mode;
	}
	public String getMsLevel() {
		return msLevel;
	}
	public String getPrecursorType() {
		return precursorType;
	}
	public ArrayList<Double> getIntensities() {
		return intensities;
	}
	public ArrayList<Double> getMzs() {
		return mzs;
	}
	public ArrayList<ArrayList<Double>> getSpectrumList() {
		return spectrumList;
	}
	
	

}