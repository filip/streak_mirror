Documentation
==========

This project is an experimental app to test relationship between social platforms and artworks at home using FRAMED. The app captures video and converts it into linear streaks. Every hour, it posts this image to it's own twitter account @streakmirror.

Code used:

• twitterTools by phil langley https://github.com/phiLangley/twitterTools

• Mirror 2 by Daniel Shiffman. 

What does it do:

• It uses camera feed to draw pixels

• Positions of those pixels are converted into rectanges that are not cleared but drawn on top of each other

• Rectangle colour is derived depending on the time of the day (clock) – r,g,b = seconds, minutes, hours

• Screencapture is tweeted to the public Twitter account @streakmirror



