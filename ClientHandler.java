import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static int jaEscolheram = 0;
    public static String gameState = "";
    private int numeroEscolhido = -1;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public int getNumeroEscolhido() {
        return numeroEscolhido;
    }

    public void setNumeroEscolhido(int numeroEscolhido) {
        this.numeroEscolhido = numeroEscolhido;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
        } 
        catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        while(socket.isConnected()){
            try {
                numeroEscolhido = Integer.parseInt(bufferedReader.readLine());
                jaEscolheram++;
                System.out.println(clientUserName + " escolheu " + numeroEscolhido);
            }
            catch(NumberFormatException e){
                continue;
            }
            catch (IOException e) {
                closeEveryThing(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public static void broadCastMessage(String messageToSend){
        for(ClientHandler clientHandler : clientHandlers){
            try {
                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } 
            catch (IOException e) {
                clientHandler.closeEveryThing(clientHandler.socket, clientHandler.bufferedReader, clientHandler.bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadCastMessage(clientUserName + " perdeu!");
    }

    public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } 
        catch (IOException e) {   
            e.printStackTrace();
        }
    }

    public void venceu(){
        try {
            this.bufferedWriter.write("VENCEU");
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } 
        catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void perdeu(){
        try {
            this.bufferedWriter.write("PERDEU");
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } 
        catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public static void resetHandlers(){
        for(ClientHandler clientHandler : ClientHandler.clientHandlers){
          clientHandler.setNumeroEscolhido(-1);
        }
        ClientHandler.jaEscolheram = 0;
    }

    public static void changeClientsGameState(String mensagem){
        broadCastMessage("GAMESTATE:" + mensagem);
    }
}
