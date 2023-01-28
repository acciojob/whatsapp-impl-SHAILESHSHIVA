package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private HashMap<String,User> userData;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.userData = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String addUser(String mobile,String name){
        try{
            if(!userData.containsKey(mobile)){
                userData.put(mobile,new User(name,mobile));
            }
        }catch(Exception e){
            return "User already exists";

        }

        return "SUCCESS";

    }

    public Group addGroup(List<User> user){

        if(user.size()==2) return this.personalGroup(user);

        int count = this.customGroupCount++;
        String gname = "Group" + count;
        Group group = new Group(gname,user.size());
        groupUserMap.put(group,user);
        adminMap.put(group,user.get(0));

        return group;
    }

    public Group personalGroup(List<User> user){

        String gname = user.get(1).getName();
        Group personalGroup = new Group(gname,2);
        groupUserMap.put(personalGroup,user);

        return personalGroup;

    }

    public int addMessage(String msg){
        int id = this.messageId++;
        Message m = new Message(id,msg);
        return id;
    }

    public int sendMsg(Message message,User sender,Group group) throws Exception{

        if(!groupUserMap.containsKey(group)) throw new Exception("Group not present");
        List<User> lst = groupUserMap.get(group);
        for(User u:lst){
            if(u.equals(sender) == false ) throw new Exception("User is not in group");
        }

        List<Message> msg = new ArrayList<>();
        if(groupMessageMap.containsKey(group)) msg = groupMessageMap.get(group);

        msg.add(message);

        groupMessageMap.put(group,msg);

        return msg.size();

    }

    public String changeAdmin(User approver,User user,Group group ) throws Exception{
        if(!groupUserMap.containsKey(group)) throw new Exception("Group not present");
        if(!adminMap.containsKey(approver)) throw new Exception("user is not admin");

        List<User> lst = groupUserMap.get(group);
        for(User u:lst){
            if(u.equals(user) == false ) throw new Exception("User is not in group");
        }

        adminMap.put(group,user);

        return "SUCCESS";
 }




}
