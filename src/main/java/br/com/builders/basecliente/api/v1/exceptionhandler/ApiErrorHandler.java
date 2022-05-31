package br.com.builders.basecliente.api.v1.exceptionhandler;


import br.com.builders.basecliente.domain.exception.BusinessException;
import br.com.builders.basecliente.domain.exception.CodigoMensagem;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    private ReloadableResourceBundleMessageSource exceptionMessageBundle;

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        Throwable causa = ExceptionUtils.getRootCause(ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_001_ERRO_INESPERADO);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_004_RECURSO_INEXISTENTE);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_003_PARAMETRO_INVALIDO,
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }


        Error error = criarErroComCodigo(status, CodigoMensagem.FG_002_CORPO_REQUISICAO_INVALIDO);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        Error error = criarErroComCodigo(status, CodigoMensagem.FG_005_PROPRIEDADE_INEXISTENTE, path);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (CodigoMensagem.RN_004_CODIGO_NAO_ENCONTRADO.equals(ex.getCodigoMensagem())){
            status = HttpStatus.NOT_FOUND;
        }
        Error error = criarErroComCodigo(status, ex.getCodigoMensagem(), ex.getParametros());
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = criarErroComCodigo(status, CodigoMensagem.FG_001_ERRO_INESPERADO);
        } else if (body instanceof String) {
            body = criarErroComMensagem(status, (String) body);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Error criarErroComCodigo(HttpStatus status, CodigoMensagem codigoMensagem, Object...parametros) {
        String mensagem = exceptionMessageBundle.getMessage(
                codigoMensagem.getValue(), null, LocaleContextHolder.getLocale());
        String parametrosString = (!ObjectUtils.isEmpty(parametros)) ? String.format(mensagem, parametros) : mensagem;
        return this.criarErroComMensagem(status, parametrosString);
    }

    private Error criarErroComMensagem(HttpStatus status, String mensagem) {
        return Error.builder()
                .status(status.value())
                .message(mensagem)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
