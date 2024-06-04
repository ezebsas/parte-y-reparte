package com.grupo2.parteyreparte.dtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@Schema(description = "DTO of the user")
public class UserDTO {

    @Schema(name = "name", description = "User's name", example = "Example", type = "string")
    private String name;

    @Schema(name = "age", description = "User's age", example = "19", type = "int")
    private int age;

    @Schema(name = "email", description = "User's email", example = "example@example", type = "string")
    private String email;

    @Schema(name = "id", description = "User's id", example = "1", type = "string")
    private String id;

}

