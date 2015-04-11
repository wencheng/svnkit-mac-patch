# THIS ISSUE HAS BEEN SOLVED #
SEE HERE ->
http://subversion.tigris.org/issues/show_bug.cgi?id=2464


PROJECT DEPRECATED




---



---



---


## About composed/decomposed filename ##
[The issue discussed on SVNKit's mailinglist](http://www.nabble.com/Composed-and-decomposed-letters-in-cross-platform-tf2792298.html)

## Solution ##
  1. Commit file with a composed filename.
  1. Use composed name in working copy (for the files with decomposed filenames that are already exists).
  1. Treat all other files as usual.
  1. Based on SVNKit 1.1.0.

## Release note ##
**STILL UNDER TESTING**

2007/05/30 version 0.3
  * Import are working.
> Now we can use _Export_ and _Import_ to merge our old repository.

2007/05/29 version 0.2
  * Copy, Move(delete+copy) are working.
> Now we can use _copy(WC-WC)_ command to merge our old repository.(copy(WC-URL) and copy(URL-URL) will remain old filename.)
  * Bugfixes

2007/05/28 version 0.1
  * Checkout
  * Update
  * Add
  * Delete
  * Commit
  * Status
  * List
  * Mkdir
are working with SmartSVN 2.1.3, ICU4J 3.6, JDK 1.5, Mac OS X 10.4.8.


## How to use the patched SVNKit with SmartSVN ##
  1. Download [icu4j-3\_6.jar](http://icu-project.org/download/) and copy it into _SmartSVN.app/Contents/Resources/Java/_.
  1. Download svnkit-mac-patched.jar and replace the original _SmartSVN.app/Contents/Resources/Java/svnkit.jar_ with it.
  1. Add 

&lt;string&gt;

$JAVAROOT/icu4j-3\_6.jar

&lt;/string&gt;

_into ClassPath section in SmartSVN.app/Contents/Info.plist like below.
```
			<Key>ClassPath</key>
			<array>
				<string>$JAVAROOT/smartsvn.jar</string>
				<string>$JAVAROOT/svnkit.jar</string>
				<string>$JAVAROOT/icu4j-3_6.jar</string>
				<string>$JAVAROOT/tools.jar</string>
				<string>$JAVAROOT/help.jar</string>
			</array>
```_
