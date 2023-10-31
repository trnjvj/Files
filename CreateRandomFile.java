import com.johnson.jhtp3.ch17.Record;
import java.io.*;
import javax.swing.*;

public class CreateRandomFile {
   private Record blank;
   private RandomAccessFile file;
   public CreateRandomFile()
   {
      blank = new Record();
      openFile();
   }
   private void openFile()
   {
      JFileChooser fileChooser = new JFileChooser();
                   fileChooser.setFileSelectionMode(
                              JFileChooser.FILES_ONLY );
      int result = fileChooser.showSaveDialog( null );
      if ( result == JFileChooser.CANCEL_OPTION )
         return;
      File fileName = fileChooser.getSelectedFile();
      if ( fileName == null || 
           fileName.getName().equals( "" ) )
         JOptionPane.showMessageDialog( null,
            "Invalid File Name",
            "Invalid File Name",
            JOptionPane.ERROR_MESSAGE );
      else {
         try {           
            file = new RandomAccessFile( fileName, "rw" );

            for ( int i = 0; i < 100; i++ )
               blank.write( file );

            System.exit( 0 );
         }
         catch ( IOException e ) {
            JOptionPane.showMessageDialog( null,
               "File does not exist",
               "Invalid File Name",
               JOptionPane.ERROR_MESSAGE );
            System.exit( 1 );
         }
      }
   }

   public static void main( String args[] )
   {
      new CreateRandomFile();
   }   
}
