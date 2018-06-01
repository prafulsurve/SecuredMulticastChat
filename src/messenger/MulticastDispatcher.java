package Messenger;
import java.io.IOException;
import java.net.*;
import java.security.*;

public class MulticastDispatcher extends NetworkDispatcher{
    public static final String DefaultMulticastGroupIP="230.1.1.1";
    public static final int DefaultMulticastGroupPort=1314;
    
    private static final int RecvBufSize=65536;
    
    private MulticastSocket _cSock;
    private InetAddress _curAddr;
    private int _curPort;
    
    private class RecvThread extends Thread{
        private MulticastSocket _cSock;
        private NetworkDispatcher _Dispatcher;
        
        public RecvThread(NetworkDispatcher iDispatcher,MulticastSocket iSock){
            _cSock=iSock;
            _Dispatcher=iDispatcher;
        }
        
        public void run(){
            byte inBuf[]=new byte[RecvBufSize];
            DatagramPacket rPack=new DatagramPacket(inBuf,RecvBufSize);
            
            try {
                for(;;){
                    _cSock.receive(rPack);
                    _Dispatcher.DataReceived(rPack.getData(),rPack.getLength());
                
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private RecvThread _rThread;
    
    protected void DispatchToAll(byte iBuf[],int iSize) throws Exception{
        DatagramPacket dPack=new DatagramPacket(iBuf,iSize,_curAddr,_curPort);
        _cSock.send(dPack);
    }
    
    /** Creates a new instance of MulticastDispatcher */
    public MulticastDispatcher() throws Exception{
        _curAddr=InetAddress.getByName(DefaultMulticastGroupIP);
        _curPort=DefaultMulticastGroupPort;
        _cSock=new MulticastSocket(_curPort);
        _cSock.joinGroup(_curAddr);
     
        _rThread=new RecvThread(this,_cSock);
        _rThread.start();
    }
    
}
