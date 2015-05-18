# SpringCleanerLog
Tool to help with logging spring cleaner data

About
-----
Allows easy data collection by providing a simple + button for each category of things and the various states those items can be.

Backend is simple comma-separated value files, with each line having one of three formats:
* For things with one partial state (most metal items) `name,success,failure,partial`
* For things with no partial state (dragonhide and such) `name,success,failure`
* For things with multiple partial states (battlestaves, crossbows, etc) `name,success,failure,partialname1,partialvalue1,partialname2,partialvalue2,...`

A file can be passed via command line to open it directly, either as the only argument or following the `-f` flag.

Dependencies
------------
If you're compiling yourself you'll need these (though not if you're just using it):
* [Commons CLI](https://commons.apache.org/proper/commons-cli/)
* [OpenCSV](http://opencsv.sourceforge.net/)
