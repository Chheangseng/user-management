package com.tcs.user_auth_management.util.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoMessage {
    private String massage;
    private String date;
    public DtoMessage(String massage){
        this.massage = massage;
        var local = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy hh:mm a");
        this.date = local.format(formatter);
    }
}
