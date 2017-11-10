/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package birthdaymsg;

/**
 *
 * @author 00081830
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import wslibrary.Employee;
import wslibrary.EmpWS;

public class GoogleTextToSpeech {

    private static final String TEXT_TO_SPEECH_SERVICE =
            "http://translate.google.com/translate_tts";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) "
            + "Gecko/20100101 Firefox/11.0";

    /**
    This creates the output mp3 file based off the text contents and the language.
    @param destiantion - the requestesd name for the output mp3 file
    language - the language code of the text (en, es, zh, etc)
    snippets - a list of parameter strings that ar eeach less than or
    equal to 100 characters
     **/
    private static void makeAudio(String destination, String language,
            List<String> snippets) {
        try {
            //byte array that is 1 MB of the file
            byte[] buffer = new byte[1 << 20];
            //This is the output stream that will create the mp3 and store it in the directory
            //after the program runs. notice that there is no second parameter so it overwrites
            //the previous mp3 with the same name if it exists prior to this function call
            OutputStream os = new FileOutputStream(new File(destination + ".mp3"));
            //should append to the output file or not
            boolean shouldAppend = true;
            //input stream where you get the mp3 from
            //  System.setProperty("https.proxyHost", "mdproxy.ds.indianoil.in");
            //   System.setProperty("https.proxyPort", "8080");
            InputStream in = null;

            HttpURLConnection connection = null;
            for (String snippet : snippets) {
                String strUrl = TEXT_TO_SPEECH_SERVICE + "?"
                        + "tl=" + language + "&q=" + snippet;
                URL url = new URL(strUrl);
                // PROXY
                System.setProperty("http.proxyHost", "mdproxy.ds.indianoil.in");
                System.setProperty("http.proxyPort", "8080");
                //create a URLConnection with the language and snippet of text with spaces being '+'
                //  URLConnection connection = new URL("https://www.translate.google.com/translate_tts?tl=" + language + "&q=text").openConnection();
                connection = (HttpURLConnection) url.openConnection();
                //simulate a browser because Google doesn't let you get the mp3 without being able to identify it first
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
                //connect to the connection
                connection.connect();




                //get the data from the connection
                in = connection.getInputStream();
                int count;
                //while there is data from the input,
                while ((count = in.read(buffer)) != -1) {
                    //write to the output file
                    os.write(buffer, 0, count);
                    os.flush();
                }
                //close the input stream
                in.close();
                //set os to a fileoutputstream that will append to the destination.mp3 file
                //you only need to do this once since you reuse the same reference
                if (shouldAppend) {
                    os = new FileOutputStream(new File(destination + ".mp3"), true);
                    //no longer need to specify that os is FileOutputStream that appends to
                    //the file
                    shouldAppend = false;
                }
            }
            //close the input and output streams
            in.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    This takes in a string of text, converts it to URL parameter form by replacing all the spaces with '+', and separates them into an ArrayList of strings with each string in the ArrayList having a max of 100 characters including the '+'.
    @param text - A string that you want to be converted to a mp3 using Google's text to speech
     **/
    private static ArrayList<String> getParams(String text) {
        //replace all the spaces with '+'
        String paramText = text.replaceAll(" ", "+");
        //starting index of the current substring in relation to the string passed in
        int startIndex = 0;
        //list of strings that will contain strings that are <=100 characters
        ArrayList<String> result = new ArrayList<String>();
        //keep looping while the startIndex variable is not the last character in the string
        while (startIndex < text.length() - 1) {
            //get the end index of the substring, which is one plus the index of the
            //last character that is <=100 characters away from the start of the substring
            //that is not a '+' (index + 1 because the endIndex paramter of the
            //String.substring(int,int) is non-inclusive
            int endSnippetIndex = getSnippetEnd(paramText, startIndex);
            //Grab the snippet from the text parameter using the start and end index you found
            String snippet = paramText.substring(startIndex, endSnippetIndex);
            //ensure that the snippet of text has at least 2 characters
            //(sometimes there may be a '+' at the beginning of the substring)
            if (snippet.length() >= 2) {
                //if the first character is a '+', remove it
                if (snippet.substring(0, 1).equals("+")) {
                    snippet = snippet.substring(1);
                }
            }
            //add the snippet to the results array
            result.add(snippet);
            //set the startIndex to the endSnippet
            startIndex = endSnippetIndex;
        }
        //once the text has been converted into a list of <=100 character snippets, return the list
        return result;
    }

    private static int getSnippetEnd(String text, int startIndex) {
        //set the substring; if there is still 100 characters left in the origial text starting from the
        //startIndex, set the substring to be 100 cahracters. otherwise, just get the rest of the string
        //that is left starting at the startIndex
        String subtext = (text.substring(startIndex).length() > 100)
                ? text.substring(startIndex, startIndex + 100)
                : text.substring(startIndex);
        //the end index initially set as the length of the substring (you dont subtract by 1 because this is used
        //as the 2nd parameter of the String.substring(int,int) method used in getParams(String) to create the
        //substring to add to the list
        int end = subtext.length();
        //if there is <=100 characters in the substring, you are done. Otherise, you have to make sure that you
        //aren't in the middle of the word. IF you are, then decrement the 'end' index until you reach a '+' sigh.
        //Then simply remove the '+' sign, and you will not have any problems with word being cut off in the middle
        //whlie parsing
        if (text.substring(startIndex).length() > 100) {
            //while the last character is not a + sign, remove the last character of the substring
            while (!subtext.substring(subtext.length() - 1, subtext.length()).equals("+")) {
                subtext = subtext.substring(0, subtext.length() - 1);
                end--;
            }
            // get rid of the last plus sign
            subtext = subtext.substring(0, subtext.length() - 1);
            end--;
        }
        //return the end index of the substtring and offset it by the starting index
        return end + startIndex;
    }

    public static void textToSpeech(String text, String language, String outputName) {
        //convert the string to have '+' where there are spaces and create 100 charcter
        //or less snippets based off the string
        ArrayList<String> paramSnippets = getParams(text);
        //print out the snippets
        for (String snippet : paramSnippets) {
            System.out.println(snippet);
            System.out.println("------------------------------------------");
        }
        //create the mp3 file that will have an audio version of the text
        makeAudio(outputName, language, paramSnippets);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Employee emp = new Employee();
            EmpWS ews = new EmpWS();
            emp = ews.getCEmpData("10101986", "emp_status in('Active','Withdrawn')"//and emp_code in (57385, 93839)
                    + " and to_char(DOB,'dd-mm')=to_char(sysdate,'dd-mm') "
                      + " and loc_code=4200"
                    + " and current_divn in('Marketing','R&D Centre','Corporate Office','IBP','AOD')");


            int iu = 0, ii = 0;
            int tv = emp.valid;
            System.out.println("valid=" + emp.valid);
            String emp_name = "";
            int no_of_emp = 0;
            String text = "We wish happy birthday to ";
            if (emp.data.length > 1) {
                text += " the following employees ";
            }
            if (emp.valid == 1) {

                for (int i = 0; i < emp.data.length; i++) {
                    int fld = 0;
                    String sub_grp_code = "";
                    emp_name = emp.data[i][3];
                    emp_name += " " + emp.data[i][1];
                    emp_name += " of " + emp.data[i][8];
                     text +=emp_name+" ";
                    no_of_emp++;
                }
            }





            if (no_of_emp < 1) {
                text = "None of our employees are celebrating birthday today  ";
            } else {
                text += emp_name;//+"a many many happy returns of the day  ";
            }
            Date sysDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = sdf.format(sysDate);
            String fileName = "D:/rony/bday-" + dateString + ".mp3";
            textToSpeech(text, "en", fileName);

        } catch (Exception ee1) {
            System.out.println(ee1);
        }
        // String text = "This is a test for google translate text to speech functionality. As you can see, this is over the 100 character limit, but it is no problem if this text is separated into 100 character or less snippets. Also, running this program again with the same destination file name but different text will overwrite the data inside of this mp3 file. I hope you enjoyed this tutorial!";

    }
}
