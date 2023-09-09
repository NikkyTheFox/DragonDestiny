package pl.edu.pg.eti.dragondestiny.user;

import junitparams.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserDTO;
import pl.edu.pg.eti.dragondestiny.user.user.dto.UserListDTO;
import pl.edu.pg.eti.dragondestiny.user.user.entity.User;
import pl.edu.pg.eti.dragondestiny.user.user.entity.UserList;
import pl.edu.pg.eti.dragondestiny.user.user.repository.UserRepository;
import pl.edu.pg.eti.dragondestiny.user.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    /**
     * UserService is object being tested. All mocks will be automatically injected as dependencies.
     */
    @InjectMocks
    private UserService userService;

    /**
     * UserRepository mock for UserService.
     */
    @Mock
    private UserRepository userRepositoryMock;

    /**
     * GameService mock for UserService.
     */
    @Mock
    private GameService gameServiceMock;

    /**
     * Testing list of users to add to UserRepository.
     */
    private List<User> userList = new ArrayList<User>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        User user1 = new User("login1", "pass1", "name1", new ArrayList<>());
        User user2 = new User("login2", "pass2","name2", new ArrayList<>());
        ArrayList<Game> gamesList = new ArrayList<>();
        Game game1 = new Game("game1", null);
        Game game2 = new Game("game2", null);
        gamesList.add(game1);
        gamesList.add(game2);
        User user3 = new User("login3", "pass3","name3", gamesList);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        when(userRepositoryMock.findAll()).thenReturn(userList);

        when(userRepositoryMock.findById("login1")).thenReturn(Optional.of(user1));
        when(userRepositoryMock.findById("login2")).thenReturn(Optional.of(user2));
        when(userRepositoryMock.findById("login3")).thenReturn(Optional.of(user3));

        when(userRepositoryMock.findUserByLoginAndPassword("login1", "pass1")).thenReturn(Optional.of(user1));
        when(userRepositoryMock.findUserByLoginAndPassword("login2", "pass2")).thenReturn(Optional.of(user2));
        when(userRepositoryMock.findUserByLoginAndPassword("login3", "pass3")).thenReturn(Optional.of(user3));

        when(gameServiceMock.getGames(user1)).thenReturn(Optional.of(new GameList(user1.getPlayedGames())));
        when(gameServiceMock.getGames(user2)).thenReturn(Optional.of(new GameList(user2.getPlayedGames())));
        when(gameServiceMock.getGames(user3)).thenReturn(Optional.of(new GameList(user3.getPlayedGames())));

        when(gameServiceMock.getGame("game1")).thenReturn(Optional.of(game1));
        when(gameServiceMock.getGame("game2")).thenReturn(Optional.of(game2));


    }
    @Test
    public void testGetUserByLoginExisting() {
        // Arrange
        String login = "login1";
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals(login)).findFirst();

        // Act
        Optional<User> userFound = userService.getUser(login);

        // Assert
        assertTrue(userFound.isPresent());
        assertEquals(userFound.get(), userToFind.get());
    }

    @Test
    public void testGetUserByLoginNonExisting() {
        // Arrange
        String login = "nonExitingLogin";

        // Act
        Optional<User> userFound2 = userService.getUser(login);

        // Assert
        assertTrue(userFound2.isEmpty());
    }

    @Test
    public void testFindUserByLoginAndPasswordExisting() throws Exception {
        // Arrange
        String login = "login1";
        String password = "pass1";
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals(login) &&
                user.getPassword().equals(password)).findFirst();
        if (userToFind.isEmpty())
            throw new Exception("Not found user in list");

        // Act
        Optional<User> userFound = userService.getUser(login, password);

        // Assert
        assertTrue(userFound.isPresent());
        assertEquals(userFound.get(), userToFind.get());
    }

    @Test
    public void testFindUserByLoginAndPasswordNonExisting() throws Exception {
        // Arrange
        String login = "login2";
        String password = "wrongPass";

        // Act
        Optional<User> userFound = userService.getUser(login, password);

        // Assert
        assertTrue(userFound.isEmpty());
    }

    @Test
    public void testGetUsers() {
        // Arrange
        UserList userListToFind = new UserList(userList);

        // Act
        UserList userListFound = userService.getUsers();

        // Assert
        assertEquals(userListToFind.getUserList().size(), userListFound.getUserList().size());

        for (int i = 0; i < userListToFind.getUserList().size(); i++) {
            User userToFind = userListToFind.getUserList().get(i);
            User userFound = userListFound.getUserList().get(i);
            assertEquals(userToFind, userFound);
        }
    }

    @Test
    public void testDeleteUserExisting() {
        // Arrange
        String login = "login2";
        String login2 = "nonExistingLogin";

        // Act
        Optional<Boolean> response = userService.deleteUser(login);
        Optional<Boolean> response2 = userService.deleteUser(login2);

        // Assert
        assertTrue(response.isPresent());
        assertEquals(Boolean.TRUE, response.get());
        verify(userRepositoryMock, times(1)).deleteById(login);
        assertTrue(response2.isEmpty());
        verify(userRepositoryMock, times(0)).deleteById(login2);
    }

    @Test
    public void testDeleteUserNonExisting() {
        // Arrange
        String login = "nonExistingLogin";

        // Act
        Optional<Boolean> response = userService.deleteUser(login);

        // Assert
        assertTrue(response.isEmpty());
        verify(userRepositoryMock, times(0)).deleteById(login);
    }

    @Test
    public void testRegisterUserAdded() {
        // Arrange
        User userToRegister = new User("chosen0ne", "superStrongPassword", "Harry (UpdatedName)", new ArrayList<>());
        when(userRepositoryMock.save(userToRegister)).thenReturn(userToRegister);

        // Act
        Optional<User> userAdded = userService.registerUser(userToRegister);

        // Assert
        verify(userRepositoryMock, times(1)).save(userToRegister); // verify method was called 1 time
        assertTrue(userAdded.isPresent());
    }

    @Test
    public void testRegisterUserNotAdded() {
        // Arrange
        User userToRegister = new User("login2", "superStrongPassword", "Updated Name", new ArrayList<>());
        when(userRepositoryMock.save(userToRegister)).thenReturn(userToRegister);

        // Act
        Optional<User> userAdded = userService.registerUser(userToRegister);

        // Assert
        verify(userRepositoryMock, times(0)).save(userToRegister); // verify method was called 0 times
        assertTrue(userAdded.isEmpty());
    }

    @Test
    public void testUpdateUserExisting() {
        // Arrange
        User userToUpdate = new User("login2", "someWeakPass", "Anakin", new ArrayList<>());

        // Act
        Optional<User> updatedUser = userService.updateUser(userToUpdate.getLogin(), userToUpdate);

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(userToUpdate, updatedUser.get());
        verify(userRepositoryMock, times(1)).save(userToUpdate);
    }

    @Test
    public void testUpdateUserNotExisting() {
        // Arrange
        User userToUpdate = new User("newLogin01", "someWeekPass", "Anakin Updated", new ArrayList<>());

        // Act
        Optional<User> updatedUser = userService.updateUser(userToUpdate.getLogin(), userToUpdate);

        // Assert
        assertTrue(updatedUser.isEmpty());
        verify(userRepositoryMock, times(0)).save(userToUpdate);
    }

    @Test
    public void testFindGamesOfExistingUser() {
        // Arrange
        String login = "login3";
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals(login)).findFirst();

        // Act
        Optional<GameList> gameListFound = userService.findGames(login);

        // Assert
        assertTrue(gameListFound.isPresent());
        assertEquals(new GameList(userToFind.get().getPlayedGames()), gameListFound.get());
        verify(gameServiceMock, times(1)).getGames(userToFind.get());
    }

    @Test
    public void testFindGamesOfNotExistingUser() {
        // Arrange
        String login = "NonExistingUser";

        // Act
        Optional<GameList> gameListFound = userService.findGames(login);

        // Assert
        assertTrue(gameListFound.isEmpty());
        verify(gameServiceMock, times(0)).getGames(null); // verify method was called 0 times
    }

    @Test
    public void testAddGameToUserExisting() {
        // Arrange
        String login = "login1";
        Game gameToAdd = new Game("IdOfSomeCoolGame", new ArrayList<>());
        when(gameServiceMock.getGame("IdOfSomeCoolGame")).thenReturn(Optional.of(gameToAdd));

        // Act
        Optional<User> updatedUser = userService.addGameToUser(login, gameToAdd.getId());

        // Assert
        assertTrue(updatedUser.isPresent());
        assertTrue(updatedUser.get().getPlayedGames().contains(gameToAdd));
        verify(gameServiceMock, times(1)).save(gameToAdd);
    }

    @Test
    public void testAddGameToUserNotExisting() {
        // Arrange
        String login = "NonExistingLogin";
        Game gameToAdd = new Game("IdOfSomeCoolGame", new ArrayList<>());
        when(gameServiceMock.getGame("IdOfSomeCoolGame")).thenReturn(Optional.of(gameToAdd));

        // Act
        Optional<User> updatedUser = userService.addGameToUser(login, gameToAdd.getId());

        // Assert
        assertTrue(updatedUser.isEmpty());
        verify(gameServiceMock, times(0)).save(gameToAdd);
    }

    @Test
    public void testAddGameToUserGameNotExisting() {
        // Arrange
        String login = "NonExistingLogin";
        Game gameToAdd = new Game("NonExistingGameId", new ArrayList<>());
        when(gameServiceMock.getGame("NonExistingGameId")).thenReturn(Optional.empty());

        // Act
        Optional<User> updatedUser = userService.addGameToUser(login, gameToAdd.getId());

        // Assert
        assertTrue(updatedUser.isEmpty());
        verify(gameServiceMock, times(0)).save(gameToAdd);
    }

    @Test
    public void testConvertUserListToDTO() {
        // Arrange
        ModelMapper modelMapper = new ModelMapper();
        UserList userListToConvert = new UserList(userList);

        // Act
        UserListDTO userListDTO = userService.convertUserListToDTO(modelMapper, userListToConvert);

        // Assert
        for (int d = 0; d < userListDTO.getUserList().size(); d++) {
            assertTrue(userListDTO.getUserList().get(d).getClass() == UserDTO.class);
        }
    }


}
