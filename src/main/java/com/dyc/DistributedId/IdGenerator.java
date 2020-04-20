package com.dyc.DistributedId;

import java.io.Serializable;

public interface IdGenerator<T extends Serializable> {
    T nextId();
    T[] nextIds(int var1);
}

