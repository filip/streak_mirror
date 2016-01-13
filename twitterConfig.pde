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

void connectTwitter() {
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

