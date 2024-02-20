package jetbrains.kotlin.course.warmup

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"
    println(getGameRules(wordLength, maxAttemptsCount, secretExample))

    val secret = generateSecret()
    playGame(secret, wordLength, maxAttemptsCount)
}
fun generateSecret() = "ABCD"
fun countPartialMatches(secret: String, guess: String): Int {
    require(secret.length == guess.length) { "Secret and guess must have the same length." }

    val allMatches = countAllMatches(secret, guess)
    val exactMatches = countExactMatches(secret, guess)

    return allMatches - exactMatches
}
fun countExactMatches(secret: String, guess: String): Int {
    require(secret.length == guess.length) { "Secret and guess must have the same length." }

    return secret.filterIndexed { index, char -> char == guess[index] }.length
}
fun countAllMatches(secret: String, guess: String): Int {
    require(secret.length == guess.length) { "Secret and guess must have the same length." }

    val matchesInGuess = guess.filter { it in secret }.length
    val matchesInSecret = secret.filter { it in guess }.length

    return minOf(matchesInGuess, matchesInSecret)
}
fun printRoundResults(secret: String, guess: String) {
    require(secret.length == guess.length) { "Secret and guess must have the same length." }

    val fullMatches = countExactMatches(secret, guess)
    val partialMatches = countPartialMatches(secret, guess)

    println("Your guess has $fullMatches full matches and $partialMatches partial matches.")
}
fun isComplete(secret: String, guess: String): Boolean = secret == guess
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return complete && attempts <= maxAttemptsCount
}
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return !complete && attempts > maxAttemptsCount
}
fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var completed = false
    var attempts = 0 // Initialize attempt counter

    do {
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine() ?: continue

        attempts++ // Increment attempt counter
        completed = isComplete(secret, guess)

        if (!completed) {
            printRoundResults(secret, guess) // Display partial and full matches
        }

    } while (!completed && attempts <= maxAttemptsCount)

    // Check win or loss and print messages
    if (isWon(completed, attempts, maxAttemptsCount)) {
        println("Congratulations! You guessed it!")
    } else {
        println("Sorry, you lost! :( My word is $secret")
    }
}
