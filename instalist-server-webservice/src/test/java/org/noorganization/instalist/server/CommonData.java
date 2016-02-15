package org.noorganization.instalist.server;

import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.support.DatabaseHelper;

import java.io.IOException;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonData {

    public String mSecret;
    public String mEncryptedSecret;

    public CommonData() throws IOException {
        mSecret = "justATest";
        mEncryptedSecret = BCrypt.hashpw(mSecret, BCrypt.gensalt(10));

        DatabaseHelper.getInstance().initialize("org.noorganization.instalist.server.test");
    }

}
