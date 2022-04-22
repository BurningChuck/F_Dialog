package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.nio.charset.StandardCharsets.UTF_8;
public class Bot {

    private static final Map<String, String> replacePronounMap = new HashMap<>();


    private static final List<Message> responses = new ArrayList<>();
    static void init() {
        replacePronounMap.put("был", "были");
        replacePronounMap.put("я", "вы");
        replacePronounMap.put("мой", "ваш");
        replacePronounMap.put("буду", "будете");
        replacePronounMap.put("были", "был");
        replacePronounMap.put("ты", "я");
        replacePronounMap.put("вы", "я");
        replacePronounMap.put("ваш", "мой");
        replacePronounMap.put("твой", "мой");

        responses.add(new Message("я нуждаюсь в (.*)", Arrays.asList("Почему ты нуждаешься в {0}?", "Правда ли тебе поможет {0}?", "Ты уверен что ты нуждаешься в{0}?")));
        responses.add(new Message("хочу (.*)", Arrays.asList("Я не дам тебе {0}", "Ты думаешь я производитель {0}", "Ты че, какие {0}?")));
        responses.add(new Message("бот (.*)", Arrays.asList("А может ты бот?", "Кто сказал что я {0}?")));
        responses.add(new Message("вы ([^\\?]*)\\??", Arrays.asList("Почему тебя волнует {0}?", "Тебе бы хотелось, чтобы я не был {0}?")));
        responses.add(new Message("что (.*)", Arrays.asList("Почему ты спрашиваешь?", "Как бы ты ответил на такой вопрос?", "Вопрос не по теме нашего разговора")));
        responses.add(new Message("как (.*)", Arrays.asList("ужс", "жуть", "{0}? почти нормально, почти большое")));
        responses.add(new Message("Потому (.*)", Arrays.asList("Это правдивая причина?", "Какая другая причина приходит на ум?")));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            init();
            System.out.print("Вы: ");
            String message = r.readLine();
            System.out.print("Бот: ");
            String answer = getResponse(message);
            System.out.println(answer);
        }
    }

    private static String getResponse(String text) throws IOException {
        for (int i = 0; i < responses.size(); i++) {
            Message response = responses.get(i);
            Pattern pattern = Pattern.compile(response.getRegex());
            Matcher matcher = pattern.matcher(text.toLowerCase());
            if (matcher.matches()) {
                String replace = matcher.group(1).toLowerCase();
                String fullReplace = replace;
                List<String> collect = replacePronounMap.keySet().stream()
                        .filter(k -> replace.contains(" " + k + " "))
                        .collect(Collectors.toList());
                for (int j = 0; j < collect.size(); j++) {
                    fullReplace = replace.replaceAll(collect.get(j), replacePronounMap.get(collect.get(j)));
                }
                return responses.get(i).getResponses().get((int)(Math.random() * responses.get(i).getResponses().size())).replaceAll("\\{0}", fullReplace);
            }
        }
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Салех\\Desktop\\F_Dialog\\\\phrases.txt"), UTF_8);
        Random r = new Random();
        return lines.get(r.nextInt(lines.size()));

    }
}
