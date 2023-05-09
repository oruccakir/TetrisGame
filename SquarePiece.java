import java.awt.*;

public class SquarePiece extends Rectangle implements Runnable {

    public Color squareColor = null;

    public boolean isOut = false;
    
    public SquarePiece(int x, int y, int width, int height,Color squareColor){

        super(x,y,width,height);

        this.squareColor = squareColor;

    }

    public boolean equals(Object obj){

        if(this.getClass() != obj.getClass()) return false;

        SquarePiece temp = (SquarePiece) obj;

        return this.x == temp.x && this.y == temp.y && this.width == temp.width && this.height == temp.height && this.squareColor.equals(temp.squareColor);

    }

    public String toString(){

        return "X";

    }



    @Override
    public void run() {

        /*while(isOut == false){

            if(this.getY()>= 1000){
                isOut = true;
                System.out.println("nsmssö");
            } 

            

        }*/

        
       
    }


    
}
