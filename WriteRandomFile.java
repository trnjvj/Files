import com.johnson.jhtp3.ch17.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class WriteRandomFile extends JFrame {   
   private RandomAccessFile output;
   private BankUI userInterface;
   private JButton enter, open;
   public WriteRandomFile()
   {
      super( "Write to random access file" );
      userInterface = new BankUI();
      enter = userInterface.getDoTask();
      enter.setText( "Enter" );
      enter.setEnabled( false );
      enter.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               addRecord();
            }
         }
      );
      addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               if ( output != null ) {
                  addRecord();
                  closeFile();
               }
               else
                  System.exit( 0 );
            }
         }
      );
      open = userInterface.getDoTask2();
      open.setText( "Save As" );
      open.addActionListener(
         new ActionListener() {
            public void actionPerformed( ActionEvent e )
            {
               openFile();
            }
         }      
      );
      getContentPane().add( userInterface,
                            BorderLayout.CENTER );
      setSize( 300, 150 );
      show();  
   }
   private void openFile()
   {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(
         JFileChooser.FILES_ONLY );
      int result = fileChooser.showSaveDialog( this );
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
            output = new RandomAccessFile( fileName, "rw" );
            enter.setEnabled( true );
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
   private void closeFile() 
   {
      try {
         output.close();
         System.exit( 0 );
      }
      catch( IOException ex ) {
         JOptionPane.showMessageDialog( this,
            "Error closing file",
            "Error", JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }
   }
   public void addRecord()
   {
      int accountNumber = 0;
      String fields[] = userInterface.getFieldValues();
      Record record = new Record();
      if ( !fields[ BankUI.ACCOUNT ].equals( "" ) ) {
         // output the values to the file
         try {
            accountNumber =
               Integer.parseInt( fields[ BankUI.ACCOUNT ] );

            if ( accountNumber > 0 && accountNumber <= 100 ) { 
               record.setAccount( accountNumber );
      
               record.setFirstName( fields[ BankUI.FIRST ] );
               record.setLastName( fields[ BankUI.LAST ] );                
               record.setBalance( Double.parseDouble(
                                  fields[ BankUI.BALANCE ] ) );

               output.seek( ( accountNumber - 1 ) *
                            Record.size() );
               record.write( output );
            }           
            userInterface.clearFields();  // clear TextFields
         }
         catch ( NumberFormatException nfe ) {
                JOptionPane.showMessageDialog( this,
                   "Bad account number or balance",
                   "Invalid Number Format",
                   JOptionPane.ERROR_MESSAGE );
         }
         catch ( IOException io ) {
            closeFile();
         }
      }
   }
   public static void main( String args[] )
   {
      new WriteRandomFile();
   }
}
