package pl.edu.pg.eti.dragondestiny.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameDTO;
import pl.edu.pg.eti.dragondestiny.user.game.dto.GameListDTO;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.controller.UserController;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserListDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserLoginDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserRegisteredDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserList;
import pl.edu.pg.eti.dragondestiny.user.user.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTests2 {

    @InjectMocks
    UserController userController;

    /**
     * UserService mock for UserController.
     */
    @Mock
    private UserService userServiceMock;

    /**
     * GameService mock for UserController.
     */
    @Mock
    private GameService gameServiceMock;

    /**
     * ModelMapper dependency.
     */
    @Mock
    private ModelMapper modelMapper;

    /**
     * Mvc Mock.
     */
    private MockMvc mockMvc;

    /**
     * Object mapper/
     */
    private ObjectMapper objectMapper;

    /**
     * List of users to find.
     */
    private UserList userListToFind = new UserList();

    /**
     * List of games to find.
     */
    private GameList gameListToFind = new GameList();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        this.objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        User user1 = new User("harry", "pass", "Harry", new ArrayList<>());
        User user2 = new User("ron", "pass", "Ron", new ArrayList<>());
        User user3 = new User("ginny", "pass", "Ginny", new ArrayList<>());
        Game game1 = new Game("game1", new ArrayList<>(Arrays.asList(user1, user2)));
        Game game2 = new Game("game2", new ArrayList<>(Arrays.asList(user2)));
        Game game3 = new Game("game3", new ArrayList<>(Arrays.asList(user1, user2, user3)));
        user1.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game3)));
        user2.setPlayedGames(new ArrayList<>(Arrays.asList(game1, game2, game3)));
        user3.setPlayedGames(new ArrayList<>(Arrays.asList(game3)));

        userListToFind = new UserList(new ArrayList<>(Arrays.asList(user1, user2, user3)));
        gameListToFind = new GameList(new ArrayList<>(Arrays.asList(game1, game2, game3)));

        when(userServiceMock.getUsers()).thenReturn(userListToFind);
        when(userServiceMock.convertUserListToDTO(modelMapper, userServiceMock.getUsers()))
                .thenReturn(convertUserListToDTO(modelMapper, userListToFind));

        when(userServiceMock.getUser(user1.getLogin())).thenReturn(Optional.of(user1));
        when(userServiceMock.getUser(user2.getLogin())).thenReturn(Optional.of(user2));
        when(userServiceMock.getUser(user3.getLogin())).thenReturn(Optional.of(user3));

        when(userServiceMock.findGames(user1.getLogin())).thenReturn(Optional.of(new GameList(user1.getPlayedGames())));
        when(userServiceMock.findGames(user2.getLogin())).thenReturn(Optional.of(new GameList(user2.getPlayedGames())));
        when(userServiceMock.findGames(user3.getLogin())).thenReturn(Optional.of(new GameList(user3.getPlayedGames())));

        when(userServiceMock.convertUserListToDTO(eq(modelMapper), any(UserList.class)))
                .thenAnswer(invocation -> {
                    UserList userList = invocation.getArgument(1);
                    UserListDTO userListDTOToFind = new UserListDTO();
                    for (User user : userList.getUserList()) {
                        List<GameDTO> gameDTOList = new ArrayList<>();
                        for (Game game : user.getPlayedGames()) {
                            gameDTOList.add(new GameDTO(game.getId()));
                        }
                        userListDTOToFind.getUserList().add(new UserDTO(user.getLogin(), user.getName(), gameDTOList));
                    }
                    return userListDTOToFind;
                });

        when(gameServiceMock.convertGameListToDTO(eq(modelMapper), any(GameList.class)))
                .thenAnswer(invocation -> {
                    GameList gameList = invocation.getArgument(1);
                    GameListDTO gameListDTOToFind = new GameListDTO();
                    for (Game game : gameList.getGameList()) {
                        gameListDTOToFind.getGameList().add(new GameDTO(game.getId()));
                    }
                    return gameListDTOToFind;
                });
        when(modelMapper.map(any(User.class), eq(UserDTO.class)))
                .thenAnswer(invocation -> {
                    User userArgument = invocation.getArgument(0);
                    List<GameDTO> gameDTOList = new ArrayList<>();
                    for (Game game : userArgument.getPlayedGames()) {
                        gameDTOList.add(new GameDTO(game.getId()));
                    }
                    UserDTO userDTO = new UserDTO(userArgument.getLogin(), userArgument.getName(), gameDTOList);
                    return userDTO;
                });
    }

    @Test
    public void testGetUsers() throws Exception {
        // Arrange
        UserListDTO userListDTOToFind = convertUserListToDTO(modelMapper, userListToFind);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users")
                        .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserListDTO userListFound = objectMapper.readValue(responseContent, UserListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userListFound.getUserList().size(), userListDTOToFind.getUserList().size());

        for (int d = 0; d < userListFound.getUserList().size(); d++) {
            assertEquals(userListFound.getUserList().get(d), userListDTOToFind.getUserList().get(d));
        }
    }

    @Test
    public void testGetUserExisting() throws Exception {
        // Arrange
        String login = "hobbit";
        User user = new User(login, "password", "Bilbo Baggins", new ArrayList<>());
        UserDTO userToFind = new UserDTO(login, "Bilbo Baggins", new ArrayList<>());

        when(userServiceMock.getUser(login)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userToFind);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/{login}", login)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userToFind, userFound);
    }

    @Test
    public void testGetUserNotExisting() throws Exception {
        // Arrange
        String login = "hobbit";
        when(userServiceMock.getUser(login)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/{login}", login)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
        assertTrue(responseContent.isEmpty());
    }

    @Test
    public void testLoginUserExisting() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "password";
        UserLoginDTO userToLogin = new UserLoginDTO(login, password);
        User user = new User(login, password, "Bilbo Baggins", new ArrayList<>());
        UserDTO userToFind = new UserDTO(login, "Bilbo Baggins", new ArrayList<>());

        when(userServiceMock.getUser(login, password)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userToFind);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToLogin))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userToFind, userFound);
    }

    @Test
    public void testLoginUserNonExisting() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "wrongPassword";
        UserLoginDTO userToLogin = new UserLoginDTO(login, password);

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToLogin))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testRegisterUserOK() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "password";
        UserRegisteredDTO userToRegister = new UserRegisteredDTO(login, "Bilbo Baggins", password);
        User user = new User(login, password, "Bilbo Baggins", new ArrayList<>());
        UserDTO userToFind = new UserDTO(login, "Bilbo Baggins", new ArrayList<>());

        when(modelMapper.map(userToRegister, User.class)).thenReturn(user);
        when(userServiceMock.getUser(user.getLogin())).thenReturn(Optional.empty());

        when(userServiceMock.registerUser(eq(user))).thenAnswer(invocation -> {
            User registeredUser = invocation.getArgument(0);
            if (userServiceMock.getUser(registeredUser.getLogin()).isPresent()) {
                return Optional.empty();
            } else {
                return Optional.of(registeredUser);
            }
        });

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToRegister))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userToFind, userFound);
    }

    @Test
    public void testRegisterUserLoginExisting() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "password";
        UserRegisteredDTO userToRegister = new UserRegisteredDTO(login, "Bilbo Baggins", password);
        User user = new User(login, password, "Bilbo Baggins", new ArrayList<>());
        UserDTO userToFind = new UserDTO(login, "Bilbo Baggins", new ArrayList<>());

        when(modelMapper.map(userToRegister, User.class)).thenReturn(user);
        when(userServiceMock.getUser(user.getLogin())).thenReturn(Optional.of(user));

        when(userServiceMock.registerUser(eq(user))).thenAnswer(invocation -> {
            User registeredUser = invocation.getArgument(0); // Get the user argument
            if (userServiceMock.getUser(registeredUser.getLogin()).isPresent()) {
                return Optional.empty();
            } else {
                return Optional.of(registeredUser);
            }
        });

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToRegister))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetUserGamesExisting() throws Exception {
        // Arrange
        String login = "harry";
        GameList gameListToFind = new GameList (userListToFind.getUserList().stream().filter(
                user -> user.getLogin().equals(login)).findFirst().get().getPlayedGames().stream().toList());

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/{login}/games", login)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        GameListDTO gameListFound = objectMapper.readValue(responseContent, GameListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(gameListToFind.getGameList().size(), gameListFound.getGameList().size());
        for (int d = 0; d < gameListFound.getGameList().size(); d++) {
            assertEquals(gameListFound.getGameList().get(d).getId(), gameListToFind.getGameList().get(d).getId());
        }
    }

    @Test
    public void testGetUserGamesNotExisting() throws Exception {
        // Arrange
        String login = "notExistingUser";

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/{login}/games", login)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateUserOK() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "password";
        UserRegisteredDTO userToUpdate = new UserRegisteredDTO(login, "FRODO", password);
        User userUpdated = new User(login, password, "FRODO", new ArrayList<>());
        UserDTO userUpdatedDTO = new UserDTO(login, "FRODO", new ArrayList<>());

        when(userServiceMock.updateUser(login, modelMapper.map(userToUpdate, User.class))).thenReturn(Optional.of(userUpdated));

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/edit", login)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToUpdate))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userUpdatedDTO, userFound);
    }

    @Test
    public void testUpdateUserBadRequest() throws Exception {
        // Arrange
        String login = "notExistingUser";
        String password = "password";
        UserRegisteredDTO userToUpdate = new UserRegisteredDTO(login, "FRODO", password);

        when(userServiceMock.updateUser(login, modelMapper.map(userToUpdate, User.class))).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/edit", login)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToUpdate))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddGameToUserOK() throws Exception {
        // Arrange
        String login = "hobbit";
        String password = "password";
        String gameId = "game1";
        Game game = new Game("game1", new ArrayList<>());
        GameDTO gameDTO = new GameDTO("game1");
        User userToFind = new User(login, password, "Bilbo Baggins", new ArrayList<>(Arrays.asList(game)));
        UserDTO userDTOToFind = new UserDTO(login, "Bilbo Baggins", new ArrayList<>(Arrays.asList(gameDTO)));

        when(userServiceMock.addGameToUser(login, gameId)).thenReturn(Optional.of(userToFind));

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/addGame/{gameId}", login, gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDTO userFound = objectMapper.readValue(responseContent, UserDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(userDTOToFind, userFound);
    }

    @Test
    public void testAddGameToUserNotFound() throws Exception {
        // Arrange
        String login = "hobbit";
        String gameId = "game1";

        when(userServiceMock.addGameToUser(login, gameId)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/addGame/{gameId}", login, gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddGameToUserGameNotFound() throws Exception {
        // Arrange
        String login = "hobbit";
        String gameId = "gameNotExisting";
        when(userServiceMock.addGameToUser(login, gameId)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/{login}/addGame/{gameId}", login, gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteUserOK() throws Exception {
        // Arrange
        String login = "hobbit";

        when(userServiceMock.deleteUser(login)).thenReturn(Optional.of(true));

        // Act
        MvcResult mvcResult = mockMvc.perform(delete("http://localhost:8083/api/users/{login}", login)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        Boolean response = objectMapper.readValue(responseContent, Boolean.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(true, response);
    }

    @Test
    public void testDeleteUserNotExisting() throws Exception {
        // Arrange
        String login = "hobbitNotFound";

        when(userServiceMock.deleteUser(login)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(delete("http://localhost:8083/api/users/{login}", login)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddGameOK() throws Exception {
        // Arrange
        String gameId = "gameNew";

        // Act
        MvcResult mvcResult = mockMvc.perform(put("http://localhost:8083/api/users/games/{gameId}", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        Boolean response = objectMapper.readValue(responseContent, Boolean.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(true, response);
    }

    @Test
    public void testGetAllGamesOK() throws Exception {
        // Arrange
        GameListDTO gameListDTOToFind = convertGameListToDTO(modelMapper, gameListToFind);
        when(gameServiceMock.getGames()).thenReturn(Optional.of(gameListToFind));

        // Act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8083/api/users/games")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        GameListDTO gameListDTOFound = objectMapper.readValue(responseContent, GameListDTO.class);

        // Assert
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(gameListDTOToFind.getGameList().size(), gameListDTOFound.getGameList().size());
        for (int d = 0; d < gameListDTOFound.getGameList().size(); d++) {
            assertEquals(gameListDTOFound.getGameList().get(d), gameListDTOToFind.getGameList().get(d));
        }
    }

    public UserListDTO convertUserListToDTO(ModelMapper modelMapper, UserList userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList.getUserList()) {
            List<GameDTO> gameDTOList = new ArrayList<>();
            for (Game game : user.getPlayedGames()) {
                gameDTOList.add(new GameDTO(game.getId()));
            }
            userDTOList.add(new UserDTO(user.getLogin(), user.getName(), gameDTOList));
        }
        return new UserListDTO(userDTOList);
    }

    public GameListDTO convertGameListToDTO(ModelMapper modelMapper, GameList gameList) {
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameList.getGameList().forEach(game -> {
            GameDTO gameDTO = new GameDTO(game.getId());
            gameDTOList.add(gameDTO);
        });
        return new GameListDTO(gameDTOList);
    }
}
