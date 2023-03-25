package org.team2471.frc.nodeDeck

//I asked ChatGPT to create this LOL!!!

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import java.io.OutputStream
import java.io.PrintStream


object OutTerminal : StackPane() {
    var MAX_ITEMS = 10 // Set a limit for the number of items in the out terminal
    private var outputList: ListView<String>? = null // Create a ListView to display output
    private var outputItems: ObservableList<String>? = null // Create an ObservableList to store output items
    init {
        outputItems = FXCollections.observableArrayList() // Initialize the ObservableList
        outputList = ListView(outputItems) // Initialize the ListView with the ObservableList

        // Redirect System.out and System.err to the output ListView
        val outStream = PrintStream(Console(outputItems))
        System.setOut(outStream)
        System.setErr(outStream)

        // Create a StackPane layout and add the output ListView to it
        OutTerminal.children.add(outputList)

        // Print some output to the out terminal
        println("Hello, world!")
        println("This is an out terminal.")
    }
}

// Custom OutputStream class to redirect output to the ListView
internal class Console(private val outputItems: ObservableList<String>?) : OutputStream() {
    private val sb: StringBuilder = StringBuilder()

    override fun write(b: Int) {
        sb.append(b.toChar())
        if (b == '\n'.code) {
            val text = sb.toString()
            outputItems!!.add(text)
            sb.setLength(0) // Clear the StringBuilder
            if (outputItems.size > OutTerminal.MAX_ITEMS) {
                outputItems.removeAt(0) // Remove the oldest item if the limit is reached
            }
        }
    }
}
