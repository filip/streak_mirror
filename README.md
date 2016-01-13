Documentation
==========

This project is an experimental app to test relationship between social platforms and artworks at home using FRAMED. The app captures video and converts it into linear streaks. Every hour, it posts this image to it's own twitter account @streakmirror.

Code used:

• twitterTools by phil langley [https://github.com/phiLangley/twitterTools](https://frm.fm/a/filip_visnjic/streak_mirror)

• Mirror 2 by Daniel Shiffman (examples available in Processing)

What does it do:

• It uses camera feed to draw pixels

• Positions of those pixels are converted into rectangles that are not cleared but drawn on top of each other

• Rectangle colour is derived depending on the time of the day (clock) – r,g,b = seconds, minutes, hours

• Screencapture is tweeted to the public Twitter account @streakmirror (5 minutes past the hour)

The app runs on FRAMED.
See it here: [https://frm.fm/a/filip_visnjic/streak_mirror](https://frm.fm/a/filip_visnjic/streak_mirror)


Notes:

You need to 'create an app' from your twitter account in order to generate the oAuth codes/ tokens https://apps.twitter.com/

Once you have these, you can enter them in the twitterConfig tab of the processing sketch

