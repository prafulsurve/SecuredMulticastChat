/*
 * Encryption & Cryptography for the message
 */



package Messenger;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public abstract class NetworkDispatcher {
    
    private static final byte BLOCK_ENCRYPTED=(byte)0xba;
    private static final byte BLOCK_UNENCRYPTED=(byte)0xab;
    private boolean _keySet=false;
    private Cipher _encCipher;
    private Cipher _decCipher;
    SecretKeySpec _keyIV; 
    private PBEParameterSpec _paramSpec;
    private SecretKey _secretKey;
    

    private static byte[] salt = {(byte)0xc9, (byte)0x53, (byte)0x67, (byte)0x9a, (byte)0x5b, (byte)0xc8, (byte)0xae, (byte)0x18 };

    public void SetKey(String iKey){
        if(iKey==null || iKey.length()<=0){
            _keySet=false;    
            return;
        }

        _keySet=true;
        try {
             Provider sunJce = new com.sun.crypto.provider.SunJCE();
             Security.addProvider(sunJce);
             _paramSpec = new PBEParameterSpec(salt,20);
             PBEKeySpec keySpec = new PBEKeySpec(iKey.toCharArray() );
             SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
             _secretKey = keyFactory.generateSecret(keySpec);


            _encCipher.init(Cipher.ENCRYPT_MODE,_secretKey, _paramSpec);
            _decCipher.init(Cipher.DECRYPT_MODE,_secretKey, _paramSpec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int GetMaxFileSize(){
        if(_keySet)
            return _decCipher.getOutputSize(65000);
        else 
            return 65000;
    }
    
    protected NetworkDispatcher() {
        try {
            
            _encCipher=Cipher.getInstance("PBEWithMD5AndDES");
            _decCipher=Cipher.getInstance("PBEWithMD5AndDES");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected abstract void DispatchToAll(byte []iBuf,int iSize) throws Exception;
    
    protected void DispatchToAll(byte []iBuf) throws Exception{
        DispatchToAll(iBuf,iBuf.length);
    }
    
    public void DispatchToAll(Message iMsg){
        try {
            ByteArrayOutputStream bOut= new ByteArrayOutputStream();
            boolean EncData=(_keySet && !iMsg.DontEncrypt());

            if(EncData)
                bOut.write(BLOCK_ENCRYPTED);
            else
                bOut.write(BLOCK_UNENCRYPTED);
            
            
            OutputStream underlayingStream=bOut;
            if(EncData)
                underlayingStream=new CipherOutputStream(bOut,_encCipher);
            
            ObjectOutputStream ooStream=new ObjectOutputStream(underlayingStream);
            
                
            ooStream.writeObject(iMsg);
            ooStream.close();

            byte []bb=bOut.toByteArray();
            
            DispatchToAll(bOut.toByteArray());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    protected void DataReceived(byte []iBuf,int iLen){
        try {        
            if(iBuf[0]==BLOCK_ENCRYPTED){
                if(!_keySet)
                    return;
                 byte []outBuf=new byte[_decCipher.getOutputSize(iLen-1)+1];
                 iLen=_decCipher.doFinal(iBuf,1,iLen-1,outBuf,1)+1;
                 iBuf=outBuf;
            }else if(iBuf[0]!=BLOCK_UNENCRYPTED)
                return;
            
            ByteArrayInputStream bIn=new ByteArrayInputStream(iBuf,1,iLen-1);
            ObjectInputStream ooStream=new ObjectInputStream(bIn);
            Object msgIn=ooStream.readObject();
            ooStream.close();
            Message recMsg=(Message)msgIn;
            Manager.GetInstance().ParseMessage(recMsg);
            
        } 
        catch(BadPaddingException ex){
            
            try {
                _decCipher.init(Cipher.DECRYPT_MODE,_secretKey, _paramSpec);
            } catch (Exception exc) {}
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}
