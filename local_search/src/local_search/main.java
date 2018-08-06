package local_search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;


public class main {
	public static StringBuilder sb;
	static String getString(String input, String data) // API���� �ʿ��� ���� �ڸ���.
    {
        String[] dataSplit = data.split("{" + input + "}");
        String[] dataSplit2 = dataSplit[1].split("\"" + input + "\"");
        return dataSplit2[0];
    }




	
	 public static void main(String[] args) {
		 String clientId = "bYDUDRCiO_Z_L6Cixbjr";
	        String clientSecret = "N8y5x5s3BQ";
	        int display = 100;
	        for(int n=1;n<=5;n++) {
	        int start=n;
	        try {
	            String text = URLEncoder.encode("서울특별시 pc방", "utf-8");
	            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + text + "&display=" + display + "&start="+start+"&";
	 
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if (responseCode == 200) { 
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else { 
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            sb = new StringBuilder();
	            String line;
	 
	            while ((line = br.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            br.close();
	            con.disconnect();
	            String data = sb.toString();
	            String[] array;
	            array = data.split("\"");
	            String[] title = new String[display];
	            String[] link = new String[display];
	            String[] category = new String[display];
	            String[] description = new String[display];
	            String[] telephone = new String[display];
	            String[] address = new String[display];
	            String[] mapx = new String[display];
	            String[] mapy = new String[display];
	            int k = 0;
	            for (int i = 0; i < array.length; i++) {
	                if (array[i].equals("title"))
	                    title[k] = array[i + 2];
	                if (array[i].equals("link"))
	                    link[k] = array[i + 2];
	                if (array[i].equals("category"))
	                    category[k] = array[i + 2];
	                if (array[i].equals("description"))
	                    description[k] = array[i + 2];
	                if (array[i].equals("telephone"))
	                    telephone[k] = array[i + 2];
	                if (array[i].equals("address"))
	                    address[k] = array[i + 2];
	                if (array[i].equals("mapx"))
	                    mapx[k] = array[i + 2];
	                if (array[i].equals("mapy")) {
	                    mapy[k] = array[i + 2];
	                    k++;
	                }
	            }
	            	            
	            String drivername="com.mysql.cj.jdbc.Driver";
	            Class.forName(drivername);
	            Connection conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/jtest","root","123456");
	            Statement stmt = conn.createStatement();
	            for (int i = 0; i < 100; i++) {
	            title[i]=title[i].replace("<b>"," ");
	            title[i]=title[i].replace("</b>"," ");
	            String sql="insert into pcinfo values('"+title[i]+"','"+telephone[i]+"','"+address[i]+"',"+mapx[i]+","+mapy[i]+");";
	            stmt.executeUpdate(sql);
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        }
	
		 
	 }
	 
	

}
