package Messenger;

public class UserMessage extends Message{
    private User _fromuser;
    
    public UserMessage(String iMsg,User iFrom) {
        super(iMsg,iFrom);
        _fromuser=iFrom;
    }
    
}
