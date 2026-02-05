package com.aura.service.service;

import com.aura.service.dto.SentimentStats;
import com.aura.service.entity.ManagedEntity;
import com.aura.service.repository.ManagedEntityRepository;
import com.aura.service.repository.MentionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

@Service
public class MockAnalyticsService implements AnalyticsService {
    HashMap<String, String> mapOfData;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final LLMService llmService;
    private final ManagedEntityRepository managedEntityRepository;
    private final MentionRepository mentionRepository;

    @Value("${llm.prompt.generate.prediction}")
    private String llmPrompt;

    public MockAnalyticsService(LLMService llmService, ManagedEntityRepository managedEntityRepository, MentionRepository mentionRepository) {
        this.llmService = llmService;
        this.managedEntityRepository = managedEntityRepository;
        this.mentionRepository = mentionRepository;
    }

    private JsonNode callLlmAndParse(String prompt) {
        try {
            String response = llmService.generateReply(prompt);
            return objectMapper.readTree(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildPrompt(LocalDate date, double sentimentScore, double positivityRatio) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfYear = date.get(weekFields.weekOfWeekBasedYear());
        String weekKey = "W" + weekOfYear;
        String historicalData = mapOfData.getOrDefault(weekKey, "No data available for this week.");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d'th'", Locale.ENGLISH);
        String formattedDate = date.format(formatter);

        return llmPrompt
                .replace("[PASTE THE DATA FROM YOUR ORIGINAL REQUEST HERE]", historicalData)
                .replace("[Insert Date]", formattedDate)
                .replace("[Insert Score]", String.valueOf(sentimentScore))
                .replace("[Insert Ratio]", String.valueOf(positivityRatio));
    }

    @PostConstruct
    public void init() {
        mapOfData = new HashMap<>();
        mapOfData.put("W1", "The first week of January is traditionally viewed as a quiet period. Major production houses " +
                "avoid this slot to ensure their films do not experience a \"burnout\" before the Pongal peak. Consequently, " +
                "this week is populated by smaller budget entries, horror-comedies, and experimental films.\n" +
                "Average Box Office Collection: ₹1.5 crore to ₹6 crore per film.\n" +
                "Opening Weekend Collection: ₹0.8 crore to ₹3.5 crore.\n" +
                "Opening Day Collection: ₹0.3 crore to ₹1.2 crore.   \n" +
                "Genre that gave the best hit: Horror / Supernatural Thriller.\n" +
                "Key factors influencing success: This week’s success is largely predicated on the absence of competition. " +
                "Films like Pei Irukka Bayamen (2021) and Aranam (2024) utilize this window to gain maximum screen " +
                "visibility before the \"mass\" releases of the second week monopolize theaters.");
        mapOfData.put("W2", "This week is the financial epicenter of the Tamil film year. It is the \"Sankranti/Pongal\" " +
                "window where the industry’s highest earners are birthed. Over the past five years, this week has seen " +
                "historical releases like Master (2021), Varisu (2023), Thunivu (2023), and Captain Miller (2024).\n" +
                "Average Box Office Collection: ₹180 crore to ₹320 crore (Cumulative Weekly Gross). Individual superstar " +
                "films such as Varisu have grossed ₹290–293 crore worldwide.   \n" +
                "Opening Weekend Collection: Top-tier films consistently cross the ₹100 crore mark globally within the " +
                "first four days.   \n" +
                "Opening Day Collection: ₹20 crore to ₹35 crore for superstars. Master (2021) achieved ₹25.40 crore on " +
                "its opening day in Tamil Nadu alone.   \n" +
                "Genre that gave the best hit: Action / Family Drama (Mass Entertainer).\n" +
                "Key factors influencing success: Star Power and Festive Sentiment. The presence of a \"Thalapathy\" " +
                "Vijay or an Ajith Kumar is the single greatest determinant of theatrical footfalls. The festive " +
                "multiplier ensures high occupancy even in early morning shows (as early as 4:00 AM), which are often " +
                "sold out at premium rates.");
        mapOfData.put("W3", "The third week of January is a period of \"holiday spillover.\" As the primary Pongal " +
                "holidays conclude, the performance of the big-budget releases becomes dependent on word-of-mouth (WOM) " +
                "and repeat footfalls.\n" +
                "Average Box Office Collection: ₹8 crore to ₹18 crore for new entries.\n" +
                "Opening Weekend Collection: ₹4 crore to ₹10 crore.\n" +
                "Opening Day Collection: ₹1.2 crore to ₹2.5 crore.   \n" +
                "Genre that gave the best hit: Rural Action / Drama.\n" +
                "Key factors influencing success: Spillover Occupancy. New releases this week often benefit from theaters " +
                "being \"overflowing\" from the second week. Films like Pulikkuthi Pandi (2021) found their audience by " +
                "appealing specifically to rural sentiment that persists throughout the harvest month of Thai. ");
        mapOfData.put("W4", "The final week of January has evolved into a secondary lucrative slot, often used for films " +
                "with \"Pan-Indian\" potential, sports themes, or socially conscious narratives.\n" +
                "Average Box Office Collection: ₹20 crore to ₹55 crore.\n" +
                "Opening Weekend Collection: ₹12 crore to ₹30 crore.\n" +
                "Opening Day Collection: ₹3.5 crore to ₹8 crore.   \n" +
                "Genre that gave the best hit: Sports Drama / Social Thriller.\n" +
                "Key factors influencing success: Youth Appeal and Holiday Boost. The Republic Day holiday provides a " +
                "critical midweek or weekend lift. Blue Star (2024) and Singapore Saloon (2024) are quintessential " +
                "examples of films that leveraged this window by targeting urban youth and college students.");
        mapOfData.put("W5", "This week is a strategic slot for big-budget films that avoided the Pongal clash or required " +
                "additional post-production time. It is a \"solo\" window that allows for an aggressive screen expansion.\n" +
                "Average Box Office Collection: ₹45 crore to ₹110 crore.\n" +
                "Opening Weekend Collection: ₹30 crore to ₹70 crore.\n" +
                "Opening Day Collection: ₹10 crore to ₹30.41 crore. Vidaamuyarchi (2025) and Valimai (2022) are the primary " +
                "benchmarks for this week.   \n" +
                "Genre that gave the best hit: Action Thriller / High-Octane Drama.\n" +
                "Key factors influencing success: Absence of Fresh Competition. By the first week of February, January’s " +
                "blockbusters have usually exhausted their primary run. A major film released this week, such as Valimai, " +
                "can secure over 1,000 screens in Tamil Nadu alone, ensuring a massive opening day regardless of reviews.");
        mapOfData.put("W6", "The second week of February is arguably the most predictable window for \"content-driven\" " +
                "hits. The audience demographic shifts toward couples and youth, favoring romance and emotional dramas.\n" +
                "Average Box Office Collection: ₹25 crore to ₹85 crore.\n" +
                "Opening Weekend Collection: ₹12 crore to ₹45 crore.\n" +
                "Opening Day Collection: ₹3 crore to ₹11 crore.   \n" +
                "Genre that gave the best hit: Feel-Good Drama / Romantic Comedy.\n" +
                "Key factors influencing success: Music and Emotional Resonance. Dada (2023) emerged as a \"sleeper hit\" " +
                "during this week, grossing significantly relative to its modest budget. The success of Lover (2024) " +
                "further solidified the trend that realistic portrayal of relationships resonates more with the February " +
                "audience than over-the-top action.");
        mapOfData.put("W7", "The third week of February often sees a mix of dubbed releases and suspense-heavy narratives. " +
                "It is a transitional period where theater owners look for \"steady earners\" rather than \"record breakers.\"\n" +
                "Average Box Office Collection: ₹12 crore to ₹35 crore.\n" +
                "Opening Weekend Collection: ₹8 crore to ₹18 crore.\n" +
                "Opening Day Collection: ₹2.5 crore to ₹6 crore.   \n" +
                "Genre that gave the best hit: Mystery / Action Thriller.\n" +
                "Key factors influencing success: Clarity of Narrative. Films like Vaathi (2023) and Siren (2024) found " +
                "their footing by offering a clear \"moral\" or \"emotional\" hook. In the case of Vaathi, the social " +
                "commentary on the education system provided the necessary traction to cross the ₹100 crore mark globally.");
        mapOfData.put("W8", "The final week of February is often the last \"window of opportunity\" before the academic " +
                "examination season in March leads to a sharp decline in theatrical attendance.\n" +
                "Average Box Office Collection: ₹15 crore to ₹155 crore (highly volatile).\n" +
                "Opening Weekend Collection: ₹10 crore to ₹55 crore.\n" +
                "Opening Day Collection: ₹3 crore to ₹28 crore.   \n" +
                "Genre that gave the best hit: Sci-Fi Comedy / Urban Thriller.\n" +
                "Key factors influencing success: Content Innovation. Valimai (2022) and Dragon (2025) are the outliers " +
                "here. Dragon specifically outperformed expectations by grossing ₹151.83 crore, beating out the lifetime " +
                "gross of Ajith's Vidaamuyarchi in the same year." +
                "\n");
    }

    @Override
    public JsonNode getAnalytics(Long movieId) {
        ManagedEntity entity = managedEntityRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + movieId));
        LocalDate date = entity.getReleaseDate();
        SentimentStats stats = mentionRepository.getSentimentStats(movieId)
                .orElse(new SentimentStats(0.0, 0.0));

        String prompt = buildPrompt(date, stats.getAverageSentimentScore(), stats.getPositiveRatio());
        return callLlmAndParse(prompt);
    }
}
