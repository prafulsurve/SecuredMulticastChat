// Managing the Group Broadcast Message

package Messenger;


public class ChanAdvertiserThread extends Thread {
    private Channel _channel;
    public ChanAdvertiserThread(Channel iChan){
        _channel=iChan;
    }
    
    public void run(){
        Manager manager=Manager.GetInstance();
        
        for(;;){
            try {
                ServiceMessage advMsg=new ServiceMessage(manager.GetMe(),ServiceMessage.CODE_CHAN_ADV,_channel.GetName());
                manager.GetDispatcher().DispatchToAll(advMsg);
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
         }
    }
}
