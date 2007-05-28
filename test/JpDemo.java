import com.ibm.icu.text.Normalizer;
import java.io.FileWriter;

public class JpDemo
{
    public static void main(String[] args)
    {
	FileWriter f,g;
	String u,u8,n,n8;
	try {
	    u = "ぺ";
	    u8 = new String(u.getBytes(),"UTF-8");
	    n8 = Normalizer.decompose( u, false );
	    n = new String(n8.getBytes("UTF-8"));

	    print(u);
	    //print(u8);
	    print(n8);
	    print(n);

	    f = new FileWriter( "jp-decomposed.txt" );
	    f.write(u);
	    f.write('\n');
	    f.write(n);
	    f.write('\n');
	    f.flush();
	    f.close();

	    u8 = "\u3078\u309A";
	    u = new String(u8.getBytes("UTF-8"));
	    n8 = Normalizer.compose( u8, false );
	    n = new String(n8.getBytes("UTF-8"));

	    print(u);
	    print(u8);
	    print(n8);
	    print(n);

	    g = new FileWriter( "jp-composed.txt" );
	    g.write(u);
	    g.write('\n');
	    g.write(n);
	    g.write('\n');
	    g.flush();
	    g.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    static void print(String s)
    {
	System.out.println( s );
	try {
	    byte[] b = s.getBytes("UTF-8");
	    System.out.println( "len: " + b.length );
	    for ( int i = 0; i < b.length; i++ ) {
		System.out.print( String.format( "%x ", b[i] ) );
	    }
	    System.out.println();
	    System.out.println("--------");
	} catch (Exception e) {
	}
    }

}