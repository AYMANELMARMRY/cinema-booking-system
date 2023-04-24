package booking;

import booking.models.Booking;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that allows the sending of emails to customers of our application.
 *
 */
public class SendEmail {

    /**
     *
     * @param booking
     */
    public static void sendEmail(Booking booking) {

        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("to", Main.getCurrentUser().getEmail());
            parameters.put("date", booking.getMovieDate()+"");
            parameters.put("seat", booking.getSeat()+"");
            parameters.put("room", booking.getProjection().getRoomName());
            parameters.put("movie", booking.getMovieTitle());
            parameters.put("num", booking.getId()+"");
            
            URL url = new URL("http://repositories.joksolutions.com/mail/mail.php?"+getParamsString(parameters));
            URLConnection conn = url.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
            }
            br.close();
            
            /*System.out.println(getParamsString(parameters));
            con.setDoOutput(true);
            try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
                out.writeBytes(getParamsString(parameters));
                out.flush();
            }*/

        } catch (IOException ex) {
        }

    }

    /**
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
