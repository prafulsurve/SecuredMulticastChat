//Managing the Groups or Channels


package Messenger;
import java.util.*;
import Messenger.*;

public class Channel{
    private String _name;
    private String _key;
    private User _owner;
    private frmChan _ui;
    
    private LinkedList _users=new LinkedList();
    private static TreeMap _knownchannels=new TreeMap();
    
    public static Channel GetByName(String iName){
        if(_knownchannels.containsKey(iName))
            return (Channel)_knownchannels.get(iName);
        else
            return null;
    }
    
    public String GetName(){
        return _name;
    }
    
    public User GetOwner(){
        return _owner;
    }
    
    public void SetOwner(User iOwner){
        _owner=iOwner;
    }
    
    public User[] GetUsers(){
        User[] outArr=new User[_users.size()];
        
        _users.toArray(outArr);
        return outArr;
    }
    
        
    public void AddUser(User iUser){
        
        if(_users.contains(iUser))
           return;
        
        _users.add(iUser);
        _ui.UpdateNickList(GetUsers());
        
    }
    
    public void Join(User iUser){
        Notice(iUser.GetName() + " joined " + GetName());
        AddUser(iUser);
        
    }
   
    public void Notice(String iStr){
        _ui.AddRecvLine("::: "+iStr);
    }
    
    public void Part(User iUser){
        if(_users.contains(iUser))
            _users.remove(iUser);
        Notice(iUser.GetName() + " left " + GetName());
        _ui.UpdateNickList(GetUsers());
    }
    
    
    public void MessageReceived(ChannelMessage iMsg){
         _ui.AddRecvLine("< "+iMsg.GetSender().GetName() + "> " + iMsg.GetText());
    }
    
    public void SetKey(String iKey){
        _key=iKey;
        Manager.GetInstance().GetDispatcher().SetKey(iKey);
    }
    
    /** Creates a new instance of Channel */
    public Channel(String iName) {
        _name=iName;
       
        _ui=new frmChan();
        _ui.setTitle("Channel " + _name);
        _ui.setVisible(true);
        
        _knownchannels.put(_name,this);
    }
    
    public void finalize() throws Throwable{
        _knownchannels.remove(this);
        super.finalize();
    }
    
    public boolean equals(Channel iTo){
        if(iTo==null) return false;
        return _name.equals(iTo._name);
    }
    
    public static void CreateNew(String iName,String iKey){
        Channel newChan=new Channel(iName);
      if(iKey!=null && iKey.length()>0)
        newChan.SetKey(iKey);
      newChan.SetOwner(Manager.GetInstance().GetMe());
      Manager.GetInstance().SetAndAdvertiseChannel(newChan);
    }
    
    public static void JoinExisting(String iName,String iKey) throws Exception{
        Manager.GetInstance().GetDispatcher().SetKey(iKey);
        ServiceMessage newMsg=new ServiceMessage(Manager.GetInstance().GetMe(),ServiceMessage.CODE_JOIN,iName);
        Manager.GetInstance().GetDispatcher().DispatchToAll(newMsg);
    }
}
