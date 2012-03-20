package com.priceswagger.apps.pricecompare;
import com.priceswagger.apps.pricecompare.RestJSONClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class PriceSwaggerAppActivity extends Activity {
    /** Called when the activity is first created. */
		private static final String EMPTY_STRING = "";
		

		/* define them widget bitches */
		private EditText searchEditText;
		private TextView searchTypeTextView;
		private Button searchButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

				this.findAllViewsById();
				searchButton.setOnClickListener(new OnClickListener() {

					// on click...
					public void onClick(View v) {
						String query = searchEditText.getText().toString();
						longToast(query);
						RestJSONClient j = new RestJSONClient();
						j.connect("http://192.168.0.10:8420/web/api/test_json/");

						// Uncomment to print the raw json data returned from the request, we make the json obj here
						// Log.v("fk", j.result_data);

						try {
							for (int i=0; i<j.jsonArray.length(); i++) {
								Log.v("fkkkk", j.jsonArray.getJSONObject(i).getString("prod").toString());
							}
						} catch (JSONException e) {
							Log.v("fkkkk", "json exception");
						}
							
					
					}

				});

				searchEditText.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						searchEditText.setText(EMPTY_STRING);
					}
				});

				searchEditText.setOnFocusChangeListener(new DftTextOnFocusListener(getString(R.string.search_phrase)));
				//searchTypeTextView.setText("Searching for " + searchEditText.getText().toString());

    }

		private void findAllViewsById() {
			/* this function is called and sets these variables used by oncreate and other functions */
			searchEditText = (EditText) findViewById(R.id.search_edit_text);
			searchTypeTextView = (TextView) findViewById(R.id.search_type_text_view);
			searchButton = (Button) findViewById(R.id.search_button);
		}

		public void longToast(CharSequence message) {
			Toast.makeText(this, "Searching for " + message + "...", Toast.LENGTH_LONG).show();
		}

		private class DftTextOnFocusListener implements OnFocusChangeListener {
			private String defaultText;

			public DftTextOnFocusListener(String defaultText) {
				this.defaultText = defaultText;
			}
			public void onFocusChange(View v, boolean hasFocus) {
				if (v instanceof EditText) {
					EditText focusedEditText = (EditText) v;
					if (hasFocus) {
						if (focusedEditText.getText().toString().equals(defaultText)) {
							focusedEditText.setText(EMPTY_STRING);
						}
					}
					else {
						if (focusedEditText.getText().toString().equals(EMPTY_STRING)) {
							focusedEditText.setText(defaultText);
						}
					}
				}
			}
		}

}
