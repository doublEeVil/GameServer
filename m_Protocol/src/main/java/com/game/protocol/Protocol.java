package com.game.protocol;

/**
 * 协议编号
 */
public class Protocol {
    /**
     * error
     */
    public static final short Error_ErrorMsg = -1;

    /**
     * Account
     */
    public static final short Acount_Login = 101;

    /**
     * Test
     */
    public static final short Test_Test = 102;

    /**
     * ROOM
     */
    public static final short Room_FindAll = 1001; //查找房间
    public static final short Room_FindAllOk = 1002; //查找房间
    public static final short Room_JoinRoom = 1003; // 加入房间
    public static final short Room_JoinRoomOk = 1004; // 加入房间
    public static final short Room_CreateRoom = 1005; // 创建房间
    public static final short Room_CreateRoomOk = 1006; // 创建房间

    /**
     * Chat
     */
    public static final short Chat_SendMsg = 1101; //发送聊天消息
    public static final short Chat_RcvMsg = 1102; //发送聊天消息

    /**
     * Battle
     */
    public static final short Battle_Ready = 2001;
    public static final short Battle_SendPlace = 2002;
    public static final short Battle_RcvPlace = 2003;
    public static final short Battle_SendOperate = 2004;
    public static final short Battle_RcvOperate = 2005;

}
