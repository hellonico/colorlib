import colorLib.calculation.*;
import colorLib.*;
import colorLib.webServices.*;

Palette p;
PFont font;

void setup()
{
    size(340, 300);
    p = new Palette(this);
    
    font = createFont("Verdana", 16);
    textFont(font, 16);
    textLeading(16);
    
    generate();
    
}

void draw()
{
    background(0);
    
    pushMatrix();
    translate(20, 20);
    p.drawSwatches();
    popMatrix();
    
    pushMatrix();
    translate(20, 100);
    fill(255);
    text("Darkest: ", 0, 16);
    fill( p.getDarkest() );
    rect(100, 0, 200, 20);
    popMatrix();

    pushMatrix();
    translate(20, 140);
    fill(255);
    text("Lightest: ", 0, 16);
    fill( p.getLightest() );
    rect(100, 0, 200, 20);
    popMatrix();

    pushMatrix();
    translate(20, 180);
    fill(255);
    text("Average: ", 0, 16);
    fill( p.getAverage() );
    rect(100, 0, 200, 20);
    popMatrix();

    pushMatrix();
    translate(20, 220);
    fill(255);
    text("Press 'r' to generate a new palette. ", 0, 16);
    popMatrix();

}

void generate()
{
    p = new Palette(this);
    
    for (int i = 0; i < 5; i++) {
        p.addColor( color(random(255), random(255), random(255) ) );
    }
}

void keyPressed()
{
    if (key == 'r') {
        generate();
    }
}
