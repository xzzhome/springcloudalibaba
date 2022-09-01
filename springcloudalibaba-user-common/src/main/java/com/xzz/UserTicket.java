package com.xzz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTicket {
    private Long user_id;
    private Long movie_ticket_id;
    private Integer status;
}
