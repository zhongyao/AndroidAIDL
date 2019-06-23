// CalculateInterface.aidl
package com.hongri.androidaidl;
import com.hongri.androidaidl.Book;
import com.hongri.androidaidl.NewBooksArrivedListener;
// Declare any non-default types here with import statements

interface LibraryInterface {
   /**
    * 添加书籍
    */
   void addBooks(in Book book);

   /**
    * 获取书籍
    */
    List<Book> getBooks();

    /**
    * 注册图书到达Listener
    */
    void registerBooksArrivedListener(NewBooksArrivedListener listener);

    /**
      * 注销图书到达Listener
      */
    void unregisterBooksArrivedListener(NewBooksArrivedListener listener);

}
