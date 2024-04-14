package sandlab;
import java.awt.*;
import java.util.*;
public class SandLab
{
public static void main(String[] args)
{
SandLab lab = new SandLab(120, 80);
lab.run();
}
//add constants for particle types here
public static final int EMPTY = 0;
public static final int METAL = 1;
public static final int SAND = 2;
public static final int WATER = 3;
public static final int FIRE = 4;
//do not add any more fields
private int[][] grid;
private SandDisplay display;
public SandLab(int numRows, int numCols)
{
String[] names;
names = new String[5];
names[EMPTY] = "Empty";
names[METAL] = "Metal";
names[SAND]="Sand";
names[WATER]="Water";
names[FIRE]="Fire";
display = new SandDisplay("Falling Sand", numRows, numCols, names);
grid = new int[numRows][numCols];
}
//called when the user clicks on a location using the given tool
private void locationClicked(int row, int col, int tool)
{
if(tool==EMPTY){
grid[row][col]=EMPTY;
}else if(tool==METAL){
grid[row][col]=METAL;
}
else if(tool==SAND){
grid[row][col]=SAND;
}
else if(tool==WATER){
grid[row][col]=WATER;
}
else if(tool==FIRE){
grid[row][col]=FIRE;
}
}
//copies each element of grid into the display
public void updateDisplay()
{
for (int row = 0; row < grid.length; row++) {
for (int col = 0; col < grid[row].length; col++) {
if (grid[row][col] == EMPTY) {
display.setColor(row, col, Color.BLACK);
} else if (grid[row][col] == METAL) {
display.setColor(row, col, Color.GRAY);
} else if (grid[row][col] == SAND) {
display.setColor(row, col, Color.YELLOW);
} else if (grid[row][col] == WATER) {
display.setColor(row, col, Color.BLUE);
}else if (grid[row][col] == FIRE) {
display.setColor(row, col, Color.RED);
}
}
}
}
//called repeatedly.
//causes one random particle to maybe do something.
public void step()
{
int i=(int)(Math.random()*grid.length-1);
int j=(int)(Math.random()*grid[0].length);
if(grid[i][j]==SAND){
if(grid[i+1][j]==EMPTY){
grid[i][j]=EMPTY;
grid[i+1][j]=SAND;
}
else if(grid[i+1][j]==WATER){
grid[i][j]=WATER;
grid[i+1][j]=SAND;
}
}
else if (grid[i][j] == WATER) {
int randomDirection = (int) (Math.random() * 3);
int[] x = {1,0};
int[] y = {1,-1,0};
int newX = i + x[(int) (Math.random() * 2)];
int newY = j + y[randomDirection];
if (newX >= 0 && newX < grid.length && newY >= 0 && newY <
grid[0].length&&grid[i+1][j]!=METAL) {
if (grid[newX][newY] == EMPTY) {
grid[newX][newY] = WATER;
grid[i][j] = EMPTY;
}
}
}
}
//do not modify
public void run()
{
while (true)
{
for (int i = 0; i < display.getSpeed(); i++)
step();
updateDisplay();
display.repaint();
display.pause(1); //wait for redrawing and for mouse
int[] mouseLoc = display.getMouseLocation();
if (mouseLoc != null) //test if mouse clicked
locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
}
}
}
