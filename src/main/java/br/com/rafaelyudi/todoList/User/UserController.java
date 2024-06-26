package br.com.rafaelyudi.todoList.User;

import br.com.rafaelyudi.todoList.Config.Swagger.UserExampleRequestBody;
import br.com.rafaelyudi.todoList.Errors.CustomResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/v1")
@Tag(name = "User", description = "Endpoints to managing users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager auth;


    @PostMapping( value = "/register",consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(
            summary = "Create a user",
            description = "Create a user by passing JSON or XML",
            tags = "User",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = UserDTO.class)),
                            @Content(schema = @Schema(implementation = UserDTO.class))
                    }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    })
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserExampleRequestBody.class)),
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserExampleRequestBody.class))
        }
    )
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userData) {
        var userCreated = this.userService.userCreate(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }


    @DeleteMapping("/")
    @Operation(
            summary = "Delete a user",
            tags = "User",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204", content = @Content),

                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    })
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        this.userService.delete(request);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "login a user",
            tags = "User",
            responses = {
                    @ApiResponse(description = "Success" ,responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = LoginResponseDTO.class))
                    }),

                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    }),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseError.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = CustomResponseError.class))
                    })
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDTO credentials) {
        return  ResponseEntity.ok().body(this.userService.login(credentials));
    }
}

