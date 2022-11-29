import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private String gameState = "";
    private boolean jaEscolheu = false;

    public Client(Socket socket, String userName){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = userName;
        } 
        catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String numero = scanner.nextLine().trim();
                if(gameState.isEmpty()){
                    System.out.println("O jogo ainda nao comecou.");
                    continue;
                }
                if(gameState.equals("2OU1") && (!numero.equals("1") || !numero.equals("2"))){
                    System.out.println("Numero invalido para dois ou um!");
                    continue;
                }
                if(jaEscolheu){
                    System.out.println("Voce ja escolheu");
                    continue;
                }
                if(gameState != "ESCOLHER"){
                    bufferedWriter.write(numero);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    jaEscolheu = true;
                    System.out.println("Numero escolhido");
                }
            }
            scanner.close();
        } 
        catch (IOException e) {
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageServer;

                while(socket.isConnected()){
                    try {
                        messageServer = bufferedReader.readLine();
                        if(messageServer.contains("GAMESTATE")){
                            gameState = messageServer.split(":")[1];
                            switch (gameState) {
                                case "2OU1":
                                    System.out.println(gameState);
                                    break;
                                case "PAROUIMPAR":
                                    System.out.println(gameState);
                                    break;
                                case "ESCOLHER":
                                    jaEscolheu = false;
                                    System.out.println(gameState);
                                    break;
                                case "PERDEU":
                                    System.out.println(gameState);
                                    closeEveryThing(socket, bufferedReader, bufferedWriter);
                                    break;
                                case "VENCEU":
                                    System.out.println(gameState);
                                    closeEveryThing(socket, bufferedReader, bufferedWriter);
                                default:
                                    System.out.println("Deu ruim");
                                    break;
                            }
                        }
                        else{
                            System.out.println(messageServer);
                        }
                    } 
                    catch (IOException e) {
                        closeEveryThing(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Iforme seu nome para entrar no jogo: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
        scanner.close();
    }
}