package jetbrains.kotlin.course.hangman
import java.util.Random

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess: String): Boolean {
    // Remove spaces from currentGuess using the replace function
    val guessWithoutSpaces = currentGuess.replace(separator, "")

    // Compare secret with the modified guessWithoutSpaces
    return secret == guessWithoutSpaces
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    val newWordBuilder = StringBuilder()

    // Loop through each character in the secret word
    for (i in secret.indices) {
        if (secret[i] == guess) {
            // If the guess matches the secret letter, add the secret letter
            newWordBuilder.append(secret[i])
        } else {
            // Otherwise, add the corresponding character from the current guess word
            newWordBuilder.append(currentUserWord[i * 2])
        }

        // Add a separator after each character
        if (i < secret.lastIndex) {
            newWordBuilder.append(separator)
        }
    }

    // Remove the extra separator at the end
    return newWordBuilder.toString().removeSuffix(separator)
}

fun generateSecret(): String {
    // Get a random index within the range of the words list
    val randomIndex = Random().nextInt(words.size)

    // Access the random word at the generated index
    return words[randomIndex]
}

fun getHiddenSecret(wordLength: Int): String {
    // Create a list of underscores with the specified length
    val underscores = List(wordLength) { underscore }

    // Join the underscores with spaces as separators
    return underscores.joinToString(separator)
}

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }

    if (!userInput[0].isLetter()) {
        println("You should input only English letters! Try again!")
        return false
    }

    return true
}

fun safeUserInput(): Char {
    println("Please input your guess:")

    while (true) {
        val guess = safeReadLine() ?: continue // Continue loop if input is null

        if (isCorrectInput(guess)) {
            return guess.uppercase()[0] // Return uppercase first letter
        } else {
            println("Invalid input. Please try again.")
        }
    }
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    // Check if the guess is in the secret word
    if (!secret.contains(guess)) {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
        return currentUserWord // No change in current word
    }

    // Generate the new word with the updated guess
    val newWord = generateNewUserWord(secret, guess, currentUserWord)
    println("Great! This letter is in the word! The current word is $newWord")

    return newWord // Return the updated word
}

fun playGame(secret: String, maxAttemptsCount: Int) {
    var currentWord = generateSecret()
    var attemptCount = 0

    println("Welcome to Hangman!")
    println("You have $maxAttemptsCount attempts to guess the word.")
    println("The word is ${currentWord.length} letters long.")

    while (true) {
        println("\nCurrent word: $currentWord")

        val guess = safeUserInput()

        val newWord = getRoundResults(secret, guess, currentWord)
        currentWord = newWord

        if (currentWord == secret) {
            println("\nCongratulations! You guessed the word: $secret")
            return
        } else if (attemptCount == maxAttemptsCount - 1) {
            println("\nSorry, you lost! The word was: $secret")
            return
        } else {
            val attemptsLeft = maxAttemptsCount - attemptCount - 1
            println("You have $attemptsLeft attempts left.")
            attemptCount++
        }
    }
}

fun main() {
    val secret = generateSecret()
    val maxAttemptsCount = 8
    playGame(secret, maxAttemptsCount)
}