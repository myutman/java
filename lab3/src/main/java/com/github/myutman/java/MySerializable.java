package com.github.myutman.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by myutman on 12/24/17.
 *
 * Interface of IO serialization and deserialization.
 */
public interface MySerializable {
    void serialize(OutputStream out) throws IOException;
    void deserialize(InputStream in) throws IOException;
}
