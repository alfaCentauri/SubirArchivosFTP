/**
 * SubirArchivoFTP.java
 * Copyright (C) 2019 Ingeniero en Computación: Ricardo Presilla.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package subirarchivoftp;
 
import java.io.IOException;
import java.net.InetAddress;
import java.io.FileInputStream;
import java.net.SocketException;
import java.io.BufferedInputStream;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTP; 
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
/**
 * Este es una aplicación para conectarse a un servidor ftp y subir un archivo.
 * @author Ingeniero en Computación: Ricardo Presilla.
 * @version 1.0.
 */
public class SubirArchivoFTP {

    /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SocketException, 
            UnknownHostException, IOException 
    {
        String servidor = "yourserver.com";
        String usuario = "usuario";
        String clave = "pws";
        try{
            System.out.println("Conectandose al servidor "+servidor);
            System.out.println("Por favor espere.");
            FTPClient clienteFTP = new FTPClient();
            clienteFTP.connect(InetAddress.getByName(servidor));
            if(clienteFTP.login(usuario,clave))
                System.out.println("Usuario conectado correctamente.");
            else
                System.out.println("Error el usuario ó clave erronea.");            
            int reply = clienteFTP.getReplyCode();            
            System.out.println("Respuesta recibida de conexión FTP:" + reply);            
            if(FTPReply.isPositiveCompletion(reply))
            {
                System.out.println("Conectado Satisfactoriamente");    
            }
            else
            {
                clienteFTP.disconnect();
                System.out.println("Imposible conectarse al servidor");
                System.exit(1);
            }
            if(clienteFTP.changeWorkingDirectory("/"))//Cambiar directorio de trabajo
                System.out.println("Se cambió el directorio satisfactoriamente.");
            else
                System.out.println("No se cambió el directorio.");
            
            //Activar que se envie cualquier tipo de archivo
            
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
            
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream("ejercicio.txt"));//Ruta del archivo para enviar
            clienteFTP.enterLocalPassiveMode();
            if(clienteFTP.storeFile("archivo.txt", buffIn))
                System.out.println("Se cargó el archivo correctamente.");
            else
                System.out.println("No se pudo subir el archivo al servidor ftp.");
            buffIn.close(); //Cerrar envio de arcivos al FTP
            if(clienteFTP.logout())
                System.out.println("Sesion del usurio "+usuario+" ha finalizado correctamente.");
            else
                System.out.println("No se pudo cerrar la sesion del usurio "+usuario);
            clienteFTP.disconnect();//Desconectarse del servidor
        }
        catch(Exception ex)
        {
            System.out.println("Error operación cancelada.\n"+ex.getMessage());
            System.exit(1);
        }
    }
    
}
