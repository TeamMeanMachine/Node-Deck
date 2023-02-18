package org.team2471.frc.nodeDeck

import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane

class AutoPiecesGrid(title: String, preLoaded: Boolean = false): GridPane() {

    val coneOrCubeSelector = ComboBox<String>()
    val locationSelector = ComboBox<String>()
    val titleLabel = Label(title)
    var nodeValue: Int? = null
        get() = nodeValue()
    val isReady: Boolean
        get() = (coneOrCubeSelector.value != null && locationSelector.value != null)

    var fontSize = 40
    val borderWidth = " 15 15 15 15" //format in " ## ## ## ##" TT RR BB LL

    init {
        if (preLoaded) {
            titleLabel.text = title + " (preloaded)"
        }
        titleLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        coneOrCubeSelector.style = "-fx-font-size: $fontSize px"
        locationSelector.style = "-fx-font-size: $fontSize px"
        coneOrCubeSelector.items.addAll("Cone", "Cube")
        locationSelector.items.addAll("Top", "Middle", "Bottom")

        println(coneOrCubeSelector.value)

        addColumn(0, titleLabel, coneOrCubeSelector, locationSelector)
        style = "-fx-border-size: $borderWidth; -fx-border-color: black"
        setPrefSize(300.0, 150.0)
    }
    fun nodeValue(): Int? {
        var n: Int? = 0
        //logic to find out the node numbers for autos
        if (n != null) {
            if (!NTClient.isRed) {
                n += 27
            }
            if (AutoConfig.isStartingLeft) {
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
            //setting border colors depending on cone, cube, or null
            if (coneOrCubeSelector.value == null) {
                style = "-fx-border-size: $borderWidth; -fx-border-color: black"
            } else if (coneOrCubeSelector.value == "Cone") {
                style = "-fx-border-size: $borderWidth; -fx-border-color: #FFFF00"
            } else {
                style = "-fx-border-size: $borderWidth; -fx-border-color: #9900ff"
            }
        }
        if (!isReady) {
            n = null
        }

        return n
    }
}

//todo: Button selector instead of dropdown, color coding, bigger text