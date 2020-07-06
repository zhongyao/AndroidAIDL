package com.hongri.androidaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
 *
 * @author hongri
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed1, ed2;
    private Button btnCalculate;
    private TextView tv;
    public CalculateInterface mService;

    /**
     * Binder在运行在服务端中，如果服务端进程由于某种原因异常终止，这个时候Binder死亡，会导致我们的调用失败
     * 使用linkToDeath unlinkToDeath 实现恢复连接的功能。
     */
    //private IBinder.DeathRecipient mDeathRecipient = new DeathRecipient() {
    //    @Override
    //    public void binderDied() {
    //        if (mService == null) {
    //            return;
    //        }
    //        mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
    //        mService = null;
    //
    //        /**
    //         * 重新绑定远程服务
    //         */
    //        initService();
    //    }
    //};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

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
        ed1 = findViewById(R.id.et1);
        ed2 = findViewById(R.id.et2);
        btnCalculate = findViewById(R.id.btnCalculate);
        tv = findViewById(R.id.tv);

        findViewById(R.id.btnJump).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LibraryActivity.class);
            startActivity(intent);

        });

        findViewById(R.id.btnBinderPool).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, BinderPoolActivity.class);
            startActivity(intent);
        });

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
        public void onServiceConnected(ComponentName name, IBinder binder) {
            try {
                mService = CalculateInterface.Stub.asInterface(binder);
                //客户端绑定远程服务成功后，给binder设置死亡代理
                //binder.linkToDeath(mDeathRecipient, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (conn != null) {
            unbindService(conn);
        }
    }
}
