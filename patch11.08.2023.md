# Zmiany ogólne
Wszystkie medoty oraz klasy są opisane za pomocą JavaDoc 

Pousuwane niepotrzebne import'y

Pliki oraz foldery wyrzucone z repo:
- .idea/
- notes.txt
- patchnotes.txt
- notesINZ.txt
- */.mvn/
- dragondestiny-ui/angular-app/.vscode/

Zmiany kosmetyczne w nazewnictwie niektórych folderów, klas.

Zmiany kosmetyczne w nazewnictwie niektórych metod na bardziej opisową nazwę.

# Zmiany w backendzie:
## ENGINE:
### Nowe:
- **BoardList.java** -> *Struktura zawierająca listę plansz.*
- **BoardService.getBoards()** -> *Metoda zwracająca stukturę z listą plansz.*
- **BoardService.convertBoardListToDTO()** -> *Transformacja ze struktury z listą na odpowiadające DTO.*
- **GameCardService.java** -> *Nowy service wspierający obsługę requestów GameCardControllera.java.*
- **CardList.java** -> *Struktura zawierająca listę kart.*
- **CardService.getCards()** -> *Metoda zwracająca strukturę z listą kart.*
- **CardService.convertCardListToDTO()** -> *Transformacja ze struktury z listą na odpowiadające DTO.*
- **GameCardService.java** -> *Nowy service wspierający obsługę requestów w GameCardController.java.*
- **EnemyCardList.java** -> *Struktura zawierająca listę kart-wrogów.*
- **EnemyCardService.getEnemyCards()** -> *Metoda zwracająca stukturę z listą kart-wrogów.*
- **EnemyCardService.convertEnemyCardListToDTO()** -> *Transformacja ze struktury z listą kart na odpowiadające DTO.*
- **ItemCardList.java** -> *Struktura zawierająca listę kart-przedmiotów.*
- **ItemCardService.getItemCards()** -> *Metoda zwracająca strukturę z listą kart-przedmiotów.*
- **ItemCardService.convertItemCardListToDTO()** -> *Transformacja struktury z listą kart-przedmiotów na odpowiadające DTO.*
- **CharacterList.java** -> *Struktura zawierająca listę postaci.*
- **CharacterService.getCharacters()** -> *Metoda zwracająca strukturę z listą postaci.*
- **CharacterService.convertCharacterListToDTO()** -> *Transformacja struktury z listą postaci na odpowiadające DTO.*
- **GameCharacterService.java** -> *Nowy service wspierający obsługę requestów w GameCharacterController.java.*
- **FieldList.java** -> *Struktura zawierająca listę pól.*
- **BoardFieldService.java** -> *Nowy service wspierający obsługę requestów w BoardFieldController.java.*
- **FieldService.getFields()** -> *Metoda zwracająca strukturę z listą pól.*
- **FieldService.convertFieldListToDTO()** -> *Transformacja struktury z listą pól na odpowiadające DTO.*
- **GameFieldService.java** -> *Nowy service wspierający obsługę requestów w GameFieldController.java.*
- **GameList.java** -> *Struktura zawierająca listę gier.*
- **GameService.getGames()** -> *Metoda zwracająca strukturę z listą gier.*
- **GameService.convertGameListToDTO()** -> *Transformacja struktury z listą gier na odpowiadające DTO.*


### Zmiany:
- **BoardController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*
- **CardController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*
- **GameCardController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service; Oparty na własnym Service.*
- **CharacterController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*
- **GameCharacterController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service; Oparty na własnym Service.*
- **BoardFieldController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service; Oparty na własnym Service.*
- **FieldController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*
- **GameFieldController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service; Oparty na własnym Service.*
- **GameController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*

## PlayedGame:
### Usunięte:

- **EnemyCardManager.java**
- **ItemCardManager.java**
- **CharacterManager.java**
- **FieldManager.java**
- **GameManager.java**

### Nowe:

- **PlayedGameConfig.java** -> *Konfiguracja mappera do transformacji na DTO.*
- **PlayedGameProperties.java** -> *Wartości niezmienne gier.*
- **PlayedBoardDTO.java** -> *DTO dla planszy.*
- **CardDTO.java** -> *DTO dla karty.*
- **CardListDTO.java** -> *Struktura zawierająca listę DTO kart.*
- **EnemyCardDTO.java** -> *DTO dla karty-wroga.*
- **EnemyCardListDTO.java** -> *Struktura zawierająca listę DTO kart-wrogów.*
- **ItemCardDTO.java** -> *DTO dla karty-przedmiotu.* 
- **ItemCardListDTO.java** -> *Struktura zawierająca listę DTO kart-przedmiotów.*
- **CharacterDTO.java** -> *DTO dla postaci.*
- **CharacterListDTO.java** -> *Struktura zawierająca listę DTO postaci.*
- **FieldDTO.java** -> *DTO dla pola.*
- **FieldListDTO.java** -> *Struktura zawierająca listę DTO pól.*
- **FieldOptionListDTO.java** -> *Struktura zawierająca listę opcji akcji z pola.*
- **FightResultDTO.java** -> *DTO dla rezultatu walki.*
- **Interface HealthCalculable.java** -> *Użyte w Character oraz EnemyCard aby łatwo sprawdzać czy Health Poins > 0*
- **PlayedGameDTO.java** -> *DTO dla gry.*
- **PlayedGameListDTO.java** -> *Struktura zawierająca listę DTO gier.*
- **PlayedDTO.java** -> *DTO dla gracza.*
- **PlayerListDTO.java** -> *Struktura zawierająca listę DTO graczy.*
- **RoundDTO.java** -> *DTO dla rundy.*

### Zmiany:

- **EnemyCard.java** -> *Jest jedno, automatycznie zmieniane i obliczane zdrowie.*
- **Character.java** -> *Jest jedno, automatycznie zmieniane i obliczane zdrowie oraz siła.*
- **FieldOption.java** -> *Możliwość wyboru walki z wrogiem z pola; Możliwość przejścia z Bridge do Bossa i na odwrót.*
- **PlayedGameResponse.java** -> *Zmiana nazwy na **GameEngineGameDTO.java**.*
- **InitializePlayedGame.java** -> *Zmiana nazwy na **InitializingPlayedGame**; podział na Repository oraz Service.*
- **PlayedGameController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie dzieje się w metodach z Service.*
- **PlayedGameRepository.java** -> *Wyszukiwanie oraz filtrowanie oprarte na Query bezpośrednio do MongoDB.*
- **PlayedGameService.java** -> *Zbyt wiele zmian aby opisywać.*
- **PlayedService.java** -> *Rozdzielone na Repository oraz Service.*

## User:
### Nowe:

- **GameList.java** -> *Struktura zawierająca listę gier.*
- **GameService.convertGameListToDTO()** -> *Transformacja struktury z listą gier na odpowiadające DTO.*
- **UserList.java** -> *Struktura zawierająca listę użytkowników.*
- **UserService.updateUser()** -> *Obejmuje wewnętrzną funkcję updatującą.*
- **UserService.addGameToUser()** -> *Dodaje grę do listy gier użytkownika oraz użytkonika do listy graczy gry.*

### Zmiany:

- **UserController.java** -> *Uproszczono requesty; Logika oraz wyszukiwanie przeniesione do Service.*
- **UserLogin.java** -> *Przeniesione z package DTO do package object.*
- **UserRegister.java** -> *Przeniesione z package DTO do package object.*