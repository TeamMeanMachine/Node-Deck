package org.team2471.frc.nodeDeck.dynamicPanes

import com.google.gson.Gson
import javafx.application.Platform
import org.team2471.frc.nodeDeck.dynamicResources.toLinearFXPath
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.stage.Screen
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.generatedPath
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.generatedPath2D
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odometryPath
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odometryPath2D
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.updateFieldPane
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.updateGenAnimation
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.updateOdomAnimation
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.settingsImage
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.dynamicPanes.SettingsPane.settingsPopup
import java.io.File

object FilePane {
    var fileScrollPane = ScrollPane()
    var filePane = Pane()

    var refreshImage = ImageView(Image("refresh-icon.png", settingsImage.fitWidth, settingsImage.fitWidth, true, false))

    var refreshButton = Button()



    init {
        filePane.background = Background.fill(DynamicTab.backgroundColor)

        fileScrollPane.background = Background.EMPTY

        fileScrollPane.layoutX = fieldPane.width
        fileScrollPane.layoutY = fieldPane.height
        fileScrollPane.content = filePane
        fileScrollPane.isFitToWidth = true

        refreshButton.background = Background.EMPTY
        refreshButton.graphic = refreshImage
        refreshButton.layoutX = Screen.getPrimary().bounds.width - fieldPane.width - 40 - refreshImage.fitWidth - 10

        filePane.children.addAll(
            refreshButton
        )

        refreshButton.setOnAction {
            if (!settingsPopup.isShowing) {
                filePaneUpdate()
            }
        }


    }

    // TODO: Fix refresh button to call filePaneUpdate
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

                loadButton.setOnMouseClicked {
                    if (!settingsPopup.isShowing) {
                        genTransAnimation.stop()
                        odomTransAnimation.stop()
                        println("Loading path")
                        var file = File(pane.accessibleText)
                        generatedPath2D = Path2D.fromJsonString(file.readText())

                        generatedPath = generatedPath2D.toLinearFXPath()

                        file = File(pane.accessibleText.replace("generated", "odometry"))
                        odometryPath2D = Path2D.fromJsonString(file.readText())

                        odometryPath = odometryPath2D.toLinearFXPath()

                        println("${odometryPath2D.duration}******************************************")

                        updateFieldPane()
                        updateGenAnimation()
                        updateOdomAnimation()
//                    FieldPane.generatedPath = FieldPane.odometryPath
//                    FieldPane.generatedPath2D = FieldPane.odometryPath2D
//                    updateGenAnimation()
                    }
                }

                loadImage.fitHeight = (yPosIncrement * 0.75)
                loadImage.fitWidth = ((yPosIncrement * 0.75) / 1024) * 615

                pane.accessibleText = it.absolutePath

                if (fileName.startsWith("generated_2d_path")) {
                    fileName = fileName.removePrefix("generated_2d_path")
                    labelText = "Path-"


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
                    Platform.runLater {
                        filePane.children.add(pane)
                    }
                }



                yPos += yPosIncrement
            }

        }
    }
}
