package matrixOperations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class Matrix {
	
	private ArrayList<Integer> matrixShape = new ArrayList<Integer>();
	private List<List<Double>> thisMatrix;
	

	public Matrix(List<List<Double>> Matrix) {
		
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

	public List<List<Double>> getThisMatrix() {
		return thisMatrix;
	}

	
	
	public static double vectorsInnerProduct(List<Double> vectorOne, List<Double> vectorTwo) {
		if(vectorOne.size() != vectorTwo.size()) {
			throw new java.lang.Error("length of two vectors must be equal!");
		}
		double innerProduct = 0.0;
		for(int i=0; i<vectorOne.size();++i) {
			innerProduct += (vectorOne.get(i)*vectorTwo.get(i));
		}
		return innerProduct;
	}
	
	public ArrayList<ArrayList<Double>> matrixTranspose(){
		ArrayList<ArrayList<Double>> matrixT = new ArrayList<ArrayList<Double>>();
		for(int c=0; c<this.getMatrixShape().get(1);++c) {
			ArrayList<Double> newRow = new ArrayList<Double>(); 
			for(int r=0; r<this.getMatrixShape().get(0);++r) {
				newRow.add(this.getThisMatrix().get(r).get(c));
			}
			matrixT.add(c, newRow);
		}
		return matrixT;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double[] arrayvec =  {1.0,0.0,2.0};
		Double[] arrayvec2 =  {1.0,0.0,3.0};
		List<Double> listvec = Arrays.asList(arrayvec);
		List<Double> listvec2 = Arrays.asList(arrayvec2);
		double re = Matrix.vectorsInnerProduct(listvec,listvec2);
		System.out.println(re);
	}
	
	
}
