package com.hongri.anotherapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.hongri.androidaidl.Book;
import com.hongri.androidaidl.LibraryInterface;
import com.hongri.androidaidl.LibraryInterface.Stub;
import com.hongri.androidaidl.NewBooksArrivedListener;
import com.hongri.anotherapp.util.Logger;

/**
 * @author zhongyao
 * @date 2018/6/5
 */

public class LibraryService extends Service {

    private CopyOnWriteArrayList<NewBooksArrivedListener> listenerList = new CopyOnWriteArrayList<>();
    private List<Book> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new ServiceWorker()).start();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            try {
                int times = 1;
                while (times < 10) {
                    Book book = new Book();
                    book.setName("life" + times);
                    book.setPages("###" + times);
                    Thread.sleep(5000);
                    OnBooksArrived(book);
                    times++;
                    Logger.D("times:" + times);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void OnBooksArrived(Book book) {
        for (int i = 0; i < listenerList.size(); i++) {
            try {
                NewBooksArrivedListener listener = listenerList.get(i);
                if (listener != null) {
                    listener.NewBooksArrived(book);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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

        @Override
        public void registerBooksArrivedListener(NewBooksArrivedListener listener) throws RemoteException {
            if (!listenerList.contains(listener)) {
                listenerList.add(listener);
            }

        }

        @Override
        public void unregisterBooksArrivedListener(NewBooksArrivedListener listener) throws RemoteException {
            if (listenerList.contains(listener)) {
                listenerList.remove(listener);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
