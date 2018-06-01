
/*
 * For Sharing the File with Others
 */



package Messenger;
import java.io.*;
import java.util.*;

public class FileSharing {

    private LinkedList _SharedFiles=new LinkedList();
    private File _BaseShareFolder;
    
    public void ShareDir(String iPath){
        _BaseShareFolder=new File(iPath);
        _SharedFiles.clear();
        if(!_BaseShareFolder.exists())
            return;
        AddShareDir(_BaseShareFolder);
    }

    public void Unshare(){
        _BaseShareFolder=null;
        _SharedFiles.clear();
    }
    
    public String[] GetSharedFiles(){
        String[] outArr=new String[_SharedFiles.size()];
        for(int i=0;i<_SharedFiles.size();i++)
            outArr[i]=((File)_SharedFiles.get(i)).getAbsolutePath().substring(_BaseShareFolder.getAbsolutePath().length());
            
        Arrays.sort(outArr);
        return outArr;
    }
    
    public String GetFullFilePath(String iFile){
        if(_BaseShareFolder==null) return "";
        return _BaseShareFolder.getAbsolutePath()+iFile;
    }
    
    private void AddShareDir(File iDir){
        File[] fList=iDir.listFiles();
        for(int i=0;i<fList.length;i++){
            
            if(fList[i].isFile()){ 
                if(fList[i].length()>Manager.GetInstance().GetDispatcher().GetMaxFileSize())
                    continue;
                _SharedFiles.add(fList[i]);
            }else if(fList[i].isDirectory())
                AddShareDir(fList[i]);
        }
    }
    
    
    public FileSharing() {
    }
    
    public FileSharing(String iShareFld) {
        ShareDir(iShareFld);
    }
    
}
