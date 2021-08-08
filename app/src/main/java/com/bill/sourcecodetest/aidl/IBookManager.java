package com.bill.sourcecodetest.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * Created by Bill on 2021/8/8.
 */

public interface IBookManager extends IInterface {

    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;

    public static abstract class Stub extends Binder implements IBookManager {

        static final String DESCRIPTOR = "com.bill.sourcecodetest.aid.IBookManager";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBookManager asInterface(IBinder obj) {
            if (obj == null)
                return null;

            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            Log.e("Bill", "iin:" + iin);
            if (iin != null && iin instanceof IBookManager) {
                return (IBookManager) iin;
            }

            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.e("Bill", "onTransact " + code);
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_getBookList:
                    data.enforceInterface(DESCRIPTOR);
                    List<Book> result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(result);
                    return true;
                case TRANSACTION_addBook:
                    data.enforceInterface(DESCRIPTOR);
                    Book arg0;
                    if (0 != data.readInt())
                        arg0 = Book.CREATOR.createFromParcel(data);
                    else
                        arg0 = null;
                    this.addBook(arg0);
                    reply.writeNoException();
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IBookManager {

            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public List<Book> getBookList() throws RemoteException {
                Log.e("Bill", "Proxy getBookList");
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                List<Book> result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                    reply.readException();
                    result = reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Log.e("Bill", "Proxy addBook");
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    if (book != null) {
                        data.writeInt(1);
                        book.writeToParcel(data, 0);
                    } else {
                        data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addBook, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

        }

        static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION;
        static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;
    }

}
