module com.san68bot.learningsplines {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires ejml.simple;


    opens com.san68bot.learningsplines to javafx.fxml;
    exports com.san68bot.learningsplines.app;
}