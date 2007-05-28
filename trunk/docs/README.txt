File dir = new File(dir);
File[] files = dir.listFiles();
files.getName(); // this always returns a decomopsed name in Mac


the filename in .svn/entries is saved as it was committed.
ex: composed by a Win client, decomposed by a Mac client


1. work with the file added from Mac ->
	PERFECT in Mac ofcos, filename on the repository is decomposed, NOT compabible with Win
	(should be changed to "treat all filename as composed when adding and commiting")

2. work with the file added from Win ->
	LOCAL STATUS OK, patched SVNStatusEditor.getChildrenFiles(File)

	       	// wencheng DEBUG
        	System.out.println( entryName );
        	Iterator iter = childrenFiles.keySet().iterator();
        	while ( iter.hasNext() ) {
        		try {
        			Object o = iter.next();
					System.out.println( ((String)o).getBytes("UTF-8") );
				} catch (UnsupportedEncodingException e) {
				}
        	}
        	// wencheng DEBUG END
	

3. work with the file added from Mac ->
	1) LOCAL STATUS OK if filename was writen composed into .svn/entries when checkouting
	   SVNUpdateEditor.createFileInfo()
	2) COMMIT modified OK
	   SVNUpdateEditor.createFileInfo(): remain decomposed URL

4. add new file from Mac
	1) add entry with a composed file name
	   SVNWCManager.add();
	2) little tricky for SmartSVN
	   SVNAdminArea.getEntry();

5. list
    SVNLogClient.list() --> OK
    
        File f = new File("/svnkit.log");
        try {
        	FileWriter w = new FileWriter(f,true);
        	new Exception( "log" ).printStackTrace(new PrintWriter(w));
        	w.flush();
        	w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



"Unicode Normalization Forms"
http://www.unicode.org/unicode/reports/tr15/tr15-23.html


ICU

Download v3.6
http://icu.sourceforge.net/download/

License Information for v3.6
http://dev.icu-project.org/cgi-bin/viewcvs.cgi/icu4j/readme.html?revision=release-3-6#license

Explaination of ICU's Normalizer
http://icu.sourceforge.net/userguide/normalization.html

Javadoc of class "com.ibm.icu.text.Normalizer"
http://icu.sourceforge.net/apiref/icu4j/com/ibm/icu/text/Normalizer.html


SVNClient
SVNClientImpl
core.wc.SVNUpdater
core.io.SVNRepository
org.tmatesoft.svn.core.wc.SVNUpdateClient

org.tmatesoft.svn.core.internal.wc.admin;
fetchEntries()
L493
readEntry()
L561
writeEntries()
L933
writeEntry()
L977

L567
        String name = parseString(line);
        name = name != null ? name : getThisDirName();

SVNStatusClient.getChildrenFiles();



http://www.unicode.org/charts/PDF/U3040.pdf
http://ja.wikipedia.org/wiki/Unicode����_3000-3FFF

http://www.alias-i.com/blog/?p=28

J2SE 1.4 to Unicode 3.0
J2SE 5.0 to Unicode 4.0

svn

subversion/include/svn_utf.h

http://developer.apple.com/documentation/Java/Conceptual/Java14Development/04-JavaUIToolkits/JavaUIToolkits.html

Re: UTF-8 conversion error
http://svn.haxx.se/users/archive-2006-04/0841.shtml

subversion Issue 2464
http://subversion.tigris.org/issues/show_bug.cgi?id=2464

svnkit Knowledge Base
http://svnkit.com/kb/index.html

Unicode Normalization Forms
http://www.unicode.org/unicode/reports/tr15/tr15-23.html
http://unicode.org/reports/tr15/Normalizer.java

http://www.nabble.com/JavaSVN---User-f10899.html
