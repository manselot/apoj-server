package com.example.demo.model;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private boolean RoomStarted;
    public final static int roomSize = 4;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    private String leader ;

    public boolean isRoomStarted() {
        return RoomStarted;
    }

    public void setRoomStarted(boolean roomStarted) {
        RoomStarted = roomStarted;
    }

    private String name;
    private ArrayList<Member> members = new ArrayList<>();
    private int id;

    private ArrayList<String> SongList = new ArrayList<>();

    public Room(String name) {
        this.name = name;
        this.id = new Random().nextInt(0, 10000);
        this.RoomStarted = false;
    }

    public void addMembers(Member member) {
        members.add(member);
    }

    public String getName() {
        return name;
    }

    public String getMembers() {
        String s = "";
        for (Member m : members) {
            s += m.getName() + ",";
        }
        return s;
    }

    public void addSong(String song) {
        SongList.add(song);
    }

    public String getSonglist() {
        return SongList.toString();
    }

    public int getId() {
        return id;
    }

    public static Room findRoom(ArrayList<Room> lobby, int id) {
        for (Room r : lobby) {
            if (id == r.getId()) {
                return r;
            }
        }
        return null;
    }
    public boolean memberExist(String name,int id) {
        for (Member m: members) {
            if (m.getName().equals(name)) {
                if (m.getCurrentid() == m.getCurrentid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getMemberCount() {
        return members.size();
    }

    public Member findMember(String username) {
        for (Member m :members) {
            if(m.getName().equals(username))
                return m;
        }
        return null;
    }

    public String getLastAnswer() {
        StringBuilder s = new StringBuilder();
        for (Member m:
             members) {
            s.append(m.getName()).append(":").append(m.getAnswer()).append(",");
        }
        return s.toString();
    }
}