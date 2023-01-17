package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

object NodeSelector : GridPane() {
    val one = Button("", ImageView(Image("cone-icon.png")))
    val two = Button("", ImageView(Image("cube-icon.png")))
    val three = Button("", ImageView(Image("cone-icon.png")))
    val four = Button("", ImageView(Image("cone-icon.png")))
    val five = Button("", ImageView(Image("cube-icon.png")))
    val six = Button("", ImageView(Image("cone-icon.png")))
    val seven = Button("")
    val eight = Button("")
    val nine = Button("")
    val buttonSideLength: Double = 400.0
    var selectedNodeButton: Button = one
    var selectedNode: Double = 1.0


    init {
        println("NodeSelector says hi!")

        one.setPrefSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 1.0
            } else selectedNode = 3.0
            changeSelectedNodeButton(one)
            InformationPanel.updateInfoPanel()
        }

        two.setPrefSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ff"
        two.setOnMousePressed {
            selectedNode = 2.0
            changeSelectedNodeButton(two)
            InformationPanel.updateInfoPanel()
        }

        three.setPrefSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 3.0
            } else selectedNode = 1.0
            changeSelectedNodeButton(three)
            InformationPanel.updateInfoPanel()
        }

        four.setPrefSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 4.0
            } else selectedNode = 6.0
            changeSelectedNodeButton(four)
            InformationPanel.updateInfoPanel()
        }

        five.setPrefSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ff"
        five.setOnMousePressed {
            selectedNode = 5.0
            changeSelectedNodeButton(five)
            InformationPanel.updateInfoPanel()
        }

        six.setPrefSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 6.0
            } else selectedNode = 4.0
            changeSelectedNodeButton(six)
            InformationPanel.updateInfoPanel()
        }

        seven.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        seven.style = "-fx-background-color: #595959"
        seven.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 7.0
            } else selectedNode = 9.0
            changeSelectedNodeButton(seven)
            InformationPanel.updateInfoPanel()
        }

        eight.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        eight.style = "-fx-background-color: #595959"
        eight.setOnMousePressed {
            selectedNode = 8.0
            changeSelectedNodeButton(eight)
            InformationPanel.updateInfoPanel()
        }

        nine.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        nine.style = "-fx-background-color: #595959"
        nine.setOnMousePressed {
            if (TableCreator.isRedAlliance) {
                selectedNode = 9.0
            } else selectedNode = 7.0
            changeSelectedNodeButton(nine)
            InformationPanel.updateInfoPanel()
        }

        NodeSelector.addRow(1, nine, eight, seven)
        NodeSelector.addRow(2, six, five, four)
        NodeSelector.addRow(3, three, two, one)
        NodeSelector.alignment = Pos.CENTER

        changeSelectedNodeButton(one)
    }
    fun changeSelectedNodeButton(thisButton: Button) {
        if (selectedNodeButton == two || selectedNodeButton == five) {
            selectedNodeButton.style = "-fx-background-color: #9900ff"
            selectedNodeButton.graphic = ImageView(Image("cube-icon.png"))
        } else if (selectedNodeButton == seven || selectedNodeButton == eight || selectedNodeButton == nine) {
            selectedNodeButton.style = "-fx-background-color: #595959"
            selectedNodeButton.graphic = ImageView()
        } else {
            selectedNodeButton.style = "-fx-background-color: #FFFF00"
            selectedNodeButton.graphic = ImageView(Image("cone-icon.png"))
        }

        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-background-color: #ff0000"

        selectedNodeButton = thisButton
    }
}