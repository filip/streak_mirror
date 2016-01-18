import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 
import gab.opencv.*; 
import java.awt.*; 

import twitter4j.api.*; 
import twitter4j.*; 
import twitter4j.auth.*; 
import twitter4j.conf.*; 
import twitter4j.json.*; 
import twitter4j.management.*; 
import twitter4j.util.*; 
import twitter4j.examples.account.*; 
import twitter4j.examples.async.*; 
import twitter4j.examples.block.*; 
import twitter4j.examples.directmessage.*; 
import twitter4j.examples.*; 
import twitter4j.examples.favorite.*; 
import twitter4j.examples.friendsandfollowers.*; 
import twitter4j.examples.friendship.*; 
import twitter4j.examples.geo.*; 
import twitter4j.examples.help.*; 
import twitter4j.examples.json.*; 
import twitter4j.examples.list.*; 
import twitter4j.examples.media.*; 
import twitter4j.examples.oauth.*; 
import twitter4j.examples.savedsearches.*; 
import twitter4j.examples.search.*; 
import twitter4j.examples.spamreporting.*; 
import twitter4j.examples.stream.*; 
import twitter4j.examples.suggestedusers.*; 
import twitter4j.examples.timeline.*; 
import twitter4j.examples.trends.*; 
import twitter4j.examples.tweets.*; 
import twitter4j.examples.user.*; 
import twitter4j.media.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class streak_mirror extends PApplet {

/////////////////////////////////////////////////////////////////
///SEARCH////////////////////////////////////////////////////////
String searchTerm = "#LFC";
/////////////////////////////////////////////////////////////////
///CHOOSE HOW MANY TWEETS YOU WANT TO SEARCH
int numTweets = 500;
///SET LOCATION BY POINT AND RADIUS
boolean geoSearch = false;
double lat = 51.4f;
double lon = 0.13f;
double rad = 10;
String radUnit = "km";

/////////////////////////////////////////////////////////////////
///STREAM////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
String[] streamWords = {"#LFC", "#BBC"};
///CHOOSE GEO BOUNDING FOR STREAM
boolean geoStream = false;
double[][] streamLocations = {{51.4f, -0.13f}, {51.6f, -0.11f}};






// Size of each cell in the grid
int cellSize = 15;
//int cellSize = 5;
// Number of columns and rows in our system
int cols, rows;
// Variable for capture device
Capture video;
OpenCV opencv;

int multiplier;

public void setup(){
  //////////////////establish twitter connection
  connectTwitter();
  //////////////////establish twitter connection
  //size(1080, 1920);
  //size(540,960);
  
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

public void draw(){
  
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
    strokeWeight(0.2f);
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
        int c = video.pixels[loc];
        float sz = (brightness(c) / 255.0f) * cellSize;
        //fill(50);
        noFill();
        stroke(second()*3, minute()*3,hour()*3);
        strokeWeight(0.1f);
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

public void captureEvent(Capture c) {
  c.read();
}
// { site, parse token }
String imageService[][] = { 
  { "http://yfrog.com",    "<meta property=\"og:image\" content=\""}, 
  {"http://twitpic.com",   "<img class=\"photo\" id=\"photo-display\" src=\""}, 
  {"http://img.ly",        "<img alt=\"\" id=\"the-image\" src=\"" }, 
  { "http://lockerz.com/", "<img id=\"photo\" src=\""}, 
  {"http://instagr.am/",   "<meta property=\"og:image\" content=\""}
};
////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Config twitter stream setup here ////////////////////////////
//// create twitterStream object
TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
//// create twitterTweet object
Twitter twitterTweet = new TwitterFactory().getInstance();
//// create twitterSearch object
Twitter twitterSearch = new TwitterFactory().getInstance();

//// define access codes
static String OAuthConsumerKey = "";
static String OAuthConsumerSecret = "";
static String AccessToken = "";
static String AccessTokenSecret = "";
///////////////////////////// End twitter config ////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

public void connectTwitter() {
  AccessToken accessToken = loadAccessToken();
  //connect twitterStream
  twitterStream.setOAuthConsumer(OAuthConsumerKey, OAuthConsumerSecret);
  twitterStream.setOAuthAccessToken(accessToken);
  println("stream connected");
  ////connect twitterTweet
  twitterTweet.setOAuthConsumer(OAuthConsumerKey, OAuthConsumerSecret);
  twitterTweet.setOAuthAccessToken(accessToken);
  println("tweet connected");
  twitterSearch.setOAuthConsumer(OAuthConsumerKey, OAuthConsumerSecret);
  twitterSearch.setOAuthAccessToken(accessToken);
  println("search connected");
}

// Loading up the access token
private static AccessToken loadAccessToken() {
  return new AccessToken(AccessToken, AccessTokenSecret);
}

public void keyReleased() {
  ///SEND A TWEET
  if (key == 't' || key == 'T') {
    tweetNewImage();
    
  }
  ///SEARCH TWEETS
  if (key =='s' || key =='S'){
    twitterSearches();
  }
  ///STREAM TWEETS
  if (key =='f' || key =='F'){
    twitterStreams();
  }
}
Table tweetTable;
int maxTweets = 1;

public void twitterSearches(){
  
  Query query = new Query(searchTerm);
  query.setResultType(Query.RECENT);
  query.setCount(maxTweets);
  if (geoSearch ==true){
    query.geoCode(new GeoLocation(lat,lon), rad, radUnit);
  }
  
  tweetTable.addColumn("@");
  tweetTable.addColumn("Latitude");
  tweetTable.addColumn("Longitude");
  tweetTable.addColumn("Time");
  tweetTable.addColumn("Tweet");
  tweetTable.addColumn("Media");
  
  try {

    QueryResult result = twitterSearch.search(query);
    int ind = 0;
    
    ////return tweets from first page of the search   
    for (Status status : result.getTweets()) {
      String Lat = "";
      String Long = " ";
      String ID = "@" + status.getUser().getScreenName();
      
      if (status.getGeoLocation() != null){
        Lat = " " + status.getGeoLocation().getLatitude();
        Long = " " + status.getGeoLocation().getLongitude();
        Lat = trim(Lat); 
        Long = trim(Long); 
      }
      String Time = "Time: " + status.getCreatedAt();
      String Twt = status.getText();
      String hTag = "";


      TableRow newRow = tweetTable.addRow();
      newRow.setString("@",ID); 
      newRow.setString("Latitude", Lat);
      newRow.setString("Longitude",Long);
      newRow.setString("Time",Time);
      newRow.setString("Tweet",Twt);
      
      println("Location: " + status.getGeoLocation());
      println("Time: " + status.getCreatedAt());
      println("@" + status.getUser().getScreenName() + ":" + status.getText());
      
      /////get hashtags from tweets     
      int tagCount=0;
      for (HashtagEntity hashtags : status.getHashtagEntities()) {
        hTag = hashtags.getText();
        newRow.setString("Tags"+tagCount,hTag);
        tagCount++;
        //println(hTag);
      }
      
      /////get images from tweets
      String imgUrl = null;
      String imgPage = null;
      
      for (MediaEntity mediaEntity : status.getMediaEntities()) {
        imgUrl = mediaEntity.getMediaURL();
        byte[] imgBytes = loadBytes(imgUrl);
        saveBytes("data/" + ind + ".jpg", imgBytes);
        newRow.setInt("Media",ind);
      }

    ind = ind+1;

    }
    
    ////return tweets from additinal pages of the search
    for (int i = 1; i<(numTweets/maxTweets); i++){    
      if(result.hasNext()){
  
        query = result.nextQuery();
        result = twitterSearch.search(query);
        for (Status status : result.getTweets()) {
          String Lat = "";
          String Long = " ";
          String ID = "@" + status.getUser().getScreenName();
          if (status.getGeoLocation() != null){
            Lat = " " + status.getGeoLocation().getLatitude();
            Long = " " + status.getGeoLocation().getLongitude();
            Lat = trim(Lat); 
            Long = trim(Long); 
          }
          String Time = "Time: " + status.getCreatedAt();
          String Twt = status.getText();
          String hTag = "";
          
          TableRow newRow = tweetTable.addRow();
          newRow.setString("@",ID); 
          newRow.setString("Latitude", Lat);
          newRow.setString("Longitude",Long);
          newRow.setString("Time",Time);
          newRow.setString("Tweet",Twt);
          
          println("Location: " + status.getGeoLocation());
          println("Time: " + status.getCreatedAt());
          println("@" + status.getUser().getScreenName() + ":" + status.getText());
          
          /////get hashtags from tweets     
          int tagCount=0;
          for (HashtagEntity hashtags : status.getHashtagEntities()) {
            hTag = hashtags.getText();
            newRow.setString("Tags"+tagCount,hTag);
            tagCount++;
            //println(hTag);
          }
           
          /////get images from tweets
          String imgUrl = null;
          String imgPage = null;
          
          for (MediaEntity mediaEntity : status.getMediaEntities()) {
            imgUrl = mediaEntity.getMediaURL(); 
            byte[] imgBytes = loadBytes(imgUrl);
            saveBytes("data/" + ind + ".jpg", imgBytes);
            newRow.setInt("Media",ind);
          }
    
          ind = ind+1;
          
        }
      }
    }
      
  }catch(TwitterException e) {
      println("noTweets");
  }
  
  saveTable(tweetTable, "data/tweetData.csv");
  println("csv exported....");
  background(0,255,0);
 
}



  

  
/////////STREAM TWEETS
String imgUrl = null;
String imgPage = null;

public void twitterStreams(){
  twitterStream.addListener(listener);
  twitterStream.filter(new FilterQuery().track(streamWords));
  if (geoStream == true){
    twitterStream.filter(new FilterQuery().locations(streamLocations));
  }
}

StatusListener listener = new StatusListener() { 
  int count=0;
  public void onStatus(Status status) {
    
      println("Location: " + status.getGeoLocation());
      println("Time: " + status.getCreatedAt());
      println("@" + status.getUser().getScreenName() + ":" + status.getText());
 
  }
  
  public void onStallWarning(StallWarning stallWarning){}
  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
  public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
  public void onScrubGeo(long userId, long upToStatusId) {}
  public void onException(Exception ex) {}
};
/////////SEND TWEETS
public void tweetNewImage(){ 
      String imLoc= savePath("");
      String imName = "streakmirror" + ".png";
      save(imName);
      String tweetText = "#streakmirror";
      String tag = "";
      String message = tweetText + tag;
      
      try {
        
        File imageFile = new File(imLoc + imName);
        StatusUpdate status = new StatusUpdate(message);
        status.setMedia(imageFile);  
        twitterTweet.updateStatus(status);
        println("tweet sent"); 
        
      } catch(TwitterException e) {
        println("Send tweet: " + e + " Status code: " + e.getStatusCode());
      }
}


  public void settings() {  size(640,480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "streak_mirror" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
