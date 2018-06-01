package Messenger;
import java.io.*;

public class SharingListMessage extends Message implements Serializable{
    
    String[] _share;
    
    public String[] GetShareList(){
        return _share;
    }
    
    public SharingListMessage(User iFrom, String[] iSharingList) {
        super("",iFrom);
        _share=iSharingList;
        
    }
    
}
