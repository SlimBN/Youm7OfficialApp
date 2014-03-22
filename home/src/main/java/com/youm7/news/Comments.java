package com.youm7.news;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Comments extends DialogFragment {
	TelephonyManager teleManager;
	String network;
	 final String codeVoda = "60202";
	 final String codeMobinil = "60201";
	 final String codeEtisalat = "60203";
	 final String numVoda = "9999";
	 final String numMobinil = "95000";
	 final String numEtisalat = "1666";
	 final String msgVoda = "y71";
	 final String msgMobinil = "";
	 final String msgEtisalat = "73";
	String smsNum;
	String smsBody;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL,
				android.R.style.Theme_DeviceDefault_Dialog);
		
		teleManager = (TelephonyManager) getActivity().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		
		
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RelativeLayout commholder = (RelativeLayout) inflater.inflate(
				R.layout.smsdialog_layout, container, true);
		network = teleManager.getNetworkOperator();
		((Button) commholder.findViewById(R.id.btn_sms_confirm))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (network.equalsIgnoreCase(codeVoda) ) {
							smsNum = numVoda;
							smsBody = msgVoda;
							sendSms(smsNum, smsBody, false);
							Toast.makeText(
									getActivity().getApplicationContext(),
									"لقد تم الاشتراك بنجاح", Toast.LENGTH_SHORT)
									.show();
						} else if (network.equalsIgnoreCase(codeMobinil)  ) {
							smsNum = numMobinil;
							smsBody = msgMobinil;
							sendSms(smsNum, smsBody, false);
							Toast.makeText(
									getActivity().getApplicationContext(),
									"لقد تم الاشتراك بنجاح", Toast.LENGTH_SHORT)
									.show();
						} else if (network.equalsIgnoreCase(codeEtisalat) ) {
							smsNum = numEtisalat;
							smsBody = msgEtisalat;
							sendSms(smsNum, smsBody, false);
							Toast.makeText(
									getActivity().getApplicationContext(),
									"لقد تم الاشتراك بنجاح", Toast.LENGTH_LONG)
									.show();
							dismiss();
						} else {
							dismiss();
							Toast.makeText(
									getActivity().getApplicationContext(),
									"ع�?وا هذه الخدمة غير متو�?رة لشبكتك",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		((Button) commholder.findViewById(R.id.btn_sms_decline))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismiss();
						
					}
				});

		return commholder;
	}

	private void sendSms(String phonenumber, String message, boolean isBinary) {
		SmsManager manager = SmsManager.getDefault();

		manager.sendTextMessage(smsNum, null, smsBody, null, null);

	}
}
