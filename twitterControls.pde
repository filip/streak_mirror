void keyReleased() {
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
