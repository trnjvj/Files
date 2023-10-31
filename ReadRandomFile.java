import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;
import com.deitel.jhtp3.ch17.*;

public class ReadRandomFile extends JFrame {
   private BankUI userInterface;
   private RandomAccessFile input;  
   private JButton next, open;
   public ReadRandomFile()
   {
      super( "Read Client File" );
      userInterface = new BankUI();
      next = userInterface.getDoTask();
      next.setText( "Next" );
      next.setEnabled( false );
      next.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               readRecord();
            }
         }
      ); 
      addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               if ( input != null ) {
                  closeFile();
               }
               else
                  System.exit( 0 );
            }
         }
      );
      open = userInterface.getDoTask2();
      open.setText( "Read File" );
      open.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {      
                openFile();
            }
         }      
      );   
      getContentPane().add( userInterface );
      setSize( 300, 150 );
      show();  
   }
   private void openFile()
   {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(
         JFileChooser.FILES_ONLY );
      int result = fileChooser.showOpenDialog( this );
      if ( result == JFileChooser.CANCEL_OPTION )
         return;

      File fileName = fileChooser.getSelectedFile();
      if ( fileName == null ||
           fileName.getName().equals( "" ) )
         JOptionPane.showMessageDialog( this,
            "Invalid File Name",
            "Invalid File Name",
            JOptionPane.ERROR_MESSAGE );
      else {
         try {
            input = new RandomAccessFile( fileName, "r" );
            next.setEnabled( true );
            open.setEnabled( false );
         }
         catch ( IOException e ) {
            JOptionPane.showMessageDialog( this,
               "File does not exist",
               "Invalid File Name",
               JOptionPane.ERROR_MESSAGE );
         }      
      }
   } 
   public void readRecord()
   {
      DecimalFormat twoDigits = new DecimalFormat( "0.00" );
      Record record = new Record();
      try {
         do {
            record.read( input );
         } while ( record.getAccount() == 0 );
         String values[] = {
            String.valueOf( record.getAccount() ),
            record.getFirstName(),
            record.getLastName(),
            String.valueOf( record.getBalance() ) };
         userInterface.setFieldValues( values );
      }
      catch ( EOFException eof ) {
         closeFile();
      }
      catch ( IOException e ) {
         JOptionPane.showMessageDialog( this,
            "Error Reading File",
            "Error",
            JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }
   }
   private void closeFile() 
   {
      try {
         input.close();
         System.exit( 0 );
      }
      catch( IOException ex ) {
         JOptionPane.showMessageDialog( this,
            "Error closing file",
            "Error", JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }
   }
   public static void main( String args[] )
   {
      new ReadRandomFile();
   }
}
