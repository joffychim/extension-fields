package com.moqan.common.collection;

/**
 * Created by joffy on 17/10/28.
 */

public interface ObjectHashingStrategy<T> {
    int computeHashCode(final T object);
    boolean equals(final T o1, final T o2);
}
