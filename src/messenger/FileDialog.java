
/*
 * Opening & selecting the file using File Dialog
 *
 *
 */


package Messenger;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class FileDialog {
    private static File _selfile;
    
    private static File GenericFileDialog(int iType){
        return GenericFileDialog(iType,JFileChooser.FILES_ONLY);
    }
    
    private static File GenericFileDialog(int iType,int iSelMode){
        _selfile=null;
        final JDialog fDiag=new JDialog();
        fDiag.setModal(true);
        
        final JFileChooser fc=new JFileChooser();
        fc.setDialogType(iType);
        
        fc.setFileSelectionMode(iSelMode);
        fc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                if(evt.getActionCommand().equals("ApproveSelection"))
                    FileDialog._selfile=fc.getSelectedFile();
                    fDiag.setVisible(false);
                
                }
            
        });
        fDiag.getContentPane().add(fc);
        fDiag.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        fDiag.setSize(400,300);
        fDiag.setVisible(true);
        return fc.getSelectedFile();
        
        
    }
    
    public static File DirFileDialog(){
        return GenericFileDialog(JFileChooser.OPEN_DIALOG ,JFileChooser.DIRECTORIES_ONLY);
    }

    public static File OpenFileDialog(){
        return GenericFileDialog(JFileChooser.OPEN_DIALOG);
    }
    
    public static File SaveFileDialog(){
        return GenericFileDialog(JFileChooser.SAVE_DIALOG);
    }
    
    
}
