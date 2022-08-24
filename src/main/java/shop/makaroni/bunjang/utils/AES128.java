package shop.makaroni.bunjang.utils;

import shop.makaroni.bunjang.config.secret.Secret;
import shop.makaroni.bunjang.src.response.exception.DoesNotMatchPasswordEx;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static shop.makaroni.bunjang.src.response.ErrorCode.NOT_MATCH_PASSWORD_EXCEPTION;

public class AES128 {
    private final String ips;
    private final Key keySpec;

    public AES128(String key) {
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes(UTF_8);
        System.arraycopy(b, 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        this.ips = key.substring(0, 16);
        this.keySpec = keySpec;
    }

    public static String encode(String value) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(value);
    }

    public static String decode(String value) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(value);
    }

    public static void matchPassword(String password, @NotNull(message = "비밀번호를 입력해주세요.") @NotBlank(message = "비밀번호를 입력해주세요.") String givenPassword) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String decode = AES128.decode(password);
        if (!decode.equals(givenPassword)) {
            throw new DoesNotMatchPasswordEx(NOT_MATCH_PASSWORD_EXCEPTION.getMessages());
        }
    }

	public String encrypt(String value) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes()));
        byte[] encrypted = cipher.doFinal(value.getBytes(UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public String decrypt(String value) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes(UTF_8)));
        byte[] decrypted = Base64.getDecoder().decode(value.getBytes());
        return new String(cipher.doFinal(decrypted), UTF_8);
    }
}

