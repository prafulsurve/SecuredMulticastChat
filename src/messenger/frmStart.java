package Messenger;

import javax.swing.*;
import Messenger.*;

public class frmStart extends javax.swing.JFrame {
    
    private static frmStart _singObj;
    
    public static frmStart GetInstance(){
        if(_singObj==null)
            _singObj=new frmStart();
        return _singObj;
    }
    
    private frmStart() {
        initComponents();
        this.setSize(280,320);
        this.update(this.getGraphics());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbNickname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ltChans = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tbNewChan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tbKey = new javax.swing.JTextField();
        btOK = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        tbSelKey = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secured Serverless Portable Messenger");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setName("frmStart"); // NOI18N
        getContentPane().setLayout(null);
        getContentPane().add(tbNickname);
        tbNickname.setBounds(100, 10, 170, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Nickname:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 10, 90, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Join a Group...");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 50, 79, 14);

        jScrollPane1.setViewportView(ltChans);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 70, 250, 110);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("... Or create a new Group");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 220, 138, 14);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12));
        jLabel4.setText("Name:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 240, 60, 20);

        tbNewChan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbNewChanKeyTyped(evt);
            }
        });
        getContentPane().add(tbNewChan);
        tbNewChan.setBounds(80, 240, 90, 20);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12));
        jLabel5.setText("Key:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(172, 240, 40, 20);

        tbKey.setEnabled(false);
        getContentPane().add(tbKey);
        tbKey.setBounds(210, 240, 60, 20);

        btOK.setText("OK");
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });
        getContentPane().add(btOK);
        btOK.setBounds(10, 270, 260, 23);

        jButton1.setText("Update List");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(150, 50, 119, 20);
        getContentPane().add(tbSelKey);
        tbSelKey.setBounds(190, 190, 80, 20);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12));
        jLabel7.setText("Join using key:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(90, 190, 100, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbNewChanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNewChanKeyTyped
        tbKey.setEnabled(tbNewChan.getText().length()>0);
    }//GEN-LAST:event_tbNewChanKeyTyped

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        if(GetNick().length()==0){
            JOptionPane.showMessageDialog(this,"Nick cannot be empty","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(ltChans.getSelectedValue()==null && !GetCreateNewChannel()){
            JOptionPane.showMessageDialog(this,"You must select a Group to join or create a new one","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog jWait=new JDialog(this,"Wait... Checking nick and group availability");
        jWait.setResizable(false);
        //jWait.setAlwaysOnTop(true);
        jWait.setSize(400,20);
        jWait.setVisible(true);
        jWait.requestFocus();
        
        boolean NickAvail=true,ChanAvail=true;
        
        NickAvail=Manager.GetInstance().TrySetNick(GetNick());
        if(GetCreateNewChannel())
            ChanAvail=Manager.GetInstance().IsChannelFree(GetNewChannelName());
        
        jWait.dispose();
        
        if(!NickAvail){
            JOptionPane.showMessageDialog(this,"Nick already taken","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(GetCreateNewChannel()){
            if(ChanAvail){
                Channel.CreateNew(GetNewChannelName(),GetNewChannelKey());
                dispose();
            }
            else
                JOptionPane.showMessageDialog(this,"Group already exists","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            String selChan=(String)ltChans.getSelectedValue();
            try {
                Channel.JoinExisting(selChan,GetSelChannelKey());
                System.out.println("Join requested");
                synchronized(Manager.GetInstance().WaitForJoinAck){ Manager.GetInstance().WaitForJoinAck.wait(Manager.DefaultOperTimeout);}
                if(Manager.GetInstance().GetCurrentChannel()==null){
                    JOptionPane.showMessageDialog(this,"Timeout waiting welcome acknowledgement (probably due to a WRONG KEY)","Error",JOptionPane.ERROR_MESSAGE);
                    System.exit(-1);
                }
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
     
    }//GEN-LAST:event_btOKActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    UpdateChanList();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public String GetNick(){
        return tbNickname.getText();
    }
    
    public boolean GetCreateNewChannel(){
        return tbNewChan.getText().length()>0;
    }
    
    public String GetNewChannelName(){
        return tbNewChan.getText();
    }
    
    public String GetNewChannelKey(){
        
        return tbKey.getText();
    }
    
    public String GetSelChannelKey(){
        return tbSelKey.getText();
    }
    
    private void UpdateChanList(){
        ltChans.setModel(new javax.swing.AbstractListModel() {
            String[] strings = Manager.GetInstance().GetAvailableChannels();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOK;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList ltChans;
    private javax.swing.JTextField tbKey;
    private javax.swing.JTextField tbNewChan;
    private javax.swing.JTextField tbNickname;
    private javax.swing.JTextField tbSelKey;
    // End of variables declaration//GEN-END:variables
    
}
