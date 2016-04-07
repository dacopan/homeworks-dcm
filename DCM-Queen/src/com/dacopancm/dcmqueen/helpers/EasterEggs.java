package com.dacopancm.dcmqueen.helpers;

// (Crypto JCE)
import javax.crypto.Cipher;            // Proporciona encriptación y desencriptación
import javax.crypto.SecretKeyFactory;  // Representa una factoría de claves secretas

import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

// (Security JCA)
import java.security.spec.KeySpec;
import java.security.spec.AlgorithmParameterSpec;

// ARCHIVOS
// EXCEPCIONES
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Aquí va la magia, muestra la
 * WARNING Sal, de aquí, tu no deberías estar viendo esto.. esto es una ilusión,
 * no existe, esta clase java no esta....
 */
public class EasterEggs {

    Cipher ecipher;
    Cipher dcipher;

    public EasterEggs(String passPhrase) {
        byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03
        };
        int iterationCount = passPhrase.length();
        try {
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
        } catch (InvalidKeySpecException e) {
            System.out.println("EXCEPTION: InvalidKeySpecException");
        } catch (NoSuchPaddingException e) {
            System.out.println("EXCEPTION: NoSuchPaddingException");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("EXCEPTION: NoSuchAlgorithmException");
        } catch (InvalidKeyException e) {
            System.out.println("EXCEPTION: InvalidKeyException");
        }
    }

    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return new sun.misc.BASE64Encoder().encode(enc); // <- Se puede usar otra clase para conevrtir
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
        }
        return null;
    }

    public String getEgg(String str) {
        try {
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str); // <- Se puede usar otra clase para conevrtir
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");

        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
        }
        return null;
    }

}
