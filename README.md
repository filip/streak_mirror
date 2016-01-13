Documentation
==========

This project is an experimental app to test relationship between social platforms and artworks at home using FRAMED. The app captures video and converts it into linear streaks. Every hour, it posts this image to it's own twitter account @streakmirror.

Created with [Processing](http://processing.org) for [FRAMED](http://frm.fm).

Code used:

• twitterTools by phil langley [https://github.com/phiLangley/twitterTools](https://github.com/phiLangley/twitterTools)

• [Mirror 2 by Daniel Shiffman](https://github.com/processing/processing-video/blob/master/examples/Capture/Mirror2/Mirror2.pde) (examples available in Processing)

What does it do:

• It uses camera feed to draw pixels

• Positions of those pixels are converted into rectangles that are not cleared but drawn on top of each other

• Rectangle colour is derived depending on the time of the day (clock) – r,g,b = seconds, minutes, hours

• Screencapture is tweeted to the public Twitter account [@streakmirror](https://twitter.com/streakmirror) (5 minutes past the hour)

The app runs on FRAMED.
See it here: [https://frm.fm/a/filip_visnjic/streak_mirror](https://frm.fm/a/filip_visnjic/streak_mirror)


Notes:

You need to 'create an app' from your twitter account in order to generate the oAuth codes/ tokens https://apps.twitter.com/

Once you have these, you can enter them in the twitterConfig tab of the processing sketch

If using Processing 3 and above you require video libraries.

When creating an executable for FRAMED, you must compile using Windows machine. Even though Mac versions of Processing can output Windows 32bit executables that work on FRAMED, when working with external libraries (like Video) for some reason they do not. Please try to compile and prepare your executables in Windows to ensure they will be fully functional on FRAMED.

Finally, please NOTE privacy issues. This app captures video feed of the environment you may consider private. If running, please be aware that images are posted online. Even though they are abstracted, and difficult to recognise things in them, they are nonetheless using video as a source.

Issues that need addressing if this used by many:

Currently time when screens are posted is the same for all users. If there are a 100+ users running the app we will see 100+ tweets posted each hour which of course ain’t going to happen. What we need to do on our (FRAMED) front is to provide API access to each user can use proffered twitter account.

What we are also planning to address is adding API access to app specific options on frm.fm for users. This means that each app can have its dedicated settings allowing users to (for example) specify which twitter account, how often, when, colours, etc etc.

To summarise, this is just a proof of concept more than the actual app. It will probably be disconnected or blocked by Twitter at some point. Nonetheless, a fun little experiment to explore the awesome power of connected/networked devices and FRAMED.

![image](https://scontent-lhr3-1.cdninstagram.com/hphotos-xpf1/t51.2885-15/e35/1172995_527770224068478_1851576781_n.jpg
)