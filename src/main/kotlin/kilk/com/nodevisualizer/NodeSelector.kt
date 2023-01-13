package kilk.com.nodevisualizer

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region

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
    val buttonSideLength: Double = 250.0
    private val spacerLeft = Region()
    private val spacerRight = Region()


    init {
        println("NodeSelector says hi!")

//        spacerLeft.setPrefSize(9999.9, 0.0)
//        spacerRight.setPrefSize(9999.9, 0.0)
        NodeSelector.

        one.setMinSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnMousePressed {

        }

        two.setMinSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ffff"
        two.setOnMousePressed {

        }

        three.setMinSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnMousePressed {

        }

        four.setMinSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnMousePressed {

        }

        five.setMinSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ffff"
        five.setOnMousePressed {

        }

        six.setMinSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnMousePressed {

        }

        seven.setMinSize(buttonSideLength, buttonSideLength / 2)
        seven.style = "-fx-background-color: #595959"
        seven.setOnMousePressed {

        }

        eight.setMinSize(buttonSideLength, buttonSideLength / 2)
        eight.style = "-fx-background-color: #595959"
        eight.setOnMousePressed {

        }

        nine.setMinSize(buttonSideLength, buttonSideLength / 2)
        nine.style = "-fx-background-color: #595959"
        nine.setOnMousePressed {

        }

        NodeSelector.addRow(1, one, two, three)
        NodeSelector.addRow(2, four, five, six)
        NodeSelector.addRow(3, seven, eight, nine)

        NodeSelector.alignment = Pos.CENTER
    }
}