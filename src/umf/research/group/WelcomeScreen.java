package umf.research.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		
	    Button start = (Button) findViewById(R.id.btn_start);
	    Button instructions = (Button) findViewById(R.id.btn_instructions);
	    Button options = (Button) findViewById(R.id.btn_options);
	    
	    //Start Button
	    start.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.setClass(getApplicationContext(),pdt.class);
	        	startActivity(mainscreenIntent);
			}
	    });
	    
	    //Instructions Button
	    instructions.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.setClass(getApplicationContext(),Instructions.class);
	        	startActivity(mainscreenIntent);
			}
	    });	    
	    
	    //Options Button
	    options.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.setClass(getApplicationContext(),Options.class);
	        	startActivity(mainscreenIntent);
			}
	    });	    		
	}
}
