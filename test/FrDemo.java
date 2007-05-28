import com.ibm.icu.text.Normalizer;
import java.io.FileWriter;

public class FrDemo
{
    public static void main(String[] args)
    {
	FileWriter f;
	try {
	    String u8 = "\u0041\u0301";
	    String u = new String(u8.getBytes( "UTF-8" ));
	    String n8 = Normalizer.compose( u8, false );
	    String n = new String(n8.getBytes("UTF-8"));

	    print(u);
	    print(u8);
	    print(n8);
	    print(n);

	    f = new FileWriter( "a-accute.composed.txt" );
	    f.write( u );
	    f.write('\n');
	    f.write( n );
	    f.write('\n');
	    f.flush();
	    f.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    static void print(String s)
    {
	print(s,"MacRoman");
    }

    static void print(String s, String enc)
    {
	System.out.println( s );
	try {
	    byte[] b = s.getBytes(enc);
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