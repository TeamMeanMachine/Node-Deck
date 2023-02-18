package org.team2471.frc.nodeDeck

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane

class AutoPiecesGrid(title: String, preLoaded: Boolean = false): GridPane() {

    val coneOrCubeSelector = ComboBox<String>()
    val locationSelector = ComboBox<String>()
    val titleLabel = Label(title)
    val nodeValue: Int
        get() = nodeValue()

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
    fun nodeValue(): Int {
        var n = 0
        //logic to find out the node value for autos
        if (!NTClient.isRed) {
            n += 27
        }
        if (AutoConfig.isStartingLeft)  {
            n += 21
            if (coneOrCubeSelector.value == "Cone") {
                n += 3
            }
        } else {
            if (coneOrCubeSelector.value == "Cube") {
                n += 3
            }
        }
        if (locationSelector.value == "Middle") {
            n += 1
        } else if (locationSelector.value == "Bottom") {
            n += 2
        }
        return n
    }
}

//todo: Button selector instead of dropdown, color coding, bigger text