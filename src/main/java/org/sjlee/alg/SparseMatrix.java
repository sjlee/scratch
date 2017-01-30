package org.sjlee.alg;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class SparseMatrix {
  protected final int dim;

  protected SparseMatrix(int dim) {
    this.dim = dim;
  }

  public abstract SparseMatrix setValues(Value... values);

  public abstract double[] multiply(double[] vector);


  public static void main(String[] args) throws Throwable {
    if (args.length < 1) {
      System.err.println("need the implementation name");
      System.exit(-1);
    }
    String clsName = args[0];
    Class<?> cls = Class.forName(clsName);
    if (!SparseMatrix.class.isAssignableFrom(cls)) {
      System.err.println(clsName + " is not a SparseMatrix type");
      System.exit(-1);
    }
    @SuppressWarnings("unchecked")
    Class<? extends SparseMatrix> typeCls = (Class<? extends SparseMatrix>)cls;
    Constructor<? extends SparseMatrix> ctr = typeCls.getConstructor(int.class);
    SparseMatrix sm = ctr.newInstance(4);

    // could use a builder if i want to get fancy
    sm.setValues(new Value(0, 0, 2.0), new Value(0, 1, -1.0)).
       setValues(new Value(1, 0, -1.0), new Value(1, 1, 2.0), new Value(1, 2, -1.0)).
       setValues(new Value(2, 1, -1.0), new Value(2, 2, 2.0), new Value(2, 3, -1.0)).
       setValues(new Value(3, 2, -1.0), new Value(3, 3, 2.0));
    double[] vector = {1.0, 0.0, -1.0, 0};
    long begin = System.nanoTime();
    double[] answer = sm.multiply(vector);
    long duration = System.nanoTime() - begin;
    System.out.println("duration: " + duration);
    for (int i = 0; i < answer.length; i++) {
      System.out.println(answer[i]);
    }
  }
}

class Value {
  final Coordinate coord;
  final double val;

  Value(int row, int col, double v) {
    this(new Coordinate(row, col), v);
  }

  Value(Coordinate c, double v) {
    this.coord = c;
    this.val = v;
  }
}

class Coordinate {
  final int row;
  final int col;

  Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate that = (Coordinate)o;
    return this.row == that.row && this.col == that.col;
  }

  @Override
  public int hashCode() {
    return row*31 + col;
  }
}

class SparseMatrixDOK extends SparseMatrix {
  private final Map<Coordinate,Double> matrix = new HashMap<>();

  public SparseMatrixDOK(int dim) {
    super(dim);
  }

  public SparseMatrix setValues(Value... values) {
    for (Value v : values) {
      Coordinate c = v.coord;
      if (c.row < 0 || c.row > dim-1 || c.col < 0 || c.col > dim-1) {
        throw new IllegalArgumentException("invalid coordinate");
      }
      matrix.put(c, v.val);
    }
    return this;
  }

  public double[] multiply(double[] vector) {
    if (vector.length != dim) {
      throw new IllegalArgumentException("vector length " + vector.length +
          " does not match the matrix length " + dim);
    }
    double[] result = new double[vector.length];
    for (Map.Entry<Coordinate, Double> e : matrix.entrySet()) {
      Coordinate c = e.getKey();
      int row = c.row;
      int col = c.col;
      double val = e.getValue();
      result[row] += val * vector[col];
    }
    return result;
  }
}

class SparseMatrixLOL extends SparseMatrix {
  private static class ColumnValue {
    final int col;
    final double val;

    ColumnValue(int col, double val) {
      this.col = col;
      this.val = val;
    }
  }

  // list of lists storage
  private final Object[] matrix;

  public SparseMatrixLOL(int dim) {
    super(dim);
    // no generic array creation
    matrix = new Object[dim];
    for (int i = 0; i < dim; i++) {
      matrix[i] = new LinkedList<ColumnValue>();
    }
  }

  public SparseMatrix setValues(Value... values) {
    for (Value v : values) {
      Coordinate c = v.coord;
      List<ColumnValue> row = getRow(c.row);
      row.add(new ColumnValue(c.col, v.val));
    }
    return this;
  }

  @SuppressWarnings("unchecked")
  private List<ColumnValue> getRow(int i) {
    return (List<ColumnValue>)matrix[i];
  }

  public double[] multiply(double[] vector) {
    if (vector.length != dim) {
      throw new IllegalArgumentException("vector length " + vector.length +
          " does not match the matrix length " + dim);
    }
    double[] result = new double[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      // multiply the i-th row of the matrix with the vector using the indices in the value
      List<ColumnValue> row = getRow(i);
      for (ColumnValue v : row) {
        result[i] += v.val * vector[v.col];
      }
    }
    return result;
  }
}