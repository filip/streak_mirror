/////////////////////////////////////////////////////////////////
///SEARCH////////////////////////////////////////////////////////
String searchTerm = "#LFC";
/////////////////////////////////////////////////////////////////
///CHOOSE HOW MANY TWEETS YOU WANT TO SEARCH
int numTweets = 500;
///SET LOCATION BY POINT AND RADIUS
boolean geoSearch = false;
double lat = 51.4;
double lon = 0.13;
double rad = 10;
String radUnit = "km";

/////////////////////////////////////////////////////////////////
///STREAM////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
String[] streamWords = {"#LFC", "#BBC"};
///CHOOSE GEO BOUNDING FOR STREAM
boolean geoStream = false;
double[][] streamLocations = {{51.4, -0.13}, {51.6, -0.11}};


import processing.video.*;
import gab.opencv.*;
import java.awt.*;

// Size of each cell in the grid
int cellSize = 15;
//int cellSize = 5;
// Number of columns and rows in our system
int cols, rows;
// Variable for capture device
Capture video;
OpenCV opencv;

int multiplier;

void setup(){
  //////////////////establish twitter connection
  connectTwitter();
  //////////////////establish twitter connection
  //size(1080, 1920);
  //size(540,960);
  size(640,480);
  // Define colors

  multiplier = 1;
  
  cols = 640 / cellSize;
  rows = 480 / cellSize;
  colorMode(RGB, 255, 255, 255, 100);
  rectMode(CENTER);
  
  //video = new Capture(this, 640/2, 480/2);
  //opencv = new OpenCV(this, 640/2, 480/2);
  video = new Capture(this, 640, 480);
  opencv = new OpenCV(this, 640, 480); 
  opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);  

  video.start();  
  
  background(0);
  
  tweetTable = new Table();
}

void draw(){
  
  if (video.available()) {
    video.read();
    //video.loadPixels();
    
    //translate(-width/2,0);
    //scale(4,4);
    //scale(2);
    
    opencv.loadImage(video);

    //image(video, 0, 0 );
    
    noFill();
    stroke(0, 255, 0);
    strokeWeight(0.2);
    Rectangle[] faces = opencv.detect();
    //println(faces.length);

    for (int i = 0; i < faces.length; i++) {
      //println(faces[i].x + "," + faces[i].y);
      rect(faces[i].x, faces[i].y, faces[i].width, faces[i].height);
    }    


    // Begin loop for columns
    for (int i = 0; i < cols;i++) {
      // Begin loop for rows
      for (int j = 0; j < rows;j++) {

        // Where are we, pixel-wise?
        int x = i * cellSize;
        int y = j * cellSize;
        int loc = (video.width - x - 1) + y*video.width; // Reversing x to mirror the image

        // Each rect is colored white with a size determined by brightness
        color c = video.pixels[loc];
        float sz = (brightness(c) / 255.0) * cellSize;
        //fill(50);
        noFill();
        stroke(second()*3, minute()*3,hour()*3);
        strokeWeight(0.1);
        //noStroke();
        //rect(x + cellSize/2, y + cellSize/2, sz, sz);

        rect(x + cellSize/2, y + cellSize/2, second(), sz);
      }
    }
  }
  
  if (minute() == 0 && second() == 0){
   tweetNewImage(); 
  }
}

void captureEvent(Capture c) {
  c.read();
}
