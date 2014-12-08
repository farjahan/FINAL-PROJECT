package sjsu.cs175.final_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.os.AsyncTask;

public class SocketConnector extends AsyncTask<String, Integer, String> {
	public static AsyncResponse handler = null;

	@Override
	protected String doInBackground(String... params) {
		Socket socket = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		String output = "";

		try {
			socket = new Socket(Scores.ip, Scores.port);

			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			writer.write(params[0]);
			writer.newLine();
			writer.flush();

			output = "socket trying";
			while (true) {

				if ((output = reader.readLine()) != null) {

					return output;
				}
			}
		} catch (Exception e) {
			output = "error";
			e.printStackTrace();
			return e.getMessage();

		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}