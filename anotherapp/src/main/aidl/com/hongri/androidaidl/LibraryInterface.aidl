// CalculateInterface.aidl
package com.hongri.androidaidl;
import com.hongri.androidaidl.Book;
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

}
