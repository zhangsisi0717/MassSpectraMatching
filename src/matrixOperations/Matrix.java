package matrixOperations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class Matrix {
	
	private ArrayList<Integer> matrixShape = new ArrayList<Integer>();
	private ArrayList<ArrayList<Number>> thisMatrix;
	

	public Matrix(ArrayList<ArrayList<Number>> Matrix) {
		
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

	public ArrayList<ArrayList<Number>> getThisMatrix() {
		return thisMatrix;
	}

	
	
	public static double vectorsInnerProduct(List<Number> vectorOne, List<Number> vectorTwo) {
		if(vectorOne.size() != vectorTwo.size()) {
			throw new java.lang.Error("length of two vectors must be equal!");
		}
		double innerProduct = 0.0;
		for(int i=0; i<vectorOne.size();++i) {
			innerProduct += (vectorOne.get(i).doubleValue()*vectorTwo.get(i).doubleValue());
		}
		return innerProduct;
	}
	
	public ArrayList<ArrayList<Number>> matrixTranspose(){
		ArrayList<ArrayList<Number>> matrixT = new ArrayList<ArrayList<Number>>();
		for(int c=0; c<this.getMatrixShape().get(1);++c) {
			ArrayList<Number> newRow = new ArrayList<Number>(); 
			for(int r=0; r<this.getMatrixShape().get(0);++r) {
				newRow.add(this.getThisMatrix().get(r).get(c));
			}
		}
		return matrixT;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Number[] arrayvec =  {1.0,0,2.0};
//		Number[] arrayvec2 =  {1.0,0.0,3};
//		List<Number> listvec = Arrays.asList(arrayvec);
//		List<Number> listvec2 = Arrays.asList(arrayvec2);
		Number[] a1 = {1.1,1.2,1.3};
		Number[] a2 = {2.0,3.0,4.0};
//		List<Double> arraylis1 = Arrays.asList(a1);
//		List<Double> arraylis2 = Arrays.asList(a2);
		
		ArrayList<Number> list1 = new ArrayList<Number>();
		ArrayList<Number> list2 = new ArrayList<Number>();
		Collections.addAll(list1, a1);
		Collections.addAll(list2, a2);
		list1.addAll(list2);
		
		double re = Matrix.vectorsInnerProduct(list1,list2);
		System.out.println(re);
	}
	
	
}
