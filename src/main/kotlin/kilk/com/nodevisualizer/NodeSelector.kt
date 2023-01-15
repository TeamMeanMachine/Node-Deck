package kilk.com.nodevisualizer

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
    val buttonSideLength: Double = 275.0
    private var selectedButton: Button = one
    var selectedNode: Int = 1


    init {
        println("NodeSelector says hi!")

        one.setPrefSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnMousePressed {
            selectedNode = 1
            changeSelectedButtonColor(one)
            InformationPanel.updateInfoPanel()
        }

        two.setPrefSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ff"
        two.setOnMousePressed {
            selectedNode = 2
            changeSelectedButtonColor(two)
            InformationPanel.updateInfoPanel()
        }

        three.setPrefSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnMousePressed {
            selectedNode = 3
            changeSelectedButtonColor(three)
            InformationPanel.updateInfoPanel()
        }

        four.setPrefSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnMousePressed {
            selectedNode = 4
            changeSelectedButtonColor(four)
            InformationPanel.updateInfoPanel()
        }

        five.setPrefSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ff"
        five.setOnMousePressed {
            selectedNode = 5
            changeSelectedButtonColor(five)
            InformationPanel.updateInfoPanel()
        }

        six.setPrefSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnMousePressed {
            selectedNode = 6
            changeSelectedButtonColor(six)
            InformationPanel.updateInfoPanel()
        }

        seven.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        seven.style = "-fx-background-color: #595959"
        seven.setOnMousePressed {
            selectedNode = 7
            changeSelectedButtonColor(seven)
            InformationPanel.updateInfoPanel()
        }

        eight.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        eight.style = "-fx-background-color: #595959"
        eight.setOnMousePressed {
            selectedNode = 8
            changeSelectedButtonColor(eight)
            InformationPanel.updateInfoPanel()
        }

        nine.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        nine.style = "-fx-background-color: #595959"
        nine.setOnMousePressed {
            selectedNode = 9
            changeSelectedButtonColor(nine)
            InformationPanel.updateInfoPanel()
        }

        NodeSelector.addRow(1, nine, eight, seven)
        NodeSelector.addRow(2, six, five, four)
        NodeSelector.addRow(3, three, two, one)
        NodeSelector.alignment = Pos.CENTER

        changeSelectedButtonColor(one)
    }
    private fun changeSelectedButtonColor(thisButton: Button) {
        if (selectedButton == two || selectedButton == five) {
            selectedButton.style = "-fx-background-color: #9900ff"
            selectedButton.graphic = ImageView(Image("cube-icon.png"))
        } else if (selectedButton == seven || selectedButton == eight || selectedButton == nine) {
            selectedButton.style = "-fx-background-color: #595959"
            selectedButton.graphic = ImageView()
        } else {
            selectedButton.style = "-fx-background-color: #FFFF00"
            selectedButton.graphic = ImageView(Image("cone-icon.png"))
        }

        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-background-color: #ff0000"

        selectedButton = thisButton
    }
}