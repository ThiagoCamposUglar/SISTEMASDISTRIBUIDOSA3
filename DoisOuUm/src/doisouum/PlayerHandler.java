package doisouum;

import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PlayerHandler implements Runnable {
    public static ArrayList<PlayerHandler> playerHandlersList = new ArrayList<>();
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String playerUserName;
    
    public String getPlayerUserName(){
        return this.playerUserName;
    }
    
    public PlayerHandler(Socket socket){
         try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.playerUserName = bufferedReader.readLine();
            playerHandlersList.add(this);
            
            DoisOuUm.atualizaTabela();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String cmd;
        while(this.socket.isConnected()){
            try{
                cmd = bufferedReader.readLine();
                
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
