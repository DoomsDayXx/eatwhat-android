package com.what2e.eatwhat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.what2e.eatwhat.base.BaseActivity;
import com.what2e.eatwhat.bean.User;
import com.what2e.eatwhat.util.GetUserData;
import com.what2e.eatwhat.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 用户个人信息页面
 */
public class UserInformationActivity extends BaseActivity implements View.OnClickListener {
    private TextView toolbarText;
    private CircleImageView user_img;
    private TextView user_id, user_nickname, user_name, user_sex, user_phoneNumber, user_email, user_address;
    private Integer userId;
    private String phoneNumber;
    private String address;
    private String name;
    private String email;
    private String nickname;//昵称
    private String sex;
    private String avatar;//头像
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        getUserIntentData();
//        getUserData();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的资料");
        toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("修改信息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        user_img = (CircleImageView) findViewById(R.id.user_img);
        user_id = (TextView) findViewById(R.id.user_id);
        user_nickname = (TextView) findViewById(R.id.user_nickname);
        user_name = (TextView) findViewById(R.id.user_name);
        user_sex = (TextView) findViewById(R.id.user_sex);
        user_phoneNumber = (TextView) findViewById(R.id.user_phoneNumber);
        user_email = (TextView) findViewById(R.id.user_email);
        user_address = (TextView) findViewById(R.id.user_address);

        user_id.setText(userId);
        user_nickname.setText(nickname);
        user_name.setText(name);
        user_sex.setText(sex);
        user_phoneNumber.setText(phoneNumber);
        user_email.setText(email);
        user_address.setText(address);

        toolbarText.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("nickname", nickname);
                intent.putExtra("statusCode", 200);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    public static void actionStart(Context context, String id, String name, String email, String phoneNumber, String address, String nickname, String sex, String avatar) {
        Intent intent = new Intent(context, UserInformationActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("address", address);
        intent.putExtra("nickname", nickname);
        intent.putExtra("sex", sex);
        intent.putExtra("avatar", avatar);
        context.startActivity(intent);
    }

    /**
     * 获取从主界面传过来的用户数据
     */
    private void getUserIntentData() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        phoneNumber = intent.getStringExtra("phoneNumber");
        sex = intent.getStringExtra("sex");
    }

    /**
     * 获取用户数据（从本地存储）
     */
    private void getUserData() {
        GetUserData data = new GetUserData();
        user = data.getUser(UserInformationActivity.this);
        userId = user.getuId();
        name = user.getuName();
        sex = user.getSex();
        phoneNumber = user.getPhonenumber();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    name = intent.getStringExtra("name");
                    email = intent.getStringExtra("email");
                    nickname = intent.getStringExtra("nickname");
                    sex = intent.getStringExtra("sex");
                    address = intent.getStringExtra("address");
                    user_nickname.setText(nickname);
                    user_name.setText(name);
                    user_sex.setText(sex);
                    user_email.setText(email);
                    user_address.setText(address);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("nickname", nickname);
            intent.putExtra("statusCode", 200);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_text:
                if (!Util.checkNetwork(this)) {
                    break;
                } else {
                    Intent intent2 = new Intent(UserInformationActivity.this, ChangeUserInfoActivity.class);
                    startActivityForResult(intent2, 1);
                }
                break;
            default:
                break;
        }
    }
}
