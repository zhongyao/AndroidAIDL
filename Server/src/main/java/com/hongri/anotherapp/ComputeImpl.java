package com.hongri.anotherapp;

import android.os.RemoteException;
import com.hongri.androidaidl.ICompute;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
