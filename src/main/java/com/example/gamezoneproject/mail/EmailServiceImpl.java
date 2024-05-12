package com.example.gamezoneproject.mail;

import com.example.gamezoneproject.domain.user.User;
import com.example.gamezoneproject.domain.user.UserDtoMapper;
import com.example.gamezoneproject.domain.user.dto.UserTokenDto;
import com.example.gamezoneproject.domain.userToken.TemporaryTokenService;
import com.example.gamezoneproject.domain.userToken.TemporaryTokensStrategy.TemporaryTokenStrategy;
import com.example.gamezoneproject.domain.userToken.dto.TemporaryTokenDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String email;
    private final JavaMailSender javaMailSender;
    private final ThymeleafService thymeleafService;
    private final TemporaryTokenService temporaryTokenService;
    public static final int MAIL_SEND_COOLDOWN_MINUTES = 5;

    public EmailServiceImpl(JavaMailSender javaMailSender, ThymeleafService thymeleafService,
                            TemporaryTokenService temporaryTokenService) {
        this.javaMailSender = javaMailSender;
        this.thymeleafService = thymeleafService;
        this.temporaryTokenService = temporaryTokenService;
    }

    /**
     * Method is builds all email fields , message, title, from, to and send it.
     *
     * @param to           email of user to email be sent.
     * @param tokenPurpose token purpose.
     * @param args         list of args to map them in thymeleaf email template. (username,email purpose)
     */
    private void sendMail(String to, TemporaryTokenStrategy tokenPurpose, Map<String, Object> args) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            mimeMessageHelper.setFrom(email, "GameZone");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(tokenPurpose.getDescription());
            mimeMessageHelper.setText(thymeleafService.createContent(tokenPurpose.getEmailTemplate(), args), true);
            mimeMessageHelper.addInline("logo", new ClassPathResource("static/img/emailLogo.png"));

            Thread thread = new Thread(() -> javaMailSender.send(mimeMessage));
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method  builds email. Maps user to dto and put all arguments needed for thymeleaf email template to map.
     *
     * @param user        user to whom the email is to be sent.
     * @param url         url of the site to build link in mail.
     * @param linkPurpose token purpose.
     */
    @Override
    public void buildEmail(User user, String url, TemporaryTokenStrategy linkPurpose) {
        UserTokenDto userTokenDto = UserDtoMapper.mapUserTokenDto(user);

        Map<String, Object> variables = new HashMap<>();
        variables.put("user_name", userTokenDto.getUserName());
        variables.put("link_date", getLinkGeneratedDate(userTokenDto));
        variables.put("link_life_time", linkPurpose.getTokenLifeTimeMinutes());
        variables.put("verify_link", buildVerifyLink(userTokenDto, url, linkPurpose.getUrlParam()));
        variables.put("logo", "logo");
        sendMail(userTokenDto.getEmail(), linkPurpose, variables);

    }

    /**
     * Finds token by userTokenDto and formats the date of the last token sent
     *
     * @param userTokenDto userTokenDto to get last token sent.
     * @return String with formated date.
     */
    private String getLinkGeneratedDate(UserTokenDto userTokenDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        return temporaryTokenService
                .findByName(userTokenDto.getToken())
                .map(TemporaryTokenDto::getLastTokenSend)
                .map(date -> date.format(formatter))
                .orElse(LocalDateTime.now().format(formatter));
    }

    /**
     * Method builds verify link for user.
     * @param userTokenDto user token dto object for id and token value.
     * @param url base url link to build.
     * @param linkPurpose link purpose
     * @return String with verification link.
     */
    @Override
    public String buildVerifyLink(UserTokenDto userTokenDto, String url, String linkPurpose) {
        return url + "/weryfikacja/" + linkPurpose + "/" + userTokenDto.getId() + "?token=" + userTokenDto.getToken();
    }

}
