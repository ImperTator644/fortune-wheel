package back;

import back.handlers.players.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PlayerMain implements MainInterface{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        try {
            socket = new Socket(PROXY, PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Error creating a socket for client " + e.getMessage());
        }
        Player player = new Player(socket, setUserName(scanner), scanner);
    }

    private static String setUserName(Scanner scanner) {
        System.out.println("Podaj imie: ");
        return scanner.nextLine();
    }
}
