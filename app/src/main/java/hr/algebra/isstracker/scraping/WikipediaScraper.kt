package hr.algebra.isstracker.scraping

import org.jsoup.Jsoup

fun fetchWikipediaDescription(wikipediaUrl: String): String {
    return try {
        val doc = Jsoup.connect(wikipediaUrl).get()

        val firstParagraph = doc.select("p:has(b)").first()
        firstParagraph?.select("a[href^=#cite_note-]")?.forEach { it.remove() }
        firstParagraph?.select("a[href^=/wiki/Help:IPA/]")?.forEach { it.remove() }

        val cleanText = firstParagraph?.text()
            ?.replace(Regex("\\s?\\(.*?\\)\\s?"), " ")
            ?.replace(Regex("\\[.*?]"), "")
            ?.replace(Regex("\\s{2,}"), " ")

        cleanText?.trim() ?: "No description available"
    } catch (e: Exception) {
        "Failed to fetch description"
    }
}