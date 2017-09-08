import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Main { 

	private static GUI myGUI;
	private static String filename;

	public static void main(String[] args) {

		// Prompt for File Name to Import
		JOptionPane pane = new JOptionPane();
		filename = pane.showInputDialog("Type a file name to import:");

		Scanner s;
		ArrayList tasks = new ArrayList();
		ArrayList times = new ArrayList(); // times of 0 means task has not been
											// completed.

		try {
			// Input File assuming access to Path name and File Exists
			s = new Scanner(new File("C:/EclipseWorkspaces/csse371/Homework 1/" + filename));

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] details = line.split("\\|");
				String task = details[0];
				String time = details[1];

				tasks.add(task);
				times.add(time);
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(tasks.toString());
		System.out.println(times.toString());

		new Main(tasks, times);

	}

	public Main(ArrayList tasks, ArrayList times) {

		myGUI = new GUI(tasks, times, filename);

		myGUI.exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportList();
			}
		});

		myGUI.dropdown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox) e.getSource();
				String task = (String) cb.getSelectedItem();
				double start = System.currentTimeMillis();
				myGUI.addTask(task, cb.getSelectedIndex(), start);

			}
		});
	}

	public void exportList() {
		File file = new File(filename);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < myGUI.myTasks.size(); i++) {
				String line = myGUI.myTasks.get(i) + "| " + myGUI.myTimes.get(i);
				writer.write(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace(); // I'd rather declare method with throws
									// IOException and omit this catch.
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException ignore) {
				}
		}
		System.out.printf("File is located at %s%n", file.getAbsolutePath());

	}

}
