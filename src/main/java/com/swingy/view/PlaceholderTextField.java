package com.swingy.view;

import java.awt.event.*;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PlaceholderTextField extends JTextField {
	String Placeholder;
	
	public PlaceholderTextField(String text, int number) {
		super(text, number);
		Placeholder = text;
		this.addFocusListener(new FocusListener() {
			@Override
		    public void focusGained(FocusEvent e) {
		        if (getText().equals(Placeholder)) {
		            setText("");
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (getText().isEmpty()) {
		            setText(Placeholder);
		        }
		    }
		});
	}
}
