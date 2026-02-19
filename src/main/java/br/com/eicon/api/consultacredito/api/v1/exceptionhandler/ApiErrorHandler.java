package br.com.eicon.api.consultacredito.api.v1.exceptionhandler;

import br.com.eicon.api.consultacredito.domain.exception.BusinessException;
import br.com.eicon.api.consultacredito.domain.exception.CodigoMensagem;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = resolveBusinessStatus(ex);
        Error error = criarErroComCodigo(status, ex.getCodigoMensagem(), ex.getParametros());
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_001_ERRO_INESPERADO);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_004_RECURSO_INEXISTENTE);
        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException mex) {
            Error error = criarErroComCodigo(status, CodigoMensagem.FG_003_PARAMETRO_INVALIDO,
                    mex.getName(), mex.getValue(), mex.getRequiredType().getSimpleName());
            return handleExceptionInternal(ex, error, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof PropertyBindingException pex) {
            String path = joinPath(pex.getPath());
            Error error = criarErroComCodigo(status, CodigoMensagem.FG_005_PROPRIEDADE_INEXISTENTE, path);
            return handleExceptionInternal(ex, error, headers, status, request);
        }
        Error error = criarErroComCodigo(status, CodigoMensagem.FG_002_CORPO_REQUISICAO_INVALIDO);
        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = criarErroComCodigo(status, CodigoMensagem.FG_001_ERRO_INESPERADO);
        } else if (body instanceof String msg) {
            body = criarErroComMensagem(status, msg);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private HttpStatus resolveBusinessStatus(BusinessException ex) {
        return switch (ex.getCodigoMensagem()) {
            case RN_CREDITO_NAO_ENCONTRADO, RN_API_KEY_NAO_ENCONTRADA -> HttpStatus.NOT_FOUND;
            case RN_API_KEY_JA_REVOGADA -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    private Error criarErroComCodigo(HttpStatusCode status, CodigoMensagem codigoMensagem, Object... parametros) {
        String mensagem = exceptionMessageBundle.getMessage(
                codigoMensagem.getValue(), null, LocaleContextHolder.getLocale());
        String parametrosString = (!ObjectUtils.isEmpty(parametros)) ? String.format(mensagem, parametros) : mensagem;
        return criarErroComMensagem(status, parametrosString);
    }

    private Error criarErroComMensagem(HttpStatusCode status, String mensagem) {
        return Error.builder()
                .status(status.value())
                .message(mensagem)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
