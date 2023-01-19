package back.handlers;

import java.io.*;
import java.net.Socket;

public abstract class Handler {
    protected final Socket socket;
    protected BufferedReader reader;

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    protected BufferedWriter writer;

    public Handler(Socket socket){
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ioException) {
            System.out.println("Error getting reader and writer " + ioException.getMessage());
            closeEverything();
        }
    }

    protected void closeEverything() {
        try {
            if (this.reader != null) reader.close();
            if (this.writer != null) writer.close();
            if (this.socket != null) socket.close();
        } catch (IOException ioException) {
            System.out.println("Error closing everything in client handler " + ioException.getMessage());
        }
    }
}
