package lab01;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import lab01.IpmaCityForecast;
import lab01.IpmaService;

import java.util.logging.Logger;
import java.util.Scanner;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    private static final int CITY_ID_AVEIRO = 1010500;
    /*
     * loggers provide a better alternative to System.out.println
     * https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */
    private static final Logger logger = Logger.getLogger(WeatherStarter.class.getName());

    public static void main(String[] args) {

        /*
         * get a retrofit instance, loaded with the GSon lib to convert JSON into
         * objects
         * 
         * 
         */

        System.out.println("Insert Key ID");
        Scanner scan = new Scanner(System.in);
        int CITY_ID = scan.nextInt();
        System.out.println("The City ID is" + CITY_ID);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        IpmaService service = retrofit.create(IpmaService.class);
        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID_AVEIRO);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                logger.info("max temp for today: " + forecast.getData().listIterator().next().getTMax());
            } else {
                logger.info("No results!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
