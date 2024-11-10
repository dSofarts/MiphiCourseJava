package Module6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class WeatherApi {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Получить прогноз погоды");

    while (true) {
      System.out.println("\nВведите широту: ");
      double lat = scanner.nextDouble();
      System.out.println("Введите долготу: ");
      double lon = scanner.nextDouble();
      System.out.println("Введите количество дней");
      int days = scanner.nextInt();

      getAndPrintTemperature(lat, lon, days);
    }
  }

  public static void getAndPrintTemperature(double lat, double lon, int limitDays) {
    try (HttpClient httpClient = HttpClient.newHttpClient()) {
      DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
      DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s&limit=%d"
              .formatted(decimalFormat.format(lat), decimalFormat.format(lon), limitDays)))
          .GET()
          .setHeader("Content-Type", "application/json")
          .setHeader("X-Yandex-Weather-Key", "0b43b0e2-7315-42cf-86ea-9f089fe1777d")
          .build();

      try {
        HttpResponse<String> response = httpClient.send(request,
            HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + responseBody);

        if (response.statusCode() == 200) {
          System.out.printf("Температура: %d °C\n", getFactTemperature(responseBody));
          System.out.printf("Средняя температура за указанные дни: %.2f °C\n",
              getAvgTemperature(responseBody));
        } else {
          System.out.println("Ошибка: код ответа " + response.statusCode());
        }

      } catch (Exception e) {
        System.err.println("Ошибка обработки ответа: " + e.getMessage());
      }
    }
  }

  private static int getFactTemperature(String body)
      throws Exception {
    String searchString = "\"temp\":";
    int tempIndex = body.indexOf(searchString);

    int startIndex = tempIndex + searchString.length();
    int endIndex = body.indexOf(",", startIndex);
    String tempString = body.substring(startIndex, endIndex).trim();

    return Integer.parseInt(tempString);
  }

  private static double getAvgTemperature(String body)
      throws Exception {
    List<Integer> temperatures = new ArrayList<>();

    String[] days = body.split("\"temp_avg\":");
    for (int i = 1; i < days.length; i++) {
      String tempStr = days[i].split(",")[0].trim();
      int temp = Integer.parseInt(tempStr);
      temperatures.add(temp);
    }

    double sum = 0;
    for (int temp : temperatures) {
      sum += temp;
    }
    return sum / temperatures.size();
  }
}
