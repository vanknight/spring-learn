package com.learn.webflux.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Document(collection = "message_base")
public class MessageBase implements Serializable {
    @MongoId
    private String id;

    @NotBlank
    @Size(max = 200)
    private String msg;

}
