/**
 * Created by Joshua on 5/08/2014.
 */
import java.io.*;
import java.net.*;

//   129.94.175.243   port:2014
public class TCPClient
{
    public static void main(String argv[]) throws Exception
    {
        String receive;
        String input[] = new String[8];
        int flag = 0;
        Socket clientSocket = new Socket("129.94.175.243",2014);

        clientSocket.setSoTimeout(1000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        for (int i = 1; i <= 7; i++) {
            try {
                if (i == 1) {
                    outToServer.writeBytes("z3449803" + '\n');
                }
                receive = inFromServer.readLine();
                String sp[] = receive.split(";");
                System.out.println(sp[0] + " : " + sp[1]);
                input[Integer.parseInt(sp[0])] = sp[1];
                flag += Integer.parseInt(sp[0]);
                System.out.println(sp[0] + " is received" + '\n');

            }
            catch (SocketTimeoutException e){
                System.out.println("lost something");
            }
        }

        try {
            String lostN = (28 - flag) + "";
            System.out.println(lostN + " is missing, request" + '\n');
            outToServer.writeBytes(lostN + '\n');

            receive = inFromServer.readLine();
            String sp[] = receive.split(";");
            input[Integer.parseInt(sp[0])] = sp[1];

            outToServer.writeBytes(input[1] + input[2] + input[3] + input[4] + input[5] + input[6] + input[7] + '\n');
            System.out.println(input[1] + input[2] + input[3] + input[4] + input[5] + input[6] + input[7] + " is sent back");
            clientSocket.close();
        }
        catch (SocketTimeoutException e){
            System.out.println("lost error?!");
        }
    }
}
