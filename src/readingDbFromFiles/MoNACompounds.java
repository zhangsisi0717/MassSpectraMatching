package readingDbFromFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoNACompounds {
	private int cmpID;
	private String name,inchikey,molecularFormula;
	private Number totalExactMass;
	private ArrayList<MoNASpectrum> allSpectra;
	private ArrayList<Number> cmpMzRange;
	
	public MoNACompounds(int cmpID, String name, String inchikey, String molecularFormula, Number totalExactMass,ArrayList<MoNASpectrum> allSpectra) {
		this.cmpID = cmpID;
		this.name = name;
		this.inchikey = inchikey;
		this.molecularFormula = molecularFormula;
		this.totalExactMass = totalExactMass;
		this.allSpectra = allSpectra;
		this.cmpMzRange = genMzRange();
	}
	

	@Override
	public String toString() {
		return "MoNACompounds [name=" + name + ", molecularFormula=" + molecularFormula + ", totalExactMass="
				+ totalExactMass + "]";
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

	public Number getTotalExactMass() {
		return totalExactMass;
	}


	public ArrayList<MoNASpectrum> getAllSpectra() {
		return allSpectra;
	}
	
	public ArrayList<Number> getcmpMzRange(){
		return cmpMzRange;
	}
	
	
	public  ArrayList<Number> genMzRange() {
		Number minimal = 1000.0;
		Number maximal = 0;
		ArrayList<Number> allMzInvervals = new ArrayList<Number>();
		for(MoNASpectrum mona: this.allSpectra) {
			if(mona.getMzs().get(0).doubleValue()<minimal.doubleValue()) {
				minimal = mona.getMzs().get(0);
			}
			if(mona.getMzs().get(mona.getMzs().size()-1).doubleValue()>maximal.doubleValue()) {
				maximal = mona.getMzs().get(mona.getMzs().size()-1);
			}	
		}
		allMzInvervals.add(minimal);
		allMzInvervals.add(maximal);
		return allMzInvervals;
		
	}
	
}


