module assigment.three {

    exports se.lu.ics;

    exports se.lu.ics.models;

    requires javafx.fxml;

    requires javafx.controls;

    requires transitive javafx.graphics;

    opens se.lu.ics.controllers to javafx.fxml;
}
