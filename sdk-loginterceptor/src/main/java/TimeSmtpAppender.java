import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.sportradar.sdk.common.classes.FileSdkLogger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class TimeSmtpAppender extends AppenderBase<ILoggingEvent> {

    private String from;
    private List<InternetAddress> to;
    private String subject;
    private String smtpHost;
    private int smtpPort;

    private String username;
    private String password;

    private String charsetEncoding = "UTF-8";

    private final List<ILoggingEvent> events;
    private Timer timer;
    private long delay = 60000;

    public TimeSmtpAppender() {
        timer = new Timer();
        events = new ArrayList<>();
        to = new ArrayList<>();
    }

    /**
     * Add error/info log to list to be send later after period delay expire
     *
     * @param event Event that contains error/info log information
     */
    @Override
    protected void append(ILoggingEvent event) {
        if (!started)
            return;

        synchronized (events) {
            if (started) {
                events.add(event);
            }
        }
    }

    /**
     * Send messages if list is not empty or null.
     *
     * @param list list of messages to be send to email
     */
    private void sendMessages(List<ILoggingEvent> list) {

        if (!started || list == null || list.isEmpty())
            return;

        String text = "";

        for (ILoggingEvent event : list) {
            text += "\n" + doLayout(event);
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            InternetAddress[] addresses = new InternetAddress[to.size()];
            addresses = to.toArray(addresses);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create String from event
     *
     * @param event Event that contains error/info log information
     * @return
     */
    public String doLayout(ILoggingEvent event) {
        PatternLayout layout = new PatternLayout();
        layout.setContext(context);
        layout.setPattern(FileSdkLogger.LOG_PATTERN);
        layout.start();
        String result = layout.doLayout(event);
        layout.stop();
        return result;
    }

    /**
     * Get from address (gmail use username if this email is no confirmed)
     *
     * @return from address
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set from address (gmail use username if this email is no confirmed)
     *
     * @param from from address
     */
    public void setFrom(String from) {
        this.from = from;
    }

    public List<InternetAddress> getTo() {
        return to;
    }

    /**
     * @param address
     * @param personal
     * @return Return true if address added ok
     */
    public boolean addTo(String address, String personal) {
        try {
            this.to.add(new InternetAddress(address, personal));
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get subject of mail
     *
     * @return mail subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set subject of mail
     *
     * @param subject mail subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get smtp host for TLS protocol
     *
     * @return host fot TLS protocol
     */
    public String getSmtpHost() {
        return smtpHost;
    }

    /**
     * Set smtp host for TLS protocol
     *
     * @param smtpHost host fot TLS protocol
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * Get smtp port for TLS protocol
     *
     * @return port fot TLS protocol
     */
    public int getSmtpPort() {
        return smtpPort;
    }

    /**
     * Set smtp port for TLS protocol
     *
     * @param smtpPort port fot TLS protocol
     */
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * Get username to connect to mail client mail.domain.com
     *
     * @return Mail client password
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username to connect to mail client mail.domain.com
     *
     * @param username Mail client username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password to connect to mail client mail.domain.com
     *
     * @return Mail client password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password to connect to mail client mail.domain.com
     *
     * @param password Mail client password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get charset fo mail encoding
     *
     * @return Charset encoding like 'UTF-8' by default
     */
    public String getCharsetEncoding() {
        return charsetEncoding;
    }

    /**
     * Set charset fo mail encoding
     *
     * @param charsetEncoding Charset encoding like 'UTF-8' by default
     */
    public void setCharsetEncoding(String charsetEncoding) {
        this.charsetEncoding = charsetEncoding;
    }

    /**
     * Get min fixed delay between mails
     *
     * @return delay in [ms]
     */
    public long getDelay() {
        return delay;
    }

    /**
     * Set min fixed delay between mails
     *
     * @param delay Min delay between two emails in [ms]
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     * Start scheduling email sending
     */
    @Override
    public void start() {
        super.start();
        timer.scheduleAtFixedRate(new SendAllEvents(), 0, delay/*60000*/);
    }


    /**
     * Stop scheduling email sending
     */
    @Override
    public void stop() {
        super.stop();
        timer.cancel();
    }

    private class SendAllEvents extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            synchronized (events) {
                if (events.size() > 0) {
                    sendMessages(events);
                    events.clear();
                }
            }
        }
    }
}
