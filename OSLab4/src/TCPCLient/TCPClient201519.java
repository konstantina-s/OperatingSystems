package TCPCLient;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient201519 extends  Thread{

    private final int serverPort;
    private final String ip;

    public TCPClient201519(int serverPort, String ip) {
        this.serverPort = serverPort;
        this.ip=ip;
    }

    @Override
    public void run(){
        try{
            Socket socket = new Socket(ip, serverPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            Thread sender = new Thread(new Runnable() {
                String message;

                @Override
                public void run() {
                    while(true){
                        message = scanner.nextLine();
                        writer.println(message);
                        writer.flush();
                    }
                }
            });
            sender.start();

            Thread receiver = new Thread(new Runnable() {
                String message;

                @Override
                public void run() {
                    try {
                        message= reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while(message!=null){
                        System.out.println(message);
                        try {
                            message = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    writer.close();
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();

         } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPClient201519 client = new TCPClient201519(9753, "194.149.135.49");
        client.start();
    }
}