package org.example

import java.awt.*
import java.awt.event.*
import javax.swing.*

class App : JFrame(), ActionListener {

	private var button1: JButton
	private var button2: JButton
	private var label: JLabel
	private var labelA = Array(5) { JLabel() }
	private var textField = Array(5) { JTextField() }

	init {
		title = "Coin Flip Game"
		defaultCloseOperation = EXIT_ON_CLOSE
		setSize(500, 200)
		setLocation(40, 110)
		isResizable = false

		val cp: Container = contentPane
		cp.layout = GridLayout(3, 1)

		val panel1 = JPanel().apply {
			layout = GridLayout(1, 5, 5, 0)
			background = Color.GRAY
			for (i in 0 until 5) {
				textField[i].apply {
					horizontalAlignment = JTextField.CENTER
					isOpaque = false
					isEditable = false
				}
			}
			for (tf in textField) add(tf)
		}

		val panel2 = JPanel().apply {
			layout = GridLayout(1, 5, 5, 0)
			background = Color.GRAY
			for (lbl in labelA) add(lbl)
		}

		val panel3 = JPanel().apply {
			layout = GridLayout(1, 3, 5, 0)
			background = Color.GRAY
			button1 = JButton("Enter Guesses").apply { addActionListener(this@App) }
			button2 = JButton("New game").apply { addActionListener(this@App) }
			label = JLabel("Score: 0").apply { horizontalAlignment = JLabel.CENTER }
			add(button1)
			add(button2)
			add(label)
		}

		cp.add(panel1)
		cp.add(panel2)
		cp.add(panel3)

		isVisible = true
	}

	private fun coinToss(): String {
		return if ((1..2).random() == 1) "Heads" else "Tails"
	}

	override fun actionPerformed(e: ActionEvent) {
		var score = 0

		when (e.source) {
			button1 -> {
				button1.isEnabled = false
				var i = 0
				while (i < 5) {
					val string = JOptionPane.showInputDialog(rootPane, "Enter guess")
					if (string.equals("Heads", true) || string.equals("H", true) ||
						string.equals("Tails", true) || string.equals("T", true)
					) {
						textField[i].text = string
						i++
					} // invalid input will retry the same index
				}

				for (j in 0 until 5) {
					val result = coinToss()
					labelA[j].text = result
					val guess = textField[j].text
					textField[j].isOpaque = true
					textField[j].background = if (
						(result == "Heads" && (guess.equals("Heads", true) || guess.equals(
							"H",
							true
						))) ||
						(result == "Tails" && (guess.equals("Tails", true) || guess.equals(
							"T",
							true
						)))
					) {
						score++
						Color.GREEN
					} else {
						Color.RED
					}
				}
				label.text = "Score: $score"
			}

			button2 -> {
				// Reset guesses
				for (i in 0 until 5) {
					textField[i].text = ""
					textField[i].background = Color.GRAY
					textField[i].isOpaque = false
					labelA[i].text = ""
				}
				// Reset buttons and score
				button1.isEnabled = true
				label.text = "Score: 0"
			}
		}
	}
}

fun main() {
	App()
}
