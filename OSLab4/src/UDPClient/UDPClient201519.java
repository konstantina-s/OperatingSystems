package UDPClient;

import java.io.IOException;
import java.net.*;

public class UDPClient201519 extends Thread{
    private final int serverPort;
    private DatagramSocket socket;
    private InetAddress address;
    private final String message;
    private byte [] buf;

    public UDPClient201519(int serverPort, String message){
        this.serverPort = serverPort;
        this.message = message;

        try{
            this.socket = new DatagramSocket(9753 );
            this.address = InetAddress.getByAddress(new byte[]{(byte) 194, (byte) 149, (byte) 135, 49});

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        buf = message.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, this.address, this.serverPort);
        try{
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length, address, serverPort);
            socket.receive(packet);
            System.out.println(new String(packet.getData(), 0, packet.getLength()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UDPClient201519 client = new UDPClient201519(9753, "201519");
        client.start();
    }
}

