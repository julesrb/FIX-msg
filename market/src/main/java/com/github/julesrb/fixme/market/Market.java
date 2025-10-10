package com.github.julesrb.fixme.market;

import com.github.julesrb.fixme.common.NioClient;
import java.io.IOException;

public class Market extends NioClient {

    Market() throws IOException {
        super(5001);
    }
}
