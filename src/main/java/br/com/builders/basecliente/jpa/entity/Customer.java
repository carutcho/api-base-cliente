package br.com.builders.basecliente.jpa.entity;


import br.com.builders.basecliente.utils.ValidationUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Transient
    private Integer age;

    public Integer getAge(){
        LocalDateTime today = LocalDateTime.now();
        if (today.getMonth().getValue() <= birthDate.getMonth().getValue()
                && today.getDayOfMonth() <= birthDate.getDayOfMonth()){

            return today.getYear() - birthDate.getYear();
        }else{
            return (today.getYear() - birthDate.getYear()) - 1;
        }
    }


    public String getCpf(){
        return ValidationUtils.removerMascaraCPF(this.cpf);
    }

    public void setCpf (String cpf){
        this.cpf = ValidationUtils.removerMascaraCPF(cpf);
    }

}
