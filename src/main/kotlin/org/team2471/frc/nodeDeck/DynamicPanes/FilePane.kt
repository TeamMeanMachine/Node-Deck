package org.team2471.frc.nodeDeck.DynamicPanes

import com.google.gson.Gson
import `dynamic-functions`.toLinearFXPath
import javafx.animation.Animation
import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.updateFieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.updateGenAnimation
import org.team2471.frc.nodeDeck.DynamicTab
import java.io.File

object FilePane {
    var fileScrollPane = ScrollPane()
    var filePane = Pane()
    var gson = Gson()

    init {
        filePane.background = Background.fill(DynamicTab.backgroundColor)

        fileScrollPane.background = Background.EMPTY

        fileScrollPane.layoutX = fieldPane.width
        fileScrollPane.layoutY = fieldPane.height
        fileScrollPane.content = filePane
        fileScrollPane.isFitToWidth = true


    }

    fun filePaneUpdate() {
        var yPos = 0.0
        val yPosIncrement = 100 * FieldPane.fieldImageScale
        File("PathJSONs/").walkBottomUp().forEach {
            var pane = Pane()

            var loadButton = Button()

            var loadImage = ImageView("upload-icon.png")

            var label = Label()

            var fileName = it.name
            var labelText = ""


            if (fileName != "PathJSONs") {
                loadButton.maxHeight = yPosIncrement
                loadButton.maxWidth = 1000 * FieldPane.fieldImageScale
                loadButton.resize(yPosIncrement, 1000 * FieldPane.fieldImageScale)
                loadButton.background = Background.EMPTY

                loadImage.fitHeight = (yPosIncrement * 0.75)
                loadImage.fitWidth = ((yPosIncrement * 0.75) / 1024) * 615

                pane.accessibleText = it.absolutePath

                if (fileName.startsWith("generated_2d_path")) {
                    fileName = fileName.removePrefix("generated_2d_path")
                    labelText = "Gen-"
                }
                labelText += (fileName.take(10))
                fileName = fileName.removePrefix(fileName.take(10) + "T")
                labelText += "_" + fileName.take(5).replace("-", ":")
                label.text = labelText

                label.layoutX = loadImage.fitWidth + (75 * FieldPane.fieldImageScale)
                label.layoutY = (loadImage.fitHeight * 0.125)
                label.style = "-fx-font-weight: bold; -fx-font-size: ${loadImage.fitHeight * 0.75} px"

                loadButton.graphic = loadImage

                pane.children.addAll(
                    loadButton,
                    label
                )

                pane.layoutY = yPos

//                FOR SOME REASON THE DURATION OF THE PATH2D IS NULL
                pane.setOnMouseClicked {
                    var file = File(pane.accessibleText)
                    println(gson.fromJson(file.readText(), Path2D::class.java)._xyCurve.getPositionAtDistance(2.0))
                    FieldPane.generatedPath2D = gson.fromJson(file.readText(), Path2D::class.java)
                    FieldPane.generatedPath = FieldPane.generatedPath2D.toLinearFXPath()
                    updateFieldPane()
                    updateGenAnimation()
                    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                    FieldPane.generatedPath = FieldPane.odometryPath
//                    FieldPane.generatedPath2D = FieldPane.odometryPath2D
//                    updateGenAnimation()
                }

                filePane.children.add(pane)

                yPos += yPosIncrement
            }

        }
    }
}
