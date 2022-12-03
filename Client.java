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
            while(!socket.isClosed()){
                int numero = 1;
                try {
                    String txt = scanner.nextLine();
                    if(socket.isClosed()){
                        break;
                    }
                    if(gameState.isEmpty()){
                        System.out.println("O jogo ainda nao comecou.");
                        continue;
                    }
                    if(jaEscolheu){
                        System.out.println("Você ja escolheu");
                        continue;
                    }
                    numero = Integer.parseInt(txt);
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido!");
                    continue;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                if(gameState.contains("2OU1") && (numero != 1 && numero != 2)){
                    System.out.println("Número inválido para dois ou um!");
                }
                else if(gameState.contains("ESCOLHER")){
                    bufferedWriter.write(String.valueOf(numero));
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    jaEscolheu = true;
                    System.out.println("Número escolhido");
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

                while(!socket.isClosed()){
                    try {
                        messageServer = bufferedReader.readLine();
                        if(messageServer.contains("GAMESTATE")){
                            gameState = messageServer.split(":")[1];
                            switch (gameState) {
                                case "2OU1":
                                    System.out.println("Dois ou um");
                                    break;
                                case "PAROUIMPAR":
                                    System.out.println("Par ou ímpar");
                                    break;
                                case "ESCOLHER-PAROUIMPAR":
                                case "ESCOLHER-2OU1":
                                    jaEscolheu = false;
                                    System.out.println("Escolha um número");
                                    break;
                                case "PERDEU":
                                    System.out.println("Você perdeu!");
                                    closeEveryThing(socket, bufferedReader, bufferedWriter);
                                    break;
                                case "VENCEU":
                                    System.out.println("Parabéns!! Você venceu!!");
                                    closeEveryThing(socket, bufferedReader, bufferedWriter);
                                    break;
                                default:
                                    System.out.println("Algo inesperado aconteceu");
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
        System.out.println("Esperando jogo começar");
        client.listenForMessage();
        client.sendMessage();
        scanner.close();
    }
}
