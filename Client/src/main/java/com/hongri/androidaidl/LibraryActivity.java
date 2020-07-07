package com.hongri.androidaidl;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

import com.hongri.androidaidl.util.Logger;

/**
 * @author hongri
 */
public class LibraryActivity extends AppCompatActivity {

    private LibraryInterface mService;
    private List<Book> list;


    public NewBooksArrivedListener listener = new NewBooksArrivedListener.Stub() {
        @Override
        public void NewBooksArrived(Book book) throws RemoteException {
            Logger.D("NewBooksArrived:" + "book:" + book.getName() + " pages:" + book.getPages());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            try {
                mService.registerBooksArrivedListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            try {
                Book book = new Book();
                book.setName("life");
                book.setPages("234");
                mService.addBooks(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.btnGet).setOnClickListener(v -> {
            try {
                list = mService.getBooks();
                for (int i = 0; i < list.size(); i++) {
                    Logger.D("name:" + list.get(i).getName() + "age:" + list.get(i).getPages() + "\n");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.hongri.anotherapp", "com.hongri.anotherapp.LibraryService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 用户将服务端的Binder对象转换为客户端所需的AIDL接口类型的对象
             */
            mService = LibraryInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

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
