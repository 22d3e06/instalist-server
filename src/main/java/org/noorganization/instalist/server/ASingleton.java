package org.noorganization.instalist.server;

/**
 * Created by damihe on 27.01.16.
 */
public class ASingleton {

    private static ASingleton sInstance;

    public static ASingleton getInstance() {
        if (sInstance == null) {
            sInstance = new ASingleton();
        }
        return sInstance;
    }

    private ASingleton() {

    }
}
