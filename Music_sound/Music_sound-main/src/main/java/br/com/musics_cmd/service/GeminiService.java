package br.com.musics_cmd.service;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

   //chave API
  private final String API_K = "chave";
  //prompt com formatação da resposta
  private Prompt_Gemeni prompt;

    // Construtor
    public  GeminiService() {

        GoogleAiGeminiChatModel dadosGemini = GoogleAiGeminiChatModel.builder()
                .apiKey(API_K)
                .modelName("gemini-2.5-flash")
                .build();

        this.prompt = AiServices.create(Prompt_Gemeni.class, dadosGemini);
    }

    public String  informacoesArtista (String nomeArtista) {
        try {
            return prompt.obterDados(nomeArtista);
        } catch (Exception e) {
            return "Erro ao obter informações do artista: " + e.getMessage();
        }
    }
}
