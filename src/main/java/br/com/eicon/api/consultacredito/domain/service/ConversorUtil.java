package br.com.eicon.api.consultacredito.domain.service;

import org.springframework.beans.BeanUtils;

public class ConversorUtil {

    /**
     * Converte o objeto source para uma instância do targetClass, copiando os atributos com o mesmo nome.
     *
     * @param source Objeto de origem.
     * @param targetClass Classe do objeto destino.
     * @param <S> Tipo do objeto de origem.
     * @param <T> Tipo do objeto destino.
     * @return Uma nova instância de T com as propriedades copiadas de source.
     */
    public static <S, T> T converter(S source, Class<T> targetClass) {
        T target = BeanUtils.instantiateClass(targetClass);
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
