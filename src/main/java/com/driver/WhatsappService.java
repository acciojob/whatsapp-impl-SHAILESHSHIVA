package com.driver;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository repo = new WhatsappRepository();

    public String createUser(String mobile,String name){
        return repo.addUser(mobile,name);
    }

    public Group createGroup(List<User> user){

        return repo.addGroup(user);

    }

    public int createMessage(String msg){
        return repo.addMessage(msg);
    }

    public int sendMessage(Message msg,User sender,Group group) throws Exception {
        return repo.sendMsg(msg,sender,group);
    }

    public String changeAdmin(User approver,User user,Group group) throws Exception{
        return repo.changeAdmin(approver,user,group);
    }


}
