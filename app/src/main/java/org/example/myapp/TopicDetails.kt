package org.example.myapp

data class TopicDetail(
    val title: String,
    val description: String,
    val origin: String,
    val quote: String
)

val topicDetails = listOf(
    TopicDetail(
        title = "Algebraic Expressions",
        description = "In mathematics, an algebraic expression is an expression built up from integer constants, variables, and the algebraic operations (addition, subtraction, multiplication, division and exponentiation by an exponent that is a rational number).",
        origin = "The word 'algebra' is derived from the Arabic word 'al-jabr,' which appears in the title of a book, 'Kitab al-jabr wa al-muqabala' (The Compendious Book on Calculation by Completion and Balancing), written by the Persian mathematician Muhammad ibn Musa al-Khwarizmi in the 9th century.",
        quote = "\"The art of algebra is the art of solving equations.\" - G. H. Hardy"
    ),
    TopicDetail(
        title = "Exponents",
        description = "Exponentiation is a mathematical operation, written as bⁿ, involving two numbers, the base b and the exponent or power n, and pronounced as 'b raised to the power of n'.",
        origin = "The concept of exponents was first introduced by the ancient Greeks, but the modern notation was developed by René Descartes in the 17th century.",
        quote = "\"The magical thing about notation is that it allows you to think about things that you couldn't think about otherwise.\" - Leslie Lamport"
    ),
    TopicDetail(
        title = "Number Patterns",
        description = "In mathematics, a number pattern is a pattern in a sequence of numbers. This pattern generally establishes a common relationship between all numbers.",
        origin = "The study of number patterns dates back to ancient civilizations, with notable contributions from Greek mathematicians like Pythagoras.",
        quote = "\"Mathematics is the science of patterns, and we study patterns to understand the world around us.\" - Ian Stewart"
    ),
    TopicDetail(
        title = "Equations and Inequalities",
        description = "In mathematics, an equation is a statement that asserts the equality of two expressions, while an inequality is a relation which makes a non-equal comparison between two numbers or other mathematical expressions.",
        origin = "The earliest evidence of equations is from ancient Egypt, but the systematic study of equations began with the ancient Greeks.",
        quote = "\"An equation for me is something for a thought to breathe in.\" - Albert Einstein"
    ),
    TopicDetail(
        title = "Trigonometry",
        description = "Trigonometry is a branch of mathematics that studies relationships between side lengths and angles of triangles.",
        origin = "The origins of trigonometry can be traced to the Hellenistic world during the 3rd century BC.",
        quote = "\"There is geometry in the humming of the strings, there is music in the spacing of the spheres.\" - Pythagoras"
    ),
    TopicDetail(
        title = "Functions",
        description = "In mathematics, a function is a binary relation between two sets that associates every element of the first set to exactly one element of the second set.",
        origin = "The concept of a function was developed in the 17th century by mathematicians such as René Descartes, Isaac Newton, and Gottfried Wilhelm Leibniz.",
        quote = "\"The concept of a function is one of the most important in all of mathematics.\" - Richard Courant"
    ),
    TopicDetail(
        title = "Euclidean Geometry",
        description = "Euclidean geometry is a mathematical system attributed to Alexandrian Greek mathematician Euclid, which he described in his textbook on geometry: the Elements.",
        origin = "Euclid's Elements is one of the most influential works in the history of mathematics, serving as the main textbook for teaching mathematics from the time of its publication until the late 19th or early 20th century.",
        quote = "\"The laws of nature are but the mathematical thoughts of God.\" - Euclid"
    ),
    TopicDetail(
        title = "Analytical Geometry",
        description = "In classical mathematics, analytic geometry, also known as coordinate geometry or Cartesian geometry, is the study of geometry using a coordinate system.",
        origin = "Analytic geometry is widely attributed to René Descartes, who made its importance manifest in 1637 in his 'Discourse on the Method.'",
        quote = "\"Each problem that I solved became a rule which served afterwards to solve other problems.\" - René Descartes"
    ),
    TopicDetail(
        title = "Finance and Growth",
        description = "Financial mathematics is a field of applied mathematics, concerned with mathematical modeling of financial markets. It is also known as quantitative finance.",
        origin = "The field has its origins in the work of Louis Bachelier in the early 20th century, but has grown rapidly in recent decades.",
        quote = "\"The secret of getting ahead is getting started.\" - Mark Twain"
    ),
    TopicDetail(
        title = "Statistics",
        description = "Statistics is the discipline that concerns the collection, organization, analysis, interpretation, and presentation of data.",
        origin = "The modern field of statistics emerged in the late 19th and early 20th century in three stages.",
        quote = "\"In God we trust; all others must bring data.\" - W. Edwards Deming"
    ),
    TopicDetail(
        title = "Probability",
        description = "Probability is the branch of mathematics concerning numerical descriptions of how likely an event is to occur, or how likely it is that a proposition is true.",
        origin = "The theory of probability was developed in the 17th century by Blaise Pascal and Pierre de Fermat.",
        quote = "\"The theory of probability is at bottom nothing but common sense reduced to calculation.\" - Pierre-Simon Laplace"
    ),
    TopicDetail(
        title = "Calculus",
        description = "Calculus is the mathematical study of continuous change, in the same way that geometry is the study of shape and algebra is the study of generalizations of arithmetic operations.",
        origin = "Calculus was developed in the latter half of the 17th century by two mathematicians, Isaac Newton and Gottfried Wilhelm Leibniz, independently of each other.",
        quote = "\"If I have seen further it is by standing on the shoulders of Giants.\" - Isaac Newton"
    )
)
