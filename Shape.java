import java.awt.*;

public abstract class Shape{

    public int startX, startY = 0, angle = 0;

    public Color shapeColor = null;

    public String type = null;

    public SquarePiece [] squares = null;

    public double speed = 0;

    public Shape(){

    }

    public Shape(int startX, int startY,Color shapeColor){

        squares = new SquarePiece[4];

        this.shapeColor = shapeColor;

        this.startX = startX;

        this.startY = startY;

    }

    public boolean isIntersect (){

        for(int i=0; i<squares.length; i++){

            for(int k=0; k<TetrisGame.squareList.size(); k++){

                if(squares[i].intersects(TetrisGame.squareList.get(k))) return true;

            }

        }

        return false;

    }

    public Shape makeShapeMini(){

        Shape miniShape = null;

        if(this instanceof LineRectangle){

            miniShape = new LineRectangle(100, 400, shapeColor);

        }
        else if(this instanceof Square){
            
            miniShape = new Square(70, 400, shapeColor);

        }
        else if(this instanceof LeftL){

            miniShape = new LeftL(70, 400, shapeColor);

        }
        else if(this instanceof RightL){

            miniShape = new RightL(110, 400, shapeColor);

        }
        else if(this instanceof RightZ){

            miniShape = new RightZ(50, 400, shapeColor);

        }
        else if(this instanceof LeftZ){

            miniShape = new LeftZ(90, 400, shapeColor);

        }
        else if(this instanceof NormalT){

            miniShape = new NormalT(50, 400, shapeColor);

       }

       

       return miniShape;

    }

    public void moveUp(){
            
        for(int i=0; i<squares.length; i++){
            squares[i].y -=50;
        }

        startY-=50;

    }

    public void moveDown(){
        
        for(int i=0; i<squares.length; i++){
            squares[i].y +=50; 
        }

        startY +=50;

    }

    public void moveLeft(){
        
        for(int i=0; i<squares.length; i++){

            squares[i].x -=50;
 
        }

        startX -=50;

    }

    public void moveRight(){

        for(int i=0; i<squares.length; i++){

            squares[i].x +=50;

        }

        startX +=50;

    }


    public abstract void turnClockWise90();

    public abstract void turnAntiClockWise90();

    public void writeStartingPoints(){

        for(int i=0; i<squares.length; i++)
           System.out.print(squares[i].y+" ");

    }

    public boolean isOutOfBorder(){

        for(int i=0; i<squares.length; i++){

            if(squares[i].isOut == true) return true;

        }

        return false;

    }

    public void startThreads(){

        Thread temp = new Thread();

        for(int i=0; i<squares.length; i++){

            temp = new Thread(squares[i]);

            temp.start();

        }

    }






    
}

class LineRectangle extends Shape{

    public LineRectangle(int startX, int startY,Color shapeColor) {

        super(startX,startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX, startY+150, 50, 50, shapeColor);

    }

    public void turnClockWise90(){

        if(angle == 0){

            startX -=100;
            startY +=100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+150, startY, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

            startX +=100;
            startY +=100;

           squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX, startY-150, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            startX +=100;
            startY -= 100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-150, startY, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            startX -=100;

            startY -=100;

           squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX, startY+150, 50, 50, shapeColor);

           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }


}

class Square extends Shape{

    public Square (int startX, int startY,Color shapeColor){

        super(startX,startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX+50, startY+50, 50, 50, shapeColor);

    }

    public void turnClockWise90(){

    }

    public void turnAntiClockWise90(){
        
    }

}

class LeftL extends Shape{

    public LeftL(int startX, int startY,Color shapeColor){

        super(startX,startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX+50, startY+100, 50, 50, shapeColor);
        

    }

    public void turnClockWise90(){

        if(angle == 0){

            startX -=100;
            startY +=100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+100, startY-50, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

            startX +=100;
            startY +=100;

           squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX-50, startY-100, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            startX +=100;
            startY -= 100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-100, startY+50, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            startX -=100;

            startY -=100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+50, startY+100, 50, 50, shapeColor);

           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }


}

class RightL extends Shape{

    public RightL(int startX, int startY,Color shapeColor){

        super(startX,startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX-50, startY+100, 50, 50, shapeColor);

    }

    public void turnClockWise90(){

        if(angle == 0){

            startX -=100;
            startY +=100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+100, startY+50, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

            startX +=100;
            startY +=100;

           squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX+50, startY-100, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            startX +=100;
            startY -= 100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-100, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-100, startY-50, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            startX -=100;

            startY -=100;

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX, startY+100, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+100, 50, 50, shapeColor);

           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }

}

class LeftZ extends Shape{

    public LeftZ (int startX, int startY,Color shapeColor){

        super(startX, startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

    }


    public void turnClockWise90(){

        if(angle == 0){


            squares[0] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+50, startY-50, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

           squares[0] = new SquarePiece(startX-100, startY-50, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX-50, startY-50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX-50, startY-100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            squares[0] = new SquarePiece(startX-100, startY-50, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-100, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }

}

class RightZ extends Shape{

    public RightZ(int startX, int startY,Color shapeColor){

        super(startX,startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX+50, startY+50, 50, 50, shapeColor);
        squares[3] = new SquarePiece(startX+100, startY+50, 50, 50, shapeColor);

    }


    public void turnClockWise90(){

        if(angle == 0){


            squares[0] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+50, startY-50, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

           squares[0] = new SquarePiece(startX-100, startY-50, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX-50, startY-50, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX-50, startY-100, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            squares[0] = new SquarePiece(startX-100, startY-50, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-100, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX, startY+50, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }


}

class NormalT extends Shape{

    public NormalT(int startX, int startY,Color shapeColor){

        super(startX, startY,shapeColor);

        squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
        squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
        squares[2] = new SquarePiece(startX+100, startY, 50,50, shapeColor);
        squares[3] = new SquarePiece(startX+50, startY+50, 50, 50, shapeColor);


    }


    public void turnClockWise90(){

        if(angle == 0){

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX-50, startY-50, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX-50, startY+50, 50, 50, shapeColor);

            angle = 90;

        }
        else if (angle == 90){

           squares[0] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
           squares[1] = new SquarePiece(startX-50, startY, 50, 50, shapeColor);
           squares[2] = new SquarePiece(startX, startY, 50, 50, shapeColor);
           squares[3] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);

           angle = 180;

        }

        else if(angle == 180){

            squares[0] = new SquarePiece(startX-50, startY-50, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX, startY-50, 50, 50, shapeColor);
            squares[3] = new SquarePiece(startX, startY-100, 50, 50, shapeColor);

            angle = 360;

        }

        else{

            squares[0] = new SquarePiece(startX, startY, 50, 50, shapeColor);
            squares[1] = new SquarePiece(startX+50, startY, 50, 50, shapeColor);
            squares[2] = new SquarePiece(startX+100, startY, 50,50, shapeColor);
            squares[3] = new SquarePiece(startX+50, startY+50, 50, 50, shapeColor);
 
           angle = 0;

        }




    }

    public void turnAntiClockWise90(){

            turnClockWise90();
            turnClockWise90();
            turnClockWise90();

    }


}
