package org.team2471.frc.nodeDeck

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane

class AutoPiecesGrid(title: String, preLoaded: Boolean = false): GridPane() {
    val coneOrCubeSelector = ComboBox<String>()
    val locationSelector = ComboBox<String>()
    val titleLabel = Label(title)

    var fontSize = 40

    init {
        if (preLoaded) {
            titleLabel.text = title + " (preloaded)"
        }
        titleLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        coneOrCubeSelector.style = "-fx-font-size: $fontSize px"
        locationSelector.style = "-fx-font-size: $fontSize px"
        coneOrCubeSelector.items.addAll("Cone", "Cube")
        locationSelector.items.addAll("Top", "Middle", "Bottom")

        addColumn(0, titleLabel, coneOrCubeSelector, locationSelector)
        style = "-fx-border-size: 4 4 4 4; -fx-border-color: black"
        setPrefSize(300.0, 150.0)
    }
}

//todo: Button selector instead of dropdown, color coding, bigger text