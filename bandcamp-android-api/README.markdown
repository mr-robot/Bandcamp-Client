Bandcamp Client: Android Access to Bandcamp.com
=========================================================

Bandcamp.com is a great publishing platform for musicians, and a music delivery 
platform for fans. 
They also have a great API. 

I've played around with the Bandcamp API and I've created a Client library 
which simplifies it's consumption in Android Applications.

Hopefully someone finds it useful (maybe for making a Band App?)

The library is available as a JAR file from the downloads area of this GitHub repo.
The project itself is set up as an Android library project, in case you wish to use the source code
in that fashion.

There are two other folders in this repo:
 - Example - A simple sample application showing the usage of the Android App
 - Tests - The unit tests for testing the API Client

Usage
-----
To use the `BandcampClient`, firstly you need will need an API key from the nice folks 
at bandcamp.com.

Once you have referenced the Bandcamp Client in your project you will need to create
a BandcampService which you will use to retrieve information about Bands, Albums,
Tracks and Discography information.

Create an instance of BandcampService by passing your API key information into the
one of the creation methods in the BandcampServiceFactory.

Once you have an instance of BandcampService, call any of the relevant methods in 
the service to return Bandcamp objects containing the results of your call

### Style

`BandcampClient` have been built using a loosely typed object-mapping style. All of the 
results of calls to the BandcampService result in objects being returned with field values
being stored in a single Map in the object

This is based on the assumption that the API is likely to change in the future (and the fact 
that I don't like boring boilerplate javabeans with a heap of redundant getters).

The most frequently used fields have getters defined.

### Methods

- search - Return a list of artists by name (chain with discography() to get a discography based
on a search - see the Example Client for a demonstration of this)
- getDiscography - Return a list of Discography items based on band id(s) (long)
- getAlbum - Return an Album based on a given album id (long) 
- getTrack - Return a Track based on a given track id (long)
- getBand - Return a Band based on a given band id (long)

### Objects

 - DiscographyItem - Summarised representation of Album or Track for a particular Band
 - Album - Collection of Attributes describing an album (cover art etc...) and an array of Tracks
 - Track - Collection of Attributes describing a track (cover art, streaming url, lyrics etc...)
 - Band - Collection of Attributes describing a Band (url, subdomain, name, id)

Dependencies
------------
This project requires Android 1.5 and up.
It has no external (outside of the Android SDK) dependencies and uses the 
Native Android JSON libraries for deserialisation.

Version
-------
This is version v0.1.0 of this library

Example
----
In the `Example/` sub-project you will find
a sample activity that demonstrates the use of `BandcampClient`.

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Questions
---------
If you have questions regarding the use of this code, please send me an email:
mr _ robot [at| wonderfulrobot dot com

Release Notes
-------------
* v0.1.0: Initial Release

[web]: http://www.wonderfulrobot.com
[adapter]: http://github.com/mr-robot/Bandcamp-Client/tree/master
