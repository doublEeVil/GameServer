package com.game.world.game.service.base.impl;

import com.game.world.bean.Room;
import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.base.PlayerService;
import com.game.world.game.service.base.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component("com.game.world.game.service.base.RoomService")
public class RoomServiceImpl implements RoomService{
    private Map<Integer, Room> roomMap = new ConcurrentHashMap<>();
    private AtomicInteger flag = new AtomicInteger(1);

    @Autowired
    private PlayerService playerService;

    @Override
    public Collection<Room> getAllRoom() {
        return roomMap.values();
    }

    @Override
    public Room findRoom(int roomId) {
        Room room = roomMap.get(roomId);
        return room;
    }

    @Override
    public int createRoom(int playerId) {
        Room room = new Room();
        room.setRoomId(flag.incrementAndGet());
        room.setRoomOwner(playerId);
        room.getMember().add(playerId);
        room.getReadySet().add(playerId);
        roomMap.put(room.getRoomId(), room);

        WorldPlayer player = playerService.getLoadPlayer(playerId);
        player.setRoomId(room.getRoomId());

        return room.getRoomId();
    }

    @Override
    public int joinRoom(int playerId, int roomId) {
        WorldPlayer player = playerService.getLoadPlayer(playerId);
        outRoom(playerId, player.getRoomId());

        Room room = roomMap.get(roomId);
        if (room != null) {
            if (room.getMember().contains(playerId)) {
                return roomId;
            }
            if (room.getMember().size() < 2) {
                room.getMember().add(playerId);
                player.setRoomId(roomId);
                return roomId;
            }
        }
        return -1;
    }

    public void outRoom(int playerId, int roomId) {
        Room room = roomMap.get(roomId);
        if (room != null) {
            if (room.getMember().contains(playerId)) {
                room.getMember().remove(playerId);
                // 检查房主
                if (room.getMember().size() == 1) {
                    for (int pid : room.getMember()) {
                        room.setRoomOwner(pid);
                        return;
                    }
                } else {
                    room.setRoomOwner(-1);
                }
            }
        }
    }
}
