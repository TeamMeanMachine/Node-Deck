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


    init {
        println("NodeSelector says hi!")

//        spacerLeft.setPrefSize(9999.9, 0.0)
//        spacerRight.setPrefSize(9999.9, 0.0)

        one.setPrefSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnMousePressed {

        }

        two.setPrefSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ffff"
        two.setOnMousePressed {

        }

        three.setPrefSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnMousePressed {

        }

        four.setPrefSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnMousePressed {

        }

        five.setPrefSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ffff"
        five.setOnMousePressed {

        }

        six.setPrefSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnMousePressed {

        }

        seven.setPrefSize(buttonSideLength, buttonSideLength / 2)
        seven.style = "-fx-background-color: #595959"
        seven.setOnMousePressed {

        }

        eight.setPrefSize(buttonSideLength, buttonSideLength / 2)
        eight.style = "-fx-background-color: #595959"
        eight.setOnMousePressed {

        }

        nine.setPrefSize(buttonSideLength, buttonSideLength / 2)
        nine.style = "-fx-background-color: #595959"
        nine.setOnMousePressed {

        }

        NodeSelector.addRow(2, one, two, three)
        NodeSelector.addRow(3, four, five, six)
        NodeSelector.addRow(1, seven, eight, nine)

        NodeSelector.alignment = Pos.CENTER
    }
}