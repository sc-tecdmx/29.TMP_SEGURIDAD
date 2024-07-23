package mx.gob.tecdmx.seguridad.contrasenia;

import org.springframework.stereotype.Service;

@Service
public class ServiceRessetPassword {
	
//	@Value("${spring.mail.username}")
//    private String senderEmail;
//	
//	@Value("${spring.url.email}")
//    private String link;
	
//    @Autowired
//    private JavaMailSender emailSender;

    public void sendPasswordResetEmail(String destino, String token) {
//        String resetUrl = link + token;

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(senderEmail);
//        message.setTo(destino);
//        message.setSubject("Restablecimiento de Contraseña");
//        message.setText("Para restablecer tu contraseña, por favor haz clic en el siguiente enlace: " + resetUrl);
//
//        emailSender.send(message);
    }
}