import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {

	JFrame frame;
	JPanel myPanel;
	JComboBox<String> dropdown;
	JButton exportButton;
	ArrayList myTasks;
	ArrayList myTimes;

	public GUI(ArrayList tasks, ArrayList times, String filename) {
		myTasks = tasks;
		myTimes = times;

		// Set Frame
		frame = new JFrame("TODO List");
		frame.setSize(400, 500);
		frame.setLocation(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myPanel = new JPanel();
		BoxLayout layout = new BoxLayout(myPanel, BoxLayout.Y_AXIS);
		myPanel.setLayout(layout);

		exportButton = new JButton("Export List");
		exportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		myPanel.add(exportButton);

		JLabel listTitle = new JLabel(filename);
		listTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		myPanel.add(listTitle);

		addDropdown();

		frame.add(myPanel);
		frame.setVisible(true);

	}

	public void addTask(String task, int i, double starttime) {
		JCheckBox check = new JCheckBox(task);
		dropdown.removeItemAt(i);
		myPanel.add(check);

		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check.isSelected()) {
					double end = System.currentTimeMillis();
					double time = (end - starttime) / 1000;
					myPanel.remove(check);
					myTimes.set(i, time);
					JLabel completed = new JLabel(task + "  |  " + time + " secs");
					myPanel.add(completed);
					frame.validate();
					frame.repaint();
				}
			}
		});

	}

	public void addDropdown() {
		dropdown = new JComboBox<String>();
		dropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
		myPanel.add(dropdown);

		for (int i = 0; i < myTasks.size(); i++) {
			String time =  (String) myTimes.get(i);
			String task =  (String) myTasks.get(i);
			if (Double.parseDouble(time) == 0) {
				dropdown.addItem(task);
			} else {
				JLabel completed = new JLabel(task + "  |  " + time + " secs");
				myPanel.add(completed);

			}

		}
		dropdown.setMaximumSize(dropdown.getPreferredSize());

	}

}
