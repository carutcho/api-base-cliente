package br.com.builders.basecliente.services;

import br.com.builders.basecliente.domain.exception.BusinessException;
import br.com.builders.basecliente.domain.exception.CodigoMensagem;
import br.com.builders.basecliente.domain.service.CustomerService;
import br.com.builders.basecliente.domain.service.impl.CustomerServiceImpl;
import br.com.builders.basecliente.jpa.entity.Customer;
import br.com.builders.basecliente.jpa.repository.CustomerRepository;
import br.com.builders.basecliente.services.config.GeradorCustomerFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CustomerServiceTest {




    private GeradorCustomerFake geradorCustomerFake;
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepositoryMock;




    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepositoryMock);
        geradorCustomerFake = new GeradorCustomerFake();
    }

    @Test
    public void validarBuscaCpfComCpfInvalidoCalculo(){
        try{
            customerService.findByCpf(GeradorCustomerFake.CPF_INVALIDO_CALCULO);
        }catch (BusinessException e){
            Assertions.assertEquals (e.getCodigoMensagem(), CodigoMensagem.RN_002_DOCUMENTO_INVALIDO);
        }
    }

    @Test
    public void validarBuscaCpfComCpfInvalidoMenosCaracteres(){
        try{
            customerService.findByCpf(GeradorCustomerFake.CPF_INVALIDO_MENOS_CARACTERES);
        }catch (BusinessException e){
            Assertions.assertEquals (e.getCodigoMensagem(), CodigoMensagem.RN_002_DOCUMENTO_INVALIDO);
        }
    }

    @Test
    public void validarBuscaCpfComCpfVazio(){
        try{
            customerService.findByCpf("");
        }catch (BusinessException e){
            Assertions.assertEquals (e.getCodigoMensagem(), CodigoMensagem.RN_002_DOCUMENTO_INVALIDO);
        }
    }

    @Test
    public void validarBuscaCpfComCpfNulo(){
        try{
            customerService.findByCpf(null);
        }catch (BusinessException e){
            Assertions.assertEquals (e.getCodigoMensagem(), CodigoMensagem.RN_002_DOCUMENTO_INVALIDO);
        }
    }


    @Test
    public void validarBuscaCpfComCpfValidoMascara(){
        Mockito.when(customerRepositoryMock.findByCpf(GeradorCustomerFake.CPF_VALIDO_MASCARA))
                .thenReturn(Optional.ofNullable(geradorCustomerFake.getValidCustomer()));
        Customer customer = customerService.findByCpf(GeradorCustomerFake.CPF_VALIDO_MASCARA);
        Assertions.assertEquals (customer.getCpf(), GeradorCustomerFake.CPF_VALIDO_SEM_MASCARA);
    }

    @Test
    public void validarBuscaCpfComCpfValidoSemMascara(){
        Mockito.when(customerRepositoryMock.findByCpf(GeradorCustomerFake.CPF_VALIDO_SEM_MASCARA))
                .thenReturn(Optional.ofNullable(geradorCustomerFake.getValidCustomer()));
        Customer customer = customerService.findByCpf(GeradorCustomerFake.CPF_VALIDO_SEM_MASCARA);
        Assertions.assertEquals (customer.getCpf(), GeradorCustomerFake.CPF_VALIDO_SEM_MASCARA);
    }

    @Test
    public void validarIncuirCadastroSucesso(){
        Mockito.when(customerRepositoryMock.save(Mockito.any()))
                .thenReturn(geradorCustomerFake.getValidCustomer());
        Customer customer = customerService.create(geradorCustomerFake.getValidCustomer());
        Assertions.assertEquals (customer.getCpf(), GeradorCustomerFake.CPF_VALIDO_SEM_MASCARA);
    }

    @Test
    public void validarIncuirCadastroFalha(){
        Mockito.when(customerRepositoryMock.save(Mockito.any()))
                .thenReturn(geradorCustomerFake.getValidCustomer());
        try{
            Customer customerSemDataNasc = geradorCustomerFake.getValidCustomer();
            customerSemDataNasc.setBirthDate(null);
            Customer customer = customerService.create(customerSemDataNasc);
        }catch (BusinessException e) {
            Assertions.assertEquals(e.getCodigoMensagem(), CodigoMensagem.RN_CUSTOMER_002_BIRTH_DATE_VAZIO);
        }
    }

    //TODO: Fazer mais casos de testes abrangendo todos os metodos do servico e variação de testes
    //A quantidade baixa de teste é apenas para mostrar conhecimento sobre o tema


}
