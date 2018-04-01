package com.github.fabienrenaud.jjb.provider;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Wrapper providing Java methods for JsoniterScala.
 * @param <T>
 */
public interface JsoninterScalaProvider<T> {
    void serialize(OutputStream out, T t);

    T deserialize(InputStream in);
}
