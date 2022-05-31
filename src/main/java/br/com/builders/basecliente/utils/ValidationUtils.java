package br.com.builders.basecliente.utils;

import org.springframework.util.ObjectUtils;

public abstract class ValidationUtils {

    private ValidationUtils() {

    }

    public static String removerMascaraCPF(String documento) {
        if (documento == null)
            return documento;
        return documento.replaceAll("([.])*(-)*(/)*", "");
    }

    public static String extrairRaizCNPJ(String cnpj) {
        if (!ObjectUtils.isEmpty(cnpj) && cnpj.length() >= 8){
            return cnpj.substring(0,8);
        }
        return cnpj;
    }

    public static boolean isSomenteNumeros(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



}
