import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private int numeroDeJogadores = 0;
    private int numeroDeVencedores = 1;

    public Server(ServerSocket serverSocket, int numeroDeJogadores, int numeroDeVencedores) {
      this.serverSocket = serverSocket;
      this.numeroDeJogadores = numeroDeJogadores;
      this.numeroDeVencedores = numeroDeVencedores;
    }

    public void startServer() throws InterruptedException {
      try {
        System.out.println("Esperando os " + numeroDeJogadores + " se conectarem / Número de vencedores: " + numeroDeVencedores);
        while(!serverSocket.isClosed()){
          if(ClientHandler.gameState.isEmpty()){
            Socket socket = this.serverSocket.accept();
            System.out.println("Novo jogador conectado");
            ClientHandler clientHandler = new ClientHandler(socket);
            
            Thread thread = new Thread(clientHandler);
            thread.start();

            if(numeroDeJogadores == ClientHandler.clientHandlers.size()){
              if(numeroDeJogadores == 2){
                ClientHandler.gameState = "PAROUIMPAR";
              }
              else{
                ClientHandler.gameState = "2OU1";
              }
              ClientHandler.changeClientsGameState(ClientHandler.gameState);
            }
          }
          else{
            if(ClientHandler.clientHandlers.size() == this.numeroDeVencedores){
              for (ClientHandler ch : ClientHandler.clientHandlers) {
                ch.venceu();
              }
              return;
            }
            if(ClientHandler.jaEscolheram == ClientHandler.clientHandlers.size() && ClientHandler.clientHandlers.size() != 2){
              iniciarDoisOuUm();
            }
            else if(ClientHandler.jaEscolheram == ClientHandler.clientHandlers.size() && ClientHandler.clientHandlers.size() == 2){
              iniciarParOuImpar();
            }
            else{
              if(!ClientHandler.gameState.contains("ESCOLHER")){
                if(ClientHandler.clientHandlers.size() != 2){
                   ClientHandler.gameState = "ESCOLHER-2OU1";
                }
                else{
                  ClientHandler.gameState = "ESCOLHER-PAROUIMPAR";
                  for(int i = 0; i <= 1; i++){
                    ClientHandler.broadCastMessage(ClientHandler.clientHandlers.get(i).getClientUserName() + " é " + (i == 0 ? "ímpar" : "par"));
                  }
                }
                ClientHandler.changeClientsGameState(ClientHandler.gameState);
              }
              else{
                System.out.print(".");
                Thread.sleep(2000);
              }
            }
          }
        }
      } 
      catch (IOException e) {
        e.printStackTrace();
      }
      catch (Exception e){
        e.printStackTrace();
      }
    }

    public void iniciarDoisOuUm(){
      ClientHandler.gameState = "2OU1";
      ClientHandler.changeClientsGameState(ClientHandler.gameState);
      System.out.println("Dois ou um");
      for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
        ClientHandler.broadCastMessage(clientHandler.getClientUserName() + " escolheu " + clientHandler.getNumeroEscolhido());
      }
      ClientHandler ch = null;
      for(ClientHandler i : ClientHandler.clientHandlers){
        ch = i;
        for(ClientHandler j : ClientHandler.clientHandlers){
          if(!i.getClientUserName().equals(j.getClientUserName()) && i.getNumeroEscolhido() == j.getNumeroEscolhido()){
            ch = null;
            break;
          }
        }
        if(ch != null){
          i.removeClientHandler();
          i.perdeu();
          ClientHandler.resetHandlers();
          if(ClientHandler.clientHandlers.size() == 2 && numeroDeVencedores == 1){
            ClientHandler.gameState = "PAROUIMPAR";
            ClientHandler.changeClientsGameState(ClientHandler.gameState);
          }
          break;
        }
      }
      if(ch == null){
        ClientHandler.broadCastMessage("Ninguém perdeu");
      }
      ClientHandler.resetHandlers();
    }
    
    public void iniciarParOuImpar(){
      System.out.println("Par ou ímpar");
      int soma = 0;
      for(ClientHandler clientHandler : ClientHandler.clientHandlers){
        ClientHandler.broadCastMessage(clientHandler.getClientUserName() + " escolheu " + clientHandler.getNumeroEscolhido());
        soma += clientHandler.getNumeroEscolhido();
      }
      if(soma % 2 == 1){
        ClientHandler clientHandler = ClientHandler.clientHandlers.get(1);
        clientHandler.removeClientHandler();
        clientHandler.perdeu();
      }
      else{
        ClientHandler clientHandler = ClientHandler.clientHandlers.get(0);
        clientHandler.removeClientHandler();
        clientHandler.perdeu();
      }
    }
    
    public void closeServerSocket(){
      try {
        if(serverSocket != null){
          serverSocket.close();
        }
      } 
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    public static void main(String[] args) throws IOException, InterruptedException{
      Scanner input = new Scanner(System.in);
      int numeroDeJogadores;
      int numeroDeVencedores;
      try {
        System.out.println("Insira o número de jogadores que irão participar(2 a 10):");
        numeroDeJogadores = input.nextInt();
      } catch (Exception e) {
        numeroDeJogadores = 2;
      }

      if(numeroDeJogadores > 2){
        try {
          System.out.println("Insira o número vencedores(1 ou 2)");
          numeroDeVencedores = input.nextInt();
        } catch (Exception e) {
          numeroDeVencedores = 1;
        }
      }
      else{
        numeroDeVencedores = 1;
        System.out.println("1 vencedor");
      }

      input.close();

      ServerSocket serverSocket = new ServerSocket(1234);
      Server server = new Server(serverSocket, numeroDeJogadores, numeroDeVencedores);
      server.startServer();
      System.out.println("Jogo encerrado");
    }
}
