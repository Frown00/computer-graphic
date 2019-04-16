public class Vector {
    private int[] v;
    Vector(int x, int y){
        v = new int[3]; //We'll always have a 3 Vector...
        v[0] = x;
        v[1] = y;
        v[2] = 1;
        //     System.out.println("NEW VECTOR " + v[0] + " "+ v[1]);
    }
    Vector(){
        v = new int[3];
        v[0] = 0;
        v[1] = 0;
        v[2] = 1;
    }
    public int getVectorX(){
        return v[0];
    }
    public int getVectorY(){
        return v[1];
    }

    public int getVectorZ(){
        return v[2];
    }
    public void setVector(int i, double d){
        v[i] = (int)d;
    }
    public int getVector(int i){
        return v[i];
    }
    public void setVectorX(int i){
        v[0] = i;
    }
    public void setVectorY(int i){
        v[1] = i;
    }
}