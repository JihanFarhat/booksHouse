package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import sample.Controllers.Items.BookCard;
import sample.DataStructures.Stack;
import sample.Main;
import sample.Models.Book;
import sample.Models.User;

import java.io.IOException;
import java.util.ArrayList;

public class BookStacks {


    @FXML
    TextField searchField;

    @FXML
    Label loggedinAs;

    @FXML
    VBox bookCardItem;

    @FXML
    Label friendsNumber;

    Stack stackPreserve = new Stack();
    Stack stackToView = Main.stack.copy();

    @FXML
    public void initialize() throws IOException {
        loggedinAs.setText(Main.loggedIn.getName());
        ArrayList<User> filtered = new ArrayList<>(Main.loggedIn.friendsList);
        filtered.removeIf(user -> Main.loggedIn == user);
        if (filtered.size() > 0) {
            friendsNumber.setText(String.valueOf(filtered.size()));
        } else {
            friendsNumber.setText("0");
        }
        pushToCard();

        searchField.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
                try {
                    searchItem();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        friendsNumber.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.LEFT)) {
                try {
                    pushToCard();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                if (e.getCode().equals(KeyCode.RIGHT)) {
                    try {
                        popFromCard();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public void searchItem() throws Exception {
        System.out.println(searchField.getText());
        if (searchField.getText().length() > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Search.fxml"));
            Parent page = loader.load();
            Search controller = loader.getController();
            controller.searchItem(Integer.parseInt(searchField.getText()));
            Search.bookId = searchField.getText();
            Main.switchScene(page);
        }
    }

    public void pushToCard() throws IOException {
        if (!stackToView.isEmpty()) {
            Book popedBook = stackToView.getHead();
            if (popedBook != null) {
                stackPreserve.push(stackToView.pop());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Items/book card.fxml"));
                Parent bookCard = loader.load();
                BookCard b1 = loader.getController();
                bookCardItem.getChildren().clear();
                b1.setLabels(String.valueOf(popedBook.getID()), popedBook.getTitle(), popedBook.getAuthor(), String.valueOf(popedBook.getQuantity()), popedBook.getGenre());
                bookCardItem.getChildren().add(bookCard);
            }
        }

    }

    public void popFromCard() throws IOException {
        try {
            if (!stackPreserve.isEmpty() && stackPreserve.length() > 1) {
                stackToView.push(stackPreserve.pop());
                Book popedBook = stackPreserve.getHead();
                System.out.println(popedBook);
                if (popedBook != null) {
                    bookCardItem.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Items/book card.fxml"));
                    Parent bookCard = loader.load();
                    BookCard b1 = loader.getController();
                    b1.setLabels(String.valueOf(popedBook.getID()), popedBook.getTitle(), popedBook.getAuthor(), String.valueOf(popedBook.getQuantity()), popedBook.getGenre());
                    bookCardItem.getChildren().add(bookCard);
                }
            }
        } catch (Exception e) {
            System.out.println("hello :) there was an issue popping a card");
        }
    }

    public void viewFriends() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Friends.fxml"));
        Parent root = loader.load();
        Main.switchScene(root);
    }

    public void navigate(ActionEvent event) throws IOException {
        Button b1 = (Button) event.getTarget();
        try {
            if ("addBook".equals(b1.getId())) {
                FXMLLoader page = new FXMLLoader(getClass().getResource("/sample/View/AddBook.fxml"));
                Parent root = page.load();
                Main.switchScene(root);
            } else if ("BookStacks".equals(b1.getId())) {
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/BookStacks.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            } else if ("ViewLatestAdditions".equals(b1.getId())) {
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/ViewLatestAdditions.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            } else if ("ViewOtherReaders".equals(b1.getId())) {
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/ViewOtherReaders.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            } else if ("logout".equals(b1.getId())) {
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/SignUp.fxml"));
                Parent root1 = page2.load();
                Main.restoreSize2();
                Main.switchScene(root1);
            }
        } catch (Exception e) {
            System.out.println("something went wrong");
        }
    }
}
