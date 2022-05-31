package br.com.builders.basecliente.utils;

import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

public class ValidateCPF implements ValidadorDocumento{

    @Override
    public boolean isValid(String vCpf) {

        if (ObjectUtils.isEmpty(vCpf)) {
            return false;
        }

        vCpf = ValidationUtils.removerMascaraCPF(vCpf);
        try {
            if (Long.valueOf(vCpf).longValue() <= 0){
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        vCpf = "00000000000" + vCpf;
        vCpf = vCpf.substring(vCpf.length() - 11, vCpf.length());
        if (Pattern.matches("[A-Za-z]", vCpf)) {
            return false;
        }

        int x = 0;
        int soma = 0;
        int dig1 = 0;
        int dig2 = 0;
        String numcpf1;
        // String texto = "";
        int len = vCpf.length();
        x = len - 1;

        for (int i = 0; i <= len - 3; i++) {
            int y = Integer.valueOf(vCpf.substring(i, i + 1));
            soma += (y * x);
            x--;
            // texto += y;
        }

        dig1 = 11 - (soma % 11);
        if (dig1 == 10) {
            dig1 = 0;
        }
        if (dig1 == 11) {
            dig1 = 0;
        }
        numcpf1 = vCpf.substring(0, len - 2).concat(dig1 + "");
        x = 11;
        soma = 0;
        for (int i = 0; i <= len - 2; i++) {
            soma += (Integer.valueOf(numcpf1.substring(i, i + 1)) * x);
            x--;
        }

        dig2 = 11 - (soma % 11);
        if (dig2 == 10) {
            dig2 = 0;
        }
        if (dig2 == 11) {
            dig2 = 0;
        }

        if (!(dig1 + "" + dig2).equals(vCpf.substring(len - 2, len))) {
            return false;
        }

        return true;
    }
}