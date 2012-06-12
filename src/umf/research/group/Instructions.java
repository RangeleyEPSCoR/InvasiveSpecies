package umf.research.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instructions extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		
		Button start = (Button) findViewById(R.id.btn_ins_start);
		
		start.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				Intent mainscreenIntent = new Intent();
				mainscreenIntent.setClass(getApplicationContext(),pdt.class);
	        	startActivity(mainscreenIntent);
			}
	    });
	}
}