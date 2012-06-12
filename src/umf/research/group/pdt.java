package umf.research.group;	

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class pdt extends Activity {
	
	Entry currentEntry;
	ImageAdapter currentImageAdapter;
	Bitmap finalPicture;
	LocationManager locationManager;
	int badProgrammingCounter; 
	String posish;
	Gallery gallery;
	Button yes;
	Button no;
	TextView tv;
	ImageView iv;
	RelativeLayout rl;
	TextView label;
	File picture;
	
	public static Uri imagePath;
	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	public static final int CAMERA_PIC_REQUEST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.pdt);
	    
	    label = (TextView) findViewById(R.id.label);
	    iv = (ImageView) findViewById(R.id.expandedImage);
	    yes = (Button) findViewById(R.id.btn1_yes);
	    no = (Button) findViewById(R.id.btn2_no);
	    tv = (TextView) findViewById(R.id.question_box);
	    rl = (RelativeLayout) findViewById(R.id.relativeLayoutFull);
	    
	    
	    iv.setVisibility(View.GONE);
	    
	    final Context c = this;
	    
	    final HashMap<String, Entry> map = new HashMap<String, Entry>();
	    populateMap(map);
	    
	    
	    
	    //Gallery
	    currentImageAdapter = new ImageAdapter(this, map.get("catagories").getResIds());
	    gallery = (Gallery)findViewById(R.id.gallery);
	    gallery.setAdapter(currentImageAdapter);
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	label.setVisibility(View.GONE);
	            if (currentEntry.getGameState() == 1) { //If gameState == 1 we want the user to select a category with a single button
	            	label.setVisibility(View.GONE);
	            	String s = Integer.toString(position);
		            currentEntry = map.get(s);
					tv.setText(currentEntry.getQuestionDescription());
					no.setVisibility(View.VISIBLE);
					yes.setVisibility(View.VISIBLE);
					yes.setBackgroundResource(R.layout.green_btn);
					yes.setText("Yes");
					ImageAdapter  stg1 = new ImageAdapter(c, currentEntry.getResIds());
					gallery.setAdapter(stg1);
				}
	            else {
	            	label.setVisibility(View.GONE);
	            	iv.setImageResource(currentEntry.getResIds()[position]);
	            	ExpandGalleryImage();
	            }
	        }
	    });
	    
	    gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				
				if (position == 0) {
					label.setText("FLOATING LEAF PLANTS");
				}
				
				else if (position == 1) {
					label.setText("SUBMERSED PLANTS WITH WHORLS OF SMALL LANCE-SHAPED LEAVES");
					
				}
				
				else if (position == 2) {
					label.setText("SUBMERSED PLANTS WITH PAIRS OR CLUSTERS OF SMALL NARROW LEAVES");
					
				}
				
				else if (position == 3) {
					label.setText("SUBMERSED PLANTS WITH LONG FLAT ALTERNATELY-ARRANGED LEAVES");
					
				}
				
				else if (position == 4) {
					label.setText("SUBMERSED PLANTS WITH FINELY DIVIDED LEAVES");
					
				}
				
				else {
					
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}	
	    });
	    
	    //Set the hidden image OnClickListener to hide all other views when clicked
	    iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MinimizeGalleryImage();
				
			}});
	    

	    //Buttons
	    tv.setText(map.get("catagories").getQuestionDescription());
	    currentEntry = map.get("catagories");
	    yes.setBackgroundResource(R.layout.blue_btn);
	    no.setBackgroundResource(R.layout.red_btn);
	    no.setVisibility(View.GONE);
	    yes.setVisibility(View.GONE);
	    yes.setText("Choose A Catagory");
	    

	    
	    
	    //Create OnClickListeners for "yes" and "no" buttons
	    yes.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {			

				if (currentEntry.getGameState() == 1) { //If gameState == 1 we want the user to select a category with a single button
					currentEntry = map.get("0");
					tv.setText(currentEntry.getQuestionDescription());
					no.setVisibility(View.VISIBLE);
					yes.setBackgroundResource(R.layout.green_btn);
					yes.setText("Yes");
				}
				
				else if (currentEntry.getGameState() == 2) { //When gameState == 2 we want to run through the decision tree for the selected tree
					yes.setVisibility(View.VISIBLE);
					yes.setText("Yes");
					yes.setBackgroundResource(R.layout.green_btn);
					no.setVisibility(View.VISIBLE);
					currentEntry = map.get(currentEntry.getYesNext());
					ImageAdapter yesbtn = new ImageAdapter(c, currentEntry.getResIds());
					tv.setText(currentEntry.getQuestionDescription());
					gallery.setAdapter(yesbtn);
					if (currentEntry.getYesNext().equals("")) {
						no.setVisibility(View.GONE);
						yes.setBackgroundResource(R.layout.btn_yellow);
						yes.setText("Take and Send a Picture!");
						tv.setText(currentEntry.getQuestionDescription());
					}
				}
			
				else {
					locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
					//if (locationManager.isProviderEnabled(LOCATION_SERVICE) == true) {
						//Toast.makeText(getApplicationContext(), "Please turn GPS on to use this program.", Toast.LENGTH_SHORT).show();	
					//}
					
					
					
					if (CheckGPSState() == true) {
						//TAKEPIC
						
					}
					
					else {
							
					}
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
				}
			}
	    });
	    	
	    no.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				currentEntry = map.get(currentEntry.getNoNext());	
				//currentImageAdapter.changeCollection(currentEntry.getResID());
				ImageAdapter nobtn = new ImageAdapter(c, currentEntry.getResIds());
				tv.setText(currentEntry.getQuestionDescription());
				gallery.setAdapter(nobtn);
				if (currentEntry.getNoNext().equals("")) {
					no.setVisibility(View.GONE);
					yes.setBackgroundResource(R.layout.btn_yellow);
					yes.setText("Take and Send a Picture!");
					tv.setText(currentEntry.getQuestionDescription());
				}
			}
	    });
	}
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{                   
	    super.onActivityResult(requestCode, resultCode, intent);
	    Log.i("in OnActivityResult", "Activity Result");
	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		//String latLong = loc.toString();
	    emailIntent.setType("image/jpg");
	    emailIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
	    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"chrisjamesbond@gmail.com"});
	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invasive Species Android App");
	    emailIntent.putExtra(Intent.EXTRA_TEXT, "This is a message from the Android Invasive Species Application.  An invasive species may have been identified, please see attachment.  The GPS location is UNKNOWN"); 
	    emailIntent.putExtra(Intent.EXTRA_TITLE, "Emails?!");
		try { 
			pdt.this.startActivity(Intent.createChooser(emailIntent, "Send mail...")); 
	    } 		
		catch (android.content.ActivityNotFoundException ex) { 	        
	    }
	    switch (requestCode)
	    {
	        case 2:             
	            Log.i("in OnActivityResult", "Activity Resut 2");                
	            break;
	    }
	}

	private boolean CheckGPSState(){
			String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if (!provider.equals("")) {
				//GPS Enabled
				Toast.makeText(pdt.this, "GPS Enabled: " + provider, Toast.LENGTH_SHORT).show();
				try {
					Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Toast.makeText(getApplicationContext(), "GPS: " + loc.toString(), Toast.LENGTH_SHORT).show();
					
				}
				catch (Exception e){
					Toast.makeText(getApplicationContext(), "Cannot obtain GPS location", Toast.LENGTH_SHORT).show();
					
				}
				return true;
			}
			else {
				
				//Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
				//startActivity(intent);
				return false;
			}
	}
	
	public void ExpandGalleryImage() {
		gallery.setVisibility(View.GONE);
		yes.setVisibility(View.GONE);
		no.setVisibility(View.GONE);
		tv.setVisibility(View.GONE);
		iv.setVisibility(View.VISIBLE);
	}
	
	public void MinimizeGalleryImage() {
		gallery.setVisibility(View.VISIBLE);
		yes.setVisibility(View.VISIBLE);
		no.setVisibility(View.VISIBLE);
		tv.setVisibility(View.VISIBLE);
		iv.setVisibility(View.GONE);
	}
	
	public int getGalleryHeight() {
	    int galleryHeight = (int) (getGalleryWidth() * 1.7); 
	    if (galleryHeight > rl.getHeight() -(tv.getHeight() + yes.getHeight() + 4)) {
	    	galleryHeight = rl.getHeight() -(tv.getHeight() + yes.getHeight() + 4);
	    }
	    return galleryHeight;
	}
	
	public int getGalleryWidth() {
	    int galleryWidth = (int) (rl.getWidth()/1.5);
	    return galleryWidth;
	}
	
	public class ImageAdapter extends BaseAdapter{
	    int mGalleryItemBackground;
	    private Context mContext;

	    private Integer[] ImageIds;
		

	    public ImageAdapter(Context c, Integer[] mImageIds) {
	    	ImageIds = mImageIds;
	        mContext = c;
	        TypedArray attrs = mContext.obtainStyledAttributes(R.styleable.GalleryAttrs);
	        mGalleryItemBackground = attrs.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 3);
	        attrs.recycle();
	        
	        
	    }

	    public int getCount() {
	        return ImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    //This getView class uses getGalleryWidth and getGalleryHeight to determine the size of the gallery based off of the current screen size
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);

	        imageView.setImageResource(ImageIds[position]);
	        imageView.setLayoutParams(new Gallery.LayoutParams(getGalleryWidth(), getGalleryHeight() - 100));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }
	    public void changeCollection(Integer [] i) {
	    	ImageIds = i;
	    }
	    

	}
	
	public void populateMap(HashMap<String, Entry> map){
		
		map.put("catagories", new Entry("Select the catagory that best represents the specimen in your possession", new Integer [] {R.drawable.european_frog_bit_1,  R.drawable.hydrilla_1, R.drawable.european_naiad_1, R.drawable.curly_pond_weed_2, R.drawable.eurasian_watermilfoil_1}, "europeanFrogbitQ", "", 1));
	    map.put("undecided", new Entry("We are unable to determine the species of your plant.  It is still highly reccomended that you take a picture of the specimen and send it to the Maine Center for Invasive Aquatic Plants.", new Integer [] {}, "", "", 3));
	    
	    // Category 1: Floating Leaf Plants
	    map.put("0", new Entry("Is the plant attached to the sediment by a stem?", new Integer [] {R.drawable.european_frog_bit_1, R.drawable.european_frog_bit_2, R.drawable.european_frog_bit_3, R.drawable.european_frog_bit_4, R.drawable.european_frog_bit_5}, "waterChestnutQ", "europeanFrogbitA", 2));
	    map.put("europeanFrogbitA", new Entry("Suspect European Frogbit.", new Integer [] {R.drawable.european_frog_bit_1, R.drawable.european_frog_bit_2, R.drawable.european_frog_bit_3, R.drawable.european_frog_bit_4, R.drawable.european_frog_bit_5}, "", "", 3));
	    map.put("waterChestnutQ", new Entry("Does the floating part of the plant consist of a rosette of conspicuously toothed triangular shaped leaves?", new Integer [] {R.drawable.water_chestnut_1, R.drawable.water_chestnut_2, R.drawable.water_chestnut_3, R.drawable.water_chestnut_4, R.drawable.water_chestnut_5}, "waterChestnutA", "yellowFloatingHeartQ", 2));
	    map.put("waterChestnutA", new Entry("Suspect Water Chestnut.", new Integer [] {R.drawable.water_chestnut_1, R.drawable.water_chestnut_2, R.drawable.water_chestnut_3, R.drawable.water_chestnut_4, R.drawable.water_chestnut_5}, "", "", 3));
	    map.put("yellowFloatingHeartQ", new Entry("Is the leaf heart shaped, and notched almost to the center?", new Integer [] {R.drawable.yellow_floating_heart_1, R.drawable.yellow_floating_heart_2, R.drawable.yellow_floating_heart_3, R.drawable.yellow_floating_heart_4, R.drawable.yellow_floating_heart_5}, "yellowFloatingHeartA", "undecided", 2));
	    map.put("yellowFloatingHeartA", new Entry("Suspect Yellow Floating Heart", new Integer [] {R.drawable.yellow_floating_heart_1, R.drawable.yellow_floating_heart_2, R.drawable.yellow_floating_heart_3, R.drawable.yellow_floating_heart_4, R.drawable.yellow_floating_heart_5}, "", "", 3));
	    
	    // Category 2: 2. SUBMERSED PLANTS WITH WHORLS OF SMALL LANCE-SHAPED LEAVES (APPROX 1” OR LESS)
	    map.put("1", new Entry("Snip the stems several times at intervals along the stem. Count the number of leaves per whorl. Are there consistently three leaves per whorl?", new Integer [] {}, "undecided", "brazilianElodeaHydrillaQ", 2));
	    map.put("brazilianElodeaHydrillaQ", new Entry("Are there generally four leaves or more per whorl, and are the leaves finely but conspicuously toothed? (Can you see the serrations without magnification?)", new Integer [] {R.drawable.elodea_1, R.drawable.elodea_2, R.drawable.hydrilla_1, R.drawable.hydrilla_2, R.drawable.hydrilla_3}, "hydrilla", "brazilianElodea", 2));
	    map.put("brazilianElodea", new Entry("Suspect Brazilian Elodea", new Integer [] {R.drawable.elodea_1, R.drawable.elodea_2}, "", "", 3));
	    map.put("hydrilla", new Entry("Suspect Hydrilla", new Integer [] {R.drawable.hydrilla_1, R.drawable.hydrilla_2, R.drawable.hydrilla_3}, "", "", 3));
	    
	    // Category 3: SUBMERSED PLANTS WITH PAIRS OR CLUSTERS OF SMALL NARROW LEAVES
	    map.put("2", new Entry("Are the leaves finely but conspicuously serrated or “toothed”? (Can you see the serrations easily with, and sometimes without, a hand lens?)", new Integer [] {}, "europeanNaiadQ", "undecided", 2));
	    map.put("europeanNaiadQ", new Entry("Please pull a leaf away from the stem. Are the leaf bases serrated and bulging out in a blocky way (as opposed to gently flaring out)?", new Integer [] {}, "europeanNaiadA", "undecided", 2));
	    map.put("europeanNaiadA", new Entry("Suspect European Naiad", new Integer [] {}, "", "", 3));
	    
	    //Category 4: SUBMERSED PLANTS WITH LONG FLAT ALTERNATELY-ARRANGED LEAVES
	    map.put("3", new Entry("Is there more than one leaf type associated with this plant?", new Integer [] {}, "undecided", "curlyLeafPondweedQ", 2));
	    map.put("curlyLeafPondweedQ", new Entry("Are the leaves finely but conspicuously serrated and distinctly wavy (like a lasagna noodle) in appearance?", new Integer [] {R.drawable.curly_pond_weed_1, R.drawable.curly_pond_weed_2, R.drawable.curly_pond_weed_4}, "curlyLeafPondweedA", "undecided", 2));
	    map.put("curlyLeafPondweedA", new Entry("Suspect Curly Pondweed", new Integer [] {R.drawable.curly_pond_weed_1, R.drawable.curly_pond_weed_2, R.drawable.curly_pond_weed_3, R.drawable.curly_pond_weed_4, R.drawable.curly_pond_weed_5}, "", "", 3));
	    
	    //Category 5: SUBMERSED PLANTS WITH FINELY DIVIDED LEAVES
	    map.put("4", new Entry("Are the leaves fork or branch divided (as opposed to feather divided?)", new Integer [] {R.drawable.forked_leaves, R.drawable.feathered_leaves, R.drawable.branched_leaves}, "undecided", "fanwortQ", 2));
	    map.put("fanwortQ", new Entry("Are the branched leaves oppositely arranged and held to the stem by long slender leaf stems?", new Integer [] {}, "fanwortA", "parrotMilfoilQ", 2));
	    map.put("fanwortA", new Entry("Suspect Fanwort", new Integer [] {}, "", "", 3));
	    map.put("parrotMilfoilQ", new Entry("Are the leaves feather divided?", new Integer [] {R.drawable.feathered_leaves, R.drawable.eurasian_watermilfoil_1, R.drawable.eurasian_watermilfoil_2, R.drawable.parrot_feather_1, R.drawable.parrot_feather_2}, "parrotMilfoilA", "undecided", 2));
	    map.put("parrotMilfoilA", new Entry("Suspect one of the three invasive milfoils on the watch list: Eurasian Watermilfoil,Variable Watermilfoil or Parrot Feather", new Integer [] {R.drawable.eurasian_watermilfoil_1, R.drawable.eurasian_watermilfoil_2, R.drawable.parrot_feather_1, R.drawable.parrot_feather_2}, "", "", 3));
	}

  private Uri getImageUri() {
    	Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("-mm-ss");
        
        String cameraCapture = "invasivespeciessubmission-" + df.format(date)+ ".jpg";
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", cameraCapture);
        
        Uri imgUri = Uri.fromFile(file);
        imagePath = imgUri;
        return imgUri;
    }

}