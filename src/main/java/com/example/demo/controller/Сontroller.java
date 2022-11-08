package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.model.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.example.demo.model.Room.roomSize;


@RestController
public class Сontroller {


    @Value("${upload.path}")
    private String uploadPath;

    ArrayList<Room> lobby = new ArrayList<Room>();

    @PostMapping("/CreateHost")
    public int CreateHost(@RequestParam("name") String name,
                          @RequestParam("username") String username) {
        Room room = new Room(name);
        room.setLeader(username);
        lobby.add(room);
        return room.getId();
    }

    @PostMapping("/upload/{id}")
    public void upload(@PathVariable String id,
                       @RequestParam("files") MultipartFile[] files) throws IOException {
        List<MultipartFile> filesToArchive = new LinkedList<>();
        for (MultipartFile file : files) {

            if (file != null) {
                File uploadDir = new File(uploadPath + id);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                for (Room r : lobby) {
                    if (Integer.valueOf(id) == r.getId()) {
                        r.addSong(file.getOriginalFilename());
                    }
                }
                filesToArchive.add(file);
            }
        }
        doZip(filesToArchive,  uploadPath + id + "/");
    }

    @GetMapping("/JoinLobby")
    public String getLobby() {
        String s = "";
        for (Room r : lobby) {
            s += r.getName() + ":" + r.getId() + ",";
        }
        return s;
    }

    @PostMapping("/Join")
    @ResponseBody
    public ResponseEntity<Resource> join( @RequestParam("id") int id,
                                          @RequestParam("username") String name) throws MalformedURLException {
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        if (!r.memberExist(name, id)){
            if (r.getMemberCount() < roomSize) {
                r.addMembers(new Member(name, r.getId()));
            }
        }
                File filename = new File(uploadPath + id + "/song.zip");
                Resource resource = new UrlResource(filename.toURI());
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename.getName() + "\"").body(resource);

    }
    @GetMapping("/LobbyInfo/{id}")
    public String lobbyInfo(@PathVariable int id){
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        return r.getMembers();
    }

    @GetMapping("/LobbyStart/{id}")
    public String checkStart(@PathVariable int id){
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        return r.isRoomStarted() ? "started" : "notStarted";
    }
    @GetMapping("/Start/{id}")
    public void Start(@PathVariable int id){
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        r.setRoomStarted(true);
    }
    @PostMapping("/Answer")
    public void addanswer(@RequestParam("id") int id,
                          @RequestParam("username") String name,
                          @RequestParam("answer") String ans){
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        Member m = r.findMember(name);

        m.addAnswer(ans);
    }

    @GetMapping("/GetAnswer/{id}")
    public String getAnswer(@PathVariable int id){
        Room r =Room.findRoom(lobby,id);
        if(r == null) {
            throw new NullPointerException();
        }
        return r.getLastAnswer();
    }



    private static void doZip(List<MultipartFile> files,String dir){
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dir+"song.zip"));

            for (MultipartFile file : files) {
                File filename = new File("src/main/resources/targetFile.tmp");
                try (OutputStream os = new FileOutputStream(filename)) {
                    os.write(file.getBytes());
                }
                FileInputStream fis= new FileInputStream(filename);
                out.putNextEntry(new ZipEntry(file.getOriginalFilename()  ));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                out.write(buffer);
                out.closeEntry();
            }

            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    }


