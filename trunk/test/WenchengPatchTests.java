import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.SVNCommitMediator;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ibm.icu.text.Normalizer;

public class WenchengPatchTests {

	static String workdir = "workdir2";
	static SVNClientManager manager;
	static SVNURL url;
	static SVNRepository repo;
	
	@BeforeClass
	public static void setRepository() throws SVNException {
		url = SVNURL
				.parseURIEncoded("svn://localhost/svnkit-patch");
		String name = "wencheng";
		String password = "fang";

		setupLibrary();

		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(name, password);

		ISVNOptions options = SVNWCUtil.createDefaultOptions( true );

		manager = SVNClientManager.newInstance( options, authManager );
		repo = manager.createRepository(url, true);
		
		initRepo();
	}
	
	public static void initRepo() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir );
		updateClient.doCheckout(url, dst, SVNRevision.HEAD, SVNRevision.HEAD, false);
	}

	private static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}
	
	// @AfterClass protected void sth(){};
	
	//@Test
	/**
	 * checkout file(s) committed by Win
	 * (filename is composed)
	 */
	public void checkout1() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir + "/win_commit/" );
		updateClient.doUpdate( dst, SVNRevision.HEAD, false );
	}

	@Test
	/**
	 * checkout file(s) committed by Mac
	 * (filename is decomposed) 
	 */
	public void checkout2() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir  + "/mac_commit/" );
		updateClient.doUpdate( dst, SVNRevision.HEAD, false );
	}

	//@Test
	/**
	 * TODO: 
	 * checkout file(s) and dir(s) committed by Win
	 * (filename is composed) 
	 */
	public void checkout3() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir );
		updateClient.doCheckout(url.appendPath("win_commit", true), dst, SVNRevision.HEAD, SVNRevision.HEAD, true);
	}

	//@Test
	/**
	 * TODO: 
	 * checkout file(s) and dir(s) committed by Mac
	 * (filename is decomposed) 
	 */
	public void checkout4() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir );
		updateClient.doCheckout(url.appendPath("mac_commit", true), dst, SVNRevision.HEAD, SVNRevision.HEAD, true);
	}

	@Test
	/**
	 * get status of whole dir committed from Win 
	 */
	public void status1() throws SVNException {
		SVNStatusClient client = manager.getStatusClient();
		File file = new File( workdir + "/mac_commit/" );
		manager.getLogClient().doList(file, SVNRevision.HEAD, SVNRevision.HEAD, true,
				new ISVNDirEntryHandler() {
					public void handleDirEntry(SVNDirEntry e) throws SVNException {
						System.out.println( e.getName() );
					}
		});
		/*
		manager.getWCClient().doInfo(file, SVNRevision.HEAD, true,
				new ISVNInfoHandler() {
					public void handleInfo(SVNInfo info) throws SVNException {
						System.out.println( info.getPath() + " " + info.getURL() + ":" + info.getKind() );
					}
		});
		*/
		/*
		client.doStatus(file, true, true, true, false,
				new ISVNStatusHandler() {
					public void handleStatus(SVNStatus status) throws SVNException {
						try {
							System.out.println( new String(status.getFile().getName().getBytes("UTF-8")) + ":" + status.getContentsStatus() );
						} catch (UnsupportedEncodingException e) {
						}
					}
		});
		*/
	}
	
	//@Test
	/**
	 * get status of one file
	 */
	public void status2() throws SVNException {
		SVNStatusClient client = manager.getStatusClient();
		File file = new File( workdir + "/win_commit/日本語です.txt" );

		client.doStatus(file, false, false, true, false,
				new ISVNStatusHandler() {
					public void handleStatus(SVNStatus status) throws SVNException {
						try {
							System.out.println( new String(status.getFile().getName().getBytes("UTF-8")) + ":" + status.getContentsStatus() );
						} catch (UnsupportedEncodingException e) {
						}
					}
		});
	}
	
	//@Test
	/**
	 * update local copy for getting a new file
	 * @throws SVNException 
	 */
	public void update() throws SVNException {
		//addfile("だだ.txt");

		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( workdir );
		updateClient.doUpdate( dst, SVNRevision.HEAD, true );
	}

	//@Test
	/**
	 * commit modifed file
	 */
	public void commit1() throws SVNException {
		// modify
		// TODO: modify file
		
		// commit
		SVNCommitClient client = manager.getCommitClient();
        client.doCommit( new File[]{ new File( workdir + "/mac_commit/日本語だべ2.txt") }, false, "", false, false );
	}
	
	@Test
	/**
	 * add new file
	 * @throws IOException 
	 */
	public void commit2() throws SVNException, IOException {
		// add
		File f = new File(workdir + "/mac_commit/日本語だべ4.txt");
		f.createNewFile();
		manager.getWCClient().doAdd( new File(Normalizer.decompose( workdir + "/mac_commit/日本語だべ4.txt",false)),
				false, false, false, true );

		// commit
		SVNCommitClient client = manager.getCommitClient();
        client.doCommit( new File[]{ new File( workdir + "/mac_commit/日本語だべ4.txt") }, false, "", false, false );
	}

	//@Test
	/**
	 * add new dir
	 */
	public void commit3() throws SVNException {
		// add
		File dir = new File(Normalizer.decompose( workdir + "/mac_commit/追加ふぉるだ/",false ) );
		dir.mkdir();
		manager.getWCClient().doAdd( dir, false, false, false, true );

		// commit
		SVNCommitClient client = manager.getCommitClient();
        client.doCommit( new File[]{ new File( workdir + "/mac_commit/追加ふぉるだ/") }, false, "", false, false );
	}

	/**
	 * utility method for adding file 
	 * @throws SVNException
	 */
	public void addfile(String name) throws SVNException {
		ISVNEditor editor = repo.getCommitEditor( "", new SVNCommitMediator(null) );
		String filepath = "mac_commit/" + name;
		editor.openRoot(-1);
		editor.openDir( "mac_commit", -1 );
        editor.addFile( filepath, null, -1 );
        editor.applyTextDelta( filepath, null );
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta( filepath, new ByteArrayInputStream("abc".getBytes()),
        		editor, true);
         editor.closeFile(filepath, checksum);
         editor.closeDir();
         editor.closeDir();
         SVNCommitInfo commitInfo = editor.closeEdit();
         System.out.println("The file was added: " + commitInfo);
	}

	//@Test
	public void fileExists() {
		String u, n8, n = null;
		u = workdir + "/win_commit/日本語です.txt";
	    try {
		    n8 = Normalizer.decompose( u, false );
		    n = new String(n8.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}

	    System.out.println( n );
		File file = new File( n );
		System.out.println( file.exists() ); // false 
	    System.out.println( u );
		file = new File( u );
		System.out.println( file.exists() ); // true
	}

	//@Test
	public void delete() {
	}

	//@Test
	public void copy() {
	}

	// copy and delete?
	//@Test
	public void rename() {
	}

}