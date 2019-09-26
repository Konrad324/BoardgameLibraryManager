package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import com.konradmikolaj.boardgamelibrarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DemoContentCreator {

    public static final String USER_1 = "user_1";
    public static final String USER_2 = "user_2";
    public static final String USER_3 = "user_3";

    public static final String PASS_1 = "pass_1";
    public static final String PASS_2 = "pass_2";
    public static final String PASS_3 = "pass_3";

    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;

    @Autowired
    public DemoContentCreator(UserRepository userRepository, BoardGameRepository boardGameRepository) {
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
    }

    public void prepare() {
        cleanDatabase();
        createUsers();
        createBoardGames();
    }

    public void cleanDatabase() {
        userRepository.deleteAll();
        boardGameRepository.deleteAll();
    }

    public void createUsers() {
        User user1 = User.of(USER_1, UserService.encodePassword(PASS_1));
        User user2 = User.of(USER_2, UserService.encodePassword(PASS_2));
        User user3 = User.of(USER_3, UserService.encodePassword(PASS_3));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private void createBoardGames() {
        BoardGame boardGame1 = BoardGame.of(USER_1, "Osadnicy z Catanu", "Gracze są osadnikami na niedawno odkrytej wyspie Catan. Każdy z nich przewodzi świeżo założonej kolonii i rozbudowuje ją stawiając na dostępnych obszarach nowe drogi i miasta. Każda kolonia zbiera dostępne dobra naturalne.", "Dom");
        BoardGame boardGame2 = BoardGame.of(USER_1, "Domek", "W ciągu dwunastu rund zbierzesz tyle kart, by zapełnić swoją planszę pokojami, wybrać kolor dachu i znaleźć miejsce na stylowe lub praktyczne dodatki, tworząc tym samym niepowtarzalny >Domek< z charakterem", USER_1);
        BoardGame boardGame3 = BoardGame.of(USER_2, "Splendor", "Gracze wcielają się w renesansowych kupców, którzy próbują nabyć kopalnie klejnotów, środki transportu, sklepy - wszystko to w celu zdobycia jak największego prestiżu.", "Dom");
        BoardGame boardGame4 = BoardGame.of(USER_2, "Magia i Miecz", "W tej pełnej przygód grze wyruszysz na niebezpieczną wyprawę po największy skarb, legendarną Koronę Władzy. Wcielisz się w wojownika, kapłana, czarnoksiężnika lub jednego z pozostałych jedenastu bohaterów władających magią lub mieczem. ", USER_2);
        BoardGame boardGame5 = BoardGame.of(USER_2, "Robinson Crusoe", "Tym razem porywamy was na bezludną wyspę i rzucamy w wir niezwyklych przygód, które przytrafią się grupie rozbitków!", "Dom");
        BoardGame boardGame6 = BoardGame.of(USER_1, "Magia i Miecz", "W tej pełnej przygód grze wyruszysz na niebezpieczną wyprawę po największy skarb, legendarną Koronę Władzy. Wcielisz się w wojownika, kapłana, czarnoksiężnika lub jednego z pozostałych jedenastu bohaterów władających magią lub mieczem. ", USER_2);
        BoardGame boardGame7 = BoardGame.of(USER_2, "Odbudowa Warszawy", "Odbudowa Warszawy 1945-1980 to wyjątkowa gra o prostych zasadach, która zapewni dobrą zabawę całej rodzinie, a jej wariant zaawansowany spodoba się również doświadczonym graczom.", "Dom");
        BoardGame boardGame8 = BoardGame.of(USER_2, "Dobble Kids", "Podstawowa zasada gry to znalezienie jak najszybciej symbolu, który łączy dwie karty. Symbol zawsze jest tylko jeden, o czym należy pamiętać i w to nie wątpić, bo nie raz się zdarza, że nijak nie można go zauważyć.", "Dom");
        BoardGame boardGame9 = BoardGame.of(USER_2, "Osadnicy z Catanu", "Gracze są osadnikami na niedawno odkrytej wyspie Catan. Każdy z nich przewodzi świeżo założonej kolonii i rozbudowuje ją stawiając na dostępnych obszarach nowe drogi i miasta. Każda kolonia zbiera dostępne dobra naturalne.", "Dom");
        boardGameRepository.save(boardGame1);
        boardGameRepository.save(boardGame2);
        boardGameRepository.save(boardGame3);
        boardGameRepository.save(boardGame4);
        boardGameRepository.save(boardGame5);
        boardGameRepository.save(boardGame6);
        boardGameRepository.save(boardGame7);
        boardGameRepository.save(boardGame8);
        boardGameRepository.save(boardGame9);
    }
}

