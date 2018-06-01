/*
  // Code for Attaching file 

*/
package Messenger;
import java.io.*;
import java.security.*;
import java.util.*;

public class AttachmentMessage extends Message implements Serializable {
    private String _filename; // file name
    private int _filelength; // file length
    private byte[] _filecontent; // content of the file
    private byte[] _checksum;
    private boolean _requested; //The file is sent by the user, or requested by another user share
    
    public AttachmentMessage(User iFrom,File iFile,boolean iReq) throws Exception {
        super(iFile.getName(),iFrom);
        _filename=iFile.getName();
        _filelength=(int)iFile.length();
        _filecontent=new byte[_filelength];
        _requested=iReq;
        FileInputStream fIn=new FileInputStream(iFile);
        fIn.read(_filecontent);
        fIn.close();
        _checksum=CalcDigest();
    }
    
    public void SetFileName(String iName){
        _filename=iName;
    }
    public String GetFileName(){
        return _filename;
    }
    
    public boolean equals (Object obj){
        if(!(obj instanceof AttachmentMessage))
            return false;
        AttachmentMessage tMsg=(AttachmentMessage)obj;
        return GetSender().equals(tMsg.GetSender()) && _filename.equals(tMsg._filename);
    }
    public boolean IsRequested(){
        return _requested;
    }
    public boolean CheckDigest() throws Exception{
        return Arrays.equals(_checksum,CalcDigest());
    }
    
    private byte[] CalcDigest() throws Exception{
        MessageDigest md=MessageDigest.getInstance("MD5");
        return md.digest(_filecontent);
    }
    
    public byte[] GetBytes(){
        return _filecontent;
    }
    
    public int GetLength(){
        return _filelength;
    }
    
    public void SaveToFile(String iPath) throws Exception{
        FileOutputStream fOut=new FileOutputStream(iPath);
        fOut.write(_filecontent);
        fOut.close();
    }
    
    public String toString(){
        return "[" + GetSender() + "] " + _filename + " ( " + _filelength + " bytes)";
    }
    
    public String GetContent(){
       char []chArr=new char[_filecontent.length];
       for(int i=0;i<_filecontent.length;i++)
           chArr[i]=(char)_filecontent[i];
       return new String(chArr);
    }
}
