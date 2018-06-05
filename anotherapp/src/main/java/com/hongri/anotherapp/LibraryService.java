package com.hongri.anotherapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.hongri.androidaidl.Book;
import com.hongri.androidaidl.LibraryInterface;
import com.hongri.androidaidl.LibraryInterface.Stub;

/**
 * @author zhongyao
 * @date 2018/6/5
 */

public class LibraryService extends Service {

    private List<Book> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    LibraryInterface.Stub mBinder = new Stub() {
        @Override
        public void addBooks(Book book) throws RemoteException {
            list.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return list;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
