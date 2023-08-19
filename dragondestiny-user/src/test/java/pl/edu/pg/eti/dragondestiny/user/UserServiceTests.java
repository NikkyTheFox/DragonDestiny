package pl.edu.pg.eti.dragondestiny.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.eti.dragondestiny.user.game.entity.Game;
import pl.edu.pg.eti.dragondestiny.user.game.entity.GameList;
import pl.edu.pg.eti.dragondestiny.user.game.service.GameService;
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

        when(gameServiceMock.findGames(user1)).thenReturn(user1.getPlayedGames());
        when(gameServiceMock.findGames(user2)).thenReturn(user2.getPlayedGames());
        when(gameServiceMock.findGames(user3)).thenReturn(user3.getPlayedGames());

        when(gameServiceMock.findGame("game1")).thenReturn(Optional.of(game1));
        when(gameServiceMock.findGame("game2")).thenReturn(Optional.of(game2));


    }
    @Test
    public void testFindUserByLogin() {
        // Arrange
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals("login1")).findFirst();

        // Act
        Optional<User> userFound = userService.findUser("login1");

        // Assert
        assertEquals(userFound.get(), userToFind.get());
    }

    @Test
    public void testFindUserByLoginAndPassword() throws Exception {
        // Arrange
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals("login1") && user.getPassword().equals("pass1")).findFirst();
        if (userToFind.isEmpty())
            throw new Exception("Not found user in list");

        // Act
        Optional<User> userFound = userService.findUser("login1", "pass1");

        // Assert
        assertTrue(userFound.isPresent());
        assertEquals(userFound.get(), userToFind.get());
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
    public void testDeleteUser() {
        // Arrange
        Optional<User> userToDelete = userList.stream().filter(user -> user.getLogin().equals("login2")).findFirst();

        // Act
        userService.deleteUser("login2");

        // Assert
        Optional<User> userFound = userService.findUser("login2");
        verify(userRepositoryMock, times(1)).deleteById("login2");
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
        User userToUpdate = new User("login2", "someWeekPass", "Anakin", new ArrayList<>());

        // Act
        Optional<User> updatedUser = userService.updateUser(userToUpdate.getLogin(), userToUpdate);

        // Assert
        assertTrue(updatedUser.isPresent());
        assertEquals(userToUpdate, updatedUser.get());
        verify(userRepositoryMock, times(1)).save(userToUpdate); // verify method was called 1 time
    }

    @Test
    public void testUpdateUserNotExisting() {
        // Arrange
        User userToUpdate = new User("newLogin01", "someWeekPass", "Anakin Updated", new ArrayList<>());

        // Act
        Optional<User> updatedUser = userService.updateUser(userToUpdate.getLogin(), userToUpdate);

        // Assert
        assertTrue(updatedUser.isEmpty());
        verify(userRepositoryMock, times(0)).save(userToUpdate); // verify method was called 0 times
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
        verify(gameServiceMock, times(1)).findGames(userToFind.get()); // verify method was called 1 time
    }

    @Test
    public void testFindGamesOfNotExistingUser() {
        // Arrange
        String login = "NonExistingUser";
        Optional<User> userToFind = userList.stream().filter(user -> user.getLogin().equals(login)).findFirst();

        // Act
        Optional<GameList> gameListFound = userService.findGames(login);

        // Assert
        assertTrue(gameListFound.isEmpty());
        verify(gameServiceMock, times(0)).findGames(null); // verify method was called 0 times
    }

    @Test
    public void testAddGameToUserExisting() {
        // Arrange
        String login = "login1";
        Game gameToAdd = new Game("IdOfSomeCoolGame", new ArrayList<>());
        when(gameServiceMock.findGame("IdOfSomeCoolGame")).thenReturn(Optional.of(gameToAdd));

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
        when(gameServiceMock.findGame("IdOfSomeCoolGame")).thenReturn(Optional.of(gameToAdd));

        // Act
        Optional<User> updatedUser = userService.addGameToUser(login, gameToAdd.getId());

        // Assert
        assertTrue(updatedUser.isEmpty());
        verify(gameServiceMock, times(0)).save(gameToAdd);
    }

}
