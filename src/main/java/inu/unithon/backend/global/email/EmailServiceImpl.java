package inu.unithon.backend.global.email;

import inu.unithon.backend.global.email.dto.FestivalEmailDto;
import inu.unithon.backend.global.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static inu.unithon.backend.global.exception.CommonErrorCode.EMAIL_SEND_FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;
  private final String FROM_ADDRESS = "your@email.com"; // TODO: 실제 주소로 변경

  @Override
  public void sendFestivalStartEmail(FestivalEmailDto dto) {
    String html = buildStartFestivalTemplate(dto);
    sendEmail(dto.getEmail(), dto.getSubject(), html);
  }

  @Override
  public void sendFestivalEndEmail(FestivalEmailDto dto) {
    String html = buildEndFestivalTemplate(dto);
    sendEmail(dto.getEmail(), dto.getSubject(), html);
  }

  private String buildStartFestivalTemplate(FestivalEmailDto dto) {
    Context context = new Context();
    context.setVariable("festivalName", dto.getFestivalName());
    context.setVariable("festivalStart", dto.getFestivalStart());
    context.setVariable("festivalEnd", dto.getFestivalEnd());
    return templateEngine.process("email/kr/FestivalStartEmailKr", context);
  }

  private String buildEndFestivalTemplate(FestivalEmailDto dto) {
    Context context = new Context();
    context.setVariable("festivalName", dto.getFestivalName());
    context.setVariable("festivalStart", dto.getFestivalStart());
    context.setVariable("festivalEnd", dto.getFestivalEnd());
    return templateEngine.process("email/kr/FestivalEndEmailKr", context);
  }

  private void sendEmail(String to, String subject, String htmlContent) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(to);
      helper.setFrom(FROM_ADDRESS);
      helper.setSubject(subject);
      helper.setText(htmlContent, true); // true = HTML

      emailSender.send(message);
    } catch (MessagingException e) {
      log.error("메일 전송 실패: {}", e.getMessage());
      throw new CustomException(EMAIL_SEND_FAIL);
    }
  }

}

