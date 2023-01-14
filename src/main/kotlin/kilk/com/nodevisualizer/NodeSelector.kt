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


    init {
        println("NodeSelector says hi!")

        one.setPrefSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnMousePressed {
            if (NodeVisualizer.isRedAlliance) {
                NodeVisualizer.isRedAlliance = false
                ColorOutline.checkAlliance()
            } else {
                NodeVisualizer.isRedAlliance = true
                ColorOutline.checkAlliance()
            }
            changeSelectedButton(one)
        }

        two.setPrefSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ff"
        two.setOnMousePressed {
            changeSelectedButton(two)
        }

        three.setPrefSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnMousePressed {
            changeSelectedButton(three)
        }

        four.setPrefSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnMousePressed {
            changeSelectedButton(four)
        }

        five.setPrefSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ff"
        five.setOnMousePressed {
            changeSelectedButton(five)
        }

        six.setPrefSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnMousePressed {
            changeSelectedButton(six)
        }

        seven.setPrefSize(buttonSideLength, buttonSideLength / 2)
        seven.style = "-fx-background-color: #595959"
        seven.setOnMousePressed {
            changeSelectedButton(seven)
        }

        eight.setPrefSize(buttonSideLength, buttonSideLength / 2)
        eight.style = "-fx-background-color: #595959"
        eight.setOnMousePressed {
            changeSelectedButton(eight)
        }

        nine.setPrefSize(buttonSideLength, buttonSideLength / 2)
        nine.style = "-fx-background-color: #595959"
        nine.setOnMousePressed {
            changeSelectedButton(nine)
        }

        NodeSelector.addRow(2, one, two, three)
        NodeSelector.addRow(3, four, five, six)
        NodeSelector.addRow(1, seven, eight, nine)

        NodeSelector.alignment = Pos.CENTER
    }
    private fun changeSelectedButton(thisButton: Button) {
        if (selectedButton == two || selectedButton == five) {
            selectedButton.style = "-fx-background-color: #9900ff"
        } else if (selectedButton == seven || selectedButton == eight || selectedButton == nine) {
            selectedButton.style = "-fx-background-color: #595959"
        } else {
            selectedButton.style = "-fx-background-color: #FFFF00"
        }
        thisButton.style = "-fx-background-color: #ff0000"
        selectedButton = thisButton
    }
}