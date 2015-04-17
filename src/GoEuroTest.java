
/***
 * @author Tichaona Kadzinga
 * @version 1.0
 * 
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;

public class GoEuroTest {
	public static void main(String[] args) {
		int argsLength = args.length;
		// Check argument array length
		switch (argsLength) {
		case 0:
			System.out.println("Error: Too Few arguments");
		case 1:
			createCSV(args[0]);
			break;
		default:
			System.out.println("Error: Too many Arguments");
		}
	}

	private static void createCSV(String city) {
		// Create a connection to API and create csv file
		URL url;
		String jsonPattern = "(\\[|,)\\{\"_id\":(\\d+),\"key\":(.+?),\"name\":(.+?),\"fullName\":(.+?),\"iata_airport_code\":(.+?),\"type\":(.+?),\"country\":(.+?),\"geo_position\":\\{\"latitude\":(.+?),\"longitude\":(.+?)\\},\"locationId\":(.+?),\"inEurope\":(.+?),\"countryCode\":(.+?),\"coreCountry\":(.+?),\"distance\":(.+?)\\}(\\]?)";
		String requestURL = "http://api.goeuro.com/api/v2/position/suggest/en/"
				+ city;
		try {
			url = new URL(requestURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine;
			inputLine = in.readLine();
			if (inputLine.matches("\\[\\]")) {
				System.out.println("Search returned no results");
			} else {
				String csvLines = inputLine.replaceAll(jsonPattern,
						"$2,$4,$7,$9,$10\n");
				byte data[] = csvLines.getBytes();
				Path p = Paths.get("./response.csv");

				try (OutputStream out = new BufferedOutputStream(
						Files.newOutputStream(p, CREATE, APPEND))) {
					out.write(data, 0, data.length);
					System.out.println("Response written to response.csv");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			in.close();
		} catch (MalformedURLException e) {
			System.out.println("Error: Malformed url");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: IO Exception");
			e.printStackTrace();
		}

	}

}
