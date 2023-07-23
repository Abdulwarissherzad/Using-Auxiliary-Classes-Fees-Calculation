package exerciseFees;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
public class FeesGUIclass extends JFrame implements ActionListener {
                    JTextField name = new JTextField(18);
		    JTextField NoOfCource = new JTextField(10);
		    JTextField feesresult = new JTextField(10);
		    JComboBox<String> faculty  = new JComboBox<String>();
		    JButton result = new JButton("Result");
		    JTextField mealp = new JTextField(15);
                    JTextField warisname = new JTextField(15);
		    
		//    JComboBox< String> meal = new JComboBox<String>();
		    JRadioButton meal1 = new JRadioButton("High meal plan");
		    JRadioButton meal2 = new JRadioButton("Low meal plan");
		    
	public static void main(String[] args) {
	        new FeesGUIclass();
	}
	 public FeesGUIclass(){
		setLayout(new FlowLayout());
		setSize(310,290);
		setLocationRelativeTo(null);
		setTitle("Fees calculation Like teacher Erbuge");
		setVisible(true);
		add(new JLabel("En-Your name :"));
		add(name);
		add(new JLabel("Selec your cource"));
		setcombo();
		add(faculty);
		add(new JLabel("How Many cource :"));
		add(NoOfCource);
		add(result);
	    add(new JLabel("Result is:"));
	    add(feesresult);

	    
	    add(new JLabel("Your meal plan money is :"));
	    add(meal1);
	    add(meal2);
	    add(mealp);
            // For waris Name: 
            add(new Label("Developed By Eng. Waris"));
	    
	    setVisible(true);
	    setResizable(false);
	    meal1.addActionListener(this);
	    meal2.addActionListener(this);
		result.addActionListener(this);
            
            
		//pack();
	}
	 void setcombo() {
		faculty.addItem("Engineering");
		faculty.addItem("Low");
		faculty.addItem("Econemy");
		faculty.addItem("Medecal");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String mealradio = "";
		if(e.getSource() == result) {
			if(meal1.isSelected()) {
				mealradio = "meal1";}
			if(meal2.isSelected()) {
				mealradio = "meal2";}
			businesLogic bu = new businesLogic(name.getText(), faculty.getSelectedItem().toString(),
					Integer.parseInt(NoOfCource.getText()),mealradio);
			
			feesresult.setText(bu.calculat());
			mealp.setText(""+bu.mealPcal());
			
		}
	}

}


/*
 * We can don it for Combobox the same like this

 *businesLogic bu = new businesLogic(name.getText(), faculty.getSelectedItem().toString(),
					Integer.parseInt(NoOfCource.getText()),meal.getSelectedItem().toString());
 
*/