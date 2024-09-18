package sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;

public class SFTPConnector {

    private String username;
    private String remote_host;
    private int remote_port;
    private String password;

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("/home/Users/diego/.ssh/known_hosts");
        Session jschSession = jsch.getSession(username, remote_host, remote_port);
        jschSession.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");

    }

    /**
     *
     * @param SFTPHOST
     * @param SFTPWORKINGDIR /file/to/get
     */
    public void fileDownload(String SFTPHOST, String SFTPWORKINGDIR) {

        int SFTPPORT = 22;
        String SFTPUSER = username;
        String SFTPPASS = password;
        Session session = null;
        Channel channel = null;

        try {

            System.out.println("preparing the host information for sftp.");
            ChannelSftp channelSftp = setupJsch();
            System.out.println("sftp channel opened and connected.");
            channelSftp.cd(SFTPWORKINGDIR);
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(channelSftp.get("Test.txt"));
            File newFile = new File("C:/Test.txt");
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;

            while ((readCount = bis.read(buffer)) > 0) {

                System.out.println("Writing: ");
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
