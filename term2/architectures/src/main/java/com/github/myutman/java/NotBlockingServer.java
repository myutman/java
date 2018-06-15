package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class NotBlockingServer implements Runnable {

    private Socket setSocket = null;
    private int limit;
    private int started = 0;
    private int answered = 0;

    public NotBlockingServer() {
        this.limit = -1;
    }

    public NotBlockingServer(int limit, Socket setSocket) {
        this.limit = limit;
        this.setSocket = setSocket;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(55555));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            System.err.println("Socket is busy");
            return;
        }
        final Selector readSelector;
        final ReentrantLock readSelectorLock = new ReentrantLock();
        try {
            readSelector = Selector.open();
        } catch (IOException e) {
            System.err.println("Error creating selector");
            return;
        }
        final Selector writeSelector;
        final ReentrantLock writeSelectorLock = new ReentrantLock();
        try {
            writeSelector = Selector.open();
        } catch (IOException e) {
            System.err.println("Error creating selector");
            return;
        }
        ExecutorService pool = Executors.newCachedThreadPool();
        Thread threadRead = new Thread(() -> {
            while (limit == -1 || started < limit) {
                readSelectorLock.lock();
                readSelector.wakeup();
                try {
                    readSelector.select();
                } catch (IOException e) {
                    readSelectorLock.unlock();
                    continue;
                }
                Iterator<SelectionKey> keyIterator = readSelector.selectedKeys().iterator();
                readSelectorLock.unlock();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer;
                    if (key.attachment() == null) {
                        started++;
                        ByteBuffer buffer1 = ByteBuffer.allocate(16);
                        try {
                            channel.read(buffer1);
                        } catch (IOException e) {
                            System.err.println("Failed to read");
                            key.cancel();
                            continue;
                        }
                        buffer1.flip();
                        byte[] bytes = buffer1.array();
                        int z;
                        int size = 0;
                        int base = 1;
                        int ct = 0;
                        do  {
                            z = buffer1.get();
                            if (z < 0) {
                                size += (z + 128) * base;
                            } else {
                                size += z * base;
                            }
                            base *= 128;
                            ct++;
                        } while (z < 0);
                        buffer = ByteBuffer.allocate(size + ct);
                        buffer.put(bytes, 0, buffer1.limit());
                    } else {
                        buffer = (ByteBuffer) key.attachment();
                    }
                    try {
                        channel.read(buffer);
                    } catch (IOException e) {
                        System.err.println("Failed to read");
                        key.cancel();
                        continue;
                    }
                    if (buffer.position() < buffer.limit()) {
                        key.attach(buffer);
                        continue;
                    }
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.array());
                    MyArray array = Utils.processReading(inputStream);
                    key.cancel();
                    if (array == null) continue;
                    pool.submit(() -> {
                        MyArray newArray = Utils.sort(array);
                        writeSelectorLock.lock();
                        writeSelector.wakeup();
                        try {
                            channel.register(writeSelector, SelectionKey.OP_WRITE, newArray);
                        } catch (ClosedChannelException e) {
                            e.printStackTrace();
                        } finally {
                            writeSelectorLock.unlock();
                        }
                    });
                }
            }
        });
        threadRead.setDaemon(true);
        threadRead.start();
        Thread threadWrite = new Thread(() -> {
            while (limit == -1 || answered < limit) {
                writeSelectorLock.lock();
                writeSelector.wakeup();
                try {
                    int x = writeSelector.select();
                    if (x < 1) {
                        writeSelectorLock.unlock();
                        continue;
                    }
                } catch (IOException e) {
                    System.err.println("Nothing to be found");
                    writeSelectorLock.unlock();
                    continue;
                }
                Iterator<SelectionKey> keyIterator = writeSelector.selectedKeys().iterator();
                writeSelectorLock.unlock();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer;
                    if (key.attachment() instanceof MyArray) {
                        MyArray array = (MyArray) key.attachment();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        try {
                            Utils.processWriting(array, outputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] bytes = outputStream.toByteArray();
                        buffer = ByteBuffer.allocate(bytes.length);
                        buffer.put(bytes);
                        buffer.flip();
                    } else {
                        buffer = (ByteBuffer) key.attachment();
                    }
                    try {
                        channel.write(buffer);
                    } catch (IOException ignored) {

                    }
                    if (buffer.position() < buffer.limit()) {
                        key.attach(buffer);
                        continue;
                    }
                    key.cancel();
                    answered++;
                }
            }
        });
        threadWrite.setDaemon(true);
        threadWrite.start();
        int ct = 0;
        System.out.println(limit);
        if (setSocket != null) {
            try {
                RequestProtos.Request.newBuilder()
                        .setLimit(0)
                        .setType(0)
                        .build()
                        .writeDelimitedTo(setSocket.getOutputStream());
            } catch (IOException e) {
                return;
            }
        }
        while (limit == -1 || ct < limit) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    ct++;
                    socketChannel.configureBlocking(false);
                    readSelectorLock.lock();
                    readSelector.wakeup();
                    socketChannel.register(readSelector, SelectionKey.OP_READ, null);
                    readSelectorLock.unlock();
                }
            } catch (IOException ignored) {

            }
        }
        try {
            threadRead.join();
            threadWrite.join();
            serverSocketChannel.close();
        } catch (InterruptedException | IOException ignored) {

        }
    }
}
