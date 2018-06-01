package Messenger;
import java.io.*;

public abstract class Message implements Serializable{
    protected String _text;
    protected User _from;
    
    public boolean DontEncrypt(){
        return false;
    }
    
    public User GetSender(){
        return _from;
    }
    
    public String GetText(){
        return _text;
    }
    /*
    public byte[] toByteArray(){
        try {
            ByteArrayOutputStream bStream=new ByteArrayOutputStream();
            ObjectOutputStream dStream=new ObjectOutputStream(bStream);
            dStream.writeObject(_from);
            dStream.writeUTF(_text);
            dStream.close();
            return bStream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return new byte[0];
        }
    }
    */
    protected Message(String iMsg,User iFrom) {
        _text=iMsg;
        _from=iFrom;
    }
    
}
