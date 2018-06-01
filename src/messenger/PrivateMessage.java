/*
 * Serializing Private Messages
 */

package Messenger;
import java.io.*;

public class PrivateMessage extends Message implements Serializable{
    private User _to;
    
    public User GetTo(){
        return _to;
    }
    
    public PrivateMessage(String iMsg,User iFrom,User iTo) {
        super(iMsg,iFrom);
        _to=iTo;
    }
    
}
