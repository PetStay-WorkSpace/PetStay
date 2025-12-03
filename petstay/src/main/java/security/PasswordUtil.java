package security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String criptografar(String senhaTexto) {
        if (senhaTexto == null) return null;
        return BCrypt.hashpw(senhaTexto, BCrypt.gensalt(12));
    }

    public static boolean verificarSenha(String senhaTexto, String senhaHash) {
        if (senhaTexto == null || senhaHash == null) return false;
        try {
            return BCrypt.checkpw(senhaTexto, senhaHash);
        } catch (Exception e) {
            return false;
        }
    }

    public static String criptografarComEmail(String email, String senhaTexto) {
        if (email == null || senhaTexto == null) return null;
        String sal = gerarSalDoEmail(email, 12);
        return BCrypt.hashpw(senhaTexto, sal);
    }

    private static String gerarSalDoEmail(String email, int custo) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(email.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            String base64 = java.util.Base64.getEncoder().encodeToString(digest);
            
            String normalizado = base64.replace('+', '.').replace('/', '.').replace('=', '.');
            String corpoSal = normalizado.length() >= 22 ? normalizado.substring(0, 22) : String.format("%-22s", normalizado).replace(' ', '.');
            String custStr = String.format("%02d", Math.max(4, Math.min(31, custo)));
            return "$2a$" + custStr + "$" + corpoSal;
        } catch (Exception e) {
            // Fallback para sal aleatório em caso de erro
            return BCrypt.gensalt(custo);
        }
    }
}