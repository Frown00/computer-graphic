public class Matrix {
    double[][] v;

    Matrix(double[][] v){
        this.v = v;
    }
    /** Creates an Matrix that will used to translate the picture to the coordinates
     * clicked on the screen.
     **/
    public static Matrix translate(double dx, double dy){
        double dM[][] = {{1, 0, dx},
                         {0, 1, dy},
                         {0, 0 , 1}};
        return new Matrix(dM);
    }

    public static Matrix translateRotate(double e, double f){
        double dM[][] = {{1, 0, 0},
                         {0, 1, 0},
                         {e, f , 1}};
        return new Matrix(dM);
    }

    public static Matrix transpose(double[][] dM) {
        Matrix result = new Matrix(new double[dM[0].length][dM.length]);
        for(int x = 0; x < dM.length; x++) {
            for(int y = 0; y < dM[x].length; y++) {
                result.v[y][x] = dM[x][y];
            }
        }
        return result;
    }

    public static Matrix rotate(double a){
        final double rad = Math.PI * a / 180.0;
        final double dM[][] = { { Math.cos(rad), -Math.sin(rad), 0 },
                                { Math.sin(rad), Math.cos(rad), 0 },
                                { 0, 0, 1 } };
        return new Matrix(dM);
    }
    /** Creates an Matrix that will used to scale the picture by the given factor. **/
    public static Matrix scale(double fx, double fy){
        double dM[][] = {{fx, 0, 0},
                         {0, fy, 0},
                         {0, 0, 1}};
        return new Matrix(dM);
    }
//    public static Matrix shearX(double sX){
//        double dM[][] = {{1, 0, 0}, {-sX, 1, 0}, {0, 0, 1}};
//        return new Matrix(dM);
//    }
//    public static Matrix shearY(double sY){
//        double dM[][] = {{1, -sY, 0}, {0, 1, 0}, {0, 0, 1}};
//        return new Matrix(dM);
//    }
    public static Matrix multiply(Matrix x, Matrix y){
        int m1ColLength = x.v[0].length; // m1 columns length
        int m2RowLength = y.v.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = x.v.length;    // m result rows length
        int mRColLength = y.v[0].length; // m result columns length

        double[][] mResult = new double[mRRowLength][mRColLength];

        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += x.v[i][k] * y.v[k][j];
                }
            }
        }

        Matrix matrix = new Matrix(mResult);

        return matrix;
    }



    public void display() {
        for(int i = 0; i < v.length; i++) {
            System.out.print("[");
            for(int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println("]");

        }
    }
}