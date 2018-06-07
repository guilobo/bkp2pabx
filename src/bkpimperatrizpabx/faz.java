/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkpimperatrizpabx;

import static bkpimperatrizpabx.SFTPUtility.createConnectionString;
import static bkpimperatrizpabx.SFTPUtility.createDefaultOptions;
import com.jcraft.jsch.ChannelSftp;
import java.util.Vector;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;


/**
 *
 * @author NaMent
 */
public class faz {
    private StandardFileSystemManager manager;
    
    public void BaixaArquivos (FileObject localFile, FileObject remoteFile){
        
                
                try {
   
            
            //FileObject remoteFile = manager.resolveFile(createConnectionString(host, usuario, senha, remoteFolder+"/20170130/"+rs.getString("guid")+".gsm"), createDefaultOptions());
            localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
            } catch (Exception e) {
            //throw new RuntimeException(e);
            //JOptionPane.showMessageDialog(null, "Erro no backup do dia "+dia+"/"+mes+"/"+ano);
            //return;
        } finally {
          //manager.close();  
        }               
            
        
    }
}
