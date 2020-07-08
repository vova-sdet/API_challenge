package Utils;

public class PayloadUtils {

    public static String getJiraAuthorizationPayload () {
        return "{\n" +
                "    \"username\": \"" + ConfigReader.getProperties("username") + "\",\n" +
                "    \"password\": \"" + ConfigReader.getProperties("password") + "\"\n" +
                "}";
    }

    public static String getJiraCreateIssuePayload(String summary, String description, String issueType) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"TEC\"\n" +
                "        },\n" +
                "        \"summary\": \"" + summary + "\",\n" +
                "        \"description\": \"" + description +"\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"" + issueType + "\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
