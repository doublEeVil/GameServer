package com.game.world.game.service.base;

import com.game.world.bean.Room;

import java.util.Collection;
import java.util.Set;

public interface RoomService {

    Collection<Room> getAllRoom();

    Room findRoom(int roomId);

    int createRoom(int playerId);

    int joinRoom(int playerId, int roomId);
}
