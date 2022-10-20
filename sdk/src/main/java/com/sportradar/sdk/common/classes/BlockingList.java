/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A list whose {code compareAndRemove} methods block until the specified item is removed.
 */
public class BlockingList<T> {

    /**
     * Internally used list.
     */
    private final List<T> list = new ArrayList<>();

    /**
     * Returns the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this list.
     */
    public int size() {
        synchronized (list) {
            return list.size();
        }
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements.
     */
    public boolean isEmpty() {
        synchronized (list) {
            return list.isEmpty();
        }
    }

    /**
     * Adds the passed instance to the list.
     *
     * @param item the item to be added to the list.
     */
    public void add(T item) {
        Nulls.checkNotNull(item, "The item cannot be a null reference");

        synchronized (list) {
            list.add(item);
            list.notifyAll();
        }
    }

    /**
     * Blocks until the searched item is found and removed from the list.
     *
     * @param item    The item which is to be removed from the list or null to remove the first item.
     * @param timeout The time in milliseconds to wait for the item.
     * @throws InterruptedException item was not found in the list in the time allowed time window.
     */
    public void compareAndRemove(T item, long timeout) throws InterruptedException {
        checkArgument(timeout > 0, "The timeout cannot be equal to 0");

        compareAndRemoveInternal(item, null, timeout);
    }

    /**
     * Blocks until the searched item is found and removed from the list.
     *
     * @param item       The item which is to be removed from the list or null to remove the first item.
     * @param comparator the {@link Comparator} used to compare the stored items to the passed one.
     * @param timeout    The time in milliseconds to wait for the item.
     * @throws InterruptedException item was not found in the list in the time allowed time window.
     */
    public void compareAndRemove(T item, Comparator<T> comparator, long timeout) throws InterruptedException {
        Nulls.checkNotNull(comparator, "The comparator cannot be a null reference");
        checkArgument(timeout > 0, "The timeout cannot be equal to 0");

        compareAndRemoveInternal(item, comparator, timeout);
    }

    /**
     * Blocks until the searched item is found and removed from the list.
     *
     * @param item       The item which is to be removed from the list or null to remove the first item.
     * @param comparator the {@link Comparator} used to compare the stored items to the passed one.
     * @param timeout    The time in milliseconds to wait for the item.
     * @throws InterruptedException item was not found in the list in the time allowed time window.
     */
    private void compareAndRemoveInternal(T item, Comparator<T> comparator, long timeout) throws InterruptedException {
        long remainingTime = timeout;
        long sleepTime = 0;

        synchronized (list) {
            T foundItem = null;
            while (foundItem == null) {
                for (T storedItem : list) {
                    if (compare(storedItem, item, comparator)) {
                        foundItem = item;
                        break;
                    }
                }

                if (sleepTime != 0) {
                    remainingTime = remainingTime - (System.currentTimeMillis() - sleepTime);
                    if (remainingTime <= 0) {
                        throw new InterruptedException("Timed out");
                    }
                }

                if (foundItem != null) {
                    list.remove(foundItem);
                } else {
                    sleepTime = System.currentTimeMillis();
                    list.wait(remainingTime);
                }
            }
        }
    }

    /**
     * Compares the {@code stored} instance to the {@code pattern} instance optionally using the provided {@code comparator}
     *
     * @param stored     The item from the list
     * @param pattern    The item to which the item from the list will be compared to.
     * @param comparator The comparator used to compare the items of a null reference.
     * @return true if {@code stored} instance is equal to {@code pattern} instance. Otherwise false.
     */
    private boolean compare(T stored, T pattern, Comparator<T> comparator) {
        if (comparator != null) {
            return comparator.compare(stored, pattern) == 0;
        } else {
            return (stored == null && pattern == null)
                    || (stored != null && stored.equals(pattern));
        }
    }
}
