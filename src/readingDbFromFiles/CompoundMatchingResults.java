package readingDbFromFiles;

public class CompoundMatchingResults {
	
	private MoNACompounds compound;
	private MoNASpectrum bestMatchedSpectrum;
	private Number bestMatchedScore;
	
	
	public CompoundMatchingResults(MoNACompounds compound, MoNASpectrum bestMatchedSpectrum, Number bestMatchedScore) {
		this.compound = compound;
		this.bestMatchedSpectrum = bestMatchedSpectrum;
		this.bestMatchedScore = bestMatchedScore;
	}


	public MoNACompounds getCompound() {
		return compound;
	}


	public MoNASpectrum getBestMatchedSpectrum() {
		return bestMatchedSpectrum;
	}


	public Number getBestMatchedScore() {
		return bestMatchedScore;
	}


	@Override
	public String toString() {
		return "CompoundMatchingResults [compound=" + compound + "\n" + "bestMatchedSpectrum=" + bestMatchedSpectrum
				+ "\n" + "bestMatchedScore=" + bestMatchedScore + "]";
	}

	
	
	

	
}

