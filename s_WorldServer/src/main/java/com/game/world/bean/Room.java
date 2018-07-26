package com.game.world.bean;

import java.util.HashSet;
import java.util.Set;

public class Room {
    public static final int CAN_JOIN = 1;
    public static final int FULL = 2;
    private int roomId;
    private int roomOwner;
    private Set<Integer> member = new HashSet<>(2);
    private Set<Integer> readySet = new HashSet<>(2);

    private QiPan qiPan = new QiPan();

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getStatus() {
        return member.size() >= 2 ? FULL : CAN_JOIN;
    }

    public int getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(int roomOwner) {
        this.roomOwner = roomOwner;
    }

    public Set<Integer> getMember() {
        return member;
    }

    public Set<Integer> getReadySet() {
        return readySet;
    }

    public QiPan getQiPan() {
        return qiPan;
    }
}
