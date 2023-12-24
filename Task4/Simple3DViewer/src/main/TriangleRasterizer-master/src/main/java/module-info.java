module ru.vsu.cg.trianglerasterizer {
    requires javafx.controls;
    requires javafx.fxml;

    exports ru.vsu.cs.cg.rasterizerdemo.drawers;
    opens ru.vsu.cs.cg.rasterizerdemo.drawers to javafx.fxml;
    exports ru.vsu.cs.cg.rasterizerdemo.applications;
    opens ru.vsu.cs.cg.rasterizerdemo.applications to javafx.fxml;
    exports ru.vsu.cs.cg.rasterizerdemo.controllers;
    opens ru.vsu.cs.cg.rasterizerdemo.controllers to javafx.fxml;
}