package Messenger;
import java.io.*;

public class ServiceMessage extends Message implements Serializable{
    private char _code;
    String _arg;
    User _to;
    
    public final static char CODE_CHAN_ADV='a';
    public final static char CODE_CHAN_OWNER='o';
    public final static char CODE_QUERY_NICK_FREE='n';
    public final static char CODE_QUERY_CHAN_FREE='c';
    public final static char CODE_NICK_TAKEN='t';
    public final static char CODE_CHAN_TAKEN='h';
    public final static char CODE_HELOJOIN='i';
    public final static char CODE_ASK_SHARE='s';
    public final static char CODE_ASK_FILE='f';
    public final static char CODE_JOIN='j';
    public final static char CODE_PART='p';

    
    public boolean IsBroadcast(){
        return _to==null;
    }
    
    public User GetToUser(){
        return _to;
    }
    
    public boolean DontEncrypt(){
        if(_code==CODE_CHAN_ADV || _code==CODE_QUERY_NICK_FREE || _code==CODE_QUERY_CHAN_FREE || _code==CODE_NICK_TAKEN ||_code==CODE_CHAN_TAKEN)
            return true;
        return false;
    }
   
    public char GetCode(){
        return _code;
    }
    
    public String GetArg(){
        return _arg;
    }
    
    public ServiceMessage(User iFrom,User iTo,char iCode,String iArg) {
        this(iFrom,iCode,iArg);
        _to=iTo;
    }

    public ServiceMessage(User iFrom,char iCode,String iArg) {
        super("",iFrom);
        _code=iCode;
        _arg=iArg;
    }
    
}
