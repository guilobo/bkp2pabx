package bkpimperatrizpabx;

import static bkpimperatrizpabx.SFTPUtility.createConnectionString;
import static bkpimperatrizpabx.SFTPUtility.createDefaultOptions;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Calendar;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;


public class Listremoteserver {
private static String localFolder = "C:\\pabx1\\";
private static String remoteFolder;
private static String SFTPHOST = "192.168.0.0";
private static int    SFTPPORT = 22;
private static String SFTPUSER = "user";
private static String SFTPPASS = "pass";

private static String ano;
private static String mes;
private static String dia;


    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        

        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;

        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            ano = cal.get(Calendar.YEAR)+"";
            if (cal.get(Calendar.MONTH)+1 >=10)
            mes = ""+(cal.get(Calendar.MONTH)+1);
            else
            mes = "0"+(cal.get(Calendar.MONTH)+1);
            if (cal.get(Calendar.DAY_OF_MONTH) >= 10)
            dia = ""+cal.get(Calendar.DAY_OF_MONTH);
            else
            dia = "0"+cal.get(Calendar.DAY_OF_MONTH);
            
            
            remoteFolder = "/var/spool/asterisk/monitor/"+ano+"/"+mes+"/"+dia+"/";
            System.err.println(remoteFolder);
            
            
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(remoteFolder);
            Vector filelist = channelSftp.ls(remoteFolder);
            StandardFileSystemManager manager;
            manager = new StandardFileSystemManager();
            manager.init();
            
            
            
            for(int i=0; i<filelist.size();i++){
                LsEntry entry = (LsEntry) filelist.get(i);
                System.out.println(entry.getFilename());
                FileObject localFile = manager.resolveFile(localFolder+entry.getFilename());
                FileObject remoteFile = manager.resolveFile(createConnectionString(SFTPHOST, SFTPUSER, SFTPPASS, remoteFolder+entry.getFilename()), createDefaultOptions());
                //as.BaixaArquivos(localFile, remoteFile);
                localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
                
            }
            //segundo pabx
            
            session     = null;
            channel     = null;
            channelSftp = null;
            
            localFolder = "C:\\pabx2\\";
            SFTPHOST = "192.168.0.0";
            SFTPPORT = 22;
            SFTPUSER = "user";
            SFTPPASS = "pass";
            remoteFolder = "/var/www/snep2/arquivos/"+ano+"-"+mes+"-"+dia+"/";
            System.out.println(remoteFolder);
            
            
            jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(remoteFolder);
            //filelist = new Vector();
            filelist = channelSftp.ls(remoteFolder);
            manager = new StandardFileSystemManager();
            manager.init();
            
            
            
            for(int i=0; i<filelist.size();i++){
                LsEntry entry = (LsEntry) filelist.get(i);
                System.out.println(entry.getFilename());
                FileObject localFile = manager.resolveFile(localFolder+entry.getFilename());
                if (entry.getFilename().contains(".WAV")){
                try{
                FileObject remoteFile = manager.resolveFile(createConnectionString(SFTPHOST, SFTPUSER, SFTPPASS, remoteFolder+entry.getFilename()), createDefaultOptions());
                localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
                }catch(Error e){
                    System.out.println("Não Foi "+entry.getFilename());
                };}
                
            }
            System.exit(0);
            
            

        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }
}