package game;

public class Car {

   private double carX, carY, angle = 0; // x,y and angle
   private int width = 72, height = 130; // width and height
   public double wh, ww;
   // constructor
   public Car(double x, double y,int width, int height, double a, double ww, double wh) {
      this.carX = x;
      this.carY = y;
      this.width = width;
      this.height = height;
      this.ww = ww;
      this.wh = wh;
      this.angle = Math.toRadians(a);
   }

   // returning all the necessary value of this class

   public double getX() {
      return carX;
   }

   public double getY() {
      return carY;
   }

   public double getAngle() {
      return angle;
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   // setting the angle
   public void setAngle(int aa) {
      angle = Math.toRadians(aa);
   }

   // move toward the angle
   // //forward
   public void moveForward() {
      carY -= Math.cos(angle) + 0.01;
      carX += Math.sin(angle) - 0.01;
   }
   // //backward
   public void moveBackward() {
      carY += Math.cos(angle) - 0.01;
      carX -= Math.sin(angle) + 0.01;
   }
}