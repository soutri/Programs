
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import wslibrary.Employee;
import wslibrary.EmpWS;
import org.apache.commons.codec.binary.Base64;

public class SimpleTextToSpeech {

    private static final String TEXT_TO_SPEECH_SERVICE =
            "http://translate.google.com/translate_tts";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) "
            + "Gecko/20100101 Firefox/11.0";

    public static void main(String[] args) throws Exception {
        try {
            Employee emp = new Employee();
            EmpWS ews = new EmpWS();
            emp = ews.getCEmpData("10101986", "emp_status in('Active','Withdrawn')"//and emp_code in (57385, 93839)
                    // " and to_char(DOB,'dd-mm')=to_char(sysdate,'dd-mm') "
                  //  + " and loc_code=4200"
                    + " and current_divn in('Marketing','R&D Centre','Corporate Office','IBP','AOD')");

            int iu = 0, ii = 0;
            int tv = emp.valid;
            System.out.println("valid=" + emp.valid);
            String emp_name = "";
            int no_of_emp = 0;
            if (emp.valid == 1) {

                for (int i = 0; i < emp.data.length; i++) {
                    int fld = 0;
                    String sub_grp_code = "";

                    emp_name = emp.data[i][3];
                    emp_name += " " + emp.data[i][1];
                    emp_name += "of " + emp.data[i][8];
                    no_of_emp++;
                }
            }

            Language language = Language.valueOf("EN");
            String text = "We wish happy birthday to ";

            if (no_of_emp > 1) {
                text += " the following employees ";
            }
            if(no_of_emp < 1) {
            text = "None of our employees are celebrating birthday today  ";}else{
            text += emp_name ;//+"a many many happy returns of the day  ";
            }


            text = URLEncoder.encode(text, "utf-8");
            new SimpleTextToSpeech().go(language, text);
        } catch (Exception ee1) {
            System.out.println(ee1);
        }
    }

    public void go(Language language, String text) throws Exception {
        // Create url based on input params
        String strUrl = TEXT_TO_SPEECH_SERVICE + "?"
                + "tl=" + language + "&q=" + text;
        URL url = new URL(strUrl);
        HttpURLConnection connection = null;
        try {
            DataInputStream di = null;
            FileOutputStream fo = null;
            byte[] b = new byte[1];

            // PROXY
            System.setProperty("http.proxyHost", "mdproxy.ds.indianoil.in");
            System.setProperty("http.proxyPort", "8080");

            // URL u = new URL(URLName);
            //  HttpURLConnection con = (HttpURLConnection) u.openConnection();
            connection = (HttpURLConnection) url.openConnection();
            //
            // it's not the greatest idea to use a sun.misc.* class
            // Sun strongly advises not to use them since they can
            // change or go away in a future release so beware.
            //
            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            String encodedUserPwd =
                    encoder.encode("ioc\00081830:rony123".getBytes());
            connection.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
            // Etablish connection


            // Get method
            connection.setRequestMethod("GET");
            // Set User-Agent to "mimic" the behavior of a web browser. In this
            // example, I used my browser's info

            connection.addRequestProperty("User-Agent", USER_AGENT);
            connection.connect();

            // Get content
            BufferedInputStream bufIn =
                    new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1024];
            int n;
            ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
            while ((n = bufIn.read(buffer)) > 0) {
                bufOut.write(buffer, 0, n);
            }

            // Done, save data
            Date sysDate = new Date();
           // String dateString = sysDate.toString();
             SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(sysDate);
        String fileName="D:/rony/bday-"+dateString+".mp3";
            //sdf.format(sysDate)
            File output = new File(fileName);
            BufferedOutputStream out =
                    new BufferedOutputStream(new FileOutputStream(output));
            out.write(bufOut.toByteArray());
            out.flush();
            out.close();
            System.out.println("Done");
            // PROXY ----------


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public enum Language {

        FR("french"),
        EN("english");
        private final String language;

        private Language(String language) {
            this.language = language;
        }

        public String getFullName() {
            return language;
        }
    }
}
