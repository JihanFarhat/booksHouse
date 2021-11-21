package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.Models.Book;
import sample.Models.Data;

import java.io.IOException;

public class AddBook {
    @FXML
    TextField authorField, genreField, quantityField,titleField;
    @FXML
    Label messageLabel;

    public void addBook(){
        try{
            messageLabel.setText("");
            String author = authorField.getText();
            String title = titleField.getText();
            String genre = genreField.getText();
            String quantity = quantityField.getText();

            Book newBook = new Book(author , title,genre,Integer.parseInt(quantity));
            Main.binaryTree.insertion(newBook);
            Main.arrayList.add(newBook);
            Data.bookInfo.addAll(newBook);
            System.out.println("new print ");
            Main.binaryTree.DisplayInorder();
            messageLabel.setText("Thank you ! Book was added");

        }catch (Exception e){
            messageLabel.setText("Failed to add the book");
        }
    }

    public void clear(){
        authorField.setText("");
        genreField.setText("");
        titleField.setText("");
        quantityField.setText("");
    }

    public void navigate(ActionEvent event) throws IOException {
        Button b1= (Button) event.getTarget();
        try{
            if ("addBook".equals(b1.getId())) {
                FXMLLoader page = new FXMLLoader(getClass().getResource("/sample/View/AddBook.fxml"));
                Parent root = page.load();
                Main.switchScene(root);

            } else if ("BookStacks".equals(b1.getId())) {
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/BookStacks.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            }else if("ViewLatestAdditions".equals(b1.getId())){
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/ViewLatestAdditions.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            }else if("ViewOtherReaders".equals(b1.getId())){
                FXMLLoader page2 = new FXMLLoader(getClass().getResource("/sample/View/ViewOtherReaders.fxml"));
                Parent root1 = page2.load();
                Main.switchScene(root1);
            }
        }catch (Exception e){
            System.out.println("something went wrong");
        }


    }

}