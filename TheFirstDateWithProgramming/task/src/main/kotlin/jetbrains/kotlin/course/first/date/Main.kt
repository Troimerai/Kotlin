package jetbrains.kotlin.course.first.date

fun main() {
    println("Hello! I will ask you several questions.")
    println("Please answer all of them and be honest with me!")

    // **1. Ask and store TROTEN question and answer:**
    println("What is TROTEN?")
    val firstUserAnswer = readlnOrNull() // Read and store user's answer

    // **2. Ask and store graduation question and answer:**
    println("How did you spend your graduation?")
    val secondUserAnswer = readlnOrNull() // Read and store user's answer

    // **3. Ask and store spider legs question and answer:**
    println("Why does a spider need eight legs?")
    val thirdUserAnswer = readlnOrNull() // Read and store user's answer

    // **4. Print thank you message and display answers:**
    println("Thank you for answering! Here are your responses:")
    println("1. What is TROTEN? - $firstUserAnswer")
    println("2. How did you spend your graduation? - $secondUserAnswer")
    println("3. Why does a spider need eight legs? - $thirdUserAnswer")
}
