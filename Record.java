package com.johnson.jhtp3.ch17;
import java.io.*;
import com.johnson.jhtp3.ch17.BankAccountRecord;

public class Record extends BankAccountRecord {
   public Record()
   {
      this( 0, "", "", 0.0 );
   }
   public Record( int acct, String first,
                  String last, double bal )
   {
      super( acct, first, last, bal );
   }
   public void read( RandomAccessFile file ) throws IOException
   {
      setAccount( file.readInt() );   
      setFirstName( padName( file ) );
      setLastName( padName( file ) );
      setBalance( file.readDouble() );
   }
   private String padName( RandomAccessFile f )
      throws IOException
   {
      char name[] = new char[ 15 ], temp;
      for ( int i = 0; i < name.length; i++ ) {
         temp = f.readChar();
         name[ i ] = temp;
      }      
      return new String( name ).replace( '\0', ' ' );
   }
   public void write( RandomAccessFile file ) throws IOException
   {
      file.writeInt( getAccount() );
      writeName( file, getFirstName() );
      writeName( file, getLastName() );
      file.writeDouble( getBalance() );
   }
   private void writeName( RandomAccessFile f, String name )
      throws IOException
   {
      StringBuffer buf = null;
      if ( name != null ) 
         buf = new StringBuffer( name );
      else 
         buf = new StringBuffer( 15 );
      buf.setLength( 15 );
      f.writeChars( buf.toString() );
   }
   public static int size() { return 72; }
}
