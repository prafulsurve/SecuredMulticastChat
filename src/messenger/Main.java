/*
 * Secured Serverless Portable Messenger
 */

package Messenger;
import Messenger.*;

public class Main {
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Manager.GetInstance();
                frmStart.GetInstance().setVisible(true);
            }
        });          
        
    }
    
}
