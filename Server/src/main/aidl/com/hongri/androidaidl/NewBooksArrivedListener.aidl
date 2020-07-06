// CalculateInterface.aidl
package com.hongri.androidaidl;
import com.hongri.androidaidl.Book;
// Declare any non-default types here with import statements

interface NewBooksArrivedListener {
   void NewBooksArrived(in Book book);
}
