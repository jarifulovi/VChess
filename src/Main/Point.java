package Main;

public class Point {
    public int row;
    public int col;

    public Point(){
        row = -1;
        col = -1;
    }
    public Point(int row,int col){
        this.row = row;
        this.col = col;
    }
    public boolean isEqual(int row,int col){
        return this.row == row && this.col == col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point otherPoint = (Point) obj;
        return this.row == otherPoint.row && this.col == otherPoint.col;
    }

    public void setDefault(){
        this.row = -1;
        this.col = -1;
    }
}
