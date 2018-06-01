package Messenger;

public class User implements java.io.Serializable,Comparable{
    public static User Anonymous=new User();
    public static final char STATUS_NOTAUTH='0';
    public static final char STATUS_ASKINGNICK='1';
    public static final char STATUS_NICKFAILED='2';
    public static final char STATUS_AUTH='3';
        
    private String _name;
    private boolean _anonymous=false;
    private char _status=STATUS_NOTAUTH;
   
    
    public boolean IsAnonymous(){
        return _anonymous;
    }
    
    public char GetStatus(){
        return _status;
    }
    
    public void SetStatus(char iStatus){
        _status=iStatus;
    }
    
    public String GetName(){
        return _name;
    }
    /** Creates a new instance of User */
    public User(String iName) {
        _name=iName;
    }
    
    //Creates an anonymous user
    private User(){
        _anonymous=true;
        _name="???";
    }
    
    public boolean equals(Object obj){
        if(!(obj instanceof User))
            return false;
        if(obj==null) return false;
        if(IsAnonymous() || ((User)obj).IsAnonymous()) return false;
        
        return _name.equals(((User)obj)._name);
    }
    
    public String toString(){
        return _name;
    }
    
    public int compareTo(Object iTo){
        if(! (iTo instanceof User))
            return 0;
        return _name.compareTo(((User)iTo)._name);
    }
}
