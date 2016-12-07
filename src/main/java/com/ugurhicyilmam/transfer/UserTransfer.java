package com.ugurhicyilmam.transfer;

import com.ugurhicyilmam.domain.User;
import lombok.Data;

@Data
public class UserTransfer {

    private long id;
    private String username;

    public UserTransfer(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
