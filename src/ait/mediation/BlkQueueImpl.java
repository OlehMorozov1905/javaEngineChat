package ait.mediation;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The BlkQueueImpl class is a thread-safe implementation of a blocking queue.
 * It uses locks and conditions to synchronize access to the queue, ensuring
 * that producers and consumers operate in a safe and coordinated manner.
 *
 * This implementation allows producers to wait when the queue is full,
 * and consumers to wait when the queue is empty.
 *
 * @param <T> the type of elements held in this queue
 */
public class BlkQueueImpl<T> implements BlkQueue<T> {
    private LinkedList<T> queue;
    private int maxSize;
    private Lock mutex = new ReentrantLock();
    private Condition senderWaitingCondition = mutex.newCondition();
    private Condition receiverWaitingCondition = mutex.newCondition();

    /**
     * Constructs a BlkQueueImpl with the specified maximum size.
     *
     * @param maxSize the maximum number of elements the queue can hold
     */
    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        queue = new LinkedList<>();
    }

    /**
     * Pushes a message to the queue. If the queue is full, the calling thread will wait until space is available.
     *
     * @param message the message to be added to the queue
     */
    @Override
    public void push(T message) {
        mutex.lock();
        try {
            while (queue.size() >= maxSize) {
                try {
                    senderWaitingCondition.await();
                } catch (InterruptedException e) {
                    System.out.println("Thread was interrupted");
                }
            }
            queue.add(message);
            receiverWaitingCondition.signal();
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Pops a message from the queue. If the queue is empty, the calling thread will wait until a message is available.
     *
     * @return the message from the queue
     */
    @Override
    public T pop() {
        mutex.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    receiverWaitingCondition.await();
                } catch (InterruptedException e) {
                    System.out.println("Thread was interrupted");
                }
            }
            T msg = queue.poll();
            senderWaitingCondition.signal();
            return msg;
        } finally {
            mutex.unlock();
        }
    }
}
