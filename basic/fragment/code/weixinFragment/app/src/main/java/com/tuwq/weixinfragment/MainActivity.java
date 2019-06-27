package com.tuwq.weixinfragment;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainActivity extends Activity implements OnClickListener {

	private ImageButton ib_weixin;
	private ImageButton ib_contact;
	private ImageButton ib_find;
	private ImageButton ib_me;
	private ContactFragment contactFragment;
	private WeixinFragment weixinFragment;
	private FindFragment findFragment;
	private MeFragment meFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ib_weixin = (ImageButton) findViewById(R.id.ib_weixin);
		ib_contact = (ImageButton) findViewById(R.id.ib_contact);
		ib_find = (ImageButton) findViewById(R.id.ib_discover);
		ib_me = (ImageButton) findViewById(R.id.ib_me);

		ib_contact.setOnClickListener(this);
		ib_find.setOnClickListener(this);
		ib_me.setOnClickListener(this);
		ib_weixin.setOnClickListener(this);
		
		ib_weixin.performClick();
	}

	@Override
	public void onClick(View v) {
		clearIcon();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		switch (v.getId()) {
		case R.id.ib_contact:
			if(contactFragment==null){
				contactFragment = new ContactFragment();
			}
			transaction.replace(R.id.fragment_container, contactFragment);
			ib_contact.setImageResource(R.drawable.contact_list_pressed);
			break;
		case R.id.ib_weixin:
			if(weixinFragment==null){
				weixinFragment = new WeixinFragment();
			}
			transaction.replace(R.id.fragment_container, weixinFragment);
			ib_weixin.setImageResource(R.drawable.weixin_pressed);
			break;
		case R.id.ib_discover:
			if(findFragment == null){
				findFragment = new FindFragment();
			}
			transaction.replace(R.id.fragment_container, findFragment);
			ib_find.setImageResource(R.drawable.find_pressed);
			break;
		case R.id.ib_me:
			if(meFragment==null){
				meFragment = new MeFragment();
			}
			transaction.replace(R.id.fragment_container, meFragment);
			ib_me.setImageResource(R.drawable.profile_pressed);
			break;

		}
		transaction.commit();
	}

	private void clearIcon(){
		ib_contact.setImageResource(R.drawable.contact_list_normal);
		ib_find.setImageResource(R.drawable.find_normal);
		ib_me.setImageResource(R.drawable.profile_normal);
		ib_weixin.setImageResource(R.drawable.weixin_normal);
	}
}
