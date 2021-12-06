package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sample.Controllers.Items.BookItem;
import sample.Main;
import sample.Models.Book;
import sample.Models.Data;
import sample.Models.User;

import java.io.IOException;
import java.util.ArrayList;

public class AddBook {
    @FXML
    TextField authorField, genreField, quantityField, titleField, searchField;
    @FXML
    Label messageLabel, loggedinAs;
    @FXML
    Label friendsNumber;

    @FXML
    public void initialize() {
        ArrayList<User> filtered = new ArrayList<>(Main.loggedIn.friendsList);
        filtered.removeIf(user -> Main.loggedIn == user);
        if (filtered.size() > 0) {
            friendsNumber.setText(String.valueOf(filtered.size()));
        } else {
            friendsNumber.setText("0");
        }
        loggedinAs.setText(String.valueOf(Main.loggedIn.getName()));
        System.out.println(Main.loggedIn.friendsList.get(0));
    }

    public void searchItem() throws Exception {
        System.out.println(searchField.getText());
        if (searchField.getText().length() > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Search.fxml"));
            Parent page = loader.load();
            Search controller = loader.getController();
            controller.searchItem(Integer.parseInt(searchField.getText()));
            Main.switchScene(page);
        }
    }

    public void addBook() {
        try {
            if (Main.loggedIn.getType().equals("Librarian")) {
                messageLabel.setText("");
                String author = authorField.getText();
                String title = titleField.getText();
                String genre = genreField.getText();
                String quantity = quantityField.getText();

                Book newBook = new Book(author, title, genre, Integer.parseInt(quantity));
                Main.binaryTree.insertion(newBook);
                Main.arrayList.add(newBook);
                Data.bookInfo.add(newBook);
                Main.queue.enqueue(newBook);
                System.out.println("new print ");
                Main.binaryTree.DisplayInorder();
                messageLabel.setText("Thank you ! Book was added");
            } else {
                messageLabel.setText("Only Librarians Can Add books !");
            }

        } catch (Exception e) {
            messageLabel.setText("Failed to add the book");
            e.printStackTrace();
        }
    }

    public void clear() {
        authorField.setText("");
        genreField.setText("");
        titleField.setText("");
        quantityField.setText("");
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

    public void viewFriends() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/Friends.fxml"));
        Parent root = loader.load();
        Main.switchScene(root);
    }
}
