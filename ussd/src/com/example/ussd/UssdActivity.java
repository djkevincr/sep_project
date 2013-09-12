package com.example.ussd;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class UssdActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private TextView view;
	private AutoCompleteTextView number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		this.view = (TextView) findViewById(R.id.Text2);
		this.number = (AutoCompleteTextView) findViewById(R.id.Text1);
	}

	@Override
	public void onClick(View arg0) {
		String encodedHash = Uri.encode("#");
		call("*" + number.getText() + encodedHash);
		this.view.setText("");
	}

	protected void call(String phoneNumber) {
		try {
			startActivityForResult(
					new Intent("android.intent.action.CALL", Uri.parse("tel:"
							+ phoneNumber)), 1);
		} catch (Exception eExcept) {
			this.view.append("\n\n " + "\n" + eExcept.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		USSD ussd = new USSD(4000, 4000);
		if (ussd.IsFound())
			this.view.append("\n" + ussd.getMsg());
		else
			this.view.append("" + R.string.error_ussd_msg);
	}

}