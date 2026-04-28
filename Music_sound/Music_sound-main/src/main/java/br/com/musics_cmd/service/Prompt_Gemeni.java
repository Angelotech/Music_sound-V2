package br.com.musics_cmd.service;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService /*obrigatorio*/
public interface Prompt_Gemeni {
    @SystemMessage("Você é um extrator de dados musicais rigoroso.\n" +
                   "        Sua resposta deve conter APENAS as 3 melhors musicas e mais famosas, e descrição do artista de forma resumida.\n" +
                   "        Não use frases de cortesia, não diga 'Aqui está' e não adicione nada a mais do que foi pedido.\n" +
                   "        Siga exatamente este formato:\n" +
                   "       descrição: com a respota da pergunta solicitada\n" +
                   "       melhores musicas: com a respota da pergunta solicitada.")
    String obterDados(String nomeArt);


}
