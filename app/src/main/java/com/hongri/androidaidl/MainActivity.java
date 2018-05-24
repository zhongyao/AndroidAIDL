package com.hongri.androidaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * AIDL通信：
 * 参考：
 * http://www.cnblogs.com/BeyondAnyTime/p/3204119.html
 * http://www.binkery.com/archives/489.html
 * <p/>
 * 官方文档提醒何时使用AIDL是必要的：
 * 只有你允许客户端从不同的应用程序为了进程间的通信而去访问你的service，以及想在你的service处理多线程。
 * 如果不需要进行不同应用程序间的并发通信(IPC)，you should create your interface by implementing a Binder；
 * 或者你想进行IPC，但不需要处理多线程的，则implement your interface using a Messenger。
 * @author hongri
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed1, ed2;
    private Button btnCalculate;
    private TextView tv;
    public CalculateInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initService();
    }

    private void initService() {
        /**
         * 这里是显式绑定Service
         */
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.hongri.anotherapp", "com.hongri.anotherapp.CalculateService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        ed1 = (EditText) findViewById(R.id.et1);
        ed2 = (EditText) findViewById(R.id.et2);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        tv = (TextView) findViewById(R.id.tv);

        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalculate:
                double num1 = Double.parseDouble(ed1.getText().toString());
                double num2 = Double.parseDouble(ed2.getText().toString());
                try {

                    String answer = "计算结果：" + mService.doCalculate(num1, num2);
                    tv.setText(answer);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = CalculateInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
}
