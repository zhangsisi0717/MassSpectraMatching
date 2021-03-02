package matrixOperations;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Matrix {
	
	private ArrayList<Integer> matrixShape = new ArrayList<Integer>();
	private ArrayList<ArrayList<Double>> thisMatrix;
	

	public Matrix(ArrayList<ArrayList<Double>> Matrix) {
		
//		this.thisMatrix = Matrix;
		for(int r=0;r<Matrix.size()-1;++r) {
//			boolean colLenEqual = true;
			int curColSize = Matrix.get(r).size();
			int nexColSize = Matrix.get(r+1).size();
			if(curColSize !=nexColSize) {
//				colLenEqual = false;
				throw new java.lang.Error("column length should be equal for each row");
			}
		}
		this.thisMatrix = Matrix;
		this.matrixShape.add(this.thisMatrix.size());
		this.matrixShape.add(this.thisMatrix.get(0).size());
	}

	

	public ArrayList<Integer> getMatrixShape() {
		return matrixShape;
	}



	public ArrayList<ArrayList<Double>> getThisMatrix() {
		return thisMatrix;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	
}
