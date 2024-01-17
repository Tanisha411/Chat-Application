package src;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class ChatClient {
	
	//the outer container on which we add our components
	static JFrame chatWindow = new JFrame("Chat Application");
	
	//the conversation is displayed in the chatarea
	static JTextArea chatArea  = new JTextArea(22, 40); //22 rows, 40 columns
	
	//you type the message in the textField and then click on 'send'
	static JTextField textField = new JTextField(40); //40 columns
	static JLabel blankLabel = new JLabel("			");
	static JButton sendButton = new JButton("Send");
	
	static BufferedReader in;
	static PrintWriter out;
	static JLabel nameLabel = new JLabel("		");
	
	
	ChatClient()
	{
		
		chatWindow.setLayout(new FlowLayout());
		
		chatWindow.add(nameLabel);
		chatWindow.add(new JScrollPane(chatArea));
		chatWindow.add(blankLabel);
		chatWindow.add(textField);
		chatWindow.add(sendButton);
		
		//the application closes when the user clicks on the 'close' button
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		chatWindow.setSize(475, 500);
		chatWindow.setVisible(true); //displays it into the screen
		
		textField.setEditable(false);
		chatArea.setEditable(false);
		
		//binds the listener with the 'send' button
		sendButton.addActionListener(new Listener());
		
		//binds the listener with the pressing of the 'enter' key
		textField.addActionListener(new Listener());
		
		//invoking the listener on pressing 'send' and also on pressing the 'enter' key on the keyboard
		
	}
	
	
	void startChat() throws Exception
	{
		//user enters the ip address of the server
		String ipAddress = JOptionPane.showInputDialog(
				chatWindow,
				"Enter ip address: ",
				"IP Address Required!", //title bar of the dialogue box
				JOptionPane.PLAIN_MESSAGE);
		
		Socket soc = new Socket(ipAddress, 9806);
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		out = new PrintWriter(soc.getOutputStream(), true);
		
		while(true)
		{
			//captures the message sent by the server
			String str = in.readLine();
			
			if(str.equals("Name required"))
			{
				String name = JOptionPane.showInputDialog(
						chatWindow,
						"Enter a unique name: ",
						"Name Required!",
						JOptionPane.PLAIN_MESSAGE);
				
				out.println(name);
				
			}
			else if(str.equals("Name already exists"))
			{
				String name = JOptionPane.showInputDialog(
						chatWindow,
						"Enter another name: ",
						"Name already exists!",
						JOptionPane.WARNING_MESSAGE);
				
				out.println(name);
			}
			else if(str.startsWith("Name accepted"))
			{
				textField.setEditable(true);
				nameLabel.setText("You are logged in as: " + str.substring(13));
			}
			else
			{
				//anything besides the above statements received from the server would be treated as a
				//message from the other clients
				chatArea.append(str + "\n");		
			}
			
		}
		
	}
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ChatClient client = new ChatClient();
		client.startChat();
		
	} 
	
}

// this class tells the client what to do when the user presses the 'send' button
//action: pressing the 'send' button
class Listener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		//sends the typed message to the server
		ChatClient.out.println(ChatClient.textField.getText());
		
		//sets the text field to blank since the user has already typed the message and clicked on 'send'
		ChatClient.textField.setText("");
	}
}




