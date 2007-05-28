import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ibm.icu.text.Normalizer;

public class SVNTest {
	
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

		ISVNOptions options = SVNWCUtil.createDefaultOptions( 
				  new File("workdir2"), false 
				);

		manager = SVNClientManager.newInstance( options, authManager );
		repo = manager.createRepository(url, true);
	}

	private static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	//@Test
	public void checkout() throws SVNException {
		SVNUpdateClient updateClient = manager.getUpdateClient();
		File dst = new File( "workdir2" );
		updateClient.doCheckout(url, dst, SVNRevision.HEAD, SVNRevision.HEAD, true);
	}
	
	@Test
	public void commit() throws SVNException {
        SVNCommitClient client = manager.getCommitClient();
        client.doCommit( new File[]{ new File("workdir2/mac_commit/日本語だべ4.txt") }, false, "", false, false );
	}
	
	//@Test
	public void wcAdd() throws SVNException {
		manager.getWCClient().doAdd( new File(Normalizer.decompose("workdir2/mac_commit/日本語だべ4.txt",false)),
				false, false, false, true );
	}

}