package kilk.com.nodevisualizer

import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.VBox

object NodeSelector : HBox(5.0) {
    val lVbox = VBox(5.0)
    val cVbox = VBox(5.0)
    val rVbox = VBox(5.0)
    val one = Button("", ImageView(Image("cone-icon.png")))
    val two = Button("", ImageView(Image("cube-icon.png")))
    val three = Button("", ImageView(Image("cone-icon.png")))
    val four = Button("", ImageView(Image("cone-icon.png")))
    val five = Button("", ImageView(Image("cube-icon.png")))
    val six = Button("", ImageView(Image("cone-icon.png")))
    val seven = Button("")
    val eight = Button("")
    val nine = Button("")
    val buttonSideLength: Double = NodeVisualizer.screen.visualBounds.height / 3
    private val spacerLeft = Region()
    private val spacerRight = Region()


    init {
        println("NodeSelector says hi!")

        spacerLeft.setPrefSize(9999.9, 0.0)
        spacerRight.setPrefSize(9999.9, 0.0)

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

        lVbox.children.addAll(one, four, seven)
        cVbox.children.addAll(two, five, eight)
        rVbox.children.addAll(three, six, nine)

        NodeSelector.children.addAll(spacerLeft, lVbox, cVbox, rVbox, spacerRight)
    }
}