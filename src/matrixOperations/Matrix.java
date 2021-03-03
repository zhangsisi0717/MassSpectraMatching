package matrixOperations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Matrix {
	
	private ArrayList<Integer> matrixShape = new ArrayList<Integer>();
	private ArrayList<ArrayList<Number>> thisMatrix;
	private ArrayList<Number> thisVector;
	

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
//		this.thisVector = new ArrayList<Number>();
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
			matrixT.add(newRow);
		}
		return matrixT;
	}
	
	public Matrix matrixMultiplication(Matrix other){
		if(this.matrixShape.get(1) != other.matrixShape.get(0)) {
			throw new java.lang.Error("matrix shape doesnt match, this.matrixshape.get(1) != other.matrixshape.get(0)!");
		}
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int thisr=0;thisr<this.matrixShape.get(0);++thisr) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int otherc=0; otherc<other.matrixShape.get(1);++otherc) {
				ArrayList<Number> otherColVec = new ArrayList<Number>();
				for(int otherr=0; otherr<other.matrixShape.get(0);++otherr) {
					otherColVec.add(other.thisMatrix.get(otherr).get(otherc));
				}
				double thisRe = this.vectorsInnerProduct(this.thisMatrix.get(thisr), otherColVec);
				newRow.add(thisRe);
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);
	}
	
	public Matrix matrixSum(Matrix other) {
		if(this.matrixShape.get(1) != other.matrixShape.get(1) || this.matrixShape.get(0) != other.matrixShape.get(0) ) {
			throw new java.lang.Error("matrix shape doesnt match, number of rows and columns should be equal");
		}
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(other.thisMatrix.get(r).get(c).doubleValue() + this.thisMatrix.get(r).get(c).doubleValue());
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);}
		
	public Matrix matrixElementMultiply(Matrix other) { //element-wise summation of the two matrix
		if(this.matrixShape.get(1) != other.matrixShape.get(1) || this.matrixShape.get(0) != other.matrixShape.get(0) ) {
			throw new java.lang.Error("matrix shape doesnt match, number of rows and columns should be equal");
		}
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(other.thisMatrix.get(r).get(c).doubleValue() * this.thisMatrix.get(r).get(c).doubleValue());
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);	
	}
	
	public Matrix matrixElementSubtract(Matrix other) { //element-wise subtract of the two matrix. this.element-other.element
		if(this.matrixShape.get(1) != other.matrixShape.get(1) || this.matrixShape.get(0) != other.matrixShape.get(0) ) {
			throw new java.lang.Error("matrix shape doesnt match, number of rows and columns should be equal");
		}
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(this.thisMatrix.get(r).get(c).doubleValue()-other.thisMatrix.get(r).get(c).doubleValue());
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);	
	}

	
	public Matrix selfmatrixMultiplication(boolean MMT) { //MMT true: Matrix.dot(Matrix.T); MMT false: Matrix.T.dot(Matrix)
		Matrix selfT = new Matrix(this.matrixTranspose());
		if(MMT == true) {
			return this.matrixMultiplication(selfT);
		}
		else {
			return selfT.matrixMultiplication(this);
		}
	
	}
	public Matrix logMatrix(Number Coefficient, boolean log10) { //boolean log10: True, log10;  false: natural log
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				if(log10 == true) {
					newRow.add(Math.log10(this.thisMatrix.get(r).get(c).doubleValue()));}
				else {
					newRow.add(Math.log(this.thisMatrix.get(r).get(c).doubleValue()));	
				}
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);
		}

	
	public Matrix multiplyMatrix(Number Coefficient) {
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(Coefficient.doubleValue() * (this.thisMatrix.get(r).get(c).doubleValue()));
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);
		
	}
	public Matrix sumMatrix(Number Coefficient) {
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(Coefficient.doubleValue() + (this.thisMatrix.get(r).get(c).doubleValue()));
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);
		
	}
	
	public Matrix divideMatrix(Number Coefficient, boolean cdm) { //cdm:true-> coefficient/matrix, false: matrix/coefficient
		if(cdm==true) {
		ArrayList<ArrayList<Number>> newMatrix = new ArrayList<ArrayList<Number>>();
		for(int r=0;r<this.thisMatrix.size();++r) {
			ArrayList<Number> newRow = new ArrayList<Number>();
			for(int c=0;c<this.thisMatrix.get(0).size();++c) {
				newRow.add(Coefficient.doubleValue() / (this.thisMatrix.get(r).get(c).doubleValue()));
			}
			newMatrix.add(newRow);
		}
		return new Matrix(newMatrix);
		}
		else {
			return multiplyMatrix(1/Coefficient.doubleValue());
		}
		
	}	
	
	public static void main(String[] args) {
		ArrayList<ArrayList<Number>> all = new ArrayList<ArrayList<Number>>();
		Number[] a1 = {1.1,1.2,1.3,1.4};
		Number[] a2 = {2.0,3.0,4.0,5};
		Number[] a3 = {1,1,1,1};
		
		ArrayList<Number> arrA1 = new ArrayList<Number>();
		ArrayList<Number> arrA2 = new ArrayList<Number>();
		ArrayList<Number> arrA3 = new ArrayList<Number>();
		
		Collections.addAll(arrA1, a1);
		Collections.addAll(arrA2, a2);
		Collections.addAll(arrA3, a3);
		
		all.add(arrA1);
		all.add(arrA2);
		all.add(arrA3);
		

	}
	
	
}
