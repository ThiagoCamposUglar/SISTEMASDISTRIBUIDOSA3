package doisouum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Server implements Runnable {
    ServerSocket serverSocket;
    private int numJogadores;
    private int numVencedores;    
    
    public Server(int numJogadores, int numVencedores){
        this.numJogadores = numJogadores;
        this.numVencedores = numVencedores;
    }
    
    @Override
    public void run() {
        try{
            this.serverSocket = new ServerSocket(1234);
            while(!serverSocket.isClosed()){
                if(this.numJogadores != PlayerHandler.playerHandlersList.size()){
                    Socket socket = serverSocket.accept();
                    PlayerHandler playerHandler = new PlayerHandler(socket);
                    System.out.println("Novo player conectado.");
                
                    Thread thread = new Thread(playerHandler);
                    thread.start(); 
                }
                else{
                    iniciarRound();
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }    
    }
    
    public void iniciarRound(){
        
    }
    
    public void close(){
        try{
            serverSocket.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
