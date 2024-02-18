module org.sship.starship {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.sship.starship to javafx.fxml;
    exports org.sship.starship;
    exports org.sship.starship.commandpattern;
    opens org.sship.starship.commandpattern to javafx.fxml;
    exports org.sship.starship.player;
    opens org.sship.starship.player to javafx.fxml;
}