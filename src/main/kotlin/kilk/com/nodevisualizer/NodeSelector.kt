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
    val buttonSideLength: Double = 200.0
    val spacerLeft = Region()
    val spacerRight = Region()


    init {
        println("NodeSelector says hi!")


        spacerLeft.setPrefSize(9999.9, 0.0)
        spacerRight.setPrefSize(9999.9, 0.0)

        one.setMinSize(buttonSideLength, buttonSideLength)
        one.setOnMousePressed { println("one") }
        one.style = "-fx-background-color: #FFFF00"
        two.setMinSize(buttonSideLength, buttonSideLength)
        two.setOnMousePressed { println("two") }
        two.style = "-fx-background-color: #9900ffff"
        three.setMinSize(buttonSideLength, buttonSideLength)
        three.setOnMousePressed { println("three") }
        three.style = "-fx-background-color: #FFFF00"
        four.setMinSize(buttonSideLength, buttonSideLength)
        four.setOnMousePressed { println("four") }
        four.style = "-fx-background-color: #FFFF00"
        five.setMinSize(buttonSideLength, buttonSideLength)
        five.setOnMousePressed { println("five") }
        five.style = "-fx-background-color: #9900ffff"
        six.setMinSize(buttonSideLength, buttonSideLength)
        six.setOnMousePressed { println("six") }
        six.style = "-fx-background-color: #FFFF00"
        seven.setMinSize(buttonSideLength, buttonSideLength)
        seven.setOnMousePressed { println("seven") }
        eight.setMinSize(buttonSideLength, buttonSideLength)
        eight.setOnMousePressed { println("eight") }
        nine.setMinSize(buttonSideLength, buttonSideLength)
        nine.setOnMousePressed { println("nine") }

        lVbox.children.addAll(one, four, seven)
        cVbox.children.addAll(two, five, eight)
        rVbox.children.addAll(three, six, nine)

        NodeSelector.children.addAll(spacerLeft, lVbox, cVbox, rVbox, spacerRight)
    }
}