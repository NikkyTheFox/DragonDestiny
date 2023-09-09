//package pl.edu.pg.eti.dragondestiny.user;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;
//import pl.edu.pg.eti.dragondestiny.user.game.dto.GameListDTO;
//import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
//import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
//import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
//import pl.edu.pg.eti.dragondestiny.user.user.controller.UserController;
//import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;
//import pl.edu.pg.eti.dragondestiny.user.user.dto.UserListDTO;
//import pl.edu.pg.eti.dragondestiny.user.user.dto.UserLoginDTO;
//import pl.edu.pg.eti.dragondestiny.user.user.dto.UserRegisteredDTO;
//import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
//import pl.edu.pg.eti.dragondestiny.user.user.entity.UserList;
//import pl.edu.pg.eti.dragondestiny.user.user.repository.UserRepository;
//import pl.edu.pg.eti.dragondestiny.user.user.service.UserService;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserControllerTests {
//
//    @InjectMocks
//    UserController userController;
//
//    /**
//     * UserService mock for UserController.
//     */
//    @Mock
//    private UserService userService;
//
//
//
//    /**
//     * GameService mock for UserController.
//     */
//    @Mock
//    private GameService gameServiceMock;
//
//    /**
//     * ModelMapper dependency.
//     */
//    @Mock
//    private ModelMapper modelMapper;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    private UserList userListToFind = new UserList();
//
//    private UserListDTO userListDTOToFind = new UserListDTO();
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//
//        this.objectMapper = new ObjectMapper();
//
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//
//        User user1 = new User("hero", "pass", "Harry", null);
//        User user2 = new User("amazon", "pass", "Ron", null);
//        User user3 = new User("disney", "pass", "Ginny", null);
//        UserDTO userDTO1 = new UserDTO(user1.getLogin(), user1.getName(), null);
//        UserDTO userDTO2 = new UserDTO(user2.getLogin(), user2.getName(), null);
//        UserDTO userDTO3 = new UserDTO(user3.getLogin(), user3.getName(), null);
//
//        Game game1 = new Game("game1", new ArrayList<>(Arrays.asList(user1, user2)));
//        Game game2 = new Game("game2", new ArrayList<>(Arrays.asList(user2)));
//        Game game3 = new Game("game3", new ArrayList<>(Arrays.asList(user1, user2, user3)));
//        GameDTO gameDTO1 = new GameDTO(game1.getId());
//        GameDTO gameDTO2 = new GameDTO(game2.getId());
//        GameDTO gameDTO3 = new GameDTO(game3.getId());
//
//        user1.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game3)));
//        user2.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game2, game3)));
//        user3.setPlayedGames(new ArrayList<>(Arrays.asList(game3)));
//        userDTO1.setPlayedGames(new ArrayList<>(Arrays.asList(gameDTO1, gameDTO3)));
//        userDTO2.setPlayedGames(new ArrayList<>(Arrays.asList(gameDTO1, gameDTO2, gameDTO3)));
//        userDTO3.setPlayedGames(new ArrayList<>(Arrays.asList(gameDTO3)));
//
//        userListToFind = new UserList(new ArrayList<>(Arrays.asList(user1, user2, user3)));
//        userListDTOToFind = new UserListDTO(new ArrayList<>(Arrays.asList(userDTO1, userDTO2, userDTO3)));
//
//        when(userRepositoryMock.findById("login1")).thenReturn(Optional.of(user1));
//        when(userRepositoryMock.findById("login2")).thenReturn(Optional.of(user2));
//        when(userRepositoryMock.findById("login3")).thenReturn(Optional.of(user3));
//
//        when(userRepositoryMock.findUserByLoginAndPassword("login1", "pass1")).thenReturn(Optional.of(user1));
//        when(userRepositoryMock.findUserByLoginAndPassword("login2", "pass2")).thenReturn(Optional.of(user2));
//        when(userRepositoryMock.findUserByLoginAndPassword("login3", "pass3")).thenReturn(Optional.of(user3));
//
//        when(userService.updateUser(anyString(), any(User.class))).thenCallRealMethod();
//
//    }
//
//    @Test
//    public void testEditUserOK() throws Exception {
//        // Arrange
//        String login = "hobbit";
//        String password = "password";
//
//        UserRegisteredDTO userToUpdateDTO = new UserRegisteredDTO(login, "Frodo", password);
//
//        // Act
//        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/edit", login)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userToUpdateDTO))
//                .accept(MediaType.APPLICATION_JSON)).andReturn();
//
//        String responseContent = mvcResult.getResponse().getContentAsString();
//        System.out.println("STRING: " + responseContent);
//        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);
//
//        // Assert
//        assertEquals(200, mvcResult.getResponse().getStatus());
//        //assertEquals(userToFind, userFound);
//    }
//
//}
