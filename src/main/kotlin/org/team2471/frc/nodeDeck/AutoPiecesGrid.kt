package org.team2471.frc.nodeDeck

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane

class AutoPiecesGrid(title: String, preLoaded: Boolean = false): GridPane() {
    val coneOrCubeSelector = ComboBox<String>()
    val locationSelector = ComboBox<String>()
    val titleLabel = Label(title)
    init {
        if (preLoaded) {
            titleLabel.text = title + " (preloaded)"
        }
        titleLabel.style = "-fx-font-weight: bold; -fx-font-size: 15px"
        coneOrCubeSelector.items.addAll("Cone", "Cube")
        locationSelector.items.addAll("Top", "Middle", "Bottom")

        addColumn(0, titleLabel, coneOrCubeSelector, locationSelector)
        style = "-fx-border-size: 2 2 2 2; -fx-border-color: black"
        setPrefSize(100.0, 100.0)
    }
}

//todo: Button selector instead of dropdown, color coding, bigger text