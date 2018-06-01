
/*
 * Read & Write Group Message
 */
package Messenger;
import java.io.*;

public class ChannelMessage extends Message implements Serializable{
    private Channel _channel;
    
    public Channel GetChannel(){
        return _channel;
    }
    
    public ChannelMessage(String iMsg,User iFrom,Channel iToChan) {
        super(iMsg,iFrom);
        _channel=iToChan;
    }
    
   private void writeObject(java.io.ObjectOutputStream out) throws IOException{
     out.writeUTF(_channel.GetName());   
   }
 
   private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
       _channel=Channel.GetByName(in.readUTF());
   }
    
    
}
