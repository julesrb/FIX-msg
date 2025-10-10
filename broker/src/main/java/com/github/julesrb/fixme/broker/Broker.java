package com.github.julesrb.fixme.broker;

import com.github.julesrb.fixme.common.NioClient;
import java.io.IOException;

public class Broker extends NioClient {

    Broker() throws IOException {
        super(5000);
    }
}
